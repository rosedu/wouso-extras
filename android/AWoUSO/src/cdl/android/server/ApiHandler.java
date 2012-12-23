package cdl.android.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import oauth.signpost.signature.PlainTextMessageSigner;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import cdl.android.general.ServerResponse;
import cdl.android.ui.main.OAuthHelper;

/**
 * HTTP API requests to the WoUSO Server
 */
public class ApiHandler {

	private ApiHandler() {
	}

	/**
	 * Gets a general HTTP string.
	 * 
	 * @param req
	 *            The url to get it from.
	 * @return A String containing the data.
	 */
	public static String getHTTP(String url, Context context) {

		StringBuilder responseBuilder = new StringBuilder();

		/** Setup OAuth signer */
		OAuthConsumer mConsumer = new CommonsHttpOAuthConsumer(
				OAuthHelper.CONSUMER_KEY, OAuthHelper.CONSUMER_SECRET);
		mConsumer.setMessageSigner(new PlainTextMessageSigner());
		SharedPreferences mAppInfo = PreferenceManager
				.getDefaultSharedPreferences(context);
		mConsumer.setTokenWithSecret(mAppInfo.getString("auth_token", null),
				mAppInfo.getString("auth_secret", null));

		try {
			DefaultHttpClient httpclient = new DefaultHttpClient();
			HttpGet request = new HttpGet(url);
			Log.i("lala", "Requesting URL : " + url);
			mConsumer.sign(request);
			HttpResponse response = httpclient.execute(request);
			Log.i("lala", "Statusline : " + response.getStatusLine());
			InputStream data = response.getEntity().getContent();
			BufferedReader bufferedReader = new BufferedReader(
					new InputStreamReader(data));
			String responeLine;
			while ((responeLine = bufferedReader.readLine()) != null) {
				responseBuilder.append(responeLine);
			}
			Log.i("lala", "Response : " + responseBuilder.toString());
		} catch (ClientProtocolException e) {
			System.err.println("Exception: " + e.getMessage());
		} catch (IOException e) {
			System.err.println("Exception: " + e.getMessage());
		} catch (OAuthMessageSignerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OAuthExpectationFailedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OAuthCommunicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		return responseBuilder.toString();
	}

	/**
	 * Generic HTTP GET data request
	 * 
	 * @param request
	 * @return JSONObject with the server response
	 */
	public static JSONObject get(String req, Context context) {
		JSONObject jObject;
		String info = getHTTP(req, context);
		System.out.println("Got " + info);

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
	public static JSONArray getArray(String req, Context context) {
		JSONArray jObject = null;

		/** HTTP request */
		String info = getHTTP(req, context);

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
				if (server.getBoolean("success") == false)
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
