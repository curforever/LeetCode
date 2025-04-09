/*
小红拿到一个数组，对这个数组可以进行最多两次操作：选择一个元素使其+1，操作结束后数组所有二进制末尾有尽可能多的0，用java解决
输入
5
1 2 3 4 5
输出
6
说明
操作两次后数组变为2 2 4 4 5，乘积为320，二进制101000000，有6个0
*/

// 20% 
import java.util.Scanner;

public class MaximizeTrailingZeros {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = scanner.nextInt();
        }
        int maxZeros = 0;

        // 枚举所有可能的操作情况
        for (int i = -1; i < n; i++) {
            for (int j = -1; j < n; j++) {
                int[] temp = arr.clone();
                if (i != -1) {
                    temp[i]++;
                }
                if (j != -1) {
                    temp[j]++;
                }
                long product = 1;
                for (int num : temp) {
                    product *= num;
                }
                int zeros = countTrailingZeros(product);
                maxZeros = Math.max(maxZeros, zeros);
            }
        }
        System.out.println(maxZeros);
    }

    // 计算一个数二进制末尾 0 的个数
    private static int countTrailingZeros(long num) {
        int count = 0;
        while ((num & 1) == 0) {
            num >>= 1;
            count++;
        }
        return count;
    }
}