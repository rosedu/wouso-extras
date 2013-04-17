package cdl.android.ui.tops;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import cdl.android.R;
import cdl.android.general.UserInfo;

public class TopRacesItemView extends LinearLayout{
	public TopRacesItem item;
	
	public TopRacesItemView(final Context context, final TopRacesItem item) {
		super(context);
		this.item = item;
				
		LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		layoutInflater.inflate(R.layout.top_races_list_item, this, true);
		
		TextView place = (TextView) this.findViewById(R.id.top_races_place);
		place.setText(item.getPlace() + ".");
		
		TextView title = (TextView) this.findViewById(R.id.top_races_title);
		title.setText(item.getTitle());

		TextView points = (TextView) this.findViewById(R.id.top_races_points);
		points.setText("" + item.getPoints());
				
	}
	
	public void setRaceItemView(Context context, TopRacesItem item){
		this.item = item;
			
		LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		layoutInflater.inflate(R.layout.top_races_list_item, this, true);
		
		TextView place= (TextView) this.findViewById(R.id.top_races_place);
		place.setText(item.getPlace() + ".");
		
		TextView name = (TextView) this.findViewById(R.id.top_races_title);
		name.setText(item.getTitle());

		TextView points = (TextView) this.findViewById(R.id.top_races_points);
		points.setText("" + item.getPoints());
	}
	

}