package springboot.jdk.rmi;

import java.io.Serializable;

public class RmiServiceImpl implements RmiService, Serializable{

	/**
	 * 2018年8月3日下午5:58:14
	 * Tony
	 */
	private static final long serialVersionUID = 7458198227822040771L;

	@Override
	public void rmiTest() {
		System.out.println("this is a test");
	}
}
