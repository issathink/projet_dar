package httpserver.concurrency;

import httpserver.AnalyseRequest;
import httpserver.tools.HttpServerRequest;
import httpserver.tools.HttpServerResponse;
import httpserver.tools.UrlRouter;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class HandleClient extends Thread {
	AssocPool pool;

	public HandleClient(AssocPool pool) {
		this.pool = pool;
	}

	@Override
	public void run() {
		Socket client;

		while (true) {
			synchronized (pool.clients) {

				if (pool.clients.size() == 0)
					try {
						pool.clients.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

				client = pool.clients.remove(0);
				System.out.println("Je vais traiter le client " + client.getPort());
			}

			try {
				BufferedReader buff = new BufferedReader(new InputStreamReader(client.getInputStream()));
				PrintStream out = new PrintStream(new DataOutputStream(client.getOutputStream()));
				String request = "";

				while (true) {
					String line;
					line = buff.readLine();

					if ("".equals(line)) {
						HttpServerRequest httpRequest = AnalyseRequest.analyseRequest(request);
						HttpServerResponse respHttp = UrlRouter.route(httpRequest);
						out.println(respHttp.toString());
						out.close();
						buff.close();
						System.out.println(client);
						break;
					}

					if (line != null) {
						request += line+"\n";
					} else if (client.isClosed() || line == null) {
						break;
					}
				}

				System.out.println("J'ai fini avec ce client. port: " + client.getPort()+" \n");
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
			}
		}
	}
}
