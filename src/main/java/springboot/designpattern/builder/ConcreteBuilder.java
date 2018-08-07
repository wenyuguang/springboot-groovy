package springboot.designpattern.builder;

/**
 * 
 * ConcreteBuilder.java
 * Description:建造方式
 * <p>name: wen </p>
 * <p>Date:2018年8月7日 下午3:10:39</p>
 * @author Tony
 * @version 1.0
 *
 */
public class ConcreteBuilder implements Builder {

	private Product product;

	public ConcreteBuilder() {
		product = new Product();
	}

	@Override
	public void buildBasic() {
		product.setBasic("打好基础");
	}

	@Override
	public void buildWalls() {
		product.setWall("砌墙");
	}

	@Override
	public void roofed() {
		product.setRoofed("封顶大吉");
	}

	@Override
	public Product buildProduct() {
		return product;
	}

}