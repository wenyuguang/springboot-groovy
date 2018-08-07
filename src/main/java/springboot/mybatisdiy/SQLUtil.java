package springboot.mybatisdiy;

import java.util.ArrayList;
import java.util.List;

/**
 * 处理sql语句的工具类
 */
public class SQLUtil {

	public static String[] getInsertParameterNames(String sql) {
		int startIndex = sql.indexOf("values");
		int endIndex = sql.length();
		String substring = sql.substring(startIndex + 6, endIndex).replace("(", "").replace(")", "").replace("#{", "")
				.replace("}", "");
		String[] split = substring.split(",");
		return split;
	}

	public static List<String> getSelectParameterNames(String sql) {
		int startIndex = sql.indexOf("where");
		int endIndex = sql.length();
		String substring = sql.substring(startIndex + 5, endIndex);
		String[] split = substring.split("and");
		List<String> listArr = new ArrayList<>();
		for (String string : split) {
			String[] sp2 = string.split("=");
			listArr.add(sp2[0].trim());
		}
		return listArr;
	}

	/**
	 * 将SQL语句的参数替换变为?
	 * 
	 * @param sql
	 * @param parameterName
	 * @return
	 */
	public static String questionMark(String sql, String[] parameterName) {
		for (int i = 0; i < parameterName.length; i++) {
			String string = parameterName[i].trim();
			sql = sql.replace("#{" + string + "}", "?");
		}
		return sql;
	}

	public static String questionMark(String sql, List<String> parameterName) {
		for (int i = 0; i < parameterName.size(); i++) {
			String string = parameterName.get(i);
			sql = sql.replace("#{" + string + "}", "?");
		}
		return sql;
	}
}