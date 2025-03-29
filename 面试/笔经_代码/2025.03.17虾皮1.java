// LC16
import java.util.*;  
   
public class Main {  
    public static void main(String[] args) {  
        Scanner in = new Scanner(System.in);  
        String s = in.nextLine();
        int res = 1;
        int n = s.length();
        if (n <= 1) {
            System.out.println(n);
            return;
        }
        int left = 0, right = 0;
        Map<Character, Integer> mp = new HashMap<>();

        while (right < n) {
            char rc = s.charAt(right);
            int idx = mp.getOrDefault(rc, 0);
            left = Math.max(left, idx);
            res = Math.max(res, right-left+1);
            mp.put(rc, right+1);
            right++;
        }

        System.out.println(res);
        return;
    }
}
