package springboot.jdk.rmi;

import java.rmi.Naming;

public class RmiClient {

	public static void main(String[] args) {
		try {
			RmiService personService = (RmiService) Naming.lookup("rmi://127.0.0.1:6600/rmiService");
			personService.rmiTest();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
