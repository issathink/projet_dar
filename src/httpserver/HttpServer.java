package httpserver;

import java.io.IOException;
import java.net.ServerSocket;

import httpserver.concurrency.AssocPool;

public class HttpServer {
	ServerSocket socket;

	public void start(int port) {
		try {
			socket = new ServerSocket(port);
			socket.setReuseAddress(true);
			System.out.println("Launching server on : " + port);
			AssocPool pool = new AssocPool(socket);
			pool.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void close() {
		try {
			if (socket != null)
				socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
