package springboot.offer.构建乘积数组;

import java.util.Arrays;

/**
 * 
 * Test52.java
 * Description:给定一个数组A[0,1,…,n-1],
 * 请构建一个数组B[0,1,…,n-1],
 * 其中B中的元素B[i]=A[0]×A[1]×…×A[i-1]×A[i+1]×…×A[n-1],
 * 不能使用除法。
 * <p>name: wen </p>
 * <p>Date:2018年8月13日 下午3:20:17</p>
 * @author Tony
 * @version 1.0
 *
 */
public class Test52 {
    public static double[] multiply(double[] data) {
        if (data == null || data.length < 2) {
            return null;
        }

        double[] result = new double[data.length];

        // result[0]取1
        result[0] = 1;
        for (int i = 1; i < data.length; i++) {
            // 第一步每个result[i]都等于于data[0]*data[1]...data[i-1]
            // 当i=n-1时，此时result[n-1]的结果已经计算出来了【A】
            result[i] = result[i -1] * data[i - 1];
        }

        // tmp保存data[n-1]*data[n-2]...data[i+1]的结果
        double tmp = 1;
        // 第二步求data[n-1]*data[n-2]...data[i+1]
        // 【A】result[n-1]的结果已经计算出来，所以从data.length-2开始操作
        for (int i = data.length - 2; i >= 0; i--) {
            tmp *= data[i + 1];
            result[i] *= tmp;
        }

        return result;
    }

    public static void main(String[] args) {
        double[] array1 = {1, 2, 3, 4, 5};
        System.out.println(Arrays.toString(multiply(array1))); // double expected[] = {120, 60, 40, 30, 24};
        double[] array2 = {1, 2, 0, 4, 5};
        System.out.println(Arrays.toString(multiply(array2))); // double expected[] = {0, 0, 40, 0, 0};
        double[] array3 = {1, 2, 0, 4, 0};
        System.out.println(Arrays.toString(multiply(array3))); // double expected[] = {0, 0, 0, 0, 0};
        double[] array4 = {1, -2, 3, -4, 5};
        System.out.println(Arrays.toString(multiply(array4))); // double expected[] = {120, -60, 40, -30, 24};
        double[] array5 = {1, -2};
        System.out.println(Arrays.toString(multiply(array5))); // double expected[] = {-2, 1};
    }
}