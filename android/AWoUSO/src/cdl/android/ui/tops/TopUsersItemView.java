package cdl.android.ui.tops;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import cdl.android.R;
import cdl.android.general.BazaarItem;
import cdl.android.general.ServerResponse;
import cdl.android.general.UserInfo;
import cdl.android.server.ApiHandler;
import cdl.android.ui.user.OtherProfile;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class TopUsersItemView extends LinearLayout{
	public TopUsersItem item;
	
	public TopUsersItemView(final Context context, final TopUsersItem item) {
		super(context);
		this.item = item;
		
		
		LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		layoutInflater.inflate(R.layout.top_users_list_item, this, true);
		
		TextView place= (TextView) this.findViewById(R.id.top_users_place);
		place.setText(item.getPlace() + ".");
		
		ImageView avatar = (ImageView) this.findViewById(R.id.top_users_avatar);
		UserInfo.setAvatar(avatar, this.item.getAvatarURL());
		
		TextView name = (TextView) this.findViewById(R.id.top_users_full_name);
		name.setText(item.getFirstName() + " " + item.getLastName());
		
		TextView points = (TextView) this.findViewById(R.id.top_users_points);
		points.setText(item.getPoints());
		
		
		
		
	}
	
	public void setUserItemView(Context context, TopUsersItem item){
		this.item = item;
		
		
		LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		layoutInflater.inflate(R.layout.top_users_list_item, this, true);
		
		TextView place= (TextView) this.findViewById(R.id.top_users_place);
		place.setText(item.getPlace() + ".");
		
		ImageView avatar = (ImageView) this.findViewById(R.id.top_users_avatar);
		UserInfo.setAvatar(avatar, this.item.getAvatarURL());
		
		TextView name = (TextView) this.findViewById(R.id.top_users_full_name);
		name.setText(item.getFirstName() + " " + item.getLastName());
		
		TextView points = (TextView) this.findViewById(R.id.top_users_points);
		points.setText(item.getPoints());
	}
	

}
