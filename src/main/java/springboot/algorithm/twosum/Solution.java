package springboot.algorithm.twosum;

import java.util.HashMap;
import java.util.Map;

/**
 * 已知一个数组，和一个target值。返回两个目标的index，这两个目标求和就是target值
 * @author min
 *
 */
public class Solution {
	public static int[] twoSum(int[] nums, int target) {
		// 创建一下数组，要存两个index的数组。
		int[] result = new int[2];
		// 这里用hashtable也行，看心情。
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		// 扫一遍数组，一边扫一边存
		for (int i = 0; i < nums.length; i++) {
			int cur = nums[i];
			// 这里搞出来个差值，其实差值是在没找到之后添加到map里面的。
			int toFind = target - cur;
			// 如果发现之前需要这个差值，那就找index。
			if (map.containsKey(cur)) {
				result[0] = map.get(cur);
				result[1] = i;
				return result;
			}
			// 如果没有，就put到map里面
			else {
				map.put(toFind, i);
			}
		}
		return result;
	}
	public static void main(String[] args) {
		int[] nums = {1,2,3,4,5,6,7,8,9,0,10,11,12,13,14,15,16,17};
		int target = 20;
		System.out.println(twoSum(nums, target));
	}
}