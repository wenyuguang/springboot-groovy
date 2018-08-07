package springboot.designpattern.builder;

/**
 * 
 * Product.java
 * Description:房子
 * <p>name: wen </p>
 * <p>Date:2018年8月7日 下午3:09:41</p>
 * @author Tony
 * @version 1.0
 *
 */
public class Product {
	private String basic;// 地基

	private String wall;// 墙

	private String roofed;// 楼顶

	public String getBasic() {
		return basic;
	}

	public void setBasic(String basic) {
		this.basic = basic;
	}

	public String getWall() {
		return wall;
	}

	public void setWall(String wall) {
		this.wall = wall;
	}

	public String getRoofed() {
		return roofed;
	}

	public void setRoofed(String roofed) {
		this.roofed = roofed;
	}

	/** (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Product [basic=" + basic + ", wall=" + wall + ", roofed=" + roofed + "]";
	}
	
}