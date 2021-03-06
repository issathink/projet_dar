package httpserver.urlrouting;

import java.util.ArrayList;
import java.util.HashMap;

public class URL {
	private ArrayList<String> path;
	private HashMap<String, String> params;

	public void setPath(ArrayList<String> path) {
		this.path = path;
	}

	public void setParams(HashMap<String, String> params) {
		this.params = params;
	}

	public URL(String strUrl) {
		path = new ArrayList<>();
		params = new HashMap<>();

		/** First Split With Queryparams ***/
		if(strUrl.startsWith("/"))
			strUrl = strUrl.substring(1, strUrl.length());
		String[] tmp = strUrl.split("\\?");
		

		/*** If we have queryParams parse them ***/
		if (tmp.length > 1) {
			String[] paths = tmp[0].split("\\/");
			String[] prms = tmp[1].split("&");

			/*** Path contains every URI ***/
			for (int i = 0; i < paths.length; i++) {
				path.add(paths[i]);
				System.out.println(">> " + paths[i]);
			}

			/*** Params contains every queryParams ***/
			for (String str : prms) {
				String[] keyValue = str.split("=");
				/***
				 * If the queryParam has a value put it. Else insert null so we
				 * can catch an error later if needed
				 ***/
				if (keyValue.length > 1)
					params.put(keyValue[0], keyValue[1]);
				else
					params.put(keyValue[0], null);
			}
		} else {
			String[] paths = tmp[0].split("\\/");
			for (int i = 0; i < paths.length; i++) {
				path.add(paths[i]);
			}
		}
		
		System.out.println("url: " + path.toString());
	}

	public void Add(String elem) {
		this.path.add(elem);
	}

	public ArrayList<String> getPath() {
		return path;
	}

	public HashMap<String, String> getParams() {
		return params;
	}

}
