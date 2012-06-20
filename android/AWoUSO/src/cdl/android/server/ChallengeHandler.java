package cdl.android.server;

import org.json.JSONArray;
import org.json.JSONObject;

import cdl.android.ui.challenge.ChallengeInfo;
import cdl.android.ui.challenge.RChallengeList;
import cdl.android.ui.main.MainMenu;

public class ChallengeHandler {
	private static String chalInfoAPICallURL = "http://wouso-next.rosedu.org/api/challenge/list/?user=";
	private static String challAPICallURL = "http://wouso-next.rosedu.org/api/challenge/";
	private static String lchallAPICallURL = "http://wouso-next.rosedu.org/api/challenge/launch/";
	
	private ChallengeHandler() {
		
	}
	
	public static void changeChallengeState(int challenge_id, int otherState) {
		//0 = accept, 1 = play, 2 = refuse
		String myCall = challAPICallURL+challenge_id+"/";
		if(otherState == 0) {
			ApiHandler.get(myCall+"accept/?user="+MainMenu.globalUsername);
		}
		if(otherState == 2) {
			ApiHandler.get(myCall+"refuse/?user="+MainMenu.globalUsername);	
		}
	}
		
	/**
	 * Gets challenge info.
	 */
	public static ChallengeInfo getChallengeInfo(int challenge_id) {
		JSONObject object = ApiHandler.get(challAPICallURL+challenge_id+"/?user="+MainMenu.globalUsername);
		return new ChallengeInfo(object);
	}
	
	
	public static boolean startChallenge(String otherPlayer) {
		JSONObject object = ApiHandler.get(lchallAPICallURL+otherPlayer+"/?user="+MainMenu.globalUsername);
		return object!=null;
	}
	
	public static RChallengeList getChallengeList(String username) {
		JSONArray result = ApiHandler.getArray(chalInfoAPICallURL+username);
		return new RChallengeList(result);
	}

}
