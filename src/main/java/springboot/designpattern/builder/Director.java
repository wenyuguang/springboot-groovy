package springboot.designpattern.builder;

/**
 * 
 * Director.java
 * Description:建造者
 * <p>name: wen </p>
 * <p>Date:2018年8月7日 下午3:11:42</p>
 * @author Tony
 * @version 1.0
 *
 */
public class Director {

	public Product builder(Builder builder) {
		builder.buildBasic();
		builder.buildWalls();
		builder.roofed();
		return builder.buildProduct();
	}
}