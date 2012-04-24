package cdl.android.ui;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import cdl.android.R;

public class Bazaar extends ListActivity {


	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		final String[] list = getResources().getStringArray(R.array.list_array);
		setListAdapter(new ArrayAdapter<String>(this, R.layout.bazaar_list_item, list));

		ListView lv = getListView();
		lv.setTextFilterEnabled(true);

		final Toast listItem = Toast.makeText(getApplicationContext(), "Buffer", Toast.LENGTH_SHORT);

		lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				listItem.cancel();
				listItem.setText(((TextView) view).getText());
				listItem.show();
			}
		});
	}

}
