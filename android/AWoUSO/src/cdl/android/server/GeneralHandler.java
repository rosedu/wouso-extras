package cdl.android.server;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cdl.android.model.BazaarItem;
import cdl.android.model.Qotd;
import cdl.android.model.UserInfo;

public class GeneralHandler {
	private static String userInfoAPICallURL = "http://wouso-next.rosedu.org/api/info/?user=";
	private static String bazaarAPICallURL = "http://wouso-next.rosedu.org/api/bazaar/?user=";
	private static String qotdAPICallURL = "http://wouso-next.rosedu.org/api/qotd/today/?user=";
	
	private GeneralHandler() {
		
	}
	


	/**
	 * Gets User Info and parses the response
	 * @return an UserInfo instance
	 */
	public static UserInfo getUserInfo(String username) {
		JSONObject result = ApiHandler.get(userInfoAPICallURL+username);
		UserInfo user = new UserInfo(result);
		return user;
	}

	/**
	 * Gets Question of the Day and parses the response
	 * @return an Qotd instance
	 */
	public static Qotd getQOTD(String username) {
		qotdAPICallURL += username;
		JSONObject result = ApiHandler.get(qotdAPICallURL);
		Qotd qotd = new Qotd(result);
		return qotd;
	}

	//TODO 3: remove this, the bazaar info will be retrieved from a local config file 
	public static ArrayList<BazaarItem> getBazaar(String username) {
		ArrayList<BazaarItem> items = new ArrayList<BazaarItem>();
		bazaarAPICallURL += username;
		JSONObject result = ApiHandler.get(bazaarAPICallURL);

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
