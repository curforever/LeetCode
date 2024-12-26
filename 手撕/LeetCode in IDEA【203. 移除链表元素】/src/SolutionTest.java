import DataStructure.*;
import Utils.*;
import java.util.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SolutionTest {

    Solution solution = new Solution();

    @Test
    public void test1() {
        ListNode head = ListUtils.arrayToList(new int[]{1, 2, 6, 3, 4, 5, 6});
        int val = 6;

        ListNode result = solution.removeElements(head, val);


        int[] expected = new int[]{1, 2, 3, 4, 5};
        assertArrayEquals(expected, ListUtils.listToArray(result));
    }

    @Test
    public void test2() {
        ListNode head = ListUtils.arrayToList(new int[]{7,7,7,7});
        int val = 7;

        ListNode result = solution.removeElements(head, val);


        int[] expected = new int[]{};
        assertArrayEquals(expected, ListUtils.listToArray(result));
    }

}
