package cdl.android.ui.message;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;
import cdl.android.R;
import cdl.android.model.MessageItem;


/**
 * 
 * Message List Item View
 */
public class MessageItemView extends LinearLayout {
	MessageItem mItem;
	Context mContext;
	
	public MessageItemView(Context context, MessageItem item) {
		super(context);
		mContext = context;
		mItem = item;
		
		LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		layoutInflater.inflate(R.layout.message_list_item, this, true);
		
		TextView subject = (TextView) findViewById(R.id.subject);
		subject.setText(item.getSubject());
		
		TextView author = (TextView) findViewById(R.id.author);
		author.setText(item.getAuthor());
		
		TextView content = (TextView) findViewById(R.id.content);
		content.setText(item.getContent());
	
	}
}
