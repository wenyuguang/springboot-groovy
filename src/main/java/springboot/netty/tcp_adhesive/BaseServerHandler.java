package springboot.netty.tcp_adhesive;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class BaseServerHandler extends ChannelInboundHandlerAdapter {

    private int counter;
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String body = (String)msg;
        System.out.println("服务端接收到: " + body + ";the counter is: " + ++counter);
	}
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		super.exceptionCaught(ctx, cause);
	}
}
