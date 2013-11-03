package cdl.android.ui.map;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;
import cdl.android.R;

public class MemberItemView extends LinearLayout {
	MemberItem mItem;
	Context mContext;

	public MemberItemView(Context context, MemberItem item) {
		super(context);
		mContext = context;
		mItem = item;

		LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		layoutInflater.inflate(R.layout.simple_member_list, this, true);

		TextView user = (TextView) findViewById(R.id.user_name);
		user.setText(item.getName());

	}
}
