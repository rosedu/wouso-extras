package cdl.android.server;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;

import cdl.android.general.ServerResponse;
import cdl.android.ui.message.MessageItem;

public class MessageHandler {
	private static final String msgReceivedAPICallURL = GeneralHandler
			.getBaseURL() + "messages/recv/?user=";
	private static final String msgSentAPICallURL = GeneralHandler.getBaseURL()
			+ "messages/sent/?user=";
	private static final String msgAllAPICallURL = GeneralHandler.getBaseURL()
			+ "messages/all/?user=";
	private static final String msgSendAPICallURL = GeneralHandler.getBaseURL()
			+ "messages/send/?user=";

	public static ServerResponse sendMessage(String user, String to, String subject,
			String text) {
		/**
		 * Add data
		 */
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
		nameValuePairs.add(new BasicNameValuePair("receiver", to));
		nameValuePairs.add(new BasicNameValuePair("text", text));
		nameValuePairs.add(new BasicNameValuePair("subject", subject));
		return ApiHandler.sendPost(msgSendAPICallURL + user, nameValuePairs);

	}

	/**
	 * Get received messages
	 * 
	 * @param username
	 * @return
	 */
	public static ArrayList<MessageItem> getReceived(String username) {
		ArrayList<MessageItem> items = new ArrayList<MessageItem>();
		JSONArray jArray = ApiHandler
				.getArray(msgReceivedAPICallURL + username);

		try {
			for (int i = 0; i < jArray.length(); i++) {
				MessageItem msg = new MessageItem(jArray.getJSONObject(i));
				items.add(msg);
				System.out.println("Author " + msg.getAuthor() + " wrote "
						+ msg.getContent());
			}
		} catch (JSONException ex) {
			ex.printStackTrace();
		}
		return items;
	}

	/**
	 * Get sent messages
	 * 
	 * @param username
	 * @return
	 */
	public static ArrayList<MessageItem> getSent(String username) {
		ArrayList<MessageItem> items = new ArrayList<MessageItem>();
		JSONArray jArray = ApiHandler.getArray(msgSentAPICallURL + username);

		try {
			for (int i = 0; i < jArray.length(); i++) {
				MessageItem msg = new MessageItem(jArray.getJSONObject(i));
				items.add(msg);
				// System.out.println("Author " + msg.getAuthor() + " wrote " +
				// msg.getContent());
			}
		} catch (JSONException ex) {
			ex.printStackTrace();
		}
		return items;
	}

	/**
	 * Get all messages
	 * 
	 * @param username
	 * @return
	 */
	public static ArrayList<MessageItem> getAll(String username) {
		ArrayList<MessageItem> items = new ArrayList<MessageItem>();
		JSONArray jArray = ApiHandler.getArray(msgAllAPICallURL + username);

		try {
			for (int i = 0; i < jArray.length(); i++) {
				MessageItem msg = new MessageItem(jArray.getJSONObject(i));
				items.add(msg);
				// System.out.println("Author " + msg.getAuthor() + " wrote " +
				// msg.getContent());
			}
		} catch (JSONException ex) {
			ex.printStackTrace();
		}
		return items;
	}
}
