package springboot.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

public class FutureTaskTest {

	public static void main(String[] args) throws Exception {

		FutureTask<String> task = new FutureTask<String>(new Callable<String>() {
			@Override
			public String call() throws Exception {
				return Thread.currentThread().getName();
			}
		});

		new Thread(task, "有返回值的线程").start();

		String result = task.get(3000, TimeUnit.MILLISECONDS);
		System.out.println(result);
	}

}