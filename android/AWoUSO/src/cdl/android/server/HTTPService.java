package cdl.android.server;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import cdl.android.general.Qotd;
import cdl.android.general.UserInfo;

public class HTTPService extends IntentService {
	/** Service available commands */
	public static final int USER_INFO = 1;
	public static final int GET_QOTD = 2;
	public static final int POST_QOTD = 3;

	/** Service status replies */
	public static final int STATUS_RUNNING = 0;
	public static final int STATUS_ERROR_NOTIF = 1;
	public static final int STATUS_FINISHED1 = 2;
	public static final int STATUS_FINISHED2 = 3;

	public HTTPService() {
		super("queryService");
	}

	public HTTPService(String name) {
		super(name);
	}

	protected void onHandleIntent(Intent intent) {
		final ResultReceiver receiver = intent.getParcelableExtra("receiver");
		int command = intent.getIntExtra("command", -1);

		switch (command) {

		case USER_INFO:
			getUserInfo(receiver);
			break;
		case GET_QOTD:
			getQotd(receiver);
			break;
		case POST_QOTD:
			postQotd(receiver, intent.getStringExtra("answer"));
			break;
		default:
			break;

		}

		this.stopSelf();
	}

	public void getUserInfo(ResultReceiver receiver) {
		Bundle b = new Bundle();
		receiver.send(STATUS_RUNNING, Bundle.EMPTY);
		ServerResponse resp = ApiHandler.get(ApiHandler.userInfoURL, this);
		UserInfo userInfo = new UserInfo();
		if (resp.getStatus() == false) {
			b.putString(Intent.EXTRA_TEXT, resp.getError());
			receiver.send(STATUS_ERROR_NOTIF, b);
		} else {
			try {
				userInfo.parseContent(resp.getData());
				b.putParcelable("results", userInfo);
				receiver.send(STATUS_FINISHED1, b);
			} catch (JSONException e) {
				b.putString(Intent.EXTRA_TEXT, "Server response format error");
				receiver.send(STATUS_ERROR_NOTIF, b);
			}
		}
	}

	public void getQotd(ResultReceiver receiver) {
		Bundle b = new Bundle();
		receiver.send(STATUS_RUNNING, Bundle.EMPTY);
		ServerResponse resp = ApiHandler.get(ApiHandler.qotdInfoURL, this);
		Qotd qotd = new Qotd();
		if (resp.getStatus() == false) {
			b.putString(Intent.EXTRA_TEXT, resp.getError());
			receiver.send(STATUS_ERROR_NOTIF, b);
		} else {
			if (!resp.getData().has("text")) {
				b.putString(Intent.EXTRA_TEXT, "No question of the day today.");
				receiver.send(STATUS_ERROR_NOTIF, b);
				return;
			}
			try {
				qotd.parseContent(resp.getData());
				b.putParcelable("results", qotd);
				receiver.send(STATUS_FINISHED2, b);
			} catch (JSONException e) {
				b.putString(Intent.EXTRA_TEXT, "Server response format error");
				receiver.send(STATUS_ERROR_NOTIF, b);
			}
		}
	}

	public void postQotd(ResultReceiver receiver, String answer) {
		Bundle b = new Bundle();
		receiver.send(STATUS_RUNNING, Bundle.EMPTY);
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
		nameValuePairs.add(new BasicNameValuePair("answer", answer));
		ServerResponse resp = ApiHandler.sendPost(ApiHandler.qotdInfoURL,
				nameValuePairs, this);
		if (resp.getStatus() == false) {
			b.putString(Intent.EXTRA_TEXT, resp.getError());
			receiver.send(STATUS_ERROR_NOTIF, b);
		} else {
			b.putString(Intent.EXTRA_TEXT, "Qotd answered!");
			receiver.send(STATUS_ERROR_NOTIF, b);
		}
	}
}
