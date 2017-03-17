var m;

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

	mail = document.forms["signup"]["email"].value;
	login = document.forms["signup"]["login"].value;
	pwd = document.forms["signup"]["pwd"].value;
	repwd = document.forms["signup"]["repwd"].value;

	console.log("mail: " + mail);
	console.log("pwd: " + pwd);
	console.log("repwd: " + repwd);

	document.body.className += "loading";
	m = mail;
	rep = repwd;
	valMail = validateEmail(mail);
	$("#error_holder").fadeOut('fast');

	if(valMail == true && pwd == repwd && pwd.length >= 6) {		
		signup(mail, login, pwd, repwd);
	} else if(valMail == false) {
		$("#error_holder").text("Please enter a valid email.").fadeIn('fast');
		document.body.className = '';
	} else if(login.length < 6) {
		$("#error_holder").text("Your login is too short (at least 6 chars).").fadeIn('fast');
		document.body.className = '';
	} else if(pwd.length < 6) {
		$("#error_holder").text("Your password is too short (at least 6 chars).").fadeIn('fast');
		document.body.className = '';
	} else if(pwd != repwd) {
		$("#error_holder").text("Passwords doesn't match.").fadeIn('fast');
		document.body.className = '';
	} 
}


function signup(mail, login, pwd, repwd) {

	/*$.ajax({
		type: "POST",
		url : "http://localhost:8081/appli/signout",
		data : { "login": login, "mdp": pwd },
		dataType : "jsonp",
		callback : responseSignup,
		success : function(rep) {
			responseSignup(rep);
		},
		error : function(jqXHR, textStatus, errorThrown) {
			console.log(jqXHR, " et ", textStatus, " et ",errorThrown);
			alert("toz");
		}
	});*/

	var http = new XMLHttpRequest();
	var url = "http://localhost:8081/appli/signout";
	var params = "login=ipsum&pwd=binny";
	http.open("POST", url, true);

	//Send the proper header information along with the request
	http.setRequestHeader("Content-type", "application/x-www-form-urlencoded");

	http.onreadystatechange = function() {//Call a function when the state changes.
	    if(http.readyState == 4 && http.status == 200) {
	        alert(http.responseText);
	    }else{
	    	alert(http.responseText);
	    }
	}
	http.send(params);

}


function responseSignup(response) {
	response = JSON.stringify(response);

	if(response.ok != undefined) {
		$("#error_holder").fadeOut('fast');
		$("#error_holder").text(response.message).fadeIn('fast');

		setCookie(C_NAME, response.key, 30);
		window.location.href = "home.html"
	} else {
		$("error_holder").text("Unexpected error. Try again").fadeIn('fast');
		console.log("What is this: " + JSON.stringify(response));
	}

	document.body.className = '';
}
