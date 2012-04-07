__author__ = 'alex'
# Code inspired from http://oauth.googlecode.com/svn/code/python/oauth/example/client.py

from oauth import oauth
import httplib
import webbrowser

class WousoOAuthClient(oauth.OAuthClient):
    REQUEST_TOKEN_URL = '/api/oauth/request_token/'
    ACCESS_TOKEN_URL = '/api/oauth/access_token/'
    AUTHORIZATION_URL = '/api/oauth/authorize/'

    def __init__(self, server, port=80, path='', consumer_key='', consumer_secret='', https=False):
        self.server, self.port = server, port
        self.path = path
        self.consumer_key, self.consumer_secret = consumer_key, consumer_secret

        self.request_token_url = self.REQUEST_TOKEN_URL
        self.access_token_url = self.ACCESS_TOKEN_URL
        self.authorization_url = self.AUTHORIZATION_URL

        if https:
            self.connection = httplib.HTTPSConnection('%s:%d' % (self.server, self.port))
        else:
            self.connection = httplib.HTTPConnection("%s:%d" % (self.server, self.port))
        self.consumer = oauth.OAuthConsumer(self.consumer_key, self.consumer_secret)
        self.signature_method_plaintext = oauth.OAuthSignatureMethod_PLAINTEXT()
        self.signature_method_hmac_sha1 = oauth.OAuthSignatureMethod_HMAC_SHA1()

    def fetch_request_token(self, oauth_request):
        self.connection.request(oauth_request.http_method, self.request_token_url,
                                headers=oauth_request.to_header())
        response = self.connection.getresponse()
        data = response.read()
        if 'Invalid consumer' not in data:
            return oauth.OAuthToken.from_string(data)
        return None

    def authorize_token(self, oauth_request, call=False):
        url =  '%s?%s' % (self.authorization_url, oauth_request.to_postdata())
        if not call:
            # let the user handle authorization, this url should be popup in browser
            return 'http://%s:%s' % (self.server, self.port) + url

        self.connection.request(oauth_request.http_method, url)
        response = self.connection.getresponse()
        return response.read()

    def fetch_access_token(self, oauth_request):
        self.connection.request(oauth_request.http_method, self.access_token_url,
                        headers=oauth_request.to_header())
        response = self.connection.getresponse()
        return oauth.OAuthToken.from_string(response.read())

    def access_resource(self, oauth_request, url):
        self.connection.request('GET', url, headers=oauth_request.to_header())
        response = self.connection.getresponse()
        return response.read()

class WousoClient(object):
    """ API client """
    CONSUMER_KEY = 'key'
    CONSUMER_SECRET = 'secret'

    def __init__(self, edition='', access_token=None, server=None, port=80):
        self.access_token = ('', '') if access_token is None else access_token
        self.server = 'wouso.cs.pub.ro' if server is None else server
        path = '/%s' % edition if edition else ''
        self.client = WousoOAuthClient(self.server, port=port, path=path,
            consumer_key=self.CONSUMER_KEY, consumer_secret=self.CONSUMER_SECRET)

    def authorize(self):
        """ Does the OAuth conga. Opens browser window so that the user can authenticate.

        Returns: access_token, for further unattended use
        """
        oauth_request = oauth.OAuthRequest.from_consumer_and_token(self.client.consumer,
                                http_url=self.client.request_token_url,
                                http_method='POST')
        oauth_request.sign_request(self.client.signature_method_plaintext, self.client.consumer, None)
        token = self.client.fetch_request_token(oauth_request)

        # Authorize access, get verifier
        url = self.client.authorize_token(oauth_request)
        webbrowser.open_new(url)

        verifier = raw_input('Please enter verifier:')

        # Now obtain access_token
        oauth_request = oauth.OAuthRequest.from_consumer_and_token(self.client.consumer, token=token,
                                verifier=verifier,
                                http_url=self.client.access_token_url)
        oauth_request.sign_request(self.client.signature_method_plaintext, self.client.consumer, token)
        token = self.client.fetch_access_token(oauth_request)

        self.access_token = token
        return self.access_token

    def notifications(self):
        NOTIF_URL = '/api/notifications/all/'
        oauth_request = oauth.OAuthRequest.from_consumer_and_token(self.client.consumer,
                                token=self.access_token, http_method='GET', http_url=NOTIF_URL,
                                parameters={})
        oauth_request.sign_request(self.client.signature_method_plaintext, self.client.consumer, self.access_token)

        response = self.client.access_resource(oauth_request, NOTIF_URL)

        return response

    def info(self):
        INFO_URL = '/api/info/'
        oauth_request = oauth.OAuthRequest.from_consumer_and_token(self.client.consumer,
            token=self.access_token, http_method='GET', http_url=INFO_URL,
            parameters={})
        oauth_request.sign_request(self.client.signature_method_plaintext, self.client.consumer, self.access_token)

        response = self.client.access_resource(oauth_request, INFO_URL)

        return response

def run_new(*args):
    server, port = 'wouso-next.rosedu.org', 80
    if len(args) > 0:
        server = args[0]
        if len(args) > 1:
            port = int(args[1])
    wc = WousoClient(server=server, port=port)

    wc.authorize()
    print "Access token: '%s'" % wc.access_token
    print wc.info()
    print wc.notifications()

def run_existing(args):
    wc = WousoClient(server='wouso-next.rosedu.org')

    token = oauth.OAuthToken.from_string(args[0])
    wc.access_token = token
    print wc.info()
    print wc.notifications()

if __name__ == '__main__':
    import sys
    if len(sys.argv) >= 2:
        if sys.argv[1] == 'help':
            print 'Usage %s [token <string>] or <server> <port>' % sys.argv[0]
            sys.exit(0)
    if len(sys.argv) >= 3 and sys.argv[1] == 'token':
        run_existing(sys.argv[2:])
    else:
        run_new(*sys.argv[1:])
