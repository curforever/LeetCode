// 33.3%

import java.util.Scanner;

// 注意类名必须为 Main, 不要有任何 package xxx 信息
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int T = sc.nextInt();
        while (T-- > 0) {
            int n = sc.nextInt();
            String s = sc.next();
            System.out.println(getWays(s));
        }
        sc.close();
    }
    public static int getWays(String s) {
        int n  = s.length();
        if (n == 0) return 0;
        int[] dp = new int[n + 1];
        dp[0] = 1;

        for (int i = 1; i <= n; i++) {
            if (s.charAt(i - 1) >= '1' && s.charAt(i - 1) <= '9') {
                dp[i] += dp[i - 1];
            }
            if (i > 1) {
                int num = Integer.parseInt(s.substring(i - 2, i));
                if (num >= 10 && num <= 26) {
                    dp[i] += dp[i - 2];
                }
            }
        }

        return dp[n];
    }
}
