package springboot.jdk.hashmap;

/**
 * 
 * 
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
