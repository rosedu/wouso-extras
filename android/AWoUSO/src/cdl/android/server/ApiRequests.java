package cdl.android.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import cdl.android.model.BazaarItem;
import cdl.android.model.MessageItem;
import cdl.android.model.Qotd;
import cdl.android.model.UserInfo;

/**
 * HTTP API requests to the WoUSO Server
 */
public class ApiRequests {
	private String userInfoAPICallURL = "http://wouso-next.rosedu.org/api/info/?user=";
	private String bazaarAPICallURL = "http://wouso-next.rosedu.org/api/bazaar/?user=";
	private String qotdAPICallURL = "http://wouso-next.rosedu.org/api/qotd/today/?user=";
	private String msgReceivedAPICallURL = "http://wouso-next.rosedu.org/api/messages/recv/?user=";
	private String msgSentAPICallURL = "http://wouso-next.rosedu.org/api/messages/sent/?user=";
	private String msgAllAPICallURL = "http://wouso-next.rosedu.org/api/messages/all/?user=";
	private String msgSendAPICallURL = "http://wouso-next.rosedu.org/api/messages/send/?user=";
	
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
	
	
	
	public JSONArray getJArray(String req) {
		JSONArray jArray = null;
		
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
				//System.out.println("Received " + info);
			} else
				return null;
			
		} catch (ClientProtocolException e) {
			System.err.println("Exception: " + e.getMessage());
		} catch (IOException e) {
			System.err.println("Exception: " + e.getMessage());
		}

		/** TODO: Check invalid response from server or error */
		try {
			jArray = new JSONArray(info.toString());
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return jArray;
	}
	
	public void sendMessage(String user, String to, String subject, String text){
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(msgSendAPICallURL + user);

		try {
		    /**
		     * Add data
		     */
		    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
		    nameValuePairs.add(new BasicNameValuePair("receiver", to));
		    nameValuePairs.add(new BasicNameValuePair("text", text));
		    nameValuePairs.add(new BasicNameValuePair("subject", subject));
		    httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

		    /** 
		     * Execute HTTP Post Request
		     */
		    HttpResponse response = httpclient.execute(httppost);

		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
		    e.printStackTrace();
		}

	}
	
	/**
	 * Get received messages
	 * @param username
	 * @return
	 */
	public ArrayList<MessageItem> getReceived(String username) {
		ArrayList<MessageItem> items = new ArrayList<MessageItem>();
		msgReceivedAPICallURL = msgReceivedAPICallURL + username;
		JSONArray jArray = getJArray(msgReceivedAPICallURL);

		try {
			for (int i = 0; i < jArray.length(); i++) {
				MessageItem msg = new MessageItem(jArray.getJSONObject(i));
				items.add(msg);
				System.out.println("Author " + msg.getAuthor() + " wrote " + msg.getContent());
			}
		} catch (JSONException ex) {
			ex.printStackTrace();
		}
		return items;
	}
	
	/**
	 * Get sent messages
	 * @param username
	 * @return
	 */
	public ArrayList<MessageItem> getSent(String username) {
		ArrayList<MessageItem> items = new ArrayList<MessageItem>();
		msgSentAPICallURL = msgSentAPICallURL + username;
		JSONArray jArray = getJArray(msgSentAPICallURL);

		try {
			for (int i = 0; i < jArray.length(); i++) {
				MessageItem msg = new MessageItem(jArray.getJSONObject(i));
				items.add(msg);
				//System.out.println("Author " + msg.getAuthor() + " wrote " + msg.getContent());
			}
		} catch (JSONException ex) {
			ex.printStackTrace();
		}
		return items;
	}
	
	/**
	 * Get all messages
	 * @param username
	 * @return
	 */
	public ArrayList<MessageItem> getAll(String username) {
		ArrayList<MessageItem> items = new ArrayList<MessageItem>();
		msgAllAPICallURL = msgAllAPICallURL + username;
		JSONArray jArray = getJArray(msgAllAPICallURL);

		try {
			for (int i = 0; i < jArray.length(); i++) {
				MessageItem msg = new MessageItem(jArray.getJSONObject(i));
				items.add(msg);
				//System.out.println("Author " + msg.getAuthor() + " wrote " + msg.getContent());
			}
		} catch (JSONException ex) {
			ex.printStackTrace();
		}
		return items;
	}	
	

	/**
	 * Gets User Info and parses the response
	 * @return an UserInfo instance
	 */
	public UserInfo getUserInfo(String username) {
		userInfoAPICallURL += username;
		JSONObject result = get(userInfoAPICallURL);
		UserInfo user = new UserInfo(result);
		return user;
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
