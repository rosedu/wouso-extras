package cdl.android.ui.map;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

/**
* Member item
*/
public class MemberItem {
	private String name;
	private String id;

	public void parseContent(JSONObject obj) throws JSONException {
		name = obj.getString("display_name");
		id = obj.getString("id");
	}

	public String getName() {
		return name;
	}
	
	public String getId() {
		return id;
	}
}