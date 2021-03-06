package cdl.android.server;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.widget.Toast;
import cdl.android.general.ServerResponse;
import cdl.android.ui.challenge.ActiveChallenge;
import cdl.android.ui.challenge.ChallengeInfo;
import cdl.android.ui.challenge.menu.RChallengeList;

public class ChallengeHandler {

	private ChallengeHandler() {}

	/**
	 * Sends a challenge to the server using the data received as parameter
	 * 
	 * @param challenge_id
	 *            The challenge id to send to
	 * @param data
	 *            The data to send.
	 */
	public static void sendPost(Context context, int challenge_id, List<NameValuePair> data) {
		ServerResponse rsp = ApiHandler.sendPost(ApiHandler.baseChallengeURL + challenge_id + "/", data, context);
		if (rsp != null){
			if (rsp.getStatus() == true) {
				try {
					JSONObject jObject = new JSONObject(rsp.getData().getString("result"));
					Toast.makeText(context, "Challenge finished! You got " + jObject.getInt("points") + " points!", Toast.LENGTH_LONG).show();
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			else {}
		}
		else {}
	}

	/**
	 * Posts data on the server TODO move this to a different thread, it can
	 * take a lot of time!
	 * 
	 * @param data
	 *            content for post
	 */
	public static void post(Context context, ActiveChallenge data) {

		/** Build Post */
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(5);
		Map<String, String> wrapped = data.getWrappable();
		for (String keySet : wrapped.keySet()) {
			nameValuePairs.add(new BasicNameValuePair(keySet, wrapped.get(keySet)));
		}
		sendPost(context, data.getChallengeId(), nameValuePairs);
	}

	/**
	 * Change the challenge state of a player's challenge.
	 * 
	 * @param challenge_id
	 *            The challenge's id.
	 * @param otherState
	 *            The state, 0 to accept, 1 to cancel, 2 to refuse!
	 */
	public static void changeChallengeState(Context context, int challenge_id, int otherState) {
		if (otherState == 0) {
			ServerResponse rsp = ApiHandler.get(ApiHandler.baseChallengeURL 
					+ challenge_id + "/accept/", context);
		}
		if (otherState == 1) {
			ServerResponse rsp = ApiHandler.get(ApiHandler.baseChallengeURL 
					+ challenge_id + "/cancel/", context);
		}
		if (otherState == 2) {
			ServerResponse rsp = ApiHandler.get(ApiHandler.baseChallengeURL 
					+ challenge_id + "/refuse/", context);
		}
	}

	/**
	 * Gets information about a specific challenge.
	 * 
	 * @param challenge_id
	 *            The challenge's id.
	 * @return The parsed Challenge Info.
	 */
	public static ChallengeInfo getChallengeInfo(Context context, int challenge_id) {
		ServerResponse rsp = ApiHandler.get(ApiHandler.baseChallengeURL 
				+ challenge_id + "/", context);
		return new ChallengeInfo(rsp.getData());
	}

	/**
	 * Start a challenge with another player.
	 * 
	 * @param otherPlayer
	 *            The other player's username.
	 * @return whether the challenge was successfully started.
	 */
	public static boolean startChallenge(Context context, String otherPlayer) {
		ServerResponse rsp = ApiHandler.get(ApiHandler.challengeLaunchURL 
				+ otherPlayer + "/", context);
		return rsp.getData() != null;
	}
	
	/**
	 * Gets the challenge list for the logged user.
	 * 
	 * @return A wrapper class describing all the challenges.
	 */
	public static RChallengeList getChallengeList(Context context) {
		ServerResponse rsp = ApiHandler.getArray(ApiHandler.challengeListURL, context);
		return new RChallengeList(rsp.getArrayData(), context);
	}

}
