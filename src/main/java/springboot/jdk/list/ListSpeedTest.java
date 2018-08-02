package springboot.jdk.list;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

public class ListSpeedTest {

	private static int maxTestCount = 100000;

	public static void main(String[] args) {
		testArrayList();
		testLinkedList();
	}

	private static void testArrayList() {
		ArrayList<String> list = new ArrayList<>();

		// 测试添加
		long start = System.currentTimeMillis();
		for (int i = 0; i < maxTestCount; i++) {
			list.add(0, String.valueOf(i));
		}
		long end = System.currentTimeMillis();
		System.out.println("ArrayList add 操作耗时 :" + (end - start));
		// 测试查询
		start = System.currentTimeMillis();
		for (int i = 0; i < maxTestCount; i++) {
			list.get(i);
		}
		end = System.currentTimeMillis();
		System.out.println("ArrayList get 操作耗时 :" + (end - start));
		// 测试查询
		start = System.currentTimeMillis();
		for (int i = maxTestCount; i > 0; i--) {
			list.remove(0);
		}
		end = System.currentTimeMillis();
		System.out.println("ArrayList remove 操作耗时 :" + (end - start));
	}

	private static void testLinkedList() {
		LinkedList<String> list = new LinkedList<>();
		// 测试添加
		long start = System.currentTimeMillis();
		for (int i = 0; i < maxTestCount; i++) {
			list.add(0, String.valueOf(i));
		}
		long end = System.currentTimeMillis();
		System.out.println("LinkedList add 操作耗时 :" + (end - start));
		// 测试查询
		start = System.currentTimeMillis();
		for (int i = 0; i < maxTestCount; i++) {
			list.get(i);
		}
		end = System.currentTimeMillis();
		System.out.println("LinkedList get for 循环操作耗时 :" + (end - start));
		start = System.currentTimeMillis();
		// 测试查询
		Iterator<String> it = list.iterator();
		while (it.hasNext()) {
			String s = it.next();
		}
		end = System.currentTimeMillis();
		System.out.println("LinkedList get Iterator 循环操作耗时 :" + (end - start));
		// 测试查询
		start = System.currentTimeMillis();
		for (int i = maxTestCount; i > 0; i--) {
			list.remove(0);
		}
		end = System.currentTimeMillis();
		System.out.println("LinkedList remove 操作耗时 :" + (end - start));

	}
}
