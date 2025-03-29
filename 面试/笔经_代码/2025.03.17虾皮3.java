// LC1456

import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);  
        String ss = in.nextLine();
        // System.out.println(ss);
        Pattern pattern = Pattern.compile("s = \"(.*?)\", k = (\\d+)");
        Matcher matcher = pattern.matcher(ss);
        StringBuilder sb = new StringBuilder();
        int k = 0;
        if (matcher.find()) {
            sb.append(matcher.group(1));
            k = Integer.parseInt(matcher.group(2));
            // System.out.println(sb);
            // System.out.println(k);
        }

        char[] ca = sb.toString().toCharArray();
        int res = 0;
        int vow = 0;
        for (int i = 0; i < ca.length; i++) {
            if (ca[i] == 'a' || ca[i] == 'e' || ca[i] == 'i' || ca[i] == 'o' || ca[i] == 'u') {
                vow++;
            }
            if (i < k-1) {
                continue;
            }
            res = Math.max(res, vow);
            char c = ca[i-k+1];
            if (c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u') {
                vow--;
            }
        }
        
        System.out.println(res);
        return;
    }
}
