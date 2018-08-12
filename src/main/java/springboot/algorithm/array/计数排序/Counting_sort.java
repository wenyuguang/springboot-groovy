package springboot.algorithm.array.计数排序;

public class Counting_sort {

	public static void main(String[] args) {
		int[] input = new int[10];
		int k = 5;// 输入元素都是介于0到k之间
		// 随机生成输入数组，所有元素都介于0到k之间
		for (int i = 0; i < input.length; i++) {
			input[i] = (int) (Math.random() * k);// 数字范围[0,k)
		}
		System.out.println("输入数组：");
		for (int i = 0; i < input.length; i++) {
			System.out.print(input[i] + " ");
		}
		int[] output = new int[input.length];
		countsort(input, output, k);
		System.out.println();
		System.out.println("输出数组：");
		for (int i = 0; i < output.length; i++) {
			System.out.print(output[i] + " ");
		}

	}

	public static void countsort(int[] input, int[] output, int k) {
		// input为输入数组，output为输出数组，k表示有所输入数字都介于0到k之间
		int[] c = new int[k];// 临时存储区
		int len = c.length;
		// 初始化
		for (int i = 0; i < len; i++) {
			c[i] = 0;
		}
		// 检查每个输入元素，如果一个输入元素的值为input[i],那么c[input[i]]的值加1，此操作完成后，c[i]中存放了值为i的元素的个数
		for (int i = 0; i < input.length; i++) {
			c[input[i]]++;
		}
		// 通过在c中记录计数和，c[i]中存放的是小于等于i元素的数字个数
		for (int i = 1; i < len; i++) {
			c[i] = c[i] + c[i - 1];
		}
		// 把输入数组中的元素放在输出数组中对应的位置上
		for (int i = input.length - 1; i >= 0; i--) {// 从后往前遍历
			output[c[input[i]] - 1] = input[i];
			c[input[i]]--;// 该操作使得下一个值为input[i]的元素直接进入输出数组中input[i]的前一个位置
		}
	}
}