package springboot.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import org.junit.Test;

public class BlockingNIOTestPlus {

    @Test
    public void testClent() throws Exception{
        SocketChannel client = SocketChannel.open(new InetSocketAddress("127.0.0.1", 9999))   ;
        FileChannel inChannel = FileChannel.open(Paths.get("1.txt"), StandardOpenOption.READ)   ;
        ByteBuffer buf=ByteBuffer.allocate(1024)     ;
        while(inChannel.read(buf) != -1){
              buf.flip()    ;
              client.write(buf)    ;
              buf.clear()    ;
        }
        client.shutdownOutput()    ;    //结束输出

        //接收服务器反馈
        while(client.read(buf) != -1){
               buf.flip()    ;
               System.out.println(new String(buf.array(),0,buf.limit()));
               buf.clear()   ;
        }

        inChannel.close()  ;
        client.close()  ;  





    }

    @Test
    public void testServer() throws Exception{
        ServerSocketChannel server = ServerSocketChannel.open()   ;
        server.bind(new InetSocketAddress(9999))   ;
        FileChannel outChannel = FileChannel.open(Paths.get("2.jpg"), 
                                                  StandardOpenOption.WRITE,
                                                  StandardOpenOption.CREATE   )   ;
        ByteBuffer buf=ByteBuffer.allocate(1024)  ;
        SocketChannel client = server.accept();
        while(client.read(buf)!=-1){
              buf.flip()    ;
              outChannel.write(buf)    ;
              buf.clear()   ;
        }
        //发送反馈给客户端
        buf.put("乖儿子,爸爸接收到黄图啦！！！".getBytes())  ;
        buf.flip()   ;
        client.write(buf)   ;

        client.close();   
        outChannel.close();    
        server.close();    
    }
}