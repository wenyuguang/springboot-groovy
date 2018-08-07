package springboot.designpattern.builder;

/**
 * 
 * Builder.java
 * Description:建造者接口
 * <p>name: wen </p>
 * <p>Date:2018年8月7日 下午3:10:07</p>
 * @author Tony
 * @version 1.0
 *
 */
public interface Builder {

	/**
	 * 打基础
	 */
	public void buildBasic();

	/**
	 * 砌墙
	 */
	public void buildWalls();

	/**
	 * 封顶
	 */
	public void roofed();

	/**
	 * 造房子
	 * 
	 * @return
	 */
	public Product buildProduct();
}