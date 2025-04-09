// 数组拼凑最大数的字符串
/*
    输入：nums = [3, 10]
    输出："310"

    输入：nums = [5, 9, 30, 3, 34]
    输出："9534330"
*/

import java.util.Arrays;
import java.util.Comparator;

public class MaxNumberString {
    public static String largestNumber(int[] nums) {
        // 将 int 数组转换成 String 数组
        String[] strNums = new String[nums.length];
        for (int i = 0; i < nums.length; i++) {
            strNums[i] = String.valueOf(nums[i]);
        }

        // 自定义排序：比较两个字符串拼接后哪个更大
        Arrays.sort(strNums, new Comparator<String>() {
            @Override
            public int compare(String a, String b) {
                String order1 = a + b;
                String order2 = b + a;
                return order2.compareTo(order1); // 降序排列
            }
        });

        // 如果排序后第一个是 "0"，说明所有数都是0
        if (strNums[0].equals("0")) {
            return "0";
        }

        // 拼接结果
        StringBuilder result = new StringBuilder();
        for (String str : strNums) {
            result.append(str);
        }

        return result.toString();
    }

    public static void main(String[] args) {
        int[] nums1 = {3, 10};
        System.out.println(largestNumber(nums1)); // 输出 "310"

        int[] nums2 = {5, 9, 30, 3, 34};
        System.out.println(largestNumber(nums2)); // 输出 "9534330"
    }
}
