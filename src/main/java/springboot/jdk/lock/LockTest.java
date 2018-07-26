package springboot.jdk.lock;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

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

	//对象锁
	private static Object share          = new Object();
	//可重入锁
	private static Lock   reentrantLock  = new ReentrantLock();
	//
	private static Condition condition   = reentrantLock.newCondition();
	//允许多个读线程同时访问，但不允许写线程和读线程、写线程和写线程同时访问
	private static ReadWriteLock   reentrantReadWriteLock  = new ReentrantReadWriteLock(true);
	static int count                     = 0;

	public static void main(String[] args) {
//		threadWait();
//		reentrantLock();
//		reentrantReadWriteLock();
		condition();
	}
	
	public static void threadWait() {
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
	
	public static void reentrantLock() {
		new Thread(()->{
			while(count<1000) {
				try {
					reentrantLock.lock();
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}finally {
					reentrantLock.unlock();
				}
				System.out.println("线程1   " + count++);
			}
		}).start();
		new Thread(()->{
			while(count<1000) {
				try {
					reentrantLock.lock();
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}finally {
					reentrantLock.unlock();
				}
				System.out.println("线程2   " + count++);
			}
		}).start();
	}
	
	public static void reentrantReadWriteLock() {
		try {
            BufferedWriter writer = Files.newBufferedWriter(Paths.get("my.ini"), StandardCharsets.UTF_8);
            writer.write("测试文件写操作");
            writer.flush();
            writer.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
		new Thread(()->{
			while(count<1000) {
				try {
					reentrantReadWriteLock.readLock().lock();
					TimeUnit.SECONDS.sleep(1);
					try {
			            BufferedReader reader = Files.newBufferedReader(Paths.get("my.ini"), StandardCharsets.UTF_8);
			            String str = null;
			            while((str = reader.readLine()) != null){
			                System.out.print(str);
			            }
			            System.out.println("    线程1 读取完毕");
			        } catch (IOException e) {
			            e.printStackTrace();
			        }
				} catch (Exception e) {
					e.printStackTrace();
				}finally {
					reentrantReadWriteLock.readLock().unlock();
				}
			}
		}).start();
		new Thread(()->{
			while(count<1000) {
				try {
					reentrantReadWriteLock.readLock().lock();
					TimeUnit.SECONDS.sleep(1);
					try {
			            BufferedReader reader = Files.newBufferedReader(Paths.get("my.ini"), StandardCharsets.UTF_8);
			            String str = null;
			            while((str = reader.readLine()) != null){
			                System.out.print(str);
			            }
			            System.out.println("    线程2 读取完毕");
			        } catch (IOException e) {
			            e.printStackTrace();
			        }
				} catch (Exception e) {
					e.printStackTrace();
				}finally {
					reentrantReadWriteLock.readLock().unlock();
				}
			}
		}).start();
	}
	
	public static void condition() {
		new Thread(()->{
			reentrantLock.lock();
			try {
				System.out.println(Thread.currentThread().getName() + "即将进入等待状态");
				//线程进入等待状态
				condition.await();
			} catch (Exception e) {
				// TODO: handle exception
			}finally {
				reentrantLock.unlock();
			}
			System.out.println(Thread.currentThread().getName() + "  ==》继续执行");
		}) .start();
		new Thread(()->{
			reentrantLock.lock();
			try {
				System.out.println(Thread.currentThread().getName() + "即将进入等待状态");
				//唤醒一个线程
				condition.signal();
				 System.out.println(Thread.currentThread().getName()+" 线程休息结束");
			} catch (Exception e) {
				// TODO: handle exception
			}finally {
				reentrantLock.unlock();
			}
		}) .start();
	}
}
