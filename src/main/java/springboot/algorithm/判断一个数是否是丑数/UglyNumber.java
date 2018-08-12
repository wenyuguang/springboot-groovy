package springboot.algorithm.判断一个数是否是丑数;

/**
 * 丑数
 * 我们把只包含因子2，3，5的数称为丑数（Ugly Number).
 * 求按从小到大的顺序的第1500个丑数。
 * 例如6，8都是丑数，但14不是，因为它含有因子7.习惯上我们把1当作第一个丑数
 */

public class UglyNumber {

	public static boolean isUgly(int number) {
		while (number % 2 == 0)
			number /= 2;
		while (number % 3 == 0)
			number /= 3;
		while (number % 5 == 0)
			number /= 5;
		return (number == 1) ? true : false;
	}

	public static int getUglyNumber(int index) {
		if (index <= 0)
			return 0;
		int number = 0;
		int uglyFound = 0;
		while (uglyFound < index) {
			number++;
			if (isUgly(number)) {
				++uglyFound;
			}
		}
		return number;
	}

	public static void main(String[] args) {
		int index = 150;
		System.out.println(UglyNumber.getUglyNumber(index));
	}
}