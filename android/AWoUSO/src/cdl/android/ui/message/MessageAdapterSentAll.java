package cdl.android.ui.message;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * Adapter class for the message list's content Manange the items in the list
 */
public class MessageAdapterSentAll extends BaseAdapter {

	ArrayList<MessageItem> mItems;
	/** Called when a message in list is clicked **/
	OnClickListener mOnItemClick;
	Context mContext;
	final Intent readMessage;

	public MessageAdapterSentAll(Context context, ArrayList<MessageItem> items, OnClickListener onItemClick) {
		mItems = new ArrayList<MessageItem>();
		mOnItemClick = onItemClick;
		mContext = context;
		mItems = items;
		readMessage = new Intent(mContext, ReadMessageSentAll.class);
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
		MessageItemViewSentAll item;

		final String from = mItems.get(index).getAuthor();
		final String subject = mItems.get(index).getSubject();
		final String text = mItems.get(index).getContent();
		final String reply_to = mItems.get(index).getReply_to();
		final String id = mItems.get(index).getId();

		item = new MessageItemViewSentAll(mContext, mItems.get(index));
		item.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Bundle data = new Bundle();
				data.putString("from", from);
				data.putString("subject", subject);
				data.putString("text", text);
				data.putString("reply_to", reply_to);
				data.putString("id", id);
				readMessage.putExtras(data);
				mContext.startActivity(readMessage);
			}
		});
		item.setClickable(true);

		return item;
	}
}