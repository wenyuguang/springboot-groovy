package springboot.mina;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

public class TCPServerHandler extends IoHandlerAdapter {

    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {
        String str = message.toString();
        System.out.println("The message received is [" + str + "]");
        if (str.endsWith("quit")) {
            session.closeNow();
            return;
        }
    }

    @Override
    public void sessionCreated(IoSession session) throws Exception {
        System.out.println("server session created");
        super.sessionCreated(session);
    }

    @Override
    public void sessionOpened(IoSession session) throws Exception {
        System.out.println("server session Opened");
        super.sessionOpened(session);
    }

    @Override
    public void sessionClosed(IoSession session) throws Exception {
        System.out.println("server session Closed");
        super.sessionClosed(session);
    }
}