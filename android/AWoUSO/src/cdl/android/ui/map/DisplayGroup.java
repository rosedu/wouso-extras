package cdl.android.ui.map;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Window;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import cdl.android.R;
import cdl.android.general.ServerResponse;
import cdl.android.server.ApiHandler;

public class DisplayGroup extends FragmentActivity{
	public DisplayGroupEnum crtGroup;
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.group_display);
		
		RelativeLayout rl = (RelativeLayout) findViewById(R.id.group_profile);
		
		String raceId = null;
		String groupId = null;
	    String members = null;
	    String groupName = null;
	    String numberOfPoints = null;
	    String rankNumber = null;
	    
	    Bundle extras = getIntent().getExtras();
	    if (extras != null){
	       raceId = extras.getString("raceId");
	       groupId = extras.getString("id");
	        	
	       //get information about the group
	       ServerResponse resp = ApiHandler.get(
	   			ApiHandler.groupInformationURL + groupId + "/", this);
        	
	       //determinarea seriei
	       if (raceId.equals("2")){
	       	rl.setBackgroundResource(R.drawable.profiles_ca);
	       }
	       else if (raceId.equals("3")){
	      		rl.setBackgroundResource(R.drawable.profiles_cb);
	       	} else { 
	       			 rl.setBackgroundResource(R.drawable.profiles_cc);
	       	}
	       	
	    	if (resp.getStatus() == false) {
	    		Toast.makeText(this, resp.getError(), Toast.LENGTH_SHORT).show();
	   		} else {
	   			JSONObject jobj = resp.getData();
	   			try {
	   				members = jobj.getString("members");
	   				groupName = jobj.getString("name");
	   				numberOfPoints = jobj.getString("points");
	   				rankNumber = jobj.getString("rank");
	   			}
	   			catch (JSONException e){
	   			}
	   		}
	   	}
	    				
	    TextView group = (TextView) findViewById(R.id.grup);
	    TextView memberNumbers = (TextView) findViewById(R.id.membersNumber);
	    TextView points = (TextView) findViewById(R.id.points);
	    TextView rank = (TextView) findViewById(R.id.rank);
	    
	    group.setText(groupName);
	    memberNumbers.setText(members);
	    points.setText(numberOfPoints);
	    rank.setText(rankNumber);
	    
	   	
	    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		
		ft.replace(R.id.group_content, new MembersGroups());
		
		crtGroup = DisplayGroupEnum.Members;
		Resources r = this.getResources();
		((Button)this.findViewById(R.id.group_members)).setBackgroundDrawable(r.getDrawable(R.drawable.round_tab_selected));
		((Button)this.findViewById(R.id.group_activity)).setBackgroundDrawable(r.getDrawable(R.drawable.round_tab_notselected));
		((Button)this.findViewById(R.id.group_evolution)).setBackgroundDrawable(r.getDrawable(R.drawable.round_tab_notselected));
		//delete this when activity and evolution is implemented 
		//buttons invisible
		((Button)this.findViewById(R.id.group_activity)).setVisibility(4);
		((Button)this.findViewById(R.id.group_evolution)).setVisibility(4);
		
		//ft.commit();
	}
	
	
	public void  setAllUnselected(){
		Resources r = this.getResources();
		((Button)this.findViewById(R.id.group_members)).setBackgroundDrawable(r.getDrawable(R.drawable.round_tab_notselected));
		((Button)this.findViewById(R.id.group_activity)).setBackgroundDrawable(r.getDrawable(R.drawable.round_tab_notselected));
		((Button)this.findViewById(R.id.group_evolution)).setBackgroundDrawable(r.getDrawable(R.drawable.round_tab_notselected));
	}
	
	public void changeFragmentTo(DisplayGroupEnum dispGroup){
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		Resources r = this.getResources();
		
		switch(dispGroup){
		
			case Members:
					setAllUnselected();
					((Button)this.findViewById(R.id.group_members)).setBackgroundDrawable(r.getDrawable(R.drawable.round_tab_selected));
					ft.replace(R.id.group_content, new MembersGroups());
					ft.commit();
					
					break;
			
			case Activity:
					setAllUnselected();
					((Button)this.findViewById(R.id.group_activity)).setBackgroundDrawable(r.getDrawable(R.drawable.round_tab_selected)); 
					//Not implemented yet
					//ft.replace(R.id.group_content, new Groups());
					//ft.commit();
				
					break;
					
			case Evolution:
					setAllUnselected();
					((Button)this.findViewById(R.id.group_evolution)).setBackgroundDrawable(r.getDrawable(R.drawable.round_tab_selected));
					//Not implemented yet
					
					break;
			
			default:break;
			
		}
	}
}