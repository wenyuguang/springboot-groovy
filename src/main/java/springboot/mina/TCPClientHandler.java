package springboot.mina;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

public class TCPClientHandler extends IoHandlerAdapter {
	
	private final String values;

	public TCPClientHandler(String values) {
		this.values = values;
	}

	@Override
	public void sessionOpened(IoSession session) {
		session.write(values);
	}
}