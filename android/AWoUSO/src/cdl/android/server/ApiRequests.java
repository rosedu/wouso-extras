package cdl.android.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cdl.android.model.BazaarItem;
import cdl.android.model.Qotd;
import cdl.android.model.UserInfo;
import cdl.android.ui.challenge.ChallengeInfo;
import cdl.android.ui.challenge.RChallengeList;
import cdl.android.ui.main.MainMenu;

/**
 * HTTP API requests to the WoUSO Server
 */
public class ApiRequests {
	private String userInfoAPICallURL = "http://wouso-next.rosedu.org/api/info/?user=";
	private String bazaarAPICallURL = "http://wouso-next.rosedu.org/api/bazaar/?user=";
	private String qotdAPICallURL = "http://wouso-next.rosedu.org/api/qotd/today/?user=";
	private String chalInfoAPICallURL = "http://wouso-next.rosedu.org/api/challenge/list/?user=";
	private String challAPICallURL = "http://wouso-next.rosedu.org/api/challenge/";
	private String lchallAPICallURL = "http://wouso-next.rosedu.org/api/challenge/launch/";

	/**
	 * Generic HTTP GET data request
	 * @param request
	 * @return JSONObject with the server response
	 */
	public JSONObject get(String req) {
		JSONObject jObject = null;

		/** HTTP request */
		StringBuilder info = new StringBuilder();
		HttpClient client = new DefaultHttpClient();
		HttpGet request = new HttpGet(req);
		HttpResponse response = null;

		try {
			response = client.execute(request);
			int code = response.getStatusLine().getStatusCode();
			System.out.println("code - " + code);

			HttpEntity entity = response.getEntity();

			if (entity != null) {
				InputStream inStream = entity.getContent();
				BufferedReader br = new BufferedReader(new InputStreamReader(
						inStream), 8192);
				String line;
				while ((line = br.readLine()) != null) {
					info.append(line + "\n");
				}
				System.out.println("Received " + info);
			} else
				return null;
			
		} catch (ClientProtocolException e) {
			System.err.println("Exception: " + e.getMessage());
		} catch (IOException e) {
			System.err.println("Exception: " + e.getMessage());
		}

		/** TODO: Check invalid response from server or error */
		try {
			jObject = new JSONObject(info.toString());
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return jObject;
	}
	
	public JSONArray getArray(String req) {
		JSONArray jObject = null;

		/** HTTP request */
		StringBuilder info = new StringBuilder();
		HttpClient client = new DefaultHttpClient();
		HttpGet request = new HttpGet(req);
		HttpResponse response = null;

		try {
			response = client.execute(request);
			int code = response.getStatusLine().getStatusCode();
			System.out.println("code - " + code);

			HttpEntity entity = response.getEntity();

			if (entity != null) {
				InputStream inStream = entity.getContent();
				BufferedReader br = new BufferedReader(new InputStreamReader(
						inStream), 8192);
				String line;
				while ((line = br.readLine()) != null) {
					info.append(line + "\n");
				}
				System.out.println("Received " + info);
			} else
				return null;
			
		} catch (ClientProtocolException e) {
			System.err.println("Exception: " + e.getMessage());
		} catch (IOException e) {
			System.err.println("Exception: " + e.getMessage());
		}

		/** TODO: Check invalid response from server or error */
		try {
			jObject = new JSONArray(info.toString());
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return jObject;
	}
	
	public void changeChallengeState(int challenge_id, int otherState) {
		System.out.println("Changing challenge state to "+otherState+"!");
		//0 = accept, 1 = play, 2 = refuse
		String myCall = challAPICallURL+challenge_id+"/";
		if(otherState == 0) {
			System.out.println("URL will be "+myCall+"accept/ !!");
			get(myCall+"accept/?user="+MainMenu.globalUsername);
		}
		if(otherState == 2) {
			System.out.println("URL will be "+myCall+"refuse/ !!");
			get(myCall+"refuse/?user="+MainMenu.globalUsername);	
		}
	}
		
	/**
	 * Gets challenge info.
	 */
	public ChallengeInfo getChallengeInfo(int challenge_id) {
		JSONObject object = get(challAPICallURL+challenge_id+"/?user="+MainMenu.globalUsername);
		return new ChallengeInfo(object);
	}
	
	
	public boolean startChallenge(String otherPlayer) {
		JSONObject object = get(lchallAPICallURL+otherPlayer+"/?user="+MainMenu.globalUsername);
		return object!=null;
	}
	


	/**
	 * Gets User Info and parses the response
	 * @return an UserInfo instance
	 */
	public UserInfo getUserInfo(String username) {
		JSONObject result = get(userInfoAPICallURL+username);
		UserInfo user = new UserInfo(result);
		return user;
	}
	
	public RChallengeList getChallengeList(String username) {
		JSONArray result = getArray(chalInfoAPICallURL+username);
		return new RChallengeList(result);
	}

	/**
	 * Gets Question of the Day and parses the response
	 * @return an Qotd instance
	 */
	public Qotd getQOTD(String username) {
		qotdAPICallURL += username;
		JSONObject result = get(qotdAPICallURL);
		Qotd qotd = new Qotd(result);
		return qotd;
	}

	//TODO 3: remove this, the bazaar info will be retrieved from a local config file 
	public ArrayList<BazaarItem> getBazaar(String username) {
		ArrayList<BazaarItem> items = new ArrayList<BazaarItem>();
		bazaarAPICallURL += username;
		JSONObject result = get(bazaarAPICallURL);

		try {
			JSONArray arr = result.getJSONArray("spells");
			for (int i = 0; i < arr.length(); i++) {
				BazaarItem bazaar = new BazaarItem(arr.getJSONObject(i));
				items.add(bazaar);
			}
		} catch (JSONException ex) {
			ex.printStackTrace();
		}

		return items;
	}
}
