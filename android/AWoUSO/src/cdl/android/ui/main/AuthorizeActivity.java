package cdl.android.ui.main;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import cdl.android.R;

public class AuthorizeActivity extends Activity {
	public final int REQ_OK = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.authorize);
		String url = getIntent().getExtras().getString("url");
		
		//mwebView.getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);  

	    CookieSyncManager.createInstance(this); 
	    CookieManager cookieManager = CookieManager.getInstance();
	    cookieManager.removeAllCookie();
		WebView authView = (WebView) findViewById(R.id.auth_id);
		authView.getSettings().setJavaScriptEnabled(true);
		authView.setWebViewClient(new WebViewClient() { 

			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				String requestTokenFragment = "request_token/";
				String requestTokenReady = "request_token_ready/";

				if (url.contains(requestTokenReady)) {
					System.out.println("Aici vom preluca verifier" + url);
					Uri uri = Uri.parse(url);
					String verifier = uri.getQueryParameter("oauth_verifier");
					System.out.println("Got verifier " + verifier);
					((Activity)view.getContext()).setResult(REQ_OK, new Intent().putExtra("verifier", verifier));
					((Activity)view.getContext()).finish();
					return true;
				}

				return false;
			}

			public void onReceivedSslError(WebView view,
					SslErrorHandler handler, SslError error) {
				handler.proceed();
			}
		});
		System.out.println("load " + url);
		authView.loadUrl(url);
	}

}
