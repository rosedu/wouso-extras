package cdl.android.ui;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import cdl.android.R;

public class AWoUSOActivity extends Activity {
    /** Called when the activity is first created. */
	Button red;
	Button blue;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main);
        Drawable red_bg = findViewById(R.id.b_red).getBackground();  
    	PorterDuffColorFilter red_filter = new PorterDuffColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
        red_bg.setColorFilter(red_filter);
        Drawable blue_bg = findViewById(R.id.b_blue).getBackground();  
    	PorterDuffColorFilter blue_filter = new PorterDuffColorFilter(Color.BLUE, PorterDuff.Mode.SRC_ATOP);
        blue_bg.setColorFilter(blue_filter);
        red = (Button) findViewById(R.id.b_red);
        blue = (Button) findViewById(R.id.b_blue);
        red.setOnClickListener(myListener);
        blue.setOnClickListener(myListener);
    }

    View.OnClickListener myListener = new View.OnClickListener() {

	public void onClick(View v) {
			if(((Button)v).getVisibility() == View.INVISIBLE) {
				((Button)v).setVisibility(View.VISIBLE);
				if(((Button)v).getId() == red.getId()) {
					blue.setVisibility(View.INVISIBLE);
				}
				else {
					red.setVisibility(View.INVISIBLE);
				}
			}
			else {
				((Button)v).setVisibility(View.INVISIBLE);
				if(((Button)v).getId() == red.getId()) {
					blue.setVisibility(View.VISIBLE);
				}
				else {
					red.setVisibility(View.VISIBLE);
				}
			}
		}
    };
}