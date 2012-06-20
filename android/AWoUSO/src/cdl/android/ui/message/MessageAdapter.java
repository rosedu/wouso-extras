package cdl.android.ui.message;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import cdl.android.model.MessageItem;

/**
 * Adapter class for the message list's content
 * Manange the items in the list
 */
public class MessageAdapter extends BaseAdapter {
	

	ArrayList<MessageItem> mItems;
	/** Called when a message in list is clicked **/
	OnClickListener mOnItemClick;
	Context mContext;
	
	public MessageAdapter(Context context, ArrayList<MessageItem> items, OnClickListener onItemClick) {
		mItems = new ArrayList<MessageItem>();
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
		MessageItemView item;
		
		item = new MessageItemView(mContext, mItems.get(index));
		item.setOnClickListener(mOnItemClick);
		item.setClickable(true);
		
		return item;
	}
}
