package cdl.android.ui.search;

import java.util.Vector;

import cdl.android.R;
import cdl.android.ui.user.OtherProfile;
import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;


/**
 * Activity that handles search query and lists the results
 * @see UserProvider
 */
public class SearchActivity extends Activity {
	/** List filled with resulted students */
	private ListView mListView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_result);
		mListView = (ListView) findViewById(android.R.id.list);	 
		mListView.setEmptyView(findViewById(android.R.id.empty));

		Intent intent = getIntent();
		if (Intent.ACTION_VIEW.equals(intent.getAction())) { /** click on a suggestion */
			//TODO: start user view using id from - intent.getDataString()
		} else if (Intent.ACTION_SEARCH.equals(intent.getAction())) { /** click on search */
			String query = intent.getStringExtra(SearchManager.QUERY);
			showResults(query);
		}
	}

	/**
	 * Displays search results 
	 */
	private void showResults(String query) {
		Cursor cursor = managedQuery(UserProvider.CONTENT_URI, 
				null, null, new String[] {query}, null);

		if (cursor != null) {
			String[] from = new String[] {SearchManager.SUGGEST_COLUMN_TEXT_1, SearchManager.SUGGEST_COLUMN_TEXT_2};
			int[] to = new int[] {R.id.resultFirstName, R.id.resultName};

			final Vector<String> ids = new Vector();
			cursor.moveToFirst();
			
			do{
				System.out.println(cursor.getCount());
				if(cursor.getCount() == 0){
					break;
				}
				
				ids.add(cursor.getString(cursor.getColumnIndex(BaseColumns._ID)));
				//pune id-urile userilor intr-un vector
				
			}while(cursor.moveToNext());
			
			SimpleCursorAdapter words = new SimpleCursorAdapter(this,
					R.layout.search_result_item, cursor, from, to);
			mListView.setAdapter(words);
			
			final Activity activity = this;
			
			mListView.setOnItemClickListener(new OnItemClickListener() {
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					//TODO: start user view using id from String.valueOf(id)
					Intent intent = new Intent(activity, OtherProfile.class);
					
					intent.putExtra("id", ids.get(position));
					startActivity(intent);
				}
			});
		} 
	}
}
