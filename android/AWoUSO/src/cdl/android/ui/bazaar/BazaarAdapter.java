package cdl.android.ui.bazaar;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import cdl.android.general.BazaarItem;

/**
 *  Adapter class for the bazaar's content 
 *  Manages the items in the list
 */
public class BazaarAdapter extends BaseAdapter {

	private ArrayList<BazaarItem> mItems;
	private OnClickListener mOnItemClick;
	private Context mContext;

	public BazaarAdapter(Context context, ArrayList<BazaarItem> items, 
			OnClickListener onItemClick) {
		mItems = new ArrayList<BazaarItem>();
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

		item = new BazaarItemView(mContext, mItems.get(index));
		item.setOnClickListener(mOnItemClick);
		item.setClickable(true);

		return item;
	}
}
