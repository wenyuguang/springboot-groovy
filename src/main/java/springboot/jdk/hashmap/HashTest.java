package springboot.jdk.hashmap;

/**
 * HashMap容量为2次幂的原因：
 * int index = (n - 1) & hash
 * 下标利用拉链法解决碰撞冲突，可以实现一个均匀分布，空间利用率高，如果不采用2次幂会造成部分浪费
 *
 */
public class HashTest {

	public static void main(String[] args) {
		int[] hashcode = new int[] { 100000001, 100000011, 100000111,100001111, 100011111, 100111111, 101111111, 111111111 };
		for (int c : hashcode)
		    System.out.println(hash(c)%10);
		for (int c : hashcode)
		    System.err.println(hash(hash(c)));
		System.out.println(hash("test"));
	}
	
	/**
     * key的hash值与自己低16位进行异或运算
     */
    static final int hash(Object key) {
        int h;
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }
}
