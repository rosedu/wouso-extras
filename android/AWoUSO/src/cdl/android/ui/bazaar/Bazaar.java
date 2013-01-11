package cdl.android.ui.bazaar;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import cdl.android.R;
import cdl.android.general.BazaarItem;
import cdl.android.ui.main.MainActivity;

public class Bazaar extends Activity {
	private ArrayList<BazaarItem> mItems;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bazaar);

		ListView mListView = (ListView) findViewById(android.R.id.list);
		mListView.setEmptyView(findViewById(android.R.id.empty));

		/** Get Bazaar items from local storage */
		mItems = new ArrayList<BazaarItem>();
		
		 try {
			  FileInputStream fstream = new FileInputStream(MainActivity.CONFIG_SPELLS);
			  DataInputStream in = new DataInputStream(fstream);
			  BufferedReader br = new BufferedReader(new InputStreamReader(in));
			  String strLine;
			  
			  while ((strLine = br.readLine()) != null)   {
				    BazaarItem item = new BazaarItem();
				    String res[] = strLine.split(",");
					item.setId(res[0].trim());
					item.setTitle(res[1].trim());
					item.setDueDays(res[2].trim());
					item.setPrice(res[3].trim());
					item.setFilename(res[4].trim());
					item.setRequiredLevel(Integer.parseInt(res[5].trim()));
					mItems.add(item);
			  }
			  in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		mListView.setAdapter(new BazaarAdapter(this, mItems,
				new OnClickListener() {
					public void onClick(View v) {
					}
				}));
	}
}
