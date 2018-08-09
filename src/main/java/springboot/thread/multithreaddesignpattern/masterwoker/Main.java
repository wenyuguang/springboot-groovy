package springboot.thread.multithreaddesignpattern.masterwoker;

import java.util.Random;

public class Main {
	public static void main(String[] args) {
		// 线程池的个数按照机器性能来定,可以用：
		int workerCount = Runtime.getRuntime().availableProcessors();
		 System.out.println(Runtime.getRuntime().availableProcessors());
		/**
		 * 创建Master,并指定创建N个worker
		 */
		Master master = new Master(workerCount);

		/**
		 * 添加100个任务
		 */	
		Random r = new Random();
		for (int i = 1; i <= 100; i++) {
			Task t = new Task(i, r.nextInt(1000));
			// 添加到任务队列，非阻塞的，可以采用高性能的ConcurrentLinkedQueue来存放
			master.submit(t);
		}
		/**
		 * 执行所有的worker
		 */
		master.execute();
		long start = System.currentTimeMillis();

		while (true) {
			/**
			 * 如果所有的woker都停止了，那么结束
			 */
			if (master.isComplete()) {
				long end = System.currentTimeMillis() - start;
				int priceResult = master.getResult();
				System.out.println("最终结果：" + priceResult + ", 执行时间：" + end);
				break;
			}
		}

	}
}