package cdl.android.ui.map;

import org.json.JSONException;
import org.json.JSONObject;

/**
* Group item
*/
public class GroupItem {
	private String name;
	private String id;
	private String race_id;
	private String members;

	public void parseContent(JSONObject obj) throws JSONException {
		name = obj.getString("name");
		id = obj.getString("id");
		race_id = obj.getString("race_id");
		members = obj.getString("members");
	}

	public String getName() {
		return name;
	}
	
	public String getId() {
		return id;
	}
	
	public String getMembers() {
		return members;
	}
	
	public String getRace_id() {
		return race_id;
	}
}