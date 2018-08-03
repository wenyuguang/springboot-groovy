package springboot.jdk.rmi;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class RmiServer {

	public static void main(String[] args) {
		try {
			RmiService rmiService = new RmiServiceImpl();
			// 注册通讯端口
			LocateRegistry.createRegistry(6600);
			// 注册通讯路径
			Naming.rebind("rmi://127.0.0.1:6600/rmiService", rmiService);
			System.out.println("Service Start!");
			System.in.read();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
