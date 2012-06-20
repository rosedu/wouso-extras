package cdl.android.ui.challenge;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RChallengeList {
	private List<RChallengeInfo> allChallenges = new ArrayList<RChallengeInfo>();

	public RChallengeList(JSONArray result) {
		System.out.println("<===================================>");
		try{ 
		for(int i=0; i< result.length(); i++) {
			JSONObject oneChallenge = result.getJSONObject(i);
			allChallenges.add(new RChallengeInfo(oneChallenge));
		}
		} catch(JSONException ex) {
		}
	}
	
	public int getTotalChallenges() {
		return allChallenges.size();
	}
	
	public RChallengeInfo getChallenge(int id) {
		return allChallenges.get(id);
	}

}
