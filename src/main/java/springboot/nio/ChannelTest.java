package springboot.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.junit.Test;
/**
 * 一、通道（Channel）：用于源节点与目标节点的连接。在 Java NIO 中负责缓冲区中数据的传输。Channel 本身不存储数据，因此需要配合缓冲区进行传输。
 * 
 * 二、通道的主要实现类
 *  java.nio.channels.Channel 接口：
 *      |--FileChannel
 *      |--SocketChannel
 *      |--ServerSocketChannel
 *      |--DatagramChannel
 * 
 * 三、获取通道
 * 1. Java 针对支持通道的类提供了 getChannel() 方法
 *      本地 IO：
 *      FileInputStream/FileOutputStream
 *      RandomAccessFile
 * 
 *      网络IO：
 *      Socket
 *      ServerSocket
 *      DatagramSocket
 *      
 * 2. 在 JDK 1.7 中的 NIO.2 针对各个通道提供了静态方法 open()
 * 3. 在 JDK 1.7 中的 NIO.2 的 Files 工具类的 newByteChannel()
 * 
 * 四、通道之间的数据传输
 * transferFrom()
 * transferTo()
 * 
 * 五、分散(Scatter)与聚集(Gather)
 * 分散读取（Scattering Reads）：将通道中的数据分散到多个缓冲区中
 * 聚集写入（Gathering Writes）：将多个缓冲区中的数据聚集到通道中
 * 
 * 六、字符集：Charset
 * 编码：字符串 -> 字节数组
 * 解码：字节数组  -> 字符串
 * 
 */
public class ChannelTest {

    @Test
    public void testEncDec() throws Exception{
          Charset charset = Charset.forName("GBK");
          CharsetEncoder encoder = charset.newEncoder();
          CharsetDecoder decoder = charset.newDecoder()  ;
          CharBuffer charBuf = CharBuffer.allocate(1024);
          charBuf.put("HelloWorld,世界你好!");
          charBuf.flip()  ;
          ByteBuffer byteBuffer = encoder.encode(charBuf);
          for(int x=0; x<byteBuffer.limit(); x++){
              System.out.print(byteBuffer.get()+"、");
          }
          System.out.println();
          byteBuffer.flip();
          CharBuffer charBuffer = decoder.decode(byteBuffer);
          System.out.println(charBuffer.toString());
          System.out.println("-----------------------------");
//          Charset charset2 = Charset.forName("UTF-8");
          Charset charset2 = Charset.forName("GBK");
          byteBuffer.flip();
          CharBuffer charBuffer2 = charset2.decode(byteBuffer)  ;
          System.out.println(charBuffer2.toString());
    }

    @Test
    public void testCharset(){
        Map<String,Charset> charsets = Charset.availableCharsets()  ;
        Set<Entry<String,Charset>> set = charsets.entrySet()  ;
        for(Entry<String,Charset> e : set ){
            System.out.println(e.getKey()+"="+e.getValue());
        }
    }

    @Test
    public void testScatterAndGather()throws Exception{
          RandomAccessFile raf=new RandomAccessFile("1.txt", "rw")  ;
          FileChannel inChannel = raf.getChannel()  ;

          ByteBuffer buf=ByteBuffer.allocate(100);
          buf.put("testsdasdsa".getBytes());
          ByteBuffer buf2=ByteBuffer.allocate(1024);
          ByteBuffer bufs[]={buf,buf2};

          inChannel.read(bufs);
          for(ByteBuffer byteBuf : bufs){
              byteBuf.flip()  ;   //切换到读取模式
          }
          System.out.println(new String(bufs[0].array(),0,bufs[0].limit()));
          System.out.println("------------------------------------------------");
          System.out.println(new String(bufs[1].array(),0,bufs[1].limit()));

          RandomAccessFile raf2=new RandomAccessFile("2.txt", "rw")  ;
          FileChannel outChannel = raf2.getChannel()  ;
          outChannel.write(bufs)  ;

          outChannel.close();   
          inChannel.close();
          raf.close();
          raf2.close();

    }

    @Test
    public void testChannel() throws Exception{
          long start=System.currentTimeMillis();
          FileInputStream fis=null ;
          FileOutputStream fos=null ;

          fis=new FileInputStream("1.txt");
          fos=new FileOutputStream("2.txt") ;

          //1.获取通道
          FileChannel inChannel = fis.getChannel();
          FileChannel outChannel = fos.getChannel();

          //2.准备缓冲区
          ByteBuffer buf=ByteBuffer.allocate(1024);

          //3.读写
          while(inChannel.read(buf) != -1){  //读
               buf.flip();      //切换到读取模式
               outChannel.write(buf)  ;   //写
               buf.clear();           //清空缓冲区,准备再次读取
          }

          //4.关闭流
          outChannel.close();  
          inChannel.close();   
          fos.close();   
          fis.close();   

          long end=System.currentTimeMillis();
          System.out.println("拷贝任务耗时:"+(end-start)+" 毫秒");         

    }

    @Test
    public void testDirectChannel()throws Exception{
           long start=System.currentTimeMillis();
           FileChannel inChannel = FileChannel.open(Paths.get("1.jpg"), StandardOpenOption.READ);
           FileChannel outChannel = FileChannel.open(Paths.get("2.jpg"), StandardOpenOption.READ,
                                                                         StandardOpenOption.WRITE,
                                                                         StandardOpenOption.CREATE);
           //内存映射文件
           MappedByteBuffer inMappedBuf = inChannel.map(MapMode.READ_ONLY, 0, inChannel.size());
           MappedByteBuffer outMappedBuf = outChannel.map(MapMode.READ_WRITE, 0, inChannel.size());

           //直接对缓冲区中的数据进行读写操作
           byte[] temp=new byte[1024];   
           inMappedBuf.get(temp);
           outMappedBuf.put(temp) ;

           inChannel.close();   
           outChannel.close();   

           long end=System.currentTimeMillis();
           System.out.println("拷贝任务耗时:"+(end-start)+" 毫秒");        

    } 



    @Test
    public void testTransform()throws Exception{
        FileChannel inChannel = FileChannel.open(Paths.get("1.jpg"), StandardOpenOption.READ)  ;
        FileChannel outChannel = FileChannel.open(Paths.get("4.jpg"), StandardOpenOption.READ,
                                                                      StandardOpenOption.WRITE,
                                                                      StandardOpenOption.CREATE ) ;
        //inChannel.transferTo(0, inChannel.size(), outChannel);
        outChannel.transferFrom(inChannel, 0, inChannel.size());
        inChannel.close();
        outChannel.close();  
    }
}