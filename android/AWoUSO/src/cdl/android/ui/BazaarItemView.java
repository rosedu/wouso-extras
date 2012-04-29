package cdl.android.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;
import cdl.android.model.BazaarItem;
import cdl.android.R;

public class BazaarItemView extends LinearLayout {
	BazaarItem mItem;
	Context mContext;
	
	public BazaarItemView(Context context, BazaarItem item) {
		super(context);
		mContext = context;
		mItem = item;
		LayoutInflater layoutInflater = (LayoutInflater) 
		getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		layoutInflater.inflate(R.layout.bazaar_list_item, this, true);
		
		TextView title = (TextView) findViewById(R.id.title);
		title.setText(item.getTitle());
		
		TextView desc = (TextView) findViewById(R.id.description);
		desc.setText(item.getDescription());

		TextView price = (TextView) findViewById(R.id.price);
		price.setText(item.getPrice());
		
		TextView due = (TextView) findViewById(R.id.days);
		due.setText(item.getDue_days());
		
	}

}
