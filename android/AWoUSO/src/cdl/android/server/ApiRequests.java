package cdl.android.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cdl.android.model.BazaarItem;
import cdl.android.model.Qotd;
import cdl.android.model.UserInfo;

public class ApiRequests {
	private String userInfoAPICallURL = "http://wouso-next.rosedu.org/api/info/?user=";
	private String bazaarAPICallURL = "http://wouso-next.rosedu.org/api/bazaar/?user=";
	private String qotdAPICallURL = "http://wouso-next.rosedu.org/api/qotd/today/?user=";

	/**
	 * Generic data request
	 * 
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
				System.out.println("Am primiit" + info);
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
			// if (jObject.getString("status").equals("failed"))
			// // print jObject.getString("error")
			// // return null
		} catch (JSONException e) {
			e.printStackTrace();
			// return new ServerResponse(null, sInvalidResponse);
		}

		return jObject;
	}

	public void login() {
		// TODO: oauth login
	}

	/**
	 * Makes the API call to the server and parses the response into an UserInfo
	 * 
	 * @return an UserInfo instance
	 */
	public UserInfo getUserInfo(String username) {
		userInfoAPICallURL += username;
		System.out.println("calling with " + userInfoAPICallURL);
		JSONObject result = get(userInfoAPICallURL);
		UserInfo user = new UserInfo(result);
		return user;
	}

	public Qotd getQOTD(String username) {
		qotdAPICallURL += username;
		JSONObject result = get(qotdAPICallURL);
		Qotd qotd = new Qotd(result);
		return qotd;
	}

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
