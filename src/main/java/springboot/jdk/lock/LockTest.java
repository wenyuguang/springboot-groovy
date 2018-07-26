package springboot.jdk.lock;

import java.util.concurrent.TimeUnit;

/**
 * 
 * LockTest.java
 * Description:通过
 * <p>name: wen </p>
 * <p>Date:2018年7月26日 下午3:42:42</p>
 * @author Tony
 * @version 1.0
 *
 */
public class LockTest {

	static Object share = new Object();
	static int count = 0;

	public static void main(String[] args) {
		new Thread(()->{
			synchronized (share) {
				while (count <= 10000) {
					try {
						TimeUnit.SECONDS.sleep(1);
						// 唤醒等待share资源的线程，把锁交给线程（该同步锁执行完毕自动释放锁或遇到wait方法自动释放锁）
						share.notify();
						System.out.println("thread 1 print " + count++);
						// 释放CPU控制权，释放share的锁，本线程阻塞，等待被唤醒。
						share.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
			}
		}
		}).start();

		new Thread(()-> {
			synchronized (share) {
				while (count <= 10000) {
					try {
						TimeUnit.SECONDS.sleep(1);
						share.notify();
						System.out.println("thread 2 print " + count++);
						share.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}
}
