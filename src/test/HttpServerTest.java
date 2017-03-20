package test;
import httpserver.HttpServer;
import httpserver.tools.Util;

public class HttpServerTest {
	
	public static void main(String[] args) {
		HttpServer serv = new HttpServer();
		serv.start(8081);
	}

}
