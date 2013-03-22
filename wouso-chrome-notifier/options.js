function saveOptions() {
    var bkg = chrome.extension.getBackgroundPage();
    var select = document.getElementById("edition");
    var edition = select.children[select.selectedIndex].value;
    bkg.settings.edition = edition;
    bkg.loadOptions();
}

function loadOptions() {
    var bkg = chrome.extension.getBackgroundPage();
    var select = document.getElementById("edition");
    edition = bkg.settings.edition;

    for (c=0; c<select.children.length; c++) {
        if (select.children[c].value == edition)
            select.children[c].selected = true;
    }
}

document.addEventListener('DOMContentLoaded', function () {
  document.querySelector('button').addEventListener('click', saveOptions);
  loadOptions();
});