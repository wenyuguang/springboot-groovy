package springboot.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import org.junit.Test;
/**
 * 一、使用 NIO 完成网络通信的三个核心：
 * 
 * 1. 通道（Channel）：负责连接
 *      
 *     java.nio.channels.Channel 接口：
 *          |--SelectableChannel
 *              |--SocketChannel
 *              |--ServerSocketChannel
 *              |--DatagramChannel
 * 
 *              |--Pipe.SinkChannel
 *              |--Pipe.SourceChannel
 * 
 * 2. 缓冲区（Buffer）：负责数据的存取
 * 
 * 3. 选择器（Selector）：是 SelectableChannel 的多路复用器。用于监控 SelectableChannel 的 IO 状况
 * 
 */
public class BlockingNIOTest {

    @Test
    public void testClient() throws Exception {
         //1.创建通道
        SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 8989));
        //2.准备缓冲区
        FileChannel inChannel = FileChannel.open(Paths.get("1.jpg"), StandardOpenOption.READ)  ;
        ByteBuffer buf=ByteBuffer.allocate(1024)   ;
        //3.读取本地文件,发送到客户端
        while(inChannel.read(buf) != -1){
             buf.flip()  ;
             socketChannel.write(buf)   ;
             buf.clear()  ;
        }
        //4.关闭通道
        inChannel.close();   
        socketChannel.close();  


    }

    @Test
    public void testServer() throws Exception{
        //1.创建通道
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open()  ;
        //2.绑定端口号
        serverSocketChannel.bind(new InetSocketAddress(8989))   ;
        //3.准备Channel和Buffer
        FileChannel outChannel=FileChannel.open(Paths.get("2.jpg"), StandardOpenOption.WRITE,StandardOpenOption.CREATE)  ;
        ByteBuffer buf=ByteBuffer.allocate(1024)   ;
        //4.接收客户端请求
        SocketChannel socketChannel = serverSocketChannel.accept()  ;
        while(socketChannel.read(buf) != -1){
            buf.flip()    ;
            outChannel.write(buf)   ;
            buf.clear()   ;
        }
        //5.关闭流
        socketChannel.close();  
        outChannel.close();   
        serverSocketChannel.close();
    }
}