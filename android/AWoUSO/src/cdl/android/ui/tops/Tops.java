package cdl.android.ui.tops;

import cdl.android.R;
import cdl.android.R.drawable;
import cdl.android.general.ServerResponse;
import cdl.android.server.ApiHandler;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.Button;
import android.support.v4.app.*;

public class Tops extends FragmentActivity{
	public TopsEnum crtTop;
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.tops);
		
		
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		
		ft.replace(R.id.tops_content, new TopUsers());
		crtTop = TopsEnum.TopUsers;
		Resources r = this.getResources();
		((Button)this.findViewById(R.id.top_users)).setBackgroundDrawable(r.getDrawable(R.drawable.round_tab_selected));
		((Button)this.findViewById(R.id.top_groups)).setBackgroundDrawable(r.getDrawable(R.drawable.round_tab_notselected));
		((Button)this.findViewById(R.id.top_series)).setBackgroundDrawable(r.getDrawable(R.drawable.round_tab_notselected));
		ft.commit();
	}
	
	
	public void  setAllUnselected(){
		Resources r = this.getResources();
		((Button)this.findViewById(R.id.top_users)).setBackgroundDrawable(r.getDrawable(R.drawable.round_tab_notselected));
		((Button)this.findViewById(R.id.top_groups)).setBackgroundDrawable(r.getDrawable(R.drawable.round_tab_notselected));
		((Button)this.findViewById(R.id.top_series)).setBackgroundDrawable(r.getDrawable(R.drawable.round_tab_notselected));
	}
	
	public void changeFragmentTo(TopsEnum top){
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		Resources r = this.getResources();
		
		switch(top){
		
			case TopUsers:
					setAllUnselected();
					((Button)this.findViewById(R.id.top_users)).setBackgroundDrawable(r.getDrawable(R.drawable.round_tab_selected));
					ft.replace(R.id.tops_content, new TopUsers());
					ft.commit();
					
					break;
			
			case TopGroups:
				setAllUnselected();
				((Button)this.findViewById(R.id.top_groups)).setBackgroundDrawable(r.getDrawable(R.drawable.round_tab_selected));
				ft.replace(R.id.tops_content, new TopGroups());
				ft.commit();
				
				break;
				
			case TopSeries:
				setAllUnselected();
				((Button)this.findViewById(R.id.top_series)).setBackgroundDrawable(r.getDrawable(R.drawable.round_tab_selected));
				ft.replace(R.id.tops_content, new TopRaces());
				ft.commit();
				break;
			
			default:break;
			
		}
	}
}
