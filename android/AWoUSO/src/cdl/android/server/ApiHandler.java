package cdl.android.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * HTTP API requests to the WoUSO Server
 */
public class ApiHandler {
	
	private ApiHandler() {
		
	}

	/**
	 * Generic HTTP GET data request
	 * @param request
	 * @return JSONObject with the server response
	 */
	public static JSONObject get(String req) {
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
	
	public static JSONArray getArray(String req) {
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
}
