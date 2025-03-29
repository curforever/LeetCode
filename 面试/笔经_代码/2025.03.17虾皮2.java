//LC21

import java.util.*;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);  
        String ss = in.nextLine();
        // System.out.println(ss);
        Pattern pattern = Pattern.compile("L1 = \\[(.*?)\\],   L2 = \\[(.*?)\\]");
        Matcher matcher = pattern.matcher(ss);
        List<Integer> L1 = new LinkedList<>();
        List<Integer> L2 = new LinkedList<>();
        if (matcher.find()) {
            L1 = Arrays.stream(matcher.group(1).split(","))
                .map(Integer::parseInt)
                .collect(Collectors.toCollection(LinkedList::new));
            L2 = Arrays.stream(matcher.group(2).split(","))
                .map(Integer::parseInt)
                .collect(Collectors.toCollection(LinkedList::new));
            // System.out.println(L1);
            // System.out.println(L2);
        }
        List<Integer> merged = new LinkedList<>();
        Iterator<Integer> it1 = L1.iterator();
        Iterator<Integer> it2 = L2.iterator();

        Integer val1 = it1.hasNext() ? it1.next() : null;
        Integer val2 = it2.hasNext() ? it2.next() : null;

        while (val1!=null || val2!=null) {
            if (val1 == null) {
                merged.add(val2);
                val2 = it2.hasNext() ? it2.next() : null;
            } else if (val2 == null) {
                merged.add(val1);
                val1 = it1.hasNext() ? it1.next() : null;
            } else if (val1 <= val2) {
                merged.add(val1);
                val1 = it1.hasNext() ? it1.next() : null;
            } else {
                merged.add(val2);
                val2 = it2.hasNext() ? it2.next() : null;
            }
        }

        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (Integer num : merged) {
            sb.append(num).append(",");
        }
        sb.deleteCharAt(sb.length()-1);
        sb.append("]");
        
        System.out.println(sb.toString());
        return;
    }
}
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
