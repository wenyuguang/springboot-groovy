package springboot.nio;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class Test1 {
    public static void main(String[] args) throws IOException  {
        File file = new File("data.txt");
        FileOutputStream outputStream = new FileOutputStream(file);
        FileChannel channel = outputStream.getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        String string = "java nio";
        buffer.put(string.getBytes());
        buffer.flip();//此处必须要调用buffer的flip方法
        channel.write(buffer);
        channel.close();
        outputStream.close();
    }  
}