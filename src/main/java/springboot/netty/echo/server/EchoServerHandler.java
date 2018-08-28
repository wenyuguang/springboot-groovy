package springboot.netty.echo.server;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 处理服务器端通道
 */
public class EchoServerHandler extends ChannelInboundHandlerAdapter { // (1)

	@Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("Active");
    }
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) { // (2)
		System.out.println("Read");
        System.out.println("Server received : " + msg);
        ctx.write(msg);
	}
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        System.out.println("Read Complete");
        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
	}
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) { // (4)
		// 出现异常时关闭连接。
		cause.printStackTrace();
		ctx.close();
	}
}