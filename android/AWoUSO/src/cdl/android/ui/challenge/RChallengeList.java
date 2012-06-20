package cdl.android.ui.challenge;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Wrapper class, represents a list of all the active challenges for a player.
 */
public class RChallengeList {
	private List<RChallengeInfo> allChallenges = new ArrayList<RChallengeInfo>();

	/**
	 * Creates a new Challenge list from a parsed JSON array.
	 * @param result The JSONArray describing each challenge.
	 */
	public RChallengeList(JSONArray result) {
		try{ 
		for(int i=0; i< result.length(); i++) {
			JSONObject oneChallenge = result.getJSONObject(i);
			allChallenges.add(new RChallengeInfo(oneChallenge));
		}
		} catch(JSONException ex) {
		}
	}
	
	/**
	 * Gets the total number of challenges.
	 * @return The number of challenges.
	 */
	public int getTotalChallenges() {
		return allChallenges.size();
	}
	
	/**
	 * Gets the challenge info from a specific id.
	 * @param id The challenge's id.
	 * @return The challenge info.
	 */
	public RChallengeInfo getChallenge(int id) {
		return allChallenges.get(id);
	}

}
