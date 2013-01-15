package cdl.android.ui.search;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.SearchManager;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.provider.BaseColumns;
import cdl.android.server.ApiHandler;
import cdl.android.server.ServerResponse;

/**
 * Provides data for the search manager
 */
public class UserProvider extends ContentProvider {
	/** Location of the provider's authority */
	public static String AUTHORITY = "cdl.android.ui.search.UserProvider";
	/** Uri that identifies the search result */
	public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY
			+ "/user");
	/** Used in the uri to identify a student search */
	private static final int SEARCH_USER = 0;
	/** Used in the uri to identify a suggestions search */
	private static final int SEARCH_SUGGEST = 1;
	/** Identifies search data set (search results or suggestions ) */
	private static final UriMatcher sURIMatcher = buildUriMatcher();

	/**
	 * Creates an uri matcher, used by the provider to identify its data set <br />
	 * Can refer search results or suggestions
	 */
	private static UriMatcher buildUriMatcher() {
		UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
		matcher.addURI(AUTHORITY, "user", SEARCH_USER);
		matcher.addURI(AUTHORITY, SearchManager.SUGGEST_URI_PATH_QUERY,
				SEARCH_SUGGEST);
		matcher.addURI(AUTHORITY, SearchManager.SUGGEST_URI_PATH_QUERY + "/*",
				SEARCH_SUGGEST);
		return matcher;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		switch (sURIMatcher.match(uri)) {
		case SEARCH_SUGGEST:
			if (selectionArgs == null) {
				throw new IllegalArgumentException(
						"selectionArgs must be provided for the Uri: " + uri);
			}
			return getSuggestions(selectionArgs[0]);
		case SEARCH_USER:
			if (selectionArgs == null) {
				throw new IllegalArgumentException(
						"selectionArgs must be provided for the Uri: " + uri);
			}
			return search(selectionArgs[0]);
		default:
			throw new IllegalArgumentException("Unknown Uri: " + uri);
		}
	}

	/**
	 * Loads search suggestions from server
	 */
	private Cursor getSuggestions(String query) {
		String[] columns = new String[] { BaseColumns._ID,
				SearchManager.SUGGEST_COLUMN_TEXT_1,
				SearchManager.SUGGEST_COLUMN_TEXT_2,
				SearchManager.SUGGEST_COLUMN_INTENT_DATA };
		MatrixCursor res = new MatrixCursor(columns);
		if (query == null)
			return res;

		query = query.toLowerCase();
		ServerResponse result = ApiHandler.getArray(ApiHandler.searchURL
				+ query, getContext());
		JSONArray mData = result.getArrayData();
		if (mData != null)
			for (int i = 0; i < mData.length(); i++) {
				JSONObject user;
				try {
					user = mData.getJSONObject(i);
					res.addRow(new String[] { user.getString("id"),
							user.getString("first_name"),
							user.getString("last_name"), user.getString("id") });
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		return res;
	}

	/**
	 * Loads search results from server
	 */
	private Cursor search(String query) {
		String[] columns = new String[] { BaseColumns._ID,
				SearchManager.SUGGEST_COLUMN_TEXT_1,
				SearchManager.SUGGEST_COLUMN_TEXT_2 };
		MatrixCursor res = new MatrixCursor(columns);
		if (query == null)
			return null;

		query = query.toLowerCase();
		ServerResponse result = ApiHandler.getArray(ApiHandler.searchURL
				+ query, getContext());
		JSONArray mData = result.getArrayData();
		if (mData != null)
			for (int i = 0; i < mData.length(); i++) {
				JSONObject user;
				try {
					user = mData.getJSONObject(i);
					res.addRow(new String[] { user.getString("id"),
							user.getString("first_name"),
							user.getString("last_name") });
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		return res;
	}

	@Override
	public String getType(Uri uri) {
		switch (sURIMatcher.match(uri)) {
		case SEARCH_SUGGEST:
			return SearchManager.SUGGEST_MIME_TYPE;
		default:
			throw new IllegalArgumentException("Unknown URL " + uri);
		}
	}

	@Override
	public boolean onCreate() {
		return true;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		throw new UnsupportedOperationException();
	}
}