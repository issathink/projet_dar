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
	var formLength = 4;
	login = document.forms["signup"]["login"].value;
	pwd = document.forms["signup"]["pwd"].value;
	repwd = document.forms["signup"]["repwd"].value;

	document.body.className += "loading";
	rep = repwd;
	$("#error_holder").fadeOut('fast');

	if(pwd === repwd && pwd.length >= formLength && login.length >= formLength) {		
		signup(login, pwd, repwd);
	} else if(login.length < formLength) {
		$("#error_holder").text("Your login is too short (at least 4 chars).").fadeIn('fast');
		document.body.className = '';
	} else if(pwd.length < formLength || repwd.length < formLength) {
		$("#error_holder").text("Your password is too short (at least 4 chars).").fadeIn('fast');
		document.body.className = '';
	} else if(pwd !== repwd) {
		$("#error_holder").text("Passwords doesn't match.").fadeIn('fast');
		document.body.className = '';
	} 
}


function signup(login, pwd, repwd) {
	var http = new XMLHttpRequest();
	var url = "http://localhost:8081/appli/signup";
	var params = "login=" + login + "&pwd=" + pwd;
	http.open("POST", url, true);

	//Send the proper header information along with the request
	http.setRequestHeader("Content-type", "application/x-www-form-urlencoded");

	http.onreadystatechange = function() {
	    if(http.readyState == 4 && http.status == 200) {
	        responseSignup(http.responseText);
	    }
	}
	http.send(params);
}


function responseSignup(response) {
	var response = JSON.parse(response);
	if(response.ok != undefined) {
		$("#error_holder").fadeOut('fast');
		$("#error_holder").text(response.message).fadeIn('fast');
		setCookie(C_NAME, response.key, 30);
		window.location.href = "home.html"
	} else {
		// $("error_holder").text("Unexpected error. Try again").fadeIn('fast');
		// console.log("What is this: " + JSON.stringify(response));
		$("#error_holder").text(response.message).fadeIn('fast');
		console.log(response.message + " " + $("#error_holder"));
	}
	document.body.className = '';
}
