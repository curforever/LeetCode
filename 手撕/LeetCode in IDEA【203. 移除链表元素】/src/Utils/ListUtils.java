package Utils;

import DataStructure.ListNode;

import java.util.ArrayList;
import java.util.List;

public class ListUtils {
    // 根据数组创建链表
    public static ListNode arrayToList(int[] arr) {
        if (arr == null || arr.length == 0) {
            return null;
        }

        ListNode head = new ListNode(arr[0]);
        ListNode current = head;
        for (int i = 1; i < arr.length; i++) {
            current.next = new ListNode(arr[i]);
            current = current.next;
        }

        return head;
    }

    // 根据链表创建数组
    public static int[] listToArray(ListNode node) {
        ListNode current = node;
        List<Integer> resultList = new ArrayList<>();
        while (current != null) {
            resultList.add(current.val);
            current = current.next;
        }
        return resultList.stream().mapToInt(i -> i).toArray();
    }

    // 打印链表
    public static void printList(ListNode head) {
        if (head == null) {
            System.out.println("null");
            return;
        }

        ListNode current = head;
        while (current != null) {
            System.out.print(current.val + " -> ");
            current = current.next;
        }
        System.out.println("null");
    }
}
