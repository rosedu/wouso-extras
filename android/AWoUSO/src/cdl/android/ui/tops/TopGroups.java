package cdl.android.ui.tops;

import java.util.ArrayList;

import org.json.JSONArray;

import cdl.android.R;
import cdl.android.general.ServerResponse;
import cdl.android.server.ApiHandler;
import android.support.v4.app.*;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class TopGroups extends Fragment{
	ArrayList<TopGroupsItem> groupsItems;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
			
		View view =  inflater.inflate(R.layout.top_races, container, false);
		
		ListView mListView = (ListView) view.findViewById(R.id.top_races_list);
		mListView.setEmptyView(view.findViewById(android.R.id.empty));
		
		
		
		groupsItems = new ArrayList<TopGroupsItem>();
		
		ServerResponse resp = ApiHandler.getArray(ApiHandler.topGroupURL, view.getContext());
		
		
		if(resp.getStatus() == false){
		
			Toast.makeText(view.getContext(), resp.getError() , Toast.LENGTH_SHORT).show();
		
		}else{
			try {
				JSONArray topGroupsData = (JSONArray) resp.getArrayData();
				
				
				for(int i = 0; i < topGroupsData.length(); i++){
					TopGroupsItem groupsItem = new TopGroupsItem();
					
					try{
						groupsItem.parseContent(topGroupsData.getJSONObject(i));
						groupsItem.setPlace(i + 1);
						groupsItems.add(groupsItem);
						
					}catch(Exception e){
						Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
					}
				}
			}catch(Exception  e){
				e.printStackTrace();
			}
		}
	
		
		mListView.setAdapter(new TopGroupsAdapter(this.getActivity().getApplicationContext(), groupsItems));
		
		
		return view;
	}
}