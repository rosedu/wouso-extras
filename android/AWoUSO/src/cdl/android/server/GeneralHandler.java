package cdl.android.server;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cdl.android.model.BazaarItem;
import cdl.android.model.Qotd;
import cdl.android.model.UserInfo;

public class GeneralHandler {
	private static final String userInfoAPICallURL = "http://wouso-next.rosedu.org/api/info/?user=";
	private static final String bazaarAPICallURL = "http://wouso-next.rosedu.org/api/bazaar/?user=";
	private static final String qotdAPICallURL = "http://wouso-next.rosedu.org/api/qotd/today/?user=";
	
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
		JSONObject result = ApiHandler.get(qotdAPICallURL + username);
		Qotd qotd = new Qotd(result);
		return qotd;
	}

	/**
	 * Gets the available bazaar items for a username.
	 * @param username The player's username.
	 * @return A List of the parsed available items.
	 */
	//TODO 3: remove this, the bazaar info will be retrieved from a local config file
	public static ArrayList<BazaarItem> getBazaar(String username) {
		ArrayList<BazaarItem> items = new ArrayList<BazaarItem>();
		JSONObject result = ApiHandler.get(bazaarAPICallURL + username);

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
