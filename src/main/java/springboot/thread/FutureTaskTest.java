package springboot.thread;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

public class FutureTaskTest {

	public static void main(String[] args) throws Exception {

//		FutureTask<String> task = new FutureTask<String>(new Callable<String>() {
//			@Override
//			public String call() throws Exception {
//				return Thread.currentThread().getName();
//			}
//		});
//
//		new Thread(task, "有返回值的线程").start();

//		String result = task.get(3000, TimeUnit.MILLISECONDS);
//		System.out.println(result);
		ExecutorService executor = Executors.newCachedThreadPool();
		for(int i=0; i<5; i++) {
			Callable<String> c = new Task();
			MyFutureTask ft = new MyFutureTask(c);
			executor.submit(ft);
		}
		executor.shutdown();
	}

	static class MyFutureTask extends FutureTask<String> {

		public MyFutureTask(Callable<String> callable) {
			super(callable);
		}

		@Override
		protected void done() {
			try {
				System.out.println(super.get() + " 线程执行完毕！~");
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
		}

	}
	static class Task implements Callable<String> {
		 
		@Override
		public String call() throws Exception {
			Random rand = new Random();
			TimeUnit.SECONDS.sleep(rand.nextInt(12));
			return Thread.currentThread().getName();
		}
	}	

}