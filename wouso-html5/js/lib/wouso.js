var consumer = {};
var instance = "http://localhost:8000/api";

consumer.wouso = 
{ consumerKey : "key",
  consumerSecret : "secret",
  serviceProvider:
   { signatureMethod : "PLAINTEXT",
     requestTokenURL : instance + "/oauth/request_token/",
     userAuthorizationURL: instance + "/oauth/authorize/",
     accessTokenURL: instance + "/oauth/access_token/",
     echoURL: instance + "/notifications/all/",
     baseURL: instance,
   }
};

consumer.signForm =
function signForm(form, etc) {
    form.action = etc.URL.value;
    var accessor = { consumerSecret: etc.consumerSecret.value
                   , tokenSecret   : etc.tokenSecret.value};
    var message = { action: form.action
                  , method: form.method
                  , parameters: []
                  };
    for (var e = 0; e < form.elements.length; ++e) {
        var input = form.elements[e];
        if (input.name != null && input.name != "" && input.value != null
            && (!(input.type == "checkbox" || input.type == "radio") || input.checked))
        {
            message.parameters.push([input.name, input.value]);
        }
    }
    OAuth.setTimestampAndNonce(message);
    OAuth.SignatureMethod.sign(message, accessor);
    //alert(outline("message", message));
    var parameterMap = OAuth.getParameterMap(message.parameters);
    for (var p in parameterMap) {
        if (p.substring(0, 6) == "oauth_"
         && form[p] != null && form[p].name != null && form[p].name != "")
        {
            form[p].value = parameterMap[p];
        }
    }
    return true;
};

consumer.signRequest = 
function signRequest(message, accessor) {
    OAuth.setParameter(message, "oauth_signature_method", accessor.serviceProvider.signatureMethod);
    OAuth.setTimestampAndNonce(message);
    OAuth.SignatureMethod.sign(message, accessor);
    
    var parameterMap = OAuth.getParameterMap(message.parameters);
    var returnURL = message.action + "?";
    for (var p in parameterMap) {
        if (p.substring(0, 6) == "oauth_")
            returnURL += p + '=' + encodeURIComponent(parameterMap[p]) + '&';
    }
    returnURL = returnURL.substring(0, returnURL.length - 1);
    return returnURL;
};

consumer.fetchAccessToken = 
function fetchAccessToken(accessor, callbackOK, callbackFail) {
    var message = { method: "post"
          , action: accessor.serviceProvider.accessTokenURL
          , parameters: [["oauth_verifier", accessor.verifier]
          , ["oauth_signature_method", accessor.serviceProvider.signatureMethod]]
    };
    OAuth.completeRequest(message,
        { consumerKey   : accessor.consumerKey
        , consumerSecret: accessor.consumerSecret
        , token         : accessor.token
        });
    OAuth.SignatureMethod.sign(message, accessor);

    var requestAccess = newXMLHttpRequest();
    requestAccess.onreadystatechange = function receiveAccessToken() {
        if (requestAccess.readyState == 4) {
            if (requestAccess.status == 200) {
                var results = OAuth.decodeForm(requestAccess.responseText);
                var data = {};
                data.oauth_token = OAuth.getParameter(results, "oauth_token");
                data.oauth_token_secret = OAuth.getParameter(results, "oauth_token_secret");
                callbackOK(data);
            } else {
                callbackFail(requestAccess);
            }
        }
    };
    requestAccess.open(message.method, message.action, true); 
    requestAccess.setRequestHeader("Authorization", OAuth.getAuthorizationHeader("", message.parameters));
    requestAccess.send();
}

consumer.getAuthorization = 
function getAuthorization(accessor, callback_url, callbackOK, callbackFail) {
    var message = {
        method: "post", action: accessor.serviceProvider.requestTokenURL
      , parameters: [["oauth_signature_method", accessor.serviceProvider.signatureMethod]]
    };
    OAuth.completeRequest(message, accessor);
    var requestToken = newXMLHttpRequest();
    requestToken.onreadystatechange = function receiveRequestToken() {
        if (requestToken.readyState == 4) {
            if (requestToken.status == 200) {
                var results = OAuth.decodeForm(requestToken.responseText);
                message = { method: "get"
                          , action: accessor.serviceProvider.userAuthorizationURL
                          , parameters: [["oauth_callback", callback_url]]
                };
                OAuth.completeRequest(message,
                    { consumerKey   : accessor.consumerKey
                    , consumerSecret: accessor.consumerSecret
                    , token         : OAuth.getParameter(results, "oauth_token")
                    , tokenSecret   : OAuth.getParameter(results, "oauth_token_secret")
                    });
                var data = { authorizationURL: consumer.signRequest(message, accessor) 
                           , oauth_token_secret: OAuth.getParameter(results, "oauth_token_secret")
                };
                callbackOK(data);
            }
            else {
                callbackFail(requestToken);
            }
        }
    };
    requestToken.open(message.method, message.action, true); 
    requestToken.setRequestHeader("Authorization", OAuth.getAuthorizationHeader("", message.parameters));
    requestToken.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    requestToken.send(OAuth.formEncode(message.parameters));
}

consumer.callApi = 
function callApi(accessor, message, callbackOK, callbackFail) {
    // accessor must have accessToken and accessTokenSecret set
    OAuth.setParameter(message, "oauth_signature_method", accessor.serviceProvider.signatureMethod);
    OAuth.completeRequest(message, {
          consumerKey   : accessor.consumerKey
        , consumerSecret: accessor.consumerSecret
        , token         : accessor.accessToken
        , tokenSecret   : accessor.accessTokenSecret
        }
    );
    var authorizationHeader = OAuth.getAuthorizationHeader("", message.parameters);
    var requestToken = newXMLHttpRequest();
    requestToken.onreadystatechange = function receiveRequestToken() {
        if (requestToken.readyState == 4) {
            if (requestToken.status == 200) {
                callbackOK(requestToken.responseText);
            } else {
                callbackFail(requestToken);
            }
        }
    };
    requestToken.open(message.method, message.action, true); 
    requestToken.setRequestHeader("Authorization", authorizationHeader);
    requestToken.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    requestToken.send(OAuth.formEncode(message.parameters));
}

function check_login(ok_cb, fail_cb)
{
    accessor = consumer['wouso'];
    accessor.accessToken = localStorage.accessToken;
    accessor.accessTokenSecret = localStorage.accessTokenSecret;
    var message = {
          method: "get"
        , action: accessor.serviceProvider.echoURL
    };
    consumer.callApi(accessor, message, ok_cb, fail_cb);
}

function newXMLHttpRequest() {
    try{
        return new XMLHttpRequest();
    } catch(e) {
        try{
            return new ActiveXObject("Msxml2.XMLHTTP");
        } catch(e) {
            try{
                return new ActiveXObject("Microsoft.XMLHTTP");
            } catch(e) {
                alert("Sorry, your browser doesn't support AJAX.");
                throw e;
            }
        }
    }
}

function default_fail_cb(request) {
    alert('Request failed ' + request.status_code + "\n" + request.responseText);
}

/* The wouso API object */
var wouso = {};

wouso.json_get =
function json_get(action, ok_cb) {
    accessor = consumer['wouso'];
    accessor.accessToken = localStorage.accessToken;
    accessor.accessTokenSecret = localStorage.accessTokenSecret;
    var message = {
          method: "get"
        , action: action
    };
    consumer.callApi(accessor
        , message
        , function ok(data) {
            var d = JSON.parse(data);
            ok_cb(d);
        }
        , default_fail_cb
    );
}

wouso.notifications =
function notifications(ok_cb) {
    return wouso.json_get(consumer['wouso'].serviceProvider.baseURL + '/notifications/all', ok_cb)
}

wouso.info =
function info(ok_cb) {
    return wouso.json_get(consumer['wouso'].serviceProvider.baseURL + '/info', ok_cb)
}
