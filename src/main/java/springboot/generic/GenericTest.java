package springboot.generic;

import java.util.ArrayList;
import java.util.List;

public class GenericTest {

	public static void main(String[] args) {
		List<String> l1 = new ArrayList<String>();
		List<Integer> l2 = new ArrayList<Integer>();

		System.out.println(l1.getClass() == l2.getClass());
		
		Cache<String> cache1 = new Cache<String>();
		cache1.setValue("123");
		Object value2 = cache1.getValue();

		Cache<Integer> cache2 = new Cache<Integer>();
		cache2.setValue(456);
		Object value3 = cache2.getValue();
		System.out.println(value2);
		System.out.println(value3);
	}
}
