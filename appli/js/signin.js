isConnected(responseIsConnected);


function responseIsConnected(response) {
	if(response.ok != undefined) {
		console.log("already connected");
		window.location.href = "home.html";
	} else {
		console.log("Noppppppp!");
	}
}

function validate() {
	login = document.forms["signin"]["login"].value;
	pwd  = document.forms["signin"]["pwd"].value;

	document.body.className += "loading";
	var formLength = 4;

	if(login.length >= formLength && pwd.length >= formLength && login.length >= formLength) {
		signin(login, pwd);
		$("#error_holder").text("");
	} else if(login.length < formLength) {
		$("#error_holder").text("Your login is too short (at least 4 chars).").fadeIn('fast');
		document.body.className = '';
	} else if(pwd.length < formLength) {
		$("#error_holder").text("Your password is too short (at least 4 chars).").fadeIn('fast');
		document.body.className = '';
	} else {
		$("#error_holder").text("I dont know what is this error.").fadeIn('fast');
		document.body.className = '';
	}
}


function signin(login, pwd) {
	var http = new XMLHttpRequest();
	var url = "http://localhost:8081/appli/signin?login=" + login + "&pwd=" + pwd;
	var params = "login=" + login + "&pwd=" + pwd;
	http.open("GET", url, true);

	// Send the proper header information along with the request
	// http.setRequestHeader("Content-type", "application/x-www-form-urlencoded");

	http.onreadystatechange = function() {
	    if(http.readyState == 4 && http.status == 200) {
	        responseSignin(http.responseText);
	    }
	}
	http.send();
}

function responseSignin(response) {
	var response = JSON.parse(response);
	$("#error_holder").fadeOut('fast');
	console.log("Retour: " + JSON.stringify(response));

	if(response.ok != undefined) {
		// Successfully logged in
		$("#error_holder").text("Bravo! You're now logged in.").fadeIn('fast');
		document.body.className = '';
		setCookie(C_NAME, response.key, 30);
		window.location.href = "home.html";
	} else {
		// Something wrong
		$("#error_holder").text(response.message).fadeIn('fast');
		document.body.className = '';
	}
}