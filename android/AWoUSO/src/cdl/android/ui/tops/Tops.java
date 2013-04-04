package cdl.android.ui.tops;

import cdl.android.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class Tops extends Activity{
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tops);
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
	}
}
