package cdl.android.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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

/**
 * HTTP Generic API requests to the WoUSO Server
 */
public class ApiHandler {

	public static final String baseURL = "http://wouso-next.rosedu.org/api/";
	public static final String userInfoURL = baseURL + "info/";
	public static final String bazaarBuyURL = baseURL + "bazaar/buy/";
	public static final String qotdInfoURL = baseURL + "qotd/today/";
	public static final String msgReceivedAPICallURL = baseURL + "messages/recv/";
	public static final String msgSentAPICallURL = baseURL + "messages/sent/";
	public static final String msgAllAPICallURL = baseURL + "messages/all/";
	public static final String msgSendAPICallURL = baseURL + "messages/send/";
	public static final String baseChallengeURL = baseURL + "challenge/";
	public static final String challengeListURL = baseURL + "challenge/list/";
	public static final String challengeLaunchURL = baseURL + "challenge/launch/";
	public static final String searchURL = baseURL + "search/";

	/**
	 * Gets a general HTTP string.
	 * 
	 * @param req
	 *            The url to get it from.
	 * @return A String containing the data.
	 * @throws OAuthCommunicationException
	 * @throws OAuthExpectationFailedException
	 * @throws OAuthMessageSignerException
	 * @throws IOException
	 * @throws ClientProtocolException
	 */
	public static String getHTTP(String url, Context context)
			throws OAuthMessageSignerException,
			OAuthExpectationFailedException, OAuthCommunicationException,
			ClientProtocolException, IOException {

		StringBuilder responseBuilder = new StringBuilder();

		/** Setup OAuth signer */
		OAuthConsumer mConsumer = new CommonsHttpOAuthConsumer(
				OAuthHelper.CONSUMER_KEY, OAuthHelper.CONSUMER_SECRET);
		mConsumer.setMessageSigner(new PlainTextMessageSigner());
		SharedPreferences mAppInfo = PreferenceManager
				.getDefaultSharedPreferences(context);
		mConsumer.setTokenWithSecret(mAppInfo.getString("auth_token", null),
				mAppInfo.getString("auth_secret", null));

		/** Send request */
		DefaultHttpClient httpclient = new DefaultHttpClient();
		HttpGet request = new HttpGet(url);
		mConsumer.sign(request);
		HttpResponse response = httpclient.execute(request);
		InputStream data = response.getEntity().getContent();
		BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(data));
		String responeLine;
		while ((responeLine = bufferedReader.readLine()) != null) {
			responseBuilder.append(responeLine);
		}

		return responseBuilder.toString();
	}

	/**
	 * Generic HTTP GET data request
	 * 
	 * @param request
	 * @return the server's reply @see ServerResponse
	 */
	public static ServerResponse get(String req, Context context) {
		JSONObject jObject;
		String info;
		ServerResponse result = new ServerResponse();

		try {
			info = getHTTP(req, context);
		} catch (Exception e1) {
			result.setStatus(false);
			result.setError("Server communication error.");
			return result;
		}

		try {
			jObject = new JSONObject(info);
			result.setData(jObject);
			result.setStatus(true);
		} catch (JSONException e) {
			result.setStatus(false);
			result.setError("Server response format error.");
			return result;
		}

		return result;
	}

	/**
	 * Generic HTTP GET data request
	 * 
	 * @param req The request
	 * @return The server's reply @see ServerResponse
	 */
	public static ServerResponse getArray(String req, Context context) {
		JSONArray jObject;
		String info;
		ServerResponse result = new ServerResponse();

		try {
			info = getHTTP(req, context);
		} catch (Exception e1) {
			result.setStatus(false);
			result.setError("Server communication error.");
			return result;
		}

		try {
			jObject = new JSONArray(info);
			result.setArrayData(jObject);
			result.setStatus(true);
		} catch (JSONException e) {
			result.setStatus(false);
			result.setError("Server response format error.");
			return result;
		}

		return result;
	}

	/**
	 * Sends a POST with the specified data.
	 * 
	 * @param host
	 *            The host to send the POST to
	 * @param data
	 *            The data to send.
	 * @return The server's response to the POST.
	 * @throws IOException
	 * @throws OAuthCommunicationException
	 * @throws OAuthExpectationFailedException
	 * @throws OAuthMessageSignerException
	 * @throws ClientProtocolException
	 */
	public static HttpResponse postHttp(String host, List<NameValuePair> data,
			Context context) throws OAuthMessageSignerException,
			OAuthExpectationFailedException, OAuthCommunicationException,
			ClientProtocolException, IOException {
		String url = host;
		HttpPost httpost = new HttpPost(url);
		HttpResponse res = null;
		DefaultHttpClient mHttpClient = new DefaultHttpClient();

		/** Setup OAuth signer */
		OAuthConsumer mConsumer = new CommonsHttpOAuthConsumer(
				OAuthHelper.CONSUMER_KEY, OAuthHelper.CONSUMER_SECRET);
		mConsumer.setMessageSigner(new PlainTextMessageSigner());
		SharedPreferences mAppInfo = PreferenceManager
				.getDefaultSharedPreferences(context);
		mConsumer.setTokenWithSecret(mAppInfo.getString("auth_token", null),
				mAppInfo.getString("auth_secret", null));

		/** Send post */
		httpost.setEntity(new UrlEncodedFormEntity(data));
		mConsumer.sign(httpost);
		res = mHttpClient.execute(httpost);

		return res;
	}

	public static ServerResponse sendPost(String host,
			List<NameValuePair> data, Context context) {
		HttpResponse httpRes;
		ServerResponse res = new ServerResponse();

		/** Send post */
		try {
			httpRes = postHttp(host, data, context);
		} catch (Exception e) {
			res.setStatus(false);
			res.setError("Server communication error.");
			return res;
		}

		/** Read post result */
		HttpEntity entity = httpRes.getEntity();
		if (entity != null) {
			InputStream instream;
			try {
				instream = entity.getContent();
				String result = convertStreamToString(instream);
				instream.close();
				JSONObject server = new JSONObject(result);
				res.setStatus(server.getBoolean("success"));
				if (res.getStatus() == false)
					res.setError(server.getString("error"));
			} catch (Exception e) {
				res.setStatus(false);
				res.setError("Invalid server response.");
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
