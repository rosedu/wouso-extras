package cdl.android.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
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

import cdl.android.general.ServerResponse;

/**
 * HTTP API requests to the WoUSO Server
 */
public class ApiHandler {

	private ApiHandler() {
	}

	/**
	 * Gets a general HTTP string.
	 * @param req The url to get it from.
	 * @return A String containing the data.
	 */
	public static String getHTTP(String req) {

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
		return info.toString();
	}

	/**
	 * Generic HTTP GET data request
	 * 
	 * @param request
	 * @return JSONObject with the server response
	 */
	public static JSONObject get(String req) {
		JSONObject jObject;
		String info = getHTTP(req);

		/** TODO: Check invalid response from server or error */
		try {
			jObject = new JSONObject(info);
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}

		return jObject;
	}

	/**
	 * Generic HTTP GET data request
	 * 
	 * @param req
	 *            The request
	 * @return JSONArray with the server response
	 */
	public static JSONArray getArray(String req) {
		JSONArray jObject = null;

		/** HTTP request */
		String info = getHTTP(req);

		/** TODO: Check invalid response from server or error */
		try {
			jObject = new JSONArray(info);
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return jObject;
	}

	/**
	 * Sends a POST with the specified data.
	 * 
	 * @param host
	 *            The host to send the POST to
	 * @param data
	 *            The data to send.
	 * @return The server's response to the POST.
	 */
	public static ServerResponse sendPost(String host, List<NameValuePair> data) {
		ServerResponse res;
		String url = host;
		HttpPost httpost = new HttpPost(url);
		HttpResponse res2 = null;
		DefaultHttpClient mHttpClient = new DefaultHttpClient();

		/** Send post */
		try {
			httpost.setEntity(new UrlEncodedFormEntity(data));
			res2 = mHttpClient.execute(httpost);
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		/** Read post result */
		res = new ServerResponse(true, null);
		HttpEntity entity = res2.getEntity();
		if (entity != null) {
			InputStream instream;
			try {
				instream = entity.getContent();
				String result = convertStreamToString(instream);
				instream.close();
				JSONObject server = new JSONObject(result);
				if (server.getBoolean("succes") == false)
					res = new ServerResponse(server.getBoolean("succes"),
							server.getString("error"));
				else
					res = new ServerResponse(server.getBoolean("succes"),
							server.getString("correct"));

			} catch (JSONException e) {
				e.printStackTrace();
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		return res;
	}

	private static String convertStreamToString(InputStream is) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();

		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
		}
		return sb.toString();
	}

}
