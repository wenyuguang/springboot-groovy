package springboot.netty.filetransfer;

import java.io.File;
import java.io.RandomAccessFile;
import java.util.concurrent.TimeUnit;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.DefaultFileRegion;
import io.netty.channel.FileRegion;
import io.netty.channel.SimpleChannelInboundHandler;

public class FileServerHandler extends SimpleChannelInboundHandler<String> {
	// 操作系统识别的换行符
	private static final String CR = System.getProperty("line.separator");

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
		File file = new File(msg);
		if (file.exists()) {
			if (!file.isFile()) {
				// 写入换行符表示文件结束
				ctx.writeAndFlush("Not a file: " + file + CR);
				return;
			}
			// 换行符表示文件结尾
			ctx.write(file + " " + file.length() + CR);
			TimeUnit.SECONDS.sleep(2);
			RandomAccessFile randomAccessFile = new RandomAccessFile(msg, "r");
			FileRegion region = new DefaultFileRegion(randomAccessFile.getChannel(), 0, randomAccessFile.length());
			ctx.write(region);
			// 写入换行符表示文件结束
			ctx.writeAndFlush(CR);
			randomAccessFile.close();
		} else {
			ctx.writeAndFlush("File not found: " + file + CR);
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		cause.printStackTrace();
		ctx.close();
	}
}