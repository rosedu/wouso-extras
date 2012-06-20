package cdl.android.ui.challenge;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class ChallengeConnection {
	
	private HttpClient mHttpClient = new DefaultHttpClient();

	/**
	 * Sends a post to the server using the data received as parameter
	 */
	public void sendPost(int challenge_id,List<NameValuePair> data) {
		String url = "http://wouso-next.rosedu.org/api/challenge/"+challenge_id+"/";
		HttpPost httpost = new HttpPost(url);

		/** Send post */
		try {
			httpost.setEntity(new UrlEncodedFormEntity(data));
			mHttpClient.execute(httpost);
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Posts data on the server
	 * 
	 * @param data
	 *            content for post
	 * @param type
	 *            Post's type (e.g.: group)
	 * @return a {@link ServerResponse} that shows if the post succedeed or not
	 */
	public void post(ActiveChallenge data, String type) {

		/** Build Post */
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(5);
		Map<String, String> wrapped = data.getWrappable();
		for(String keySet : wrapped.keySet()) {
			nameValuePairs.add(new BasicNameValuePair(keySet, wrapped.get(keySet)));
		}
		sendPost(data.getChallengeId(), nameValuePairs);
	}

}
