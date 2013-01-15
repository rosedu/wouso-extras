package cdl.android.ui.bazaar;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import cdl.android.general.BazaarItem;
import cdl.android.server.ApiHandler;
import cdl.android.server.ServerResponse;
import cdl.android.R;

/**
 * Bazaar List Item View
 */
public class BazaarItemView extends LinearLayout {
	private BazaarItem mItem;

	public BazaarItemView(Context context, BazaarItem item) {
		super(context);
		mItem = item;

		LayoutInflater layoutInflater = (LayoutInflater) getContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		layoutInflater.inflate(R.layout.bazaar_list_item, this, true);

		TextView title = (TextView) findViewById(R.id.title);
		title.setText(item.getTitle());

		TextView price = (TextView) findViewById(R.id.price);
		price.setText(item.getPrice());

		TextView due = (TextView) findViewById(R.id.days);
		due.setText(item.getDueDays());

		Button buy = (Button) findViewById(R.id.buyButton);
		buy.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
				nameValuePairs.add(new BasicNameValuePair("spell", mItem
						.getId()));
				ServerResponse resp = ApiHandler.sendPost(
						ApiHandler.bazaarBuyURL, nameValuePairs, v.getContext());

				if (resp.getStatus() == false) {
					Toast.makeText(v.getContext(), resp.getError(),
							Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(v.getContext(),
							"Bought " + mItem.getTitle(), Toast.LENGTH_SHORT).show();
				}
			}
		});

	}

}
