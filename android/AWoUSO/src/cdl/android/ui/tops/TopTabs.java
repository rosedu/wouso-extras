package cdl.android.ui.tops;

import cdl.android.R;
import android.support.v4.app.*;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class TopTabs extends Fragment{
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
			View view = inflater.inflate(R.layout.top_tabs, container, false);
			
			final Tops tops = (Tops) getActivity();
			
			
			Button btn1 = (Button) view.findViewById(R.id.top_users);
			btn1.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					tops.changeFragmentTo(TopsEnum.TopUsers);
				}});
			
			Button btn2 = (Button) view.findViewById(R.id.top_groups);
			btn2.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					tops.changeFragmentTo(TopsEnum.TopGroups);
				}});
			
			Button btn3 = (Button) view.findViewById(R.id.top_series);
			btn3.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					tops.changeFragmentTo(TopsEnum.TopSeries);
				}});
			
			return view;
	}

}
