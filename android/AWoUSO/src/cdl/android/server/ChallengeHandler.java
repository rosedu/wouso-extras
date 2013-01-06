package cdl.android.server;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import cdl.android.ui.challenge.ActiveChallenge;
import cdl.android.ui.challenge.ChallengeInfo;
import cdl.android.ui.challenge.menu.RChallengeList;
import cdl.android.ui.main.Profile;

public class ChallengeHandler {
//	private static final String baseChallengeURL = GeneralHandler.getBaseURL() + "challenge/";
//	private static final String challengeListURL = GeneralHandler.getBaseURL() + "challenge/list/?user=";
//	private static final String challengeLaunchURL = GeneralHandler.getBaseURL() + "challenge/launch/";
	private static HttpClient mHttpClient = new DefaultHttpClient();

	private ChallengeHandler() {

	}

	/**
	 * Sends a challenge to the server using the data received as parameter
	 * 
	 * @param challenge_id
	 *            The challenge id to send to
	 * @param data
	 *            The data to send.
	 */
	public static void sendPost(int challenge_id, List<NameValuePair> data) {
		String url = "http://wouso-next.rosedu.org/api/challenge/" + challenge_id;
		HttpPost httpost = new HttpPost(url);

		/** Send post */
		try {
			httpost.setEntity(new UrlEncodedFormEntity(data));
			mHttpClient.execute(httpost);
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Posts data on the server TODO move this to a different thread, it can
	 * take a lot of time!
	 * 
	 * @param data
	 *            content for post
	 */
	public static void post(ActiveChallenge data) {

		/** Build Post */
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(5);
		Map<String, String> wrapped = data.getWrappable();
		for (String keySet : wrapped.keySet()) {
			nameValuePairs.add(new BasicNameValuePair(keySet, wrapped.get(keySet)));
		}
		sendPost(data.getChallengeId(), nameValuePairs);
	}

	/**
	 * Change the challenge state of a player's challenge.
	 * 
	 * @param challenge_id
	 *            The challenge's id.
	 * @param otherState
	 *            The state, 0 to accept, 2 to refuse!
	 */
	public static void changeChallengeState(int challenge_id, int otherState) {
//		String myCall = baseChallengeURL + challenge_id + "/";
		if (otherState == 0) {
			//ApiHandler.get(myCall + "accept/", this);
		}
		if (otherState == 2) {
			//ApiHandler.get(myCall + "refuse/");
		}
	}

	/**
	 * Gets information about a specific challenge.
	 * 
	 * @param challenge_id
	 *            The challenge's id.
	 * @return The parsed Challenge Info.
	 */
	public static ChallengeInfo getChallengeInfo(int challenge_id) {
		//TODO:
		//JSONObject object = ApiHandler.get(baseChallengeURL + challenge_id + "/");
		//return new ChallengeInfo(object);
		return null;
	}

	/**
	 * Start a challenge with another player.
	 * 
	 * @param otherPlayer
	 *            The other player's username.
	 * @return whether the challenge was successfully started.
	 */
	public static boolean startChallenge(String otherPlayer) {
		//TODO:
		//JSONObject object = ApiHandler.get(challengeLaunchURL + otherPlayer + "/");
		//return object != null;
		return false;
	}
	
	/**
	 * Gets the challenge list for the logged user.
	 * 
	 * @return A wrapper class describing all the challenges.
	 */
	public static RChallengeList getChallengeList() {
		//TODO:
		//JSONArray result = ApiHandler.getArray(challengeListURL + MainMenu.getLoggedUsername());
		//return new RChallengeList(result);
		return null;
	}

}
