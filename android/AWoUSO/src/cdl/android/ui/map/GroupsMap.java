package cdl.android.ui.map;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.Toast;
import cdl.android.R;

public class GroupsMap extends Activity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.groups_map);

		WebView myWebView = (WebView) findViewById(R.id.webview);
		myWebView.getSettings().setBuiltInZoomControls(true);
		myWebView.getSettings().setSupportZoom(true);
		myWebView.loadUrl("http://swarm.cs.pub.ro/~irinap/map.html");
		myWebView.getSettings().setJavaScriptEnabled(true);

		final DisplayGroup action = new DisplayGroup(this);
		myWebView.addJavascriptInterface(action, "act");

	}

	class DisplayGroup {
		Context context;

		public DisplayGroup(Context context) {
			this.context = context;
		}

		public void show(String text) {
			Toast.makeText(context, text, 1).show();
			// TODO: go to group view activity
		}
	}

}
