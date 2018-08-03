package springboot.reference;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;

/**
 * 
 * PhantomReferenceTest.java
 * Description: “虚引用”顾名思义，就是形同虚设，与其他几种引用都不同，虚引用并不会决定对象的生命周期。
 * 如果一个对象仅持有虚引用，那么它就和没有任何引用一样，在任何时候都可能被垃圾回收器回收。
 * <p>name: wen </p>
 * <p>Date:2018年8月3日 下午4:42:41</p>
 * @author Tony
 * @version 1.0
 *
 */
public class PhantomReferenceTest {
	static class TestObject {

	}

	public static void main(String[] args) throws InterruptedException {
		ReferenceQueue<TestObject> queue = new ReferenceQueue<>();
		PhantomReference<TestObject> phantomReference = new PhantomReference<>(new TestObject(), queue);

		System.out.println(phantomReference.get() == null);// true

	}
}