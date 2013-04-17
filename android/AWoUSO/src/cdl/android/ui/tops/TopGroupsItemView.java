package cdl.android.ui.tops;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;
import cdl.android.R;

public class TopGroupsItemView extends LinearLayout{
	public TopGroupsItem item;
	
	public TopGroupsItemView(final Context context, final TopGroupsItem item) {
		super(context);
		this.item = item;
				
		LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		layoutInflater.inflate(R.layout.top_groups_list_item, this, true);
		
		TextView place = (TextView) this.findViewById(R.id.top_groups_place);
		place.setText(item.getPlace() + ".");
		
		TextView title = (TextView) this.findViewById(R.id.top_groups_title);
		title.setText(item.getTitle());

		TextView points = (TextView) this.findViewById(R.id.top_groups_points);
		points.setText("" + item.getPoints());
	}
	
	public void setGroupsItemView(Context context, TopGroupsItem item){
		this.item = item;
			
		LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		layoutInflater.inflate(R.layout.top_groups_list_item, this, true);
		
		TextView place= (TextView) this.findViewById(R.id.top_groups_place);
		place.setText(item.getPlace() + ".");
		
		TextView name = (TextView) this.findViewById(R.id.top_groups_title);
		name.setText(item.getTitle());

		TextView points = (TextView) this.findViewById(R.id.top_groups_points);
		points.setText("" + item.getPoints());
	}
}
