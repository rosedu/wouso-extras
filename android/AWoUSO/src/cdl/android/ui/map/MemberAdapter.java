package cdl.android.ui.map;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class MemberAdapter extends BaseAdapter{
	ArrayList<MemberItem> mItems;
	Context mContext;
	
	public MemberAdapter(Context context, ArrayList<MemberItem> items) {
		mItems = new ArrayList<MemberItem>();
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
		MemberItemView item;

		final String name = mItems.get(index).getName();

		item = new MemberItemView(mContext, mItems.get(index));
		
		return item;
	}

}
