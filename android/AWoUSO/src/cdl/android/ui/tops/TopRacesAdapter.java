package cdl.android.ui.tops;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class TopRacesAdapter extends BaseAdapter{
	private ArrayList<TopRacesItem> items;
	private Context context;
	
	
	public TopRacesAdapter(Context context, ArrayList<TopRacesItem> items){
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
			 convertView = new TopRacesItemView(context, items.get(position));
		 } else {
			 ((TopRacesItemView)convertView).setRaceItemView(context, items.get(position));
		 }

		 
		return convertView;
	}


	

}
