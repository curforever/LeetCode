// 找最小m，使得n%m=0、n/m为质数

/*
给你一个数n，找最小的m，使得n能被m整除，即n mod m = 0且n、m为指数，若不存在符合要求的m输出0，用java解决 输入第一行表示有T组数据 输入 4 3 4 5 6 输出 1 2
*/

/*
这题用int通过0%
用long并且函数解耦后才100%
但在100%的正确代码情况下，测试用例修改后还是会答案错误（莫名其妙）
*/

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int T = sc.nextInt();

        while (T-- > 0) {
            boolean flag = false;
            long n = sc.nextLong();
            System.out.println(help(n));
        }
    }

    public static Long help(long n) {
        for (long m = 1; m <= n; m++) {
            if (n % m == 0) {
                long k = n / m;
                if (isPrime(k)) {
                    return m;
                }
            }
        }
        return 0L;
    }

    public static boolean isPrime(long num) {
        if (num < 2) {
            return false;
        }
        if (num == 2 || num == 3) {
            return true;
        }
        if (num % 2 == 0 || num % 3 == 0) {
            return false;
        }
        for (int i = 5; i * i <= num; i += 6) {
            if (num % i == 0 || num % (i + 2) == 0) {
                return false;
            }
        }
        return true;
    }
}


