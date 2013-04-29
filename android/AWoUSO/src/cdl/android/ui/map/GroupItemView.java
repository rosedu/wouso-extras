package cdl.android.ui.map;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;
import cdl.android.R;

public class GroupItemView extends LinearLayout {
	GroupItem mItem;
	Context mContext;

	public GroupItemView(Context context, GroupItem item) {
		super(context);
		mContext = context;
		mItem = item;

		LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		layoutInflater.inflate(R.layout.simple_group_list, this, true);

		TextView user = (TextView) findViewById(R.id.group_name);
		user.setText(item.getName());

	}
}
