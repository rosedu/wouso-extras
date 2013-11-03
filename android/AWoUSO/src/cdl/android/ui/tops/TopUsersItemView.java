package cdl.android.ui.tops;

import cdl.android.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TopUsersItemView extends LinearLayout{
	public TopUsersItem item;
	
	public TopUsersItemView(final Context context, final TopUsersItem item) {
		super(context);
		this.item = item;
		
		
		LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		layoutInflater.inflate(R.layout.top_users_list_item, this, true);
		
		TextView place= (TextView) this.findViewById(R.id.top_users_place);
		place.setText(item.getPlace() + ".");
		
	//	ImageView avatar = (ImageView) this.findViewById(R.id.top_users_avatar);
	//	UserInfo.setAvatar(avatar, this.item.getAvatarURL(), 0);
		
		TextView name = (TextView) this.findViewById(R.id.top_users_full_name);
		name.setText(item.getDisplayName());
		
		TextView levelNo = (TextView) this.findViewById(R.id.top_users_level_no);
		levelNo.setText(" " + item.getLevel());
		
		TextView points = (TextView) this.findViewById(R.id.top_users_points);
		points.setText(item.getPoints());
		
	}
	
	public void setUserItemView(Context context, TopUsersItem item){
		this.item = item;
		
		LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		layoutInflater.inflate(R.layout.top_users_list_item, this, true);
		
		TextView place= (TextView) this.findViewById(R.id.top_users_place);
		place.setText(item.getPlace() + ".");
		
	//	ImageView avatar = (ImageView) this.findViewById(R.id.top_users_avatar);
	//	UserInfo.setAvatar(avatar, this.item.getAvatarURL());
		
		TextView name = (TextView) this.findViewById(R.id.top_users_full_name);
		name.setText(item.getDisplayName());
		
		TextView points = (TextView) this.findViewById(R.id.top_users_points);
		points.setText(item.getPoints());
	}
	

}
