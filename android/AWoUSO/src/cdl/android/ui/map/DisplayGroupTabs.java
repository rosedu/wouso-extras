package cdl.android.ui.map;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import cdl.android.R;

public class DisplayGroupTabs extends Fragment {
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
			View view = inflater.inflate(R.layout.group_display_tabs, container, false);
			
			final DisplayGroup dgroup = (DisplayGroup) getActivity();			
			
			Button btn1 = (Button) view.findViewById(R.id.group_members);
			btn1.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					dgroup.changeFragmentTo(DisplayGroupEnum.Members);
				}});
			
			Button btn2 = (Button) view.findViewById(R.id.group_activity);
			btn2.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					dgroup.changeFragmentTo(DisplayGroupEnum.Activity);
				}});
			
			Button btn3 = (Button) view.findViewById(R.id.group_evolution);
			btn3.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					dgroup.changeFragmentTo(DisplayGroupEnum.Evolution);
				}});
			
			return view;
	}

}