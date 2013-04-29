package cdl.android.ui.map;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import cdl.android.ui.user.OtherProfile;

public class GroupAdapter extends BaseAdapter{
	ArrayList<GroupItem> mItems;
	Context mContext;
	final Intent displayGroup;
	
	public GroupAdapter(Context context, ArrayList<GroupItem> items) {
		mItems = new ArrayList<GroupItem>();
		mContext = context;
		mItems = items;
		displayGroup = new Intent(mContext, DisplayGroup.class);
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
		GroupItemView item;

		final String id = mItems.get(index).getId();
		final String race_id = mItems.get(index).getRace_id();
		item = new GroupItemView(mContext, mItems.get(index));
		item.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Bundle data = new Bundle();
				data.putString("id", id);
				data.putString("raceId", race_id);
				displayGroup.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				displayGroup.putExtras(data);
				mContext.startActivity(displayGroup);
			}
		});
		item.setClickable(true);
		
		return item;
	}

}
