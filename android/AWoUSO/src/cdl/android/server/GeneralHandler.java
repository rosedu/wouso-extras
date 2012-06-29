package cdl.android.server;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cdl.android.general.BazaarItem;
import cdl.android.general.Qotd;
import cdl.android.general.UserInfo;

public class GeneralHandler {
	private static final String baseURL = "http://wouso-next.rosedu.org/api/";
	private static final String userInfoURL = baseURL + "info/?user=";
	private static final String bazaarInfoURL = baseURL + "bazaar/?user=";
	private static final String qotdInfoURL = baseURL + "qotd/today/?user=";

	private GeneralHandler() {

	}

	public static String getBaseURL() {
		return baseURL;
	}

	/**
	 * Gets User Info and parses the response
	 * 
	 * @return an UserInfo instance
	 */
	public static UserInfo getUserInfo(String username) {
		JSONObject result = ApiHandler.get(userInfoURL + username);
		UserInfo user = new UserInfo(result);
		return user;
	}

	/**
	 * Gets Question of the Day and parses the response
	 * 
	 * @return an Qotd instance
	 * @throws JSONException
	 */
	public static Qotd getQOTD(String username) throws JSONException {
		JSONObject result = ApiHandler.get(qotdInfoURL + username);
		Qotd qotd = new Qotd(result);
		return qotd;
	}

	/**
	 * Gets the available bazaar items for a username.
	 * 
	 * @param username
	 *            The player's username.
	 * @return A List of the parsed available items.
	 */
	// TODO 3: remove this, the bazaar info will be retrieved from a local config file <= perhaps just cache it locally and check for changes?
	public static ArrayList<BazaarItem> getBazaar(String username) {
		ArrayList<BazaarItem> items = new ArrayList<BazaarItem>();
		JSONObject result = ApiHandler.get(bazaarInfoURL + username);

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
