package cdl.android.ui.tops;

import java.util.ArrayList;

import cdl.android.ui.user.OtherProfile;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;

public class TopUsersAdapter extends BaseAdapter{
	private ArrayList<TopUsersItem> items;
	private Context context;
	
	
	public TopUsersAdapter(Context context, ArrayList<TopUsersItem> items){
		
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
			 convertView = new TopUsersItemView(context, items.get(position));
		 } else {
			 ((TopUsersItemView)convertView).setUserItemView(context, items.get(position));
		 }
		 final View row = convertView;
		 convertView.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					Intent intent = new Intent(row.getContext(), OtherProfile.class);
					intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					intent.putExtra("id", "" + items.get(position).getId());
					row.getContext().startActivity(intent);
				}
			}
		);
		 convertView.setClickable(true);
		 
		return convertView;
	}


	

}
