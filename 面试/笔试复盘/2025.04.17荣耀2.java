// 200分 100%
/*
输入一个矩阵，每个元素是一个double，将其中所有大于零的元素提取到一维数组中
输出这个数组的中位数（保留两位小数）
*/
import java.util.*;

// 注意类名必须为 Main, 不要有任何 package xxx 信息
public class Main {
    public static PriorityQueue<Double> minHeap = new PriorityQueue<>(Comparator.naturalOrder());
    public static PriorityQueue<Double> maxHeap = new PriorityQueue<>(Comparator.reverseOrder());
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();
        input = input.substring(1, input.length() - 1);
        // System.out.println(input);
        String[] rows = input.split(";");
        for (String row : rows) {
            String[] nums = row.split(" ");
            for (String num : nums) {
                if (num.equals(""))
                    continue;
                double val = Double.parseDouble(num);
                // System.out.printf("%.2f\n", val);
                if (val > 0) {
                    add(val);
                }
            }
        }
        if (maxHeap.size() == minHeap.size()) {
            System.out.printf("%.2f\n", (maxHeap.peek()+minHeap.peek()) / 2.0);
        } else {
            System.out.printf("%.2f\n", maxHeap.peek());
        }
    }

    public static void add(double num) {
        if (maxHeap.isEmpty() || num <= maxHeap.peek()) {
            maxHeap.offer(num);
        } else {
            minHeap.offer(num);
        }

        if (maxHeap.size() > minHeap.size()+1)
            minHeap.offer(maxHeap.poll());
        else if (minHeap.size() > maxHeap.size())
            maxHeap.offer(minHeap.poll());
    }
}