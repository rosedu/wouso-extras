package cdl.android.ui.challenge;

import org.json.JSONException;
import org.json.JSONObject;

public class RChallengeInfo {
	private int challengeId;
	//private int winnerId;
	private String fromId, toId;
	private String status;
	private String date;

	public RChallengeInfo(JSONObject jo) {
		try {
			status = jo.getString("status");
			toId = jo.getString("user_to");
			fromId = jo.getString("user_from");
			date = jo.getString("date");
			challengeId = jo.getInt("id");
			
		} catch (JSONException jex) {
			jex.printStackTrace();
		}
	}

	public int getChallengeId() {
		return challengeId;
	}

	public String getFrom() {
		return fromId;
	}

	public String getTo() {
		return toId;
	}

	public String getStatus() {
		return status;
	}

	public String getDate() {
		return date;
	}

	public void setStatus(String string) {
		status = string;
		
	}
}
