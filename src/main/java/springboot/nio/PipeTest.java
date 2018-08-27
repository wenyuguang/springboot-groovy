package springboot.nio;

import java.nio.ByteBuffer;
import java.nio.channels.Pipe;
import java.nio.channels.Pipe.SinkChannel;
import java.nio.channels.Pipe.SourceChannel;

import org.junit.Test;

public class PipeTest {

	@Test
	public void testPipe() throws Exception {
		// 1.获取管道
		Pipe pipe = Pipe.open();
		SinkChannel sinkChannel = pipe.sink();
		// 2.准备缓冲区
		ByteBuffer buf = ByteBuffer.allocate(1024);
		buf.put("单向管道发送数据".getBytes());
		buf.flip();
		// 4.发送数据
		sinkChannel.write(buf);
		buf.clear();
		// 5.接收数据
		SourceChannel sourceChannel = pipe.source();
		sourceChannel.read(buf);
		buf.flip();
		System.out.println(new String(buf.array(), 0, buf.limit()));
	}
}