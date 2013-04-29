package cdl.android.ui.map;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import cdl.android.R;

public class GroupsMap extends Activity {
	
	Context context = this;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map);

		Button oxynia = (Button) findViewById(R.id.oxynia);
		Button nifes = (Button) findViewById(R.id.nifes);
		Button zota = (Button) findViewById(R.id.zota);
		
		oxynia.setOnClickListener(new OnClickListener()
		{   
		    @Override
		    public void onClick(View v)
		    {
		    	Intent i = new Intent(context, Race.class);
		    	i.putExtra("raceId", "2");
		    	startActivity(i);
		    }
		});
		
		nifes.setOnClickListener(new OnClickListener()
		{   
		    @Override
		    public void onClick(View v)
		    {
		    	Toast.makeText(getApplicationContext(), "Nifes",
						Toast.LENGTH_SHORT).show();
		    }
		});
		
		zota.setOnClickListener(new OnClickListener()
		{   
		    @Override
		    public void onClick(View v)
		    {
		    	Toast.makeText(getApplicationContext(), "Zota",
						Toast.LENGTH_SHORT).show();
		    }
		});

	}

}
