package springboot.exception.jvm;

/**
 * 
 * ThreadDeadlockTest.java
 * Description: 锁竞争导致死锁
 * <p>name: wen </p>
 * <p>Date:2018年7月31日 下午3:51:07</p>
 * @author Tony
 * @version 1.0
 *
 */
public class ThreadDeadlockTest {

	public static void main(String[] args) throws InterruptedException {
		Object obj1 = new Object();
		Object obj2 = new Object();
		Object obj3 = new Object();

		Thread t1 = new Thread(new SyncThread(obj1, obj2), "线程1");
		Thread t2 = new Thread(new SyncThread(obj2, obj3), "线程2");
		Thread t3 = new Thread(new SyncThread(obj3, obj1), "线程3");

		t1.start();
		Thread.sleep(5000);
		t2.start();
		Thread.sleep(5000);
		t3.start();

	}

}

class SyncThread implements Runnable {
	private Object obj1;
	private Object obj2;

	public SyncThread(Object o1, Object o2) {
		this.obj1 = o1;
		this.obj2 = o2;
	}

	@Override
	public void run() {
		String name = Thread.currentThread().getName();
		System.out.println(name + " 请求锁 :" + obj1);
		synchronized (obj1) {
			System.out.println(name + " 请求锁 :" + obj1);
			work();
			System.out.println(name + " 请求锁 :" + obj2);
			synchronized (obj2) {
				System.out.println(name + " 请求锁 :" + obj2);
				work();
			}
			System.out.println(name + " 释放锁：" + obj2);
		}
		System.out.println(name + " 释放锁：" + obj1);
		System.out.println(name + " 完成。");
	}

	private void work() {
		try {
			Thread.sleep(30000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}