package springboot.jdk.lock;

import java.util.concurrent.atomic.AtomicBoolean;

public class ThreadSafeSingletonTest {

	private static AtomicBoolean shouldEnter = new AtomicBoolean(false);
	private static AtomicBoolean shouldLeave = new AtomicBoolean(false);
	private static volatile ThreadSafeSingletonTest instance;

	private ThreadSafeSingletonTest() {
	}

	public static ThreadSafeSingletonTest getInstance() {
		if (instance == null) {
			if (shouldEnter.compareAndSet(false, true)) {
				instance = new ThreadSafeSingletonTest();
				shouldLeave.set(true);
			}
			while (!shouldLeave.get()) {
			}
		}
		return instance;
	}
}
