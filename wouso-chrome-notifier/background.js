var wouso_host = "wouso.cs.pub.ro";
var notif_base = "/ajax/notifications/";

var wouso_url = "https://wouso.cs.pub.ro";
var notif_url = wouso_url + notif_base;
var minutes_update = 2;

/* settings class */
settings = {
    get edition() {
        return localStorage['edition'];
    },
    set edition(val) {
        localStorage['edition'] = val;
    }
}

function loadOptions() {
    var edition = settings.edition;
    if (edition == undefined)
        edition = "https://" + wouso_host + "/" + "2012";
    if (edition.indexOf('http') != 0)
        wouso_url = "https://" + wouso_host + "/" + edition;
    else
        wouso_url = edition;
    notif_url = wouso_url + notif_base;
    refresh();
}

function refresh() {
    var the_object = {};
    var http_request = new XMLHttpRequest();
    http_request.open( "GET", notif_url, true );
    http_request.onreadystatechange = function () {
        if ( http_request.readyState == 4 && http_request.status == 200 ) {
            the_object = JSON.parse( http_request.responseText );
            if ( the_object.count == -1) {
                chrome.browserAction.setIcon({path:"icon-offline.png"});
                chrome.browserAction.setBadgeText({text: ""});
            }
            else {
                chrome.browserAction.setIcon({path:"icon.png"});
                if (the_object.count == 0)
                    chrome.browserAction.setBadgeText({text: ""});
                else
                    chrome.browserAction.setBadgeText({text:String(the_object.count)});
            }
        }
    };
    http_request.send(null);
}

chrome.browserAction.onClicked.addListener(function(tab) {
    refresh();
    chrome.tabs.getAllInWindow(undefined, function(tabs) {
        for (var i = 0, tab; tab = tabs[i]; i++) {
            if (tab.url && (tab.url.indexOf(wouso_url) == 0)) {
                chrome.tabs.update(tab.id, {selected: true});
                return;
            }
        }
        chrome.tabs.create({url: wouso_url});
    });
});

chrome.browserAction.setBadgeBackgroundColor({color:[200, 0, 0, 255]});

window.setInterval(refresh, minutes_update * 60000);
loadOptions();
