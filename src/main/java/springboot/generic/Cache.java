package springboot.generic;

public class Cache<T> {
	T value;

	public Object getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}

}