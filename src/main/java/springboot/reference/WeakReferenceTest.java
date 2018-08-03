package springboot.reference;

import java.lang.ref.WeakReference;
import java.util.concurrent.TimeUnit;

/**
 * 
 * WeakReferenceTest.java
 * Description:弱引用与软引用的区别在于：只具有弱引用的对象拥有更短暂的生命周期。
 * 在垃圾回收器线程扫描它所管辖的内存区域的过程中，一旦发现了只具有弱引用的对象，
 * 不管当前内存空间足够与否，都会回收它的内存。
 * 不过，由于垃圾回收器是一个优先级很低的线程，因此不一定会很快发现那些只具有弱引用的对象。
 * <p>name: wen </p>
 * <p>Date:2018年8月3日 下午4:38:41</p>
 * @author Tony
 * @version 1.0
 *
 */
public class WeakReferenceTest {
    static class TestObject{

    }

    public static void main(String[] args) throws InterruptedException {
        WeakReference<TestObject> weakReference=new WeakReference<>(new TestObject());

        System.out.println(weakReference.get() == null);//false

        System.gc();
        TimeUnit.SECONDS.sleep(1);//暂停一秒钟

        System.out.println(weakReference.get() == null);//true
    }
}