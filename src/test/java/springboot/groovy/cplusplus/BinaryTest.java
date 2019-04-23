package springboot.groovy.cplusplus;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * 小端：较高的有效字节存放在较高的的存储器地址，较低的有效字节存放在较低的存储器地址。

	大端：较高的有效字节存放在较低的存储器地址，较低的有效字节存放在较高的存储器地址。
	JVM中,实际是以大端存储的
 * @author Jony
 * 2019年4月23日
 */
public class BinaryTest {

	public static void main(String[] args) throws IOException {
		int aa = 0xbabe;
		System.out.println(aa);
		int a = 0x12345678;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(baos);
		dos.writeInt(a);
		byte[] b = baos.toByteArray();
		for(int i = 0;i<4;i++){
			System.out.println(Integer.toHexString(b[i]));
		}
	}
}
