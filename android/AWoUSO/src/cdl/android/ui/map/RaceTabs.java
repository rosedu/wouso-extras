package cdl.android.ui.map;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import cdl.android.R;

public class RaceTabs extends Fragment {
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
			View view = inflater.inflate(R.layout.race_tabs, container, false);
			
			final Race race = (Race) getActivity();			
			
			Button btn1 = (Button) view.findViewById(R.id.race_members);
			btn1.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					race.changeFragmentTo(RaceEnum.Members);
				}});
			
			Button btn2 = (Button) view.findViewById(R.id.race_groups);
			btn2.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					race.changeFragmentTo(RaceEnum.Groups);
				}});
			
			return view;
	}

}
