package httpserver.tools;

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
		ArrayList<String> path = new ArrayList<>();
		HashMap<String, String> params = new HashMap<>();

		/** First Split With Queryparams ***/
		String[] tmp = strUrl.split("\\?");
		System.out.println("TMp length: " + strUrl);

		/*** If we have queryParams parse them ***/
		if (tmp.length > 1) {
			String[] paths = tmp[0].split("\\/");
			String[] prms = tmp[1].split("&");

			/*** Path contains every URI ***/
			for (int i = 1; i < paths.length; i++) {
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

			for (int i = 1; i < paths.length; i++) {
				path.add(paths[i]);
			}
		}
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
