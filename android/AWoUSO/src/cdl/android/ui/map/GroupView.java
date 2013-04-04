package cdl.android.ui.map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;
import cdl.android.R;
import cdl.android.general.ServerResponse;
import cdl.android.server.ApiHandler;

public class GroupView extends Activity {
	TabHost tabHost;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setContentView(R.layout.groups);

        String groupId = null; 
        String serie = null;
        String members = null;
        String rank = null;
        Bundle extras = getIntent().getExtras();
        if (extras != null){
        	groupId = extras.getString("groupId");
        	
        	ServerResponse resp = ApiHandler.get(
    				ApiHandler.groupInformationURL + groupId + "/", this);

    		if (resp.getStatus() == false) {
    			Toast.makeText(this, resp.getError(), Toast.LENGTH_SHORT).show();
    		} else {
    			JSONObject info = resp.getData();
    			try {
					serie = info.getString("name");
					members = info.getString("members");
					rank = info.getString("rank");
				} catch (JSONException e) {
					Toast.makeText(this, "Server response format error.",
							Toast.LENGTH_SHORT).show();
				}
    		}
    		
    		TextView group = (TextView) findViewById(R.id.serie);
    		TextView numbers = (TextView) findViewById(R.id.numberOfMembers);
    		TextView position = (TextView) findViewById(R.id.rank);
    		
    		group.setText(serie);
    		numbers.setText(members);
    		position.setText(rank);
        }
    }
	

}
