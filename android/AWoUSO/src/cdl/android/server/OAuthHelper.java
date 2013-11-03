package cdl.android.server;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.OAuthProvider;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.exception.OAuthException;
import oauth.signpost.signature.PlainTextMessageSigner;

public class OAuthHelper {
	public static final String CONSUMER_KEY = "key";
	public static final String CONSUMER_SECRET = "secret";
	final String SERVER_ADDRESS = "https://wouso.cs.pub.ro/2013";
	private OAuthConsumer mConsumer;
	private OAuthProvider mProvider;
	private String mCallbackURL;

	public OAuthHelper() {
		mConsumer = new CommonsHttpOAuthConsumer(CONSUMER_KEY, CONSUMER_SECRET);
		mProvider =	new MyCommonsHttpOAuthProvider(
				SERVER_ADDRESS + "/api/oauth/request_token/",
				SERVER_ADDRESS + "/api/oauth/access_token/",
				SERVER_ADDRESS + "/api/oauth/authorize/");

		mConsumer.setMessageSigner(new PlainTextMessageSigner());
		mProvider.setOAuth10a(true);
		mCallbackURL = SERVER_ADDRESS + "/api/oauth/request_token_ready/";
	}

	public String getRequestToken() {
		String requestToken = null; 

		try {
			requestToken = mProvider.retrieveRequestToken(mConsumer,
					mCallbackURL);
			requestToken += "&oauth_consumer_key=" + CONSUMER_KEY;
		} catch (OAuthException e) {
			e.printStackTrace();
		}

		return requestToken; 
	}

	public String[] getAccessToken(String verifier) {
		try {
			mProvider.retrieveAccessToken(mConsumer, verifier);
		} catch (OAuthException e) {
			e.printStackTrace();
		}

		return new String[] { mConsumer.getToken(), mConsumer.getTokenSecret() };
	}

}
