package springboot.thread.multithreaddesignpattern.future.thread;

/**
 * 真实数据
 */
public class RealData implements Data {

	private String result;

	public RealData(String queryStr) {
		try {
			System.out.println("根据" + queryStr + "进行查询，这是一个很耗时的操作..");
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		result = "20000";
	}

	@Override
	public String getResultData() {
		return result;
	}
}