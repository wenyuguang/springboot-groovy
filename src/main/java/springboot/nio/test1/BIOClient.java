package springboot.nio.test1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class BIOClient {

	public static void main(String[] args) throws UnknownHostException, IOException {
		Socket socket = new Socket("127.0.0.1", 80);
		System.out.println("连接成功");
//		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//		System.out.println(bufferedReader.readLine());
		PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
        printWriter.println("向服務器發送東西");
        printWriter.flush();
        byte[] buf = new byte[2048];
        System.out.println("准备读取数据~~");
        try {
            //两种读取数据方式
            int count = socket.getInputStream().read(buf);        //会阻塞
            System.out.println("方式一： 读取数据" + new String(buf).trim() + " count = " + count);
            Thread.sleep(1*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
	}

}
