package springboot.groovy.cplusplus;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

/**
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
* Decode.
* @author Jony
* 2019年4月9日
*/
@ChannelHandler.Sharable
public class ProtocolDecoder extends ReplayingDecoder<byte[]> {

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		state();
	}
}
