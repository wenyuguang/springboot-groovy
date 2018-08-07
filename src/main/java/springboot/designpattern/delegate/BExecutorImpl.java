package springboot.designpattern.delegate;

/**
 * 
 * BExecutorImpl.java
 * Description:执行者B
 * <p>name: wen </p>
 * <p>Date:2018年8月7日 上午10:16:08</p>
 * @author Tony
 * @version 1.0
 *
 */
public class BExecutorImpl implements IExecutor {

	@Override
	public void doSomething() {
		System.out.println("B 干活了");
	}
}
