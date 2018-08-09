package springboot.thread.multithreaddesignpattern.future.thread;

/**
 * 模拟一个service请求
 */
public class MainTest {
	public static void main(String[] args) throws InterruptedException {

		/**
		 * 未来模式的执行类,提供处理请求的方法handleRequest
		 */
		FutureExcutor fc = new FutureExcutor();
		/**
		 * 1、Data是一个接口，提供实现类FuctureData和RealData
		 * 2、未来模式执行类，先给我们返回一个FuctureData,然后开了一个线程去加载真实数据了
		 * 
		 */
		Data data = fc.handleRequest("请求参数");
		/**
		 * 收到FutureData后，可以去处理其他业务逻辑
		 */
		System.out.println("做其他的事情...");

		/**
		 * 1、处理完其他，要调用未来数据FutureData的方法加载真实数据了
		 * 1.1、这个getResultData肯定的是阻塞的，因为不确定真实数据是否加载成功，只有当setResultData调用了，才能把阻塞打开
		 * 所以这两个方法getResultData、setResultData可以用阻塞队列来实现SynchronousQueue
		 * 1.2、这里采用的是wait,notify,synchronized来实现的
		 */
		String result = data.getResultData();
		System.out.println(result);

	}
}