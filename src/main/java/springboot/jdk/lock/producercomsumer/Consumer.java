package springboot.jdk.lock.producercomsumer;

import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Consumer implements Runnable {

	private final Vector<?> sharedQueue;

	public Consumer(Vector<?> sharedQueue) {
		this.sharedQueue = sharedQueue;
	}

	@Override
	public void run() {
		while (true) {
			try {
				System.out.println("Consumer: " + consume());
				Thread.sleep(50);
			} catch (InterruptedException ex) {
				Logger.getLogger(Consumer.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}

	private int consume() throws InterruptedException {

		// wait if queue is empty
		while (sharedQueue.isEmpty()) {
			synchronized (sharedQueue) {
				System.out.println("Queue is empty " + Thread.currentThread().getName() + " is waiting , size: "
						+ sharedQueue.size());
				sharedQueue.wait();
			}
		}

		// otherwise consume element and notify waiting producer
		synchronized (sharedQueue) {
			sharedQueue.notifyAll();
			return (Integer) sharedQueue.remove(0);
		}
	}
}