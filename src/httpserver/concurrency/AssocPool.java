package httpserver.concurrency;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

public class AssocPool {
	ServerSocket socket;
	ArrayList<Socket> clients;
	ArrayList<Thread> threads;
	HashMap<String, String> table;

	public AssocPool(ServerSocket socket) {
		this.socket = socket;
		threads = new ArrayList<>();
		clients = new ArrayList<>();
		table = new HashMap<>();
	}

	public void start() {
		for (int i = 0; i < 10; i++) {
			threads.add(new HandleClient(this));
			threads.get(i).start();
		}

		System.out.println("Pool Threads created...");
		while (true) {
			Socket client = null;
			try {
				client = socket.accept();
				// System.out.println("Le client " + client.getPort() + " a ete accepte.");

				synchronized (this.clients) {
					clients.add(client);
					this.clients.notify();
				}
			} catch (IOException e) {
				System.out.println("What are those ???");
				e.printStackTrace();
			}
		}
	}
}
