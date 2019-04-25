package springboot.groovy.cplusplus;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.RandomAccessFile;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.DefaultFileRegion;
import io.netty.channel.FileRegion;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.stream.ChunkedFile;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

public class CplusplusHandler extends ChannelInboundHandlerAdapter {

    private int loss_connect_time = 0;
    
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("server channelRead.." + ctx.channel().remoteAddress() + "->Server :" + msg.toString());
        ctx.writeAndFlush(changeSizeEndBytes(msg.toString().getBytes()));
//        RandomAccessFile raf = null;
//        long length = -1;
//        try {
//            raf = new RandomAccessFile("f://1.mp3", "r");
//            length = raf.length();
//        } catch (Exception e) {
//            ctx.writeAndFlush("ERR: " + e.getClass().getSimpleName() + ": " + e.getMessage() + '\n');
//            return;
//        } finally {
//            if (length < 0 && raf != null) {
//                raf.close();
//            }
//        }
//        System.err.println("file length :" + length );
////        ctx.write("OK: " + raf.length() + '\n');
//        if (ctx.pipeline().get(SslHandler.class) == null) {
//            // SSL not enabled - can use zero-copy file transfer.
//        	System.err.println("进来了");
//            ctx.write(new DefaultFileRegion(raf.getChannel(), 0, length));
//        } else {
//            // SSL enabled - cannot use zero-copy file transfer.
//            ctx.write(new ChunkedFile(raf));
//        }
//        ctx.writeAndFlush("\n");
//        ctx.writeAndFlush("\n");
        
//        RandomAccessFile randomAccessFile = new RandomAccessFile("f://1.png", "r");
//        long length = randomAccessFile.length();
//        System.err.println("str 长度：" + length);
//		FileRegion region = new DefaultFileRegion(randomAccessFile.getChannel(), 0, randomAccessFile.length());
//		ctx.write(region);
		// 写入换行符表示文件结束
//		ctx.writeAndFlush(CR);
//		randomAccessFile.close();
        
//        InputStream in = new FileInputStream("f://1.mp3");
//	    ByteArrayOutputStream o = new ByteArrayOutputStream();
//	    byte[] buffer = new byte[1024 * 4];
//	    int n = 0;
//	    while ((n = in.read(buffer)) != -1) {
//	        o.write(buffer, 0, n);
//	    }
//	    in.close();
//	    byte [] bbb = o.toByteArray();
//	    System.err.println("str 长度：" + length);
//	    ctx.writeAndFlush(bbb);
//	    saveFile(bbb, "f://", "3.mp3");
	}
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		System.out.println("Read Complete");
		ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
	}
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		super.exceptionCaught(ctx, cause);
	}
	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state() == IdleState.READER_IDLE) {
                loss_connect_time++;
                System.out.println("5 秒没有接收到客户端的信息了");
                if (loss_connect_time > 2) {
                    System.out.println("关闭这个不活跃的channel");
                    ctx.channel().close();
                }
            }
        } else {
            super.userEventTriggered(ctx, evt);
        }
	}
	/**
	* 切换大小端续
	*/
	private byte[] changeSizeEndBytes(byte[] a){
	    byte[] b = new byte[a.length];
	    for (int i = 0; i < b.length; i++) {
	        b[i] = a[b.length - i - 1];
	    }
	    return b;
	}
}
