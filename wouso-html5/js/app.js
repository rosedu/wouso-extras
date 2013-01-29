// The code below uses require.js, a module system for javscript:
// http://requirejs.org/docs/api.html#define

require.config({ 
    baseUrl: 'js/lib',
    paths: {'jquery':
            ['//ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min',
             'jquery']},

});


// When you write javascript in separate files, list them as
// dependencies along with jquery
define("app", function(require) {
    var $ = require('jquery');

    require('bootstrap/tab');
    require('oauth');
    require('sha1');
    require('wouso');

    // configure navigation
    $('.nav-tabs a').click(function(e) {
        $(this).tab('show');
    });
    $('a[href="#main"]').on('shown', function (e) {
        wouso.info(function (data) {
            $('#main-info').html('Welcome, ' + data.first_name + " " + data.last_name + '<br/>'
                                + 'Race: ' + data.race + '<br/>'
                                + 'Group: ' + data.group.name
            );
        });
        wouso.notifications(function (data) {
            $('#main-notif').html('Notifications Count: ' + data.count);
        });
    });

    // start
    if (!check_callback_extended()) {
        // initialize page
        check_login(function ok(data) {$('a[href="#main"]').tab('show');},
                    function fail(request) {$('a[href="#authorize"]').tab('show');});
    }
    
});

function check_callback() {
    // Check document.location for callback
    // return an object with protocol, message and query
    var ret = {};
    if (document.location.search.substring(1).substring(0, 1) == 'q') {
        var query = document.location.search.substring(3);
        query = decodeURIComponent(query);
        ret.protocol = query.substring(0, query.indexOf(":"));
        query = query.substring(query.indexOf(":") + 3);
        if (query.indexOf("?") >= 0) {
            ret.message = query.substring(0, query.indexOf("?"));
            ret.query = query.substring(query.indexOf("?") + 1);
        }
        else {
            ret.message = query;
            ret.query = '';
        }
    }
    return ret;
}

function start_authorization(consumerName) {
    var accessor = consumer['wouso'];
    
    consumer.getAuthorization(accessor, "web+wouso://success", 
        function ok(data) {
            localStorage.tokenSecret = data.oauth_token_secret;
            document.location = data.authorizationURL;
        },
        default_fail_cb
    );
}

// before everything
var path = '';
if (document.location.indexOf('?'))
    path = document.location.substring(0, document.location.indexOf('?'));
else
    path = document.location;
navigator.registerProtocolHandler("web+wouso", path + "?q=%s", "Wouso Open Web Application");

function check_callback_extended() {
    cc = check_callback();

    if (cc.protocol == "web+wouso" && cc.message == "success" && cc.query) {
        var results = OAuth.decodeForm(cc.query);
        var accessor = consumer['wouso'];
        accessor.verifier = OAuth.getParameter(results, "oauth_verifier");
        accessor.token = OAuth.getParameter(results, "oauth_token");
        accessor.tokenSecret = localStorage.tokenSecret;
        
        consumer.fetchAccessToken(accessor, function ok(data) {
                localStorage.accessToken = data.oauth_token;
                localStorage.accessTokenSecret = data.oauth_token_secret;
                // show main page
                $('a[href="#main"]').tab('show');
            }
            , default_fail_cb
        );
        return true;
    }
    return false;
}
