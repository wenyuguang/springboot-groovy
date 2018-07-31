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
 * Description:synchronized是可中断锁，而Lock是可中断锁。
 * <\n>
 * 自旋锁、阻塞锁、重入锁、偏向锁、轻量锁和重量锁
 * <p>
 * 1、自旋锁：
 * 采用让当前线程不停的在循环体内执行实现，当循环的条件被其它线程改变时才能进入临界区
 * <\n>
 * 优缺点分析：
 * 由于自旋锁只是将当前线程不停地执行循环体，不进行线程状态的改变，所以响应速度更快。但当线程数不停增加时，性能下降明显，因为每个线程都需要执行，占用CPU时间。如果线程竞争不激烈，并且保持锁的时间段。适合使用自旋锁。
 * <p>
 * 2、阻塞锁：
 * 阻塞锁改变了线程的运行状态，让线程进入阻塞状态进行等待，当获得相应的信号（唤醒或者时间）时，才可以进入线程的准备就绪状态，转为就绪状态的所有线程，通过竞争，进入运行状态。
 * <p>
 * 优缺点分析：
 * 阻塞锁的优势在于，阻塞的线程不会占用cpu时间，不会导致 CPu占用率过高，但进入时间以及恢复时间都要比自旋锁略慢。在竞争激烈的情况下 阻塞锁的性能要明显高于自旋锁。
 * <p>
 * 3、重入锁：
 * Java中的synchronized同步块是可重入的。这意味着如果一个java线程进入了代码中的synchronized同步块，
 * 并因此获得了该同步块使用的同步对象对应的管程上的锁，那么这个线程可以进入由同一个管程对象所同步的另
 * 一个java代码块。
 * <\n>
 * ReentrantLock与synchronized比较：
 * 前者使用灵活，但是必须手动开启和释放锁
 * 前者扩展性好，有时间锁等候(tryLock())，可中断锁等候(lockInterruptibly())，锁投票等，
 * 适合用于高度竞争锁和多个条件变量的地方前者提供了可轮询的锁请求，可以尝试去获取锁(tryLock())，
 * 如果失败，则会释放已经获得的锁。有完善的错误恢复机制，可以避免死锁的发生。
 * <\n>
 * 优缺点分析：
 * 可重入锁的最大优点就是可以避免死锁。缺点是必须手动开启和释放锁。
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
		reentrantTryLock();
//		reentrantReadWriteLock();
//		condition();
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
	
	public static void reentrantTryLock() {
		new Thread(()->{
			while(count<1000) {
				if(reentrantLock.tryLock()) {
					try {
						TimeUnit.NANOSECONDS.sleep(1);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}finally {
						reentrantLock.unlock();
					}
					System.out.println("线程1获取到锁   " + count++);
				}else {
					System.out.println("线程1未获取到锁");
				}
			}
		}).start();
		new Thread(()->{
			while(count<1000) {
				if(reentrantLock.tryLock()) {
					try {
						TimeUnit.NANOSECONDS.sleep(1);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}finally {
						reentrantLock.unlock();
					}
					System.out.println("线程2获取到锁   " + count++);
				}else {
					System.out.println("线程2未获取到锁");
				}
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
