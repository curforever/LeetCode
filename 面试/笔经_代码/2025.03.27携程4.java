import java.util.Scanner;
import java.util.*;

// 注意类名必须为 Main, 不要有任何 package xxx 信息
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        PriorityQueue<int[]> queue = new PriorityQueue<>(
            (a,b) -> a[0]!=b[0] ? Integer.compare(a[0], b[0]) : Integer.compare(a[1], b[1])
        );
        PriorityQueue<Integer> queue2 = new PriorityQueue<>();

        for (int i = 0; i < n; i++) {
            int start = sc.nextInt();
            int end = sc.nextInt();
            queue.offer(new int[]{start, end});
        }

        int res = 0;
        while (!queue.isEmpty()) {
            int[] cur = queue.poll();
            int start = cur[0];
            int end = cur[1];
            while (!queue2.isEmpty() && queue2.peek()<start) {
                queue2.poll();
            }
            res += queue2.size();
            queue2.offer(end);
        }

        System.out.println(res);
        sc.close();
    }
}
