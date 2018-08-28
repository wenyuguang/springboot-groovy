package springboot.netty.timeserver;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class TimeServer {

	private int port;
	
	TimeServer(int port){
		this.port = port;
	}
	
	public static void main(String[] args) throws InterruptedException {
		int port;
		if (args.length > 0) {
			port = Integer.parseInt(args[0]);
		} else {
			port = 8080;
		}
		new TimeServer(port).run();
	}
	
	public void run() throws InterruptedException {
		EventLoopGroup masterGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			
			ServerBootstrap serverBootstrap = new ServerBootstrap();
			serverBootstrap.group(masterGroup, workerGroup)
			.channel(NioServerSocketChannel.class)
			.childHandler(new ChannelInitializer<Channel>() {
				protected void initChannel(Channel ch) throws Exception {
					ch.pipeline().addLast(new TimeServerHandler());
				};
			})
			.option(ChannelOption.SO_BACKLOG, 128)
			.childOption(ChannelOption.SO_KEEPALIVE, true);
			
			ChannelFuture channelFuture = serverBootstrap.bind(port).sync();
			channelFuture.channel().closeFuture().sync();
		} finally {
			masterGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}

	
}
