package cdl.android.ui.bazaar;

import java.util.ArrayList;

import cdl.android.general.BazaarItem;
import cdl.android.general.SummaryItem;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;

public class SummaryAdapter extends BaseAdapter{
	private ArrayList<SummaryItem> mItems;
	private OnClickListener mOnItemClick;
	private Context mContext;
	
	public SummaryAdapter(Context context, ArrayList<SummaryItem> items, OnClickListener onItemClick) {
		mItems = new ArrayList<SummaryItem>();
		mOnItemClick = onItemClick;
		mContext = context;
		mItems = (ArrayList<SummaryItem>) items.clone();
	}
	
	public int getCount() {
		return mItems.size();
	}

	public Object getItem(int index) {
		return mItems.get(index);
	}

	@Override
	public long getItemId(int position) {
			return position;
	}

	@Override
	public View getView(int index, View convertView, ViewGroup parent) {
		SummaryItemView item;

		item = new SummaryItemView(mContext, mItems.get(index));
		item.setOnClickListener(mOnItemClick);
		item.setClickable(true);

		return item;
	}

}
