package cdl.android.server;

import org.json.JSONArray;
import org.json.JSONObject;

import cdl.android.ui.challenge.ChallengeInfo;
import cdl.android.ui.challenge.RChallengeList;
import cdl.android.ui.main.MainMenu;

public class ChallengeHandler {
	private static final String chalInfoAPICallURL = "http://wouso-next.rosedu.org/api/challenge/list/?user=";
	private static final String challAPICallURL = "http://wouso-next.rosedu.org/api/challenge/";
	private static final String lchallAPICallURL = "http://wouso-next.rosedu.org/api/challenge/launch/";
	
	private ChallengeHandler() {
		
	}
	
	/**
	 * Change the challenge state of a player's challenge.
	 * @param challenge_id The challenge's id.
	 * @param otherState The state, 0 to accept, 2 to refuse!
	 */
	public static void changeChallengeState(int challenge_id, int otherState) {
		String myCall = challAPICallURL+challenge_id+"/";
		if(otherState == 0) {
			ApiHandler.get(myCall+"accept/?user="+MainMenu.getLoggedUsername());
		}
		if(otherState == 2) {
			ApiHandler.get(myCall+"refuse/?user="+MainMenu.getLoggedUsername());	
		}
	}
		
	/**
	 * Gets information about a specific challenge.
	 * @param challenge_id The challenge's id.
	 * @return The parsed Challenge Info.
	 */
	public static ChallengeInfo getChallengeInfo(int challenge_id) {
		JSONObject object = ApiHandler.get(challAPICallURL+challenge_id+"/?user="+MainMenu.getLoggedUsername());
		return new ChallengeInfo(object);
	}
	
	
	/**
	 * Start a challenge with another player.
	 * @param otherPlayer The other player's username.
	 * @return whether the challenge was successfully started.
	 */
	public static boolean startChallenge(String otherPlayer) {
		JSONObject object = ApiHandler.get(lchallAPICallURL+otherPlayer+"/?user="+MainMenu.getLoggedUsername());
		return object!=null;
	}
	
	/**
	 * Gets the challenge list for the logged user.
	 * @return A wrapper class describing all the challenges.
	 */
	public static RChallengeList getChallengeList() {
		JSONArray result = ApiHandler.getArray(chalInfoAPICallURL+MainMenu.getLoggedUsername());
		return new RChallengeList(result);
	}

}
