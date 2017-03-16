package test;
import httpserver.HttpServer;

public class HttpServerTest {
	
	public static void main(String[] args) {
		HttpServer serv = new HttpServer();
		serv.start(8081);
	}

}
