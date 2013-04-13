package cdl.android.ui.tops;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cdl.android.R;
import cdl.android.general.BazaarItem;
import cdl.android.general.ServerResponse;
import cdl.android.server.ApiHandler;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

public class TopUsers extends Fragment{
	private ArrayList<TopUsersItem> playerItems;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
			
			View view =  inflater.inflate(R.layout.top_users, container, false);
			
			ListView mListView = (ListView) view.findViewById(R.id.top_users_list);
			mListView.setEmptyView(view.findViewById(android.R.id.empty));
			
			playerItems = new ArrayList<TopUsersItem>();
			
			ServerResponse resp = ApiHandler.getArray("http://wouso-next.rosedu.org/api/top/player/", view.getContext());
			
			
			if(resp.getStatus() == false){
			
				Toast.makeText(view.getContext(), resp.getError() , Toast.LENGTH_SHORT).show();
			
			}else{
				try {
					JSONArray topPlayersData = (JSONArray) resp.getArrayData();
					
					
					for(int i = 0; i < topPlayersData.length(); i++){
						TopUsersItem playerItem = new TopUsersItem();
						
						try{
							playerItem.parseContent(topPlayersData.getJSONObject(i));
							playerItem.setPlace(i + 1);
							playerItems.add(playerItem);
							
						}catch(Exception e){
							Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
						}
					}
				}catch(Exception  e){
					e.printStackTrace();
				}
			}
			
			
			mListView.setAdapter(new TopUsersAdapter(this.getActivity().getApplicationContext(), playerItems));
			
			
			return view;
	}
}
