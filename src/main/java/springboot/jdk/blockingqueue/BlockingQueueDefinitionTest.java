package springboot.jdk.blockingqueue;

import java.util.LinkedList;
import java.util.List;

public class BlockingQueueDefinitionTest {

	private List<Object> queue = new LinkedList<Object>();
	private int limit = 10;

	public BlockingQueueDefinitionTest() {
	}
	public BlockingQueueDefinitionTest(int limit) {
		this.limit = limit;
	}

	public synchronized void enqueue(Object item) throws InterruptedException {
		// 注意要使用while循环，而不是if，因为下面的notifyAll可能把正在等待的入队线程给唤醒
		while (this.queue.size() == this.limit) {
			wait();
		}
		if (this.queue.size() == 0) {
			notifyAll();
		}
		this.queue.add(item);
	}

	public synchronized Object dequeue() throws InterruptedException {
		while (this.queue.size() == 0) {
			wait();
		}
		if (this.queue.size() == this.limit) {
			notifyAll();
		}

		return this.queue.remove(0);
	}

	public static void main(String[] args) throws InterruptedException {
		BlockingQueueDefinitionTest blockingQueueDefinitionTest = new BlockingQueueDefinitionTest();
		blockingQueueDefinitionTest.enqueue("test");
		Object obj = blockingQueueDefinitionTest.dequeue();
		System.out.println(obj);
	}
}