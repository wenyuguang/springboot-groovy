package springboot.thread.multithreaddesignpattern.masterwoker;

public class Task {
	private int id;
	private int price;

	public Task(int id, int price) {
		super();
		this.id = id;
		this.price = price;
	}

	public Task() {
	}

	/**
	 * get id value
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * set id value
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * get price value
	 * @return the price
	 */
	public int getPrice() {
		return price;
	}

	/**
	 * set price value
	 * @param price the price to set
	 */
	public void setPrice(int price) {
		this.price = price;
	}
}