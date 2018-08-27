package springboot.nio.test1;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class NIOServer {

	public static int id = 100001;
	public static int bufferSize = 2048;
	public static void main(String[] args) throws IOException {
		
		//创建通道
		ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
		//创建选择器
		Selector selector = Selector.open();
		//创建socket
		InetSocketAddress inetSocketAddress = new InetSocketAddress("0.0.0.0", 80);
		//通道绑定socket
		serverSocketChannel.socket().bind(inetSocketAddress);
		//设置为阻塞模式
		serverSocketChannel.configureBlocking(false);
		//绑定选择器 
		serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT).attach(id++);
		System.out.println("Server started .... port:80");
		listener(selector);
	}

	private static void listener(Selector selector) throws IOException {
		
		for(;;) {
			try {
				selector.select();
				Set<SelectionKey> selectionKeySet = selector.selectedKeys();
				Iterator<SelectionKey> it = selectionKeySet.iterator();
				while(it.hasNext()) {
					SelectionKey selectionKey = it.next();
					//不移除会再次循环处理
					it.remove();
					//判断事件类型
					if(selectionKey.isAcceptable()) {
						System.out.println(selectionKey.attachment() + "--接受请求事件");
						ServerSocketChannel serverSocketChannel  = (ServerSocketChannel )selectionKey.channel();
						serverSocketChannel.accept().configureBlocking(false)
						.register(selector, SelectionKey.OP_WRITE | SelectionKey.OP_READ).attach(id++);
						System.out.println(selectionKey.attachment() + " - 已连接");
						
					}
					//可读操作
					if(selectionKey.isReadable()) {
						System.out.println(selectionKey.attachment() + " - 读数据事件");
						SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
						ByteBuffer byteBuffer = ByteBuffer.allocate(bufferSize);
						socketChannel.read(byteBuffer);
						System.out.println(selectionKey.attachment() + " - 读取数据：" + new String(byteBuffer.array()).trim());
						socketChannel.close();
					}
					if(selectionKey.isWritable()) {
						System.out.println(selectionKey.attachment() + " - 写数据事件");
						SocketChannel socketChannel = (SocketChannel)selectionKey.channel();
						ByteBuffer byteBuffer = ByteBuffer.allocate(bufferSize);
						String sendText = "这是一个测试发送消息\n";
						byteBuffer.put(sendText.getBytes());
						byteBuffer.flip();
						while(byteBuffer.hasRemaining()) {
							socketChannel.write(byteBuffer);
						}
					}
					if(selectionKey.isConnectable()) {
						System.out.println(selectionKey.attachment() + " - 连接事件");
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}
		}
	}
}