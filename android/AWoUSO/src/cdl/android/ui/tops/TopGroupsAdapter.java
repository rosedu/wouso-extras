package cdl.android.ui.tops;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class TopGroupsAdapter extends BaseAdapter{
	private ArrayList<TopGroupsItem> items;
	private Context context;
	
	
	public TopGroupsAdapter(Context context, ArrayList<TopGroupsItem> items){
		this.items = items;
		this.context = context;
	}
	
	
	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public Object getItem(int position) {
		return items.get(position);
	}

	@Override
	public long getItemId(int position) {
		return new Long(items.get(position).getId());
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		 
		 if(convertView == null){
			 convertView = new TopGroupsItemView(context, items.get(position));
		 } else {
			 ((TopGroupsItemView)convertView).setGroupsItemView(context, items.get(position));
		 }

		 
		return convertView;
	}


	

}