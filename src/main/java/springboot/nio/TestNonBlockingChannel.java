package springboot.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.Iterator;
import java.util.Scanner;

import org.junit.Test;

/**
 * 一、使用 NIO 完成网络通信的三个核心：
 * 
 * 1. 通道（Channel）：负责连接
 * 
 * java.nio.channels.Channel 接口： |--SelectableChannel |--SocketChannel
 * |--ServerSocketChannel |--DatagramChannel
 * 
 * |--Pipe.SinkChannel |--Pipe.SourceChannel
 * 
 * 2. 缓冲区（Buffer）：负责数据的存取
 * 
 * 3. 选择器（Selector）：是 SelectableChannel 的多路复用器。用于监控 SelectableChannel 的 IO 状况
 * 
 */
public class TestNonBlockingChannel {

	@Test
	public void testClient() throws Exception {
		// 创建客户端通道
		SocketChannel sChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 6666));
		// 配置为非阻塞式NIO
		sChannel.configureBlocking(false);
		// 准备缓冲区
		ByteBuffer buffer = ByteBuffer.allocate(1024);
		Scanner sc = new Scanner(System.in);
		while (sc.hasNext()) {
			buffer.put((new Date().toString() + " : " + sc.next()).getBytes());
			buffer.flip(); // 切换成读模式
			// 向服务器端发送消息
			sChannel.write(buffer);
			buffer.clear();
		}
		sc.close();
		// 关闭通道
		sChannel.close();

	}

	@Test
	public void testServer() throws Exception {
		// 1.创建服务器端通道
		ServerSocketChannel ssChannel = ServerSocketChannel.open();
		// 2.绑定端口号
		ssChannel.bind(new InetSocketAddress(6666));
		// 3.设置非阻塞模式
		ssChannel.configureBlocking(false);
		// 4.获取选择器
		Selector selector = Selector.open();
		// 5.将选择器注册到通道上
		ssChannel.register(selector, SelectionKey.OP_ACCEPT);
		// 6.以轮训的方式获取选择器上已经准备就绪的事件
		while (selector.select() > 0) {
			// 7.接收全部选择键
			Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
			while (iterator.hasNext()) {
				// 8.接收选择键
				SelectionKey selectionKey = iterator.next();
				// 9.根据键值判断具体是什么事件
				if (selectionKey.isAcceptable()) {
					// 10.接收就绪,获取客户端连接
					SocketChannel sChannel = ssChannel.accept();
					// 11.切换到非阻塞模式
					sChannel.configureBlocking(false);
					// 12.将通道注册到选择器上
					sChannel.register(selector, SelectionKey.OP_READ);
				} else if (selectionKey.isReadable()) {
					// 13.获取读状态的通道
					SocketChannel sChannel = (SocketChannel) selectionKey.channel();
					ByteBuffer dst = ByteBuffer.allocate(1024);
					// 14.读取数据
					Integer length = 0;
					while ((length = sChannel.read(dst)) > 0) {
						dst.flip();
						System.out.println(new String(dst.array(), 0, length));
						dst.clear();
					}

				}
				// 15.取消选择键
				iterator.remove();
			}

		}
	}
}