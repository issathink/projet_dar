package exemple;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;

public class Users {

	public static final String FILENAME = "src/exemple/users.txt";

	public static ArrayList<User> getUsers() {
		ArrayList<User> users = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader(FILENAME))) {
			String line;

			while ((line = br.readLine()) != null) {
				String[] lines = line.split(";");
				users.add(new User(lines[0], lines[1], lines[2], lines[3]));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return users;
	}

	public static void addUser(String username, String pwd, double lat, double lng) {
		try {
			String content = username + ";" + pwd + ";" + lat + ";" + lng + "\n";
			Files.write(Paths.get(FILENAME), content.getBytes(), StandardOpenOption.APPEND);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		System.out.println(getUsers());
		System.out.println("-----------------------");
		addUser("tt", "test", 1.24, 2.48);
		System.out.println(getUsers());
	}
}
