package springboot.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadPoolTest {

	public static void main(String[] args) throws InterruptedException {
		/**
		 * 缓存线程池，60秒后会回收空闲线程
		 */
		ExecutorService executorService = Executors.newCachedThreadPool(new ThreadFactoryTest());
		executorService.execute(()->{
			System.out.println(Thread.currentThread().getName());
		});
		
		executorService = Executors.newFixedThreadPool(5, new ThreadFactoryTest());
		for(int i = 0; i < 10; i++) {
			executorService.execute(()->{
				try {
					TimeUnit.MICROSECONDS.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println(Thread.currentThread().getName());
			});
		}
		executorService = Executors.newSingleThreadExecutor(new ThreadFactoryTest());
		for(int i = 0; i < 10; i++) {
			executorService.execute(()->{
				try {
					TimeUnit.MICROSECONDS.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println(Thread.currentThread().getName());
			});
		}
		boolean over = true;
		do {
			over = executorService.awaitTermination(1L, TimeUnit.SECONDS);
			if(!over)System.out.println("还没完，莫着急");
		}while(over);
		executorService.shutdown();
		try {
			TimeUnit.MICROSECONDS.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if(over)executorService.shutdownNow();
		System.out.println("newCachedThreadPool关闭没得？"+executorService.isTerminated());
		
		
//		ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1, new ThreadFactoryTest());
//		scheduledExecutorService.scheduleAtFixedRate(()->{
//			for(int i = 0; i < 10; i++) {
//				try {
//					TimeUnit.MICROSECONDS.sleep(10);
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
//				System.out.println(Thread.currentThread().getName());
//			}
//		}, 0, 10, TimeUnit.SECONDS);
//		scheduledExecutorService.awaitTermination(60L, TimeUnit.SECONDS);
//		scheduledExecutorService.shutdownNow();
	}

	static class ThreadFactoryTest implements ThreadFactory {
        private static final AtomicInteger poolNumber = new AtomicInteger(1);
        private final ThreadGroup group;
        private final AtomicInteger threadNumber = new AtomicInteger(1);
        private final String namePrefix;

        ThreadFactoryTest() {
            SecurityManager s = System.getSecurityManager();
            group = (s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
            namePrefix = "测试线程工厂号-" + poolNumber.getAndIncrement() + "-线程号-";
        }

        public Thread newThread(Runnable r) {
            Thread t = new Thread(group, r, namePrefix + threadNumber.getAndIncrement(), 0);
            if (t.isDaemon())
                t.setDaemon(false);
            if (t.getPriority() != Thread.NORM_PRIORITY)
                t.setPriority(Thread.NORM_PRIORITY);
            return t;
        }
    }
}
