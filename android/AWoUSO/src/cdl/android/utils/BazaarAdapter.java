package cdl.android.utils;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import cdl.android.model.BazaarItem;
import cdl.android.ui.BazaarItemView;

/**
 *  Adapter class for the bazaar's content 
 *  Manages the items in the list
 */
public class BazaarAdapter extends BaseAdapter {

	/** Array filled with list's elements */
	private ArrayList<BazaarItem> mItems = new ArrayList<BazaarItem>();
	/** Called when a list item is clicked */
	private OnClickListener mOnItemClick;
	private Context mContext;

	public BazaarAdapter(Context context, ArrayList<BazaarItem> items, 
			OnClickListener onItemClick) {
		mOnItemClick = onItemClick;
		mContext = context;
		mItems = items;
	}

	public int getCount() {
		return mItems.size();
	}

	public Object getItem(int index) {
		return mItems.get(index);
	}

	public long getItemId(int index) {
		return index;
	}

	public View getView(int index, View convertView, ViewGroup parent) {
		BazaarItemView item;
		BazaarItem it = mItems.get(index);

		item = new BazaarItemView(mContext, mItems.get(index));
		item.setOnClickListener(mOnItemClick);
		item.setClickable(true);

		return item;
	}
}
