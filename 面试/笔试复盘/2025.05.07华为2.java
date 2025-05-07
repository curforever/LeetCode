// 200分 100%
/*

*/

// We have imported the necessary tool classes.
// If you need to import additional packages or classes, please import here.

public class Main {
    public static class TreeNode {
        int val;
        TreeNode left, right;
        TreeNode() {}
        TreeNode(int val) {
            this.val = val;
        }
        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }
    
    static int res = 0;
    
    public static void main(String[] args) {
    // please define the JAVA input here. For example: Scanner s = new Scanner(System.in);
    // please finish the function body here.
    // please define the JAVA output here. For example: System.out.println(s.nextInt());
        Scanner sc = new Scanner(System.in);
        String line = sc.nextLine().trim();
        if (line.isEmpty())
            return;
        String[] nodes = line.split("\\s+");
        if (nodes[0].equals("N"))
            return;
        // for (String node : nodes) {
        //     System.out.println(node);
        // }
        
        TreeNode root = new TreeNode(Integer.parseInt(nodes[0]));
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        
        int i = 1;
        while (!queue.isEmpty() && i < nodes.length) {
            TreeNode cur = queue.poll();
            if(!nodes[i].equals("N")) {
                TreeNode leftNode = new TreeNode(Integer.parseInt(nodes[i]));
                cur.left = leftNode;
                queue.offer(leftNode);
                // System.out.println(leftNode.val);
            }
            i++;
            if(i >= nodes.length)
                break;
            if(!nodes[i].equals("N")) {
                TreeNode rightNode = new TreeNode(Integer.parseInt(nodes[i]));
                cur.right = rightNode;
                queue.offer(rightNode);
            }
            i++;
        }
        
        
        // inorder(root);
        // root
        if (process(root) == 0) {    // 头节点没有覆盖，需要摄像头，res++
            res++;
        }
        System.out.println(res);
        
    }
    
   
    public static void inorder(TreeNode node) {
        if(node == null)    return;
        inorder(node.left);
        System.out.println(node.val+" ");
        inorder(node.right);
    }
    
    // 0 左右孩子都覆盖，父节点是无覆盖
    // 1 左右孩子至少一个没覆盖，父节点要放摄像头，res++
    // 2 左右孩子至少有一个摄像头，父节点覆盖
    // 3 头节点没有覆盖，需要摄像头，res++
    
    public static int process(TreeNode root) {
        if (root == null) { // 空节点默认覆盖
            return 2;
        }
        int left = process(root.left);
        int right = process(root.right);
        
        if (left == 2 && right == 2) {    // 左右孩子都覆盖
            return 0;
        } else if (left == 0 || right == 0) {    // 左右孩子至少一个没覆盖
            res++;
            return 1;
        } else {    // 左右孩子至少有一个摄像头
            return 2;
        }
    }
}