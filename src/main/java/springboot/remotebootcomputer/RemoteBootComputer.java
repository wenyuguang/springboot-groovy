package springboot.remotebootcomputer;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;

/**
 * <pre>
  * 魔术包是用16进制表示的数据包，它由固定的前缀数据以及固定重复次数的目标主机MAC地址所组成。
  * 所谓固定前缀数据即6对“FF”，所谓固定重复次数即16次。
  * 也就是说魔术包是由12个“F”加重复16次的主机MAC地址组成。
 * </pre>
 * zcht
 * @author Jony
 *
 */
public class RemoteBootComputer {

	/**
     * main方法，发送UDP广播，实现远程开机
     */
    public static void main(String[] args) {
        String ip = "";//主机ip地址
        String mac = "".replace("-", "").replace(":", "");//主机mac地址
        int port = 800;//端口号
        //魔术包数据 
        StringBuilder stringBuilber = new StringBuilder();
        for(int i = 0; i < 16; i ++) {
        	stringBuilber.append(mac);
        }
        String magicPacage = "0xFFFFFFFFFFFF" + stringBuilber.toString();
        //转换为2进制的魔术包数据
        byte[] command = hexToBinary(magicPacage);

        //广播魔术包
        try {
            //1.获取ip地址
            InetAddress address = InetAddress.getByName(ip);
            //2.获取广播socket
            MulticastSocket socket = new MulticastSocket(port);
            //3.封装数据包
            DatagramPacket packet = new DatagramPacket(command, command.length, address, port);
            //4.发送数据
            socket.send(packet);
            //5.关闭socket
            socket.close();
        } catch (UnknownHostException e) {
            //Ip地址错误时候抛出的异常
            e.printStackTrace();
        } catch (IOException e) {
            //获取socket失败时候抛出的异常
            e.printStackTrace();
        }
    }

    /**
     * 将16进制字符串转换为用byte数组表示的二进制形式
     * @param hexString：16进制字符串
     * @return：用byte数组表示的十六进制数
     */
    private static byte[] hexToBinary(String hexString){
        //1.定义变量：用于存储转换结果的数组
        byte[] result = new byte[hexString.length()];

        //2.去除字符串中的16进制标识"0X"并将所有字母转换为大写
        hexString = hexString.toUpperCase().replace("0X", "");

        //3.开始转换
        //3.1.定义两个临时存储数据的变量
        char tmp1 = '0';
        char tmp2 = '0';
        //3.2.开始转换，将每两个十六进制数放进一个byte变量中
        for(int i = 0; i < hexString.length(); i += 2){
            result[i/2] = (byte)((hexToDec(tmp1)<<4)|(hexToDec(tmp2)));
        }
        return result;
    }

    /**
          * 用于将16进制的单个字符映射到10进制的方法
     * @param c：16进制数的一个字符
     * @return：对应的十进制数
     */
    private static byte hexToDec(char c){
        return (byte)"0123456789ABCDEF".indexOf(c);
    }
}
