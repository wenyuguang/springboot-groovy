package springboot.designpattern.delegate;

/**
 * 
 * AExecutorImpl.java
 * Description:执行者A
 * <p>name: wen </p>
 * <p>Date:2018年8月7日 上午10:15:52</p>
 * @author Tony
 * @version 1.0
 *
 */
public class AExecutorImpl implements IExecutor {

	@Override
	public void doSomething() {
		System.out.println("A 干活了");
	}
}
