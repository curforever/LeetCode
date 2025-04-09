// 循环字符串
/*
一个循环字符串的子字符串s，可以多次连接到自身以获得此循环字符串的最小可能字符串长度是多少？

输入“cabca“ 
输出3 
因为cab长度为3
*/

public class MinRepeatedSubstring {
    public static int minRepeatedSubstringLength(String s) {
        int n = s.length();
        
        // 尝试每个可能的子串长度 i
        for (int i = 1; i <= n; i++) {
            // 如果字符串长度可以被 i 整除
            if (n % i == 0) {
                String sub = s.substring(i, j);
                // 构造一个由 sub 重复拼接的字符串
                StringBuilder sb = new StringBuilder();
                for (int j = 0; j < n / i; j++) {
                    sb.append(sub);
                }
                
                // 如果拼接得到的字符串 sb 包含原字符串 s，返回子串的长度 i
                if (sb.toString().contains(s)) {
                    return i;
                }
            }
        }
        
        return n; // 如果找不到，返回整个字符串长度
    }

    public static void main(String[] args) {
        String s = "cabca";
        System.out.println(minRepeatedSubstringLength(s)); // 输出 3
    }
}
