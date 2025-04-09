// 60%

import java.util.Scanner;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = in.nextInt(); 
        }
        Stack<Integer> st = new Stack<>();
        int res1 = -1, res2 = -1;
        for (int i = 0; i < a.length; i++) {
            int aa = a[i];
            if (aa > 0) {
                st.push(aa);
            } else {
                int ex = -aa;
                if (!st.isEmpty() && st.peek() == ex) {
                    st.pop();
                } else {
                    res1 = i+1;
                    break;
                    
                }
            }
        }
        res2 = res1+1;
        System.out.println(res1 + " " + res2);

    }
}
