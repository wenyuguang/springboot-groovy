package springboot.netty.reconnect;

import java.util.concurrent.TimeUnit;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;
import springboot.netty.heartbeat.HeartBeatClientHandler;

public class HeartBeatsReconnectClientDemo {

	public static void main(String[] args) throws InterruptedException {
        new HeartBeatsReconnectClientDemo().connect("127.0.0.1", 8080);
	}
	public void connect(String host, int port) throws InterruptedException {
		EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
		ChannelFuture channelFuture = null;
		try {
			Bootstrap bootstrap = new Bootstrap();
			bootstrap.group(eventLoopGroup)
			.channel(NioSocketChannel.class)
			.option(ChannelOption.TCP_NODELAY, true)
			.handler(new LoggingHandler(LogLevel.DEBUG))
			.handler(new ChannelInitializer<Channel>() {
				@Override
				protected void initChannel(Channel ch) throws Exception {
					ch.pipeline().addLast("ping", new IdleStateHandler(0, 4, 0, TimeUnit.SECONDS));
					ch.pipeline().addLast("decoder", new StringDecoder());
					ch.pipeline().addLast("encoder", new StringEncoder());
					ch.pipeline().addLast(new HeartBeatClientHandler());
				}
			});
			
			channelFuture = bootstrap.connect(host, port);
			channelFuture.channel().closeFuture().sync();
		} finally {
			if(null != channelFuture) {
				if(channelFuture.channel() != null && channelFuture.channel().isOpen()) {
					channelFuture.channel().close();
					eventLoopGroup.shutdownGracefully();
				}
			}
			System.out.println("准备重连");
	        connect(host, port);
	        System.out.println("重连成功");
		}
	}
}
