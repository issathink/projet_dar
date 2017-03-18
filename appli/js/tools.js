var C_NAME   = "projet_dar_cookie";

/************************ Cookie mnam mnam mnam ***********************/
function setCookie(cname, cvalue, minutes) {

    var d = new Date();
    d.setTime(d.getTime() + (minutes * 60 * 60 * 1000));
    var expires = "expires=" + d.toUTCString();

    document.cookie = cname + "=" + cvalue + "; " + expires;
    console.log("[SetCookie] OK " + cname + ", " + cvalue + ", " + minutes);
}

function getCookie(cname) {
    var name = cname + "=";
    var ca = document.cookie.split(';');

    for ( var i = 0; i < ca.length; i++) {
        var c = ca[i];

        while (c.charAt(0) == ' ')
            c = c.substring(1);

        if (c.indexOf(name) == 0) {
            str = c.substring(name.length, c.length);
            if(str == "-1") {
                console.log("Oh oh it has been reinitialised.");
                return null;
            }
            console.log("[GetCookie] OK " + cname + ": " + str);
            return str;
        }
    }

    console.log("[GetCookie] Nothing to show");
    return null;
}


function checkCookie(name) {
    us = getCookie(name);
    if (us != null) {
        // alert("Welcome again " + us);
    } else {
        console.log("[CheckCookie] " + name + " FAILED! ");
    }
}


function isConnected(callBack) {
    genId = getCookie(C_NAME);
    if(genId == null) {
        console.log("No previous session id.");
        callBack({});
        return;
    }

    $.ajax({
        url : "../isconnected?",
        type : "get",
        crossDomain: false,
        data : "format=json" + "&session_id=" + genId,
        dataType : "jsonp",
        success : function(rep) {
            callBack(rep);
        },
        error : function(jqXHR, textStatus, errorThrown) {
            // We do nothing if there isn't an active session
            console.log("Error(" + textStatus + ") : " + jqXHR.responseText);
            console.log("Maybe user is not connected.");
            callBack({});        
        }
    });
}


/* Convetion : Cookie value equals "-1" means there is no active session. */
function logout() {
    setCookie(C_NAME, "-1", 1);
    window.location.href = "index.html";
}


function errorFunction(resultatXHR, statut, erreur, fctName) {
    console.log("Error(" + status + ") : " + resultatXHR.responseText);
    console.log("Error loading "+fctName);
    topBar(resultatXHR.responseText.erreur, true);
}


function topBar(message, err, valTime) {
    if(valTime == undefined)
        valTime = 5000;
    if(err)
        $("<div />", { class: 'erreur_topbar', text: message }).hide().prependTo("body")
        .slideDown('fast').delay(valTime).fadeOut(function() { $(this).remove(); });
    else
        $("<div />", { class: 'ok_topbar', text: message }).hide().prependTo("body")
        .slideDown('fast').delay(valTime).fadeOut(function() { $(this).remove(); });
}

