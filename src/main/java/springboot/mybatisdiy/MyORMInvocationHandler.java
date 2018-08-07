package springboot.mybatisdiy;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class MyORMInvocationHandler implements InvocationHandler {

	public MyORMInvocationHandler() {
	}

	// 目前只是简单实现对select/insert的拦截
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		System.out.println("---------使用动态代理拦截接口方法--------");
		// 1.insert：判断方法上是否存在@MyInsert
		MyInsert MyInsert = method.getDeclaredAnnotation(MyInsert.class);
		if (MyInsert != null) {
			return doInsert(MyInsert, method, args);
		}
		// 2.select：判断方法上是否存在@MySelect
		MySelect MySelect = method.getDeclaredAnnotation(MySelect.class);
		if (MySelect != null) {
			return doSelect(MySelect, method, args);
		}
		return null;
	}

	private Object doSelect(MySelect mySelect, Method method, Object[] args)
			throws SQLException, InstantiationException, IllegalAccessException {
		// 获取注解上查询的SQL语句
		String selectSQL = mySelect.value();
		// 获取方法上的参数名,和实际传入的参数值绑定在一起
		ConcurrentHashMap<Object, Object> acturlParamsMap = getActurlParamsMap(method, args);
		List<String> selectParameterNames = SQLUtil.getSelectParameterNames(selectSQL);
		List<Object> sqlParams = new ArrayList<>();
		for (String parameterName : selectParameterNames) {
			Object parameterValue = acturlParamsMap.get(parameterName.trim());
			sqlParams.add(parameterValue);
		}
		// 将sql语句参数替换成?
		String newSql = SQLUtil.questionMark(selectSQL, selectParameterNames);
		System.out.println("newSQL:" + newSql + ",sqlParams:" + sqlParams.toString());

		// 调用jdbc代码底层执行sql语句
		ResultSet res = JDBCUtil.query(newSql, sqlParams);
		// 判断是否存在值
		if (!res.next()) {
			return null;
		}
		// 下标往上移动移位
		res.previous();
		// 使用反射机制获取方法的类型(本例只是简单演示,实际可能返回集合类型,暂不考虑,假设查询数据库只返回一条记录)
		Class<?> returnType = method.getReturnType();
		// 获取当前类型的对象的所有属性
		Field[] declaredFields = returnType.getDeclaredFields();
		List<Object> resList = new ArrayList<Object>();
		while (res.next()) {
			// 使用反射创建返回对象，并且根据数据库查到的数据赋值
			Object record = returnType.newInstance();
			for (Field field : declaredFields) {
				String fieldName = field.getName();
				Object fieldValue = res.getObject(fieldName);
				field.setAccessible(true);
				field.set(record, fieldValue);
			}
			resList.add(record);
		}
		// 假设只查询到一条数据库记录
		return resList.get(0);
	}

	private Object doInsert(MyInsert MyInsert, Method method, Object[] args) {
		// 获取@MyInsert的SQL语句
		String insertSql = MyInsert.value();
		// 将方法的实际参数值和注解中的参数进行绑定
		ConcurrentHashMap<Object, Object> acturlParamsMap = getActurlParamsMap(method, args);
		// 将sql语句中的参数和注解参数名对应的实际参数绑定
		String[] sqlInsertParameterNames = SQLUtil.getInsertParameterNames(insertSql);
		List<Object> sqlParams = getSqlParamValue(sqlInsertParameterNames, acturlParamsMap);
		// 将注解的sql语句中的参数替换为?
		String newSQL = SQLUtil.questionMark(insertSql, sqlInsertParameterNames);
		System.out.println("newSQL:" + newSQL + ",sqlParams:" + sqlParams.toString());
		// 调用jdbc底层代码执行语句
		return JDBCUtil.insert(newSQL, false, sqlParams);
	}

	private List<Object> getSqlParamValue(String[] sqlInsertParameter, ConcurrentHashMap<Object, Object> paramsMap) {
		List<Object> sqlParams = new ArrayList<>();
		for (String paramName : sqlInsertParameter) {
			Object paramValue = paramsMap.get(paramName.trim());
			sqlParams.add(paramValue);
		}
		return sqlParams;
	}

	private ConcurrentHashMap<Object, Object> getActurlParamsMap(Method method, Object[] args) {
		ConcurrentHashMap<Object, Object> paramsMap = new ConcurrentHashMap<>();
		// 获取方法上的参数
		Parameter[] parameters = method.getParameters();
		for (int i = 0; i < parameters.length; i++) {
			Parameter parameter = parameters[i];
			MyParam MyParam = parameter.getDeclaredAnnotation(MyParam.class);
			if (MyParam != null) {
				// 参数名称
				String paramName = MyParam.value();
				// 实际传入的参数值
				Object paramValue = args[i];
				paramsMap.put(paramName, paramValue);
			}
		}
		return paramsMap;
	}
}