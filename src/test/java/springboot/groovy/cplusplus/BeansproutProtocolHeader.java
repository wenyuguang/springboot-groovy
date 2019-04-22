package springboot.groovy.cplusplus;

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
* @author Jony
* 2019年4月9日
*/
public class BeansproutProtocolHeader {

   /** 协议头长度 */
   public static final int HEADER_SIZE = 16;
   /** Magic */
   public static final short MAGIC = (short) 0xbabe;

   //Message Code: 0x01 ~ 0x0f
   /** 请求 */
   public static final byte REQUEST                    = 0x01;     // Request
   /** 响应 */
   public static final byte RESPONSE                   = 0x02;     // Response
   /** 发布服务 */
   public static final byte PUBLISH_SERVICE            = 0x03;     // 发布服务
   /** 取消发布服务 */
   public static final byte PUBLISH_CANCEL_SERVICE     = 0x04;     // 取消发布服务
   /** 订阅服务 */
   public static final byte SUBSCRIBE_SERVICE          = 0x05;     // 订阅服务
   /** 通知下线 */
   public static final byte OFFLINE_NOTICE             = 0x06;     // 通知下线
   /** ack */
   public static final byte ACK                        = 0x07;     // Acknowledge
   /** 心跳 */
   public static final byte HEARTBEAT                  = 0x0f;     // Heartbeat
   /** sign 低地址4位 */
   private byte messageCode;       // sign 低地址4位

   /** Serializer Code: 0x01 ~ 0x0f */
   // 位数限制最多支持15种不同的序列化/反序列化方式
   // protostuff   = 0x01
   // hessian      = 0x02
   // kryo         = 0x03
   // java         = 0x04
   // ...
   // XX1          = 0x0e
   // XX2          = 0x0f
   /** sign 高地址4位 */
   private byte serializerCode;    // sign 高地址4位
   /** 响应状态码 */
   private byte status;            // 响应状态码
   /** 请求id */
   private long id;                // request.invokeId, 用于映射 <id, request, response> 三元组
   /** 消息长度 */
   private int bodySize;           // 消息体长度

   public static byte toSign(byte serializerCode, byte messageCode) {
       return (byte) ((serializerCode << 4) | (messageCode & 0x0f));
   }

   public void sign(byte sign) {
       // sign 低地址4位
       this.messageCode = (byte) (sign & 0x0f);
       // sign 高地址4位, 先转成无符号int再右移4位
       this.serializerCode = (byte) ((((int) sign) & 0xff) >> 4);
   }

   public byte messageCode() {
       return messageCode;
   }

   public byte serializerCode() {
       return serializerCode;
   }

   public byte status() {
       return status;
   }

   public void status(byte status) {
       this.status = status;
   }

   public long id() {
       return id;
   }

   public void id(long id) {
       this.id = id;
   }

   public int bodySize() {
       return bodySize;
   }

   public void bodySize(int bodyLength) {
       this.bodySize = bodyLength;
   }

   @Override
   public String toString() {
       return "BeansproutProtocolHeader{" +
               "messageCode=" + messageCode +
               ", serializerCode=" + serializerCode +
               ", status=" + status +
               ", id=" + id +
               ", bodySize=" + bodySize +
               '}';
   }
}