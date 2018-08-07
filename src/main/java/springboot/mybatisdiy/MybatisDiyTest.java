package springboot.mybatisdiy;

/**
 * 
 * MybatisDiyTest.java
 * Description:测试类，检验orm框架的效果
 * <p>name: wen </p>
 * <p>Date:2018年8月7日 下午5:23:48</p>
 * @author Tony
 * @version 1.0
 *
 */
public class MybatisDiyTest {
	public static void main(String[] args) {
		// 使用动态代理调用Mapper接口方法
		UserMapper userMapper = MySqlSession.getMapper(UserMapper.class);
		User user = userMapper.selectUser("hello", 88);
		if (user != null) {
			System.out.println("select result:" + user.getUserName() + "," + user.getUserAge() + ",id:" + user.getId());

		} else {
			System.out.println("select result: No result!");
		}

		int res = userMapper.insertUser(1, "testname", 25);
		System.out.println("insert result:" + res);
	}
}