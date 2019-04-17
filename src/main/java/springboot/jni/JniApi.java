package springboot.jni;

public class JniApi {

	public static native void testHello();
	
	public static native boolean certificateValidation(String secretKey);
	
	public static native String encrypt(String key);
	
	public static native String decrypt(String key);
	
	public native String encrypt(String plainText, String key);
	
    public native String decrypt(String cipherText, String key);
}
