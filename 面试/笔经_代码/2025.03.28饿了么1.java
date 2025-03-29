// 字符串替换

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int k = sc.nextInt();
        sc.nextLine();
        String s = sc.nextLine();
        int q = sc.nextInt();
        // System.out.println(q);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            if ((i + 1) % k == 0) {
                if (s.charAt(i) >= 'a' && s.charAt(i) <= 'z') {
                    sb.append(Character.toUpperCase(s.charAt(i)));
                } else {
                    sb.append(Character.toLowerCase(s.charAt(i)));
                }
            } else {
                sb.append((int)s.charAt(i));
            }
        }
        int l = -1, r = -1;
        while (q-- != 0) {
            l = sc.nextInt();
            r = sc.nextInt();
            // System.out.println(l+" "+r);

            System.out.println(sb.toString().substring(l - 1, r));
        }
        return;
    }
}
