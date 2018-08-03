package springboot.reference;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * SoftReferenceTest.java
 * Description:如果一个对象只具有软引用，
 * 则内存空间足够，垃圾回收器就不会回收它；
 * 如果内存空间不足了，就会回收这些对象的内存。
 * 只要垃圾回收器没有回收它，该对象就可以被程序使用。
 * 软引用可用来实现内存敏感的高速缓存。
 * 软引用可以和一个引用队列（ReferenceQueue）联合使用，
 * 如果软引用所引用的对象被垃圾回收器回收，Java虚拟机就会把这个软引用加入到与之关联的引用队列中。
 * <p>name: wen </p>
 * <p>Date:2018年8月3日 下午4:35:02</p>
 * @author Tony
 * @version 1.0
 *
 */
public class SoftReferenceTest {

    static class HeapObject {
        byte[] bs = new byte[10240000 * 1024];
    }

    public static void main(String[] args) {
        ReferenceQueue<HeapObject> queue=new ReferenceQueue<>();
        SoftReference<HeapObject> softReference = new SoftReference<>(new HeapObject(),queue);

        List<HeapObject> list = new ArrayList<>();

        while (true) {
            if (softReference.get() != null) {
                list.add(new HeapObject());
                System.out.println("list.add");
            } else {
                System.out.println("---------软引用已被回收---------");
                break;
            }
            System.gc();
        }
        Reference<? extends  HeapObject> pollRef = queue.poll();
        while (pollRef != null) {
            System.out.println(pollRef);
            System.out.println(pollRef.get());
            pollRef = queue.poll();
        }
    }
}