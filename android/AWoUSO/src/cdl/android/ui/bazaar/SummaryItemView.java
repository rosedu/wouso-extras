package cdl.android.ui.bazaar;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import cdl.android.R;
import cdl.android.general.SummaryItem;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SummaryItemView extends LinearLayout{
	private SummaryItem mItem;
	static final String WOUSO = "https://wouso.cs.pub.ro/2013";
	
	public SummaryItemView(Context context, SummaryItem item) {
		super(context);
		mItem = item;

		LayoutInflater layoutInflater = (LayoutInflater) getContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		layoutInflater.inflate(R.layout.summary_list_item, this, true);
	
		TextView title = (TextView) findViewById(R.id.title);
		title.setText(item.getSpellTitle());

		TextView amount = (TextView) findViewById(R.id.amount);
		amount.setText(item.getAmount());

		
		ImageView image = (ImageView) findViewById(R.id.image);
		
		this.setSpellImg(image, WOUSO + item.getImageURL());
	}
	
	public SummaryItem getItem(){
		return mItem;
	}
	
	private void setSpellImg(ImageView img, String url) {

		Bitmap b = BitmapFactory.decodeResource(img.getContext()
				.getResources(), R.drawable.empty);
		img.setImageBitmap(b);
		try {
			HttpURLConnection con = (HttpURLConnection) (new URL(url))
					.openConnection();
			con.connect();
			b = BitmapFactory.decodeStream(con.getInputStream());
			img.setImageBitmap(b);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
	}
}
