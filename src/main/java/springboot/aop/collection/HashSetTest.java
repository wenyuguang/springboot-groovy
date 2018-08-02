package springboot.aop.collection;

import java.util.HashSet;
import java.util.Set;

public class HashSetTest {

	public static void main(String[] args) {
		Object[] str = new Object[5];
		Set<String> hashSet = new HashSet<>();
		hashSet.add("test");
		hashSet.add("test1");
		hashSet.add("test2");
		hashSet.toArray(str);
		System.out.println(str);
		System.out.println(hashSet);
	}
}
