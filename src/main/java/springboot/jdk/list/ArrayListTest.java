package springboot.jdk.list;

/**
 * 
 * 为什么数组长度的最大值是(Integer.MAX_VALUE - 8),
 * 数组作为一个对象，需要一定的内存存储对象头信息，
 * 对象头信息最大占用内存不可超过8字节。
 * 数组的对象头信息相较于其他Object，多了一个表示数组长度的信息
 *
 */
public class ArrayListTest {

	private static int i;
	public static void main(String[] args) {
		System.out.println(i);
	}

}
