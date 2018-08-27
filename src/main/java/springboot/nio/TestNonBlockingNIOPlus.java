package springboot.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Date;
import java.util.Iterator;
import java.util.Scanner;

import org.junit.Test;

public class TestNonBlockingNIOPlus {

    @Test
    public void testSend() throws Exception{
       DatagramChannel dc=DatagramChannel.open();
       dc.configureBlocking(false) ;
       ByteBuffer buf=ByteBuffer.allocate(1024) ;
       Scanner sc=new Scanner(System.in)  ;
       while(sc.hasNext()){
           String msg=sc.next() ;
           buf.put((new Date().toString()+" : "+msg).getBytes())  ;
           buf.flip();
           dc.send(buf, new InetSocketAddress("127.0.0.1",9999))  ;
           buf.clear() ;
       }
       dc.close();  
    }


    @Test
    public void testReceive() throws Exception{
         DatagramChannel dc = DatagramChannel.open()  ;
         dc.configureBlocking(false) ;
         dc.bind(new InetSocketAddress(9999));
         Selector selector = Selector.open()  ;
         dc.register(selector, SelectionKey.OP_READ);
         while(selector.select()>0){
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator()  ;
                while(iterator.hasNext()){
                    SelectionKey selectionKey = iterator.next();
                    if(selectionKey.isReadable()){
                        ByteBuffer buf=ByteBuffer.allocate(1024)  ;
                        dc.receive(buf);
                        buf.flip();
                        System.out.println(new String(buf.array(),0,buf.limit()));
                        buf.clear()  ;
                    }
                }
                iterator.remove();    
         }
    }
}