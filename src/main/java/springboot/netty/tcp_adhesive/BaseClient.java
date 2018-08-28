package springboot.netty.tcp_adhesive;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;

public class BaseClient {

    static final String HOST = System.getProperty("host", "127.0.0.1");
    static final int    PORT = Integer.parseInt(System.getProperty("port", "8080"));
    static final int    SIZE = Integer.parseInt(System.getProperty("size", "256"));
    
	public static void main(String[] args) throws InterruptedException {
//		EventLoopGroup group = new NioEventLoopGroup();
//		try {
//			Bootstrap b = new Bootstrap();
//			b.group(group).channel(NioSocketChannel.class).option(ChannelOption.TCP_NODELAY, true)
//					.handler(new ChannelInitializer<SocketChannel>() {
//						@Override
//						public void initChannel(SocketChannel ch) throws Exception {
//							ChannelPipeline p = ch.pipeline();
//							// p.addLast(new LineBasedFrameDecoder(1024));
//							p.addLast(new StringDecoder());
//							p.addLast(new BaseClientHandler());
//						}
//					});
//
//			ChannelFuture future = b.connect(HOST, PORT).sync();
//			future.channel().writeAndFlush("Hello Netty Server ,I am a common client");
//			future.channel().closeFuture().sync();
//		} finally {
//			group.shutdownGracefully();
//		}
		
		
		
		EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
		try {
			Bootstrap bootstrap = new Bootstrap();
			bootstrap.group(eventLoopGroup)
			.channel(NioSocketChannel.class)
			.option(ChannelOption.TCP_NODELAY, true)
			.handler(new ChannelInitializer<SocketChannel>() {
				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					ChannelPipeline channelPipeline = ch.pipeline();
					channelPipeline.addLast(new StringDecoder());
					channelPipeline.addLast(new BaseClientHandler());
				};
			});
			ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 8080).sync();
			channelFuture.channel().writeAndFlush("Hello Netty Server ,I am a common client");
			channelFuture.channel().flush();
			
		} finally {
			eventLoopGroup.shutdownGracefully();
		}
	}
}
