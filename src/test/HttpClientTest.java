package test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class HttpClientTest {
	
	public static void main(String args[]) throws IOException {
		Socket skt = null;

		try {
			skt = new Socket("localhost", 10000);
			PrintWriter out = new PrintWriter(skt.getOutputStream(), true);
			
			
			BufferedReader in = new BufferedReader(new InputStreamReader(skt.getInputStream()));
			
			out.print("Accept:");
			

			System.out.println(in.readLine());

			in.close();
		} catch (Exception e) {
			System.out.print(e);
		} finally {
			if (skt != null) {
				skt.close();
			}
		}
	}
	
}
