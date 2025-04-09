// 从n取k个异或后求和

import java.util.Scanner;

public class Main {
    static int n, k;
    static int[] a;
    static long sum = 0;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        n = sc.nextInt();
        k = sc.nextInt();
        a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = sc.nextInt();
        }
        // System.out.println(n+" "+k+" "+a[0]+" "+a[1]+" "+a[2]);
        help(0, 0, 0);
        System.out.println(sum);
    }

    public static void help(int idx, int cnt, int cur) {
        if (cnt == k) {
            sum += cur;
            return;
        }
        if (idx >= n) {
            return;
        }
        help(idx + 1, cnt + 1, cur ^ a[idx]);
        help(idx + 1, cnt, cur);

    }
}
