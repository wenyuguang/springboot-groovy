package springboot.serialize.singledamage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * 
 * Singleton.java Description:使用双重校验锁方式实现单例
 * <p>
 * name: wen
 * </p>
 * <p>
 * Date:2018年7月31日 下午5:07:59
 * </p>
 * 
 * @author Tony
 * @version 1.0
 *
 */
public class Singleton implements Serializable {
	/**
	 * 2018年7月31日下午5:08:03 Tony
	 */
	private static final long serialVersionUID = -5256894302962857449L;
	private volatile static Singleton singleton;

	private Singleton() {
	}

	public static Singleton getSingleton() {
		if (singleton == null) {
			synchronized (Singleton.class) {
				if (singleton == null) {
					singleton = new Singleton();
				}
			}
		}
		return singleton;
	}

	private Object readResolve() {
		return singleton;
	}

	public static void main(String[] args) throws IOException, ClassNotFoundException {
		// Write Obj to file
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("tempFile"));
		oos.writeObject(Singleton.getSingleton());
		oos.close();
		// Read Obj from file
		File file = new File("tempFile");
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
		Singleton newInstance = (Singleton) ois.readObject();
		ois.close();
		System.out.println(Singleton.getSingleton());
		System.out.println(newInstance);
		// 判断是否是同一个对象
		System.out.println(newInstance == Singleton.getSingleton());
	}
}