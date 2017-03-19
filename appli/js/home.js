isConnected(responseIsConnected);

/* Update navbar if user is connected */
function responseIsConnected(response) {
	console.log(response);
	response = JSON.parse(response);
	if(response.connected == true) {
		loadConnectedUsers();
	} else {
		var errorMsg = "You should be connected to access this page.";
		topBar(errorMsg, true, 3000);
		setTimeout(function() { window.location.href = "signin.html"; }, 3000);
		boolIsConnected = false;
	}
}

function loadConnectedUsers() {
	var http = new XMLHttpRequest();
    var url = "http://localhost:8081/appli/online";
    http.open("GET", url, true);

    http.onreadystatechange = function() {
        if(http.readyState == 4 && http.status == 200) {
            display(http.responseText);
        }
    }
    http.send();
}

function display(response) {
	console.log(response);
	var str = "";
	response = JSON.parse(response);

	if(response.users.length == 0) {
		str = "No user available"
	} else {
		for (var i = 0; i < response.users.length; i++)
			str += '<li class="list-group-item">' + response.users[i] + '</li>';
	}
	document.getElementById("list_ul").innerHTML = str;
}

function worker_function() {
    // all code here
    function timedLoadUsers() {
		var http = new XMLHttpRequest();
	    var url = "http://localhost:8081/appli/online";
	    http.open("GET", url, true);

	    http.onreadystatechange = function() {
	        if(http.readyState == 4 && http.status == 200) {
	            postMessage(http.responseText);
	        }
	    }
	    http.send();
	    setTimeout(timedLoadUsers, 30000);
	}

	timedLoadUsers();
}

// This is in case of normal worker start
if(window!=self)
  worker_function();

worker = new Worker(URL.createObjectURL(new Blob(["("+worker_function.toString()+")()"], {type: 'text/javascript'})));

worker.onmessage = function(e) {
	console.log("worker working ...");
	display(e.data);
};
