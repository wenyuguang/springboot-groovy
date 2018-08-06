package springboot.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadPoolDefinitionTest {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		threadPool();
	}

	public static void threadPool() throws InterruptedException, ExecutionException {
		/**
		 * 核心线程数为1，最大线程数为200,60秒后回收空闲线程
		 */
		
		ExecutorService executorService = new ThreadPoolExecutor(1, 200, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(), new DefinitionThreadFactory());
		for(int i = 0; i < 200; i++) {
			Future<?> future = executorService.submit(new SubmitTest(i));
			Object rt = future.get();
			System.out.println(rt);
			
		}
		boolean d = true;
		do {
//			executorService.shutdown();
			d = !executorService.awaitTermination(500, TimeUnit.MILLISECONDS);
		}while(d);
		System.out.println("over");
	}
	
	static class DefinitionThreadFactory implements ThreadFactory {
        private static final AtomicInteger poolNumber = new AtomicInteger(1);
        private final ThreadGroup group;
        private final AtomicInteger threadNumber = new AtomicInteger(1);
        private final String namePrefix;

        DefinitionThreadFactory() {
            SecurityManager s = System.getSecurityManager();
            group = (s != null) ? s.getThreadGroup() :
                                  Thread.currentThread().getThreadGroup();
            namePrefix = "自定义线程池pool-" +
                          poolNumber.getAndIncrement() +
                         "-thread-";
        }

        public Thread newThread(Runnable r) {
            Thread t = new Thread(group, r,
                                  namePrefix + threadNumber.getAndIncrement(),
                                  0);
            if (t.isDaemon())
                t.setDaemon(false);
            if (t.getPriority() != Thread.NORM_PRIORITY)
                t.setPriority(Thread.NORM_PRIORITY);
            return t;
        }
    }
	
	static class SubmitTest implements Callable<String> {
        private int id; 

        public SubmitTest(int id) { 
                this.id = id; 
        } 

        /** 
         * 任务的具体过程，一旦任务传给ExecutorService的submit方法，则该方法自动在一个线程上执行。 
         * 
         * @return 
         * @throws Exception 
         */
        @Override
        public String call() throws Exception { 
                System.out.println("call()方法被自动调用,干活！！！             " + Thread.currentThread().getName()); 
                //一个模拟耗时的操作
                for (int i = 999999; i > 0; i--) ; 
                return"call()方法被自动调用，任务的结果是：" + id + "    " + Thread.currentThread().getName(); 
        } 
}
}
