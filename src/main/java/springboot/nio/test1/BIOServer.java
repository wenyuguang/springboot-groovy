package springboot.nio.test1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class BIOServer {

	public static void main(String[] args) throws IOException {
		ServerSocket serverSocket = new ServerSocket(80);
		while(true) {
			Socket socket = serverSocket.accept();
			InputStream inputStream = socket.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
			System.out.println("Client:" + br.readLine());
			String line;
	        PrintWriter os = new PrintWriter(socket.getOutputStream());
	        line = "hello";
	        os.println(line);
	        os.flush();
	        os.close();
	        br.close();
	        inputStream.close();
	        socket.close();
		}
	}

}
