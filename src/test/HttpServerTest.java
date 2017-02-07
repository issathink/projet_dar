package test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import httpserver.AnalyseRequest;
import httpserver.tools.HttpServerRequest;

public class HttpServerTest {
	
	public static void main(String[] args) {
	      try {
	         ServerSocket srvr = new ServerSocket(10000);
	         System.out.println("Starting server ...");
	         Socket skt = srvr.accept();
	         System.out.print("Server has connected!\n");
	         BufferedReader reader = new BufferedReader(new InputStreamReader(skt.getInputStream()));
	         
	         String tmp = null;
	         String result = "";
	         
	         while(!(tmp = reader.readLine()).equals("")) {
	        	 result += tmp + "\n";
	        	 System.out.println("Yo " + tmp);
	         }
	         
	         System.out.println("Jai fini ");
	         HttpServerRequest response = AnalyseRequest.analyseRequest(result);
	         
	         PrintWriter out = new PrintWriter(skt.getOutputStream(), true);
	         out.print(response.toString());
	         out.close();
	         skt.close();
	         srvr.close();
	      }
	      catch(Exception e) {
	         System.out.print(e.getMessage());
	      }
	}

}
