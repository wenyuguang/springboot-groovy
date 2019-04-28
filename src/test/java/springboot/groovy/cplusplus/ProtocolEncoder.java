package springboot.groovy.cplusplus;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * /**
 * 传输层协议头
*<pre>
* **************************************************************************************************
*                                          Protocol
*  ┌ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ┐
*       2   │   1   │    1   │     8     │      4      │
*  ├ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ┤
*           │       │        │           │             │
*  │  MAGIC   Sign    Status   Invoke Id    Body Size                    Body Content              │
*           │       │        │           │             │
*  └ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ┘
*
 * 消息头16个字节定长
* 2byte 16位 魔数magic = (short) 0xbabe
* 1byte 8位  消息标志位, 高地址4位用来表示序列化类型,低地址4位用来表示消息类型request/response/heartbeat等
* 1byte 8位  状态位, 设置请求响应状态
* 8byte 64位 消息 id, long 类型
* 4byte 32位 消息体 body 长度, int 类型
* </pre>
* Encode.
* @author Jony
* 2019年4月9日
*/
@ChannelHandler.Sharable
public class ProtocolEncoder extends MessageToByteEncoder<byte[]>{

	@Override
	protected void encode(ChannelHandlerContext ctx, byte[] msg, ByteBuf out) throws Exception {
		String str = "   zhe shi yi ge ceshi.这是一个测试";
		byte[] b = str.getBytes();
		
		InputStream in = new FileInputStream("C:\\Users\\wenyu\\eclipse-workspace\\beansprout-demo\\rpc-provider\\target\\rpc-provider-0.0.1-SNAPSHOT.jar");
	    ByteArrayOutputStream o = new ByteArrayOutputStream();
	    byte[] buffer = new byte[1024 * 4];
	    int n = 0;
	    while ((n = in.read(buffer)) != -1) {
	        o.write(buffer, 0, n);
	    }
	    in.close();
	    byte [] bbb = o.toByteArray();
	    System.err.println("str 长度：" + bbb.length);
//	    ctx.writeAndFlush(bbb);
//	    out.writeBytes(bbb);
		System.err.println("msg length :" + msg.length);
		out
		.writeShort(0xbabe)
		.writeByte(0x0f)
        .writeByte(0x02)
        .writeLong(0x01)
        .writeInt(bbb.length)
        .writeBytes(bbb);
		
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

	private void saveFile(byte[] bfile, String filePath, String fileName) {  
        BufferedOutputStream bos = null;  
        FileOutputStream fos = null;  
        File file = null;  
        try {  
            File dir = new File(filePath);  
            if(!dir.exists()&&dir.isDirectory()){//判断文件目录是否存在  
                dir.mkdirs();  
            }  
            file = new File(filePath+"\\"+fileName);  
            fos = new FileOutputStream(file);  
            bos = new BufferedOutputStream(fos);  
            bos.write(bfile);  
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {  
            if (bos != null) {  
                try {  
                    bos.close();  
                } catch (IOException e1) {  
                    e1.printStackTrace();  
                }  
            }  
            if (fos != null) {  
                try {  
                    fos.close();  
                } catch (IOException e1) {  
                    e1.printStackTrace();  
                }  
            }  
        }
	}
}
