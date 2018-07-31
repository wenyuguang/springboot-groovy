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
 * Singleton.java
 * Description:使用双重校验锁方式实现单例,通过对Singleton的序列化与反序列化得到的对象是一个新的对象，这就破坏了Singleton的单例性。
 * <p>name: wen </p>
 * <p>Date:2018年7月31日 下午4:53:03</p>
 * @author Tony
 * @version 1.0
 *
 */
public class DamageSingleton implements Serializable {
	/**
	 * 2018年7月31日下午5:02:39
	 * Tony
	 */
	private static final long serialVersionUID = -6713376943288994945L;
	private volatile static DamageSingleton singleton;

	private DamageSingleton() {
	}

	public static DamageSingleton getSingleton() {
		if (singleton == null) {
			synchronized (DamageSingleton.class) {
				if (singleton == null) {
					singleton = new DamageSingleton();
				}
			}
		}
		return singleton;
	}
	public static void main(String[] args) throws IOException, ClassNotFoundException {
        //Write Obj to file
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("tempFile"));
        oos.writeObject(DamageSingleton.getSingleton());
        oos.close();
        //Read Obj from file
        File file = new File("tempFile");
        ObjectInputStream ois =  new ObjectInputStream(new FileInputStream(file));
        DamageSingleton newInstance = (DamageSingleton) ois.readObject();
        ois.close();
        //判断是否是同一个对象
        System.out.println(newInstance == DamageSingleton.getSingleton());
    }
}