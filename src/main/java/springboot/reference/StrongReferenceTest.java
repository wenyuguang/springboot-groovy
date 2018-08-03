package springboot.reference;

import java.util.concurrent.TimeUnit;

/**
 * 
 * StrongReferenceTest.java
 * Description:强引用是使用最普遍的引用。
 * 如果一个对象具有强引用，那垃圾回收器绝不会回收它。
 * 当内存空间不足，Java虚拟机宁愿抛出OutOfMemoryError错误，使程序异常终止，
 * 也不会靠随意回收具有强引用的对象来解决内存不足的问题。
 * <p>name: wen </p>
 * <p>Date:2018年8月3日 下午4:34:45</p>
 * @author Tony
 * @version 1.0
 *
 */
public class StrongReferenceTest {
	public static void main(String[] args) throws InterruptedException {
		StrongReferenceTest object = new StrongReferenceTest();

		System.gc();
		TimeUnit.SECONDS.sleep(1);// 暂停一秒钟

		System.out.println(object == null);// false
	}
}