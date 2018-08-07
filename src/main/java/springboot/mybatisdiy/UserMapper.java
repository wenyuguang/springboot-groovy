package springboot.mybatisdiy;

public interface UserMapper {

	@MyInsert("insert into test(id, name, age) values(#{id}, #{name}, #{age})")
	public int insertUser(@MyParam("id") Integer id, @MyParam("name") String name, @MyParam("age") Integer age);

	@MySelect("select * from test where name = #{name} and age = #{age} ")
	User selectUser(@MyParam("name") String name, @MyParam("age") Integer age);

}