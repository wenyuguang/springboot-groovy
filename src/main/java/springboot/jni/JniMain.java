package springboot.jni;

public class JniMain {

	static {System.load("C:\\Users\\wenyu\\eclipse-workspace/springboot-groovy/cpluspluscode/jnitest/x64/Release/jnitest.dll");}
//	static {
//		String dllName = "/cpluspluscode/jnitest/x64/Release/jnitest.dll";
//		try {
//			InputStream in = sigar.class.getResourceAsStream(dllName);
//			File dllFile = new File("");
//			String filePath = dllFile.getAbsolutePath() + File.separator + dllName.substring(dllName.lastIndexOf("/") + 1);
//			System.out.println(filePath);
//			File dll = new File(filePath);
//			if(dll.exists()) dll.delete(); 
//			FileOutputStream out = new FileOutputStream(dll);
//
//			int i;
//			byte[] buf = new byte[1024];
//			try {
//				while ((i = in.read(buf)) != -1) {
//					out.write(buf, 0, i);
//				}
//			} finally {
//				in.close();
//				out.close();
//			}
//			System.load(dll.getAbsolutePath());
//			dll.delete();
//
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
	
	public static void main(String[] args) {
		JniApi.testHello();
		
		System.out.println(JniApi.decrypt("test"));
	}
}
