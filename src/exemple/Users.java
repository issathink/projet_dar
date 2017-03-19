package exemple;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;

import org.json.JSONException;
import org.json.JSONObject;

import httpserver.sessions.HttpSession;
import httpserver.sessions.HttpSessionManager;
import httpserver.tools.HttpServerRequest;
import httpserver.tools.HttpServerResponse;
import httpserver.tools.StatusCodes;
import httpserver.tools.Util;

public class Users {

	public static final String FILENAME = "src/exemple/users.txt";

	public static HashMap<String, User> getUsers() {
		HashMap<String, User> users = new HashMap<>();
		try (BufferedReader br = new BufferedReader(new FileReader(FILENAME))) {
			String line;

			while ((line = br.readLine()) != null) {
				String[] lines = line.split(";");
				users.put(lines[0], new User(lines[0], lines[1]));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return users;
	}
	
	// GET
	public void connected(HttpServerRequest request, HttpServerResponse response) {
		JSONObject object = new JSONObject();
		Map<String, String> bodyContent = request.getParams();
		String cook = bodyContent.get("key");
		try {
			object.put("ok", "ok");
			object.put("connected", HttpSessionManager.getSessions().containsKey(cook));
			response.setContent(object.toString());
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	// GET
	public void online(HttpServerRequest request, HttpServerResponse response) {
		JSONObject object = new JSONObject();
		
		try {
			object.put("ok", "ok");
			Map<String, HttpSession> sessions = HttpSessionManager.getSessions();
			HashSet<String> users = new HashSet<>();
			for(Entry<String, HttpSession> entry : sessions.entrySet())
				if(entry.getValue().getContent() != null)
					users.add((String) entry.getValue().getContent());
			object.put("users", users);
			response.setContent(object.toString());
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	// POST
	public void signin(HttpServerRequest request, HttpServerResponse response) {
		JSONObject object = new JSONObject();
		HashMap<String, String> bodyContent = Util.toHashMapBody(request.getBody());
		String login = bodyContent.get("login");
		String pwd = bodyContent.get("pwd");
		
		if(login == null || pwd == null) {
			System.out.println("Nah bad request");
			response.setError(StatusCodes.ErrorBadRequest);
			return;
		}
		HashMap<String, User> users = Users.getUsers();
		try {
			if(users.get(login) == null) {
				String message = "Username '" + login + "' not found.";
				object.put("message", message);
			} else {
				if(users.get(login).getPwd().equals(pwd)) {
					HttpSessionManager.getSessions().get(request.getSessionId()).setContent(login);
					object.put("ok", "ok");
					object.put("key", request.getSessionId());
				} else {
					object.put("message", "Password or username don't match.");
				}
			}
			response.setContent(object.toString());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		System.out.println("Content: " + response.getContent());
	}
	
	// POST
	public void signup(HttpServerRequest request, HttpServerResponse response) {
		JSONObject object = new JSONObject();
		HashMap<String, String> bodyContent = Util.toHashMapBody(request.getBody());
		String login = bodyContent.get("login");
		String pwd = bodyContent.get("pwd");;
		
		if(login == null || pwd == null) {
			response.setError(StatusCodes.ErrorBadRequest);
			return;
		}
		
		try {
			HashMap<String, User> users = getUsers();
			String message = "Username " + login + " already exists.";
			if (users.get(login) != null) {
				object.put("message", message);
			} else {
				object.put("ok", "ok");
				HttpSessionManager.getSessions().get(request.getSessionId()).setContent(login);
				object.put("key", request.getSessionId());
			}
			
			response.setContent(object.toString());
			String content = login + ";" + pwd + "\n";
			Files.write(Paths.get(FILENAME), content.getBytes(), StandardOpenOption.APPEND);
		} catch (JSONException | IOException e) {
			e.printStackTrace();
		}
	}
	
	// POST 
	public void signout(HttpServerRequest request, HttpServerResponse response) {
		JSONObject object = new JSONObject();
		HashMap<String, String> bodyContent = Util.toHashMapBody(request.getBody());
		String login = bodyContent.get("login");
		String pwd = bodyContent.get("pwd");;
		System.out.println("Au debut login " + login + " pwd: " + pwd);
		
		if(login == null || pwd == null) {
			System.out.println("Nah bad request");
			response.setError(StatusCodes.ErrorBadRequest);
			return;
		}
		HashMap<String, User> users = Users.getUsers();
		try {
			if(users.get(login) == null) {
				String message = "Username " + login + " not found.";
				object.put("message", message);
			} else {
				if(users.get(login).getPwd().equals(pwd)) {
					object.put("ok", "ok");
					object.put("key", request.getSessionId());
				} else {
					object.put("message", "Password or username don't match.");
				}
			}
			response.setContent(object.toString());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		System.out.println("Content: " + response.getContent());
	}
	
	// GET
	public void activate(HttpServerRequest request, HttpServerResponse response) {
		
	}
	
	public static void main(String[] args) {
		System.out.println(getUsers());
		System.out.println("-----------------------");
		// addUser("tt", "test");
		System.out.println(getUsers());
	}
}
