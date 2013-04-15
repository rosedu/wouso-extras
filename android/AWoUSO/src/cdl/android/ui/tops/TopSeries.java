package cdl.android.ui.tops;

import cdl.android.R;
import android.support.v4.app.*;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;



public class TopSeries extends Fragment{

	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
			
			View view =  inflater.inflate(R.layout.top_groups, container, false);
			
			TextView textView = (TextView) view.findViewById(R.id.basic_aux);
			textView.setTextColor(Color.BLUE);
			textView.setText("Top Series is not ready yet");
			
		
			
			
			return view;
	}
}