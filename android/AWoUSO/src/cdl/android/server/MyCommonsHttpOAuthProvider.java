package cdl.android.server;

import java.io.IOException;

import oauth.signpost.AbstractOAuthProvider;
import oauth.signpost.commonshttp.HttpRequestAdapter;
import oauth.signpost.commonshttp.HttpResponseAdapter;
import oauth.signpost.http.HttpRequest;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;

//---///
public class MyCommonsHttpOAuthProvider extends AbstractOAuthProvider {
	private static final long serialVersionUID = 1L;

    private transient HttpClient httpClient;

    public MyCommonsHttpOAuthProvider(String requestTokenEndpointUrl, String accessTokenEndpointUrl,
            String authorizationWebsiteUrl) {
        super(requestTokenEndpointUrl, accessTokenEndpointUrl, authorizationWebsiteUrl);
        this.httpClient = HttpToHttps.getInstance().sslClient(new DefaultHttpClient());
    }

    public MyCommonsHttpOAuthProvider(String requestTokenEndpointUrl, String accessTokenEndpointUrl,
            String authorizationWebsiteUrl, HttpClient httpClient) {
        super(requestTokenEndpointUrl, accessTokenEndpointUrl, authorizationWebsiteUrl);
        this.httpClient = HttpToHttps.getInstance().sslClient(httpClient);
    }

    public void setHttpClient(HttpClient httpClient) {
        this.httpClient = HttpToHttps.getInstance().sslClient(httpClient);
    }

    protected HttpRequest createRequest(String endpointUrl) throws Exception {
        HttpPost request = new HttpPost(endpointUrl);
        return new HttpRequestAdapter(request);
    }

    protected oauth.signpost.http.HttpResponse sendRequest(HttpRequest request) throws Exception {
        org.apache.http.HttpResponse response = httpClient.execute((HttpUriRequest) request.unwrap());
        return new HttpResponseAdapter(response);
    }

    protected void closeConnection(HttpRequest request, oauth.signpost.http.HttpResponse response)
            throws Exception {
        if (response != null) {
            HttpEntity entity =  ((HttpResponse) response.unwrap()).getEntity();
            if (entity != null) {
                try {
                    // free the connection
                    entity.consumeContent();
                } catch (IOException e) {
                    // this means HTTP keep-alive is not possible
                    e.printStackTrace();
                }
            }
        }
    }
}
