// 100分 100%
/*
输入
4
xxx=lyf/${ttt}/test
ttt=www
yyy=seeyou
aa=/aaa/@{xxx}/bbb/${yyy}/ccc

输出
/aaa/lyf/www/test/bbb/seeyou/ccc

就是变量替换，输出的是最后一行右边的实际字符串
*/

import java.util.*;

// 注意类名必须为 Main, 不要有任何 package xxx 信息
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        sc.nextLine();
        HashMap<String, String> mp = new HashMap<>();
        String target = null;
        while (n-- != 0) {
            String line = sc.nextLine();
            int idx = line.indexOf('=');
            String key = line.substring(0, idx);
            String value = line.substring(idx + 1);
            mp.put(key, value);
            if (n == 0) {
                target = value;
            }
        }
        // System.out.println(target);

        String res = help(target, mp);
        while (res.indexOf('$')!=-1) {
            res = help(res, mp);
        }
        System.out.println(res);
    }

    public static String help(String value, Map<String, String> mp) {
        StringBuilder sb = new StringBuilder();
        int i = 0;
        while (i < value.length()) {
            if (value.charAt(i) == '$') {
                int end = value.indexOf('}', i);
                String var = value.substring(i + 2, end);
                String tmp = mp.containsKey(var) ? mp.get(var) : help(mp.getOrDefault(var, ""), mp);
                sb.append(tmp);
                i = end + 1;
            } else {
                sb.append(value.charAt(i));
                i++;
            }
        }
        return sb.toString();
    }
}