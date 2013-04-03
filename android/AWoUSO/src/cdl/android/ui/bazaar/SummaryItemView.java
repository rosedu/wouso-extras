package cdl.android.ui.bazaar;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import cdl.android.R;
import cdl.android.general.BazaarItem;
import cdl.android.general.ServerResponse;
import cdl.android.general.SummaryItem;
import cdl.android.server.ApiHandler;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class SummaryItemView extends LinearLayout{
	private SummaryItem mItem;

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

	}
}
