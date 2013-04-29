package cdl.android.ui.map;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import cdl.android.R;
import cdl.android.general.ServerResponse;
import cdl.android.server.ApiHandler;

public class Groups extends Fragment {
	private ArrayList<GroupItem> mItems;
    
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	
    	View view = inflater.inflate(R.layout.groups, container, false);
    	
    	ListView mListView = null;
        mListView = (ListView) view.findViewById(R.id.groups_list);
        mListView.setEmptyView(view.findViewById(android.R.id.empty));
    
        TextView serie = (TextView) getActivity().findViewById(R.id.serie);
		String numeSerie = serie.getText().toString();
		
		String serieId = null;
		if (numeSerie.equals("CA")){
			serieId = "2";
		}
		else if (numeSerie.equals("CB")){
			serieId = "3";
		} else serieId = "4";
		
		ServerResponse resp = ApiHandler.getArray(
    			ApiHandler.raceURL + serieId + "/groups/", view.getContext());
       	mItems = new ArrayList<GroupItem>();
          	
    	if (resp.getStatus() == false) {
    		Toast.makeText(view.getContext(), resp.getError(), Toast.LENGTH_SHORT).show();
    	} else {
    		try {
    			JSONArray arr = resp.getArrayData();
    			for (int i = 0; i < arr.length(); i++) {
    				GroupItem mes = new GroupItem();
    				mes.parseContent(arr.getJSONObject(i));
    				mItems.add(mes); // ordered by number
   				}
   			} catch (JSONException e) {
    				Toast.makeText(view.getContext(), "Server response format error.",
    						Toast.LENGTH_SHORT).show();
    			}
    	}
    	
    	mListView.setAdapter(new GroupAdapter(this.getActivity().getApplicationContext(), mItems));
    
    	return view;
    }
	
}