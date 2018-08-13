package springboot.offer.二叉搜索树的第k个结点;

/**
 * 
 * Test63.java
 * Description:给定一棵二叉搜索树，请找出其中的第k大的结点
 * <p>name: wen </p>
 * <p>Date:2018年8月13日 下午3:32:35</p>
 * @author Tony
 * @version 1.0
 *
 */
public class Test63 {
    private static class BinaryTreeNode {
        private int val;
        private BinaryTreeNode left;
        private BinaryTreeNode right;

        public BinaryTreeNode(int val) {
            this.val = val;
        }

        @Override
        public String toString() {
            return val + "";
        }
    }

    /**
     * 
     * @function 如果按照中序遍历的顺序遍历一棵二叉搜索树，遍历序列的数值是递增排序的。
     * 只需要用中序遍历算法遍历一棵二叉搜索树，就很容易找出它的第k大结点。
     * @author Tony
     * @creaetime 2018年8月13日 下午3:32:44
     * @param root
     * @param k
     * @return
     */
    public static BinaryTreeNode kthNode(BinaryTreeNode root, int k) {
        if (root == null || k < 1) {
            return null;
        }

        int[] tmp = {k};
        return kthNodeCore(root, tmp);
    }

    private static BinaryTreeNode kthNodeCore(BinaryTreeNode root, int[] k) {
        BinaryTreeNode result = null;

        // 先成左子树中找
        if (root.left != null) {
          result =  kthNodeCore(root.left, k);
        }

        // 如果在左子树中没有找到
        if (result == null) {
            // 说明当前的根结点是所要找的结点
            if(k[0] == 1) {
                result = root;
            } else {
                // 当前的根结点不是要找的结点，但是已经找过了，所以计数器减一
                k[0]--;
            }
        }

        // 根结点以及根结点的左子树都没有找到，则找其右子树
        if (result == null && root.right != null) {
            result = kthNodeCore(root.right, k);
        }

        return result;
    }

    public static void main(String[] args) {
        BinaryTreeNode n1 = new BinaryTreeNode(1);
        BinaryTreeNode n2 = new BinaryTreeNode(2);
        BinaryTreeNode n3 = new BinaryTreeNode(3);
        BinaryTreeNode n4 = new BinaryTreeNode(4);
        BinaryTreeNode n5 = new BinaryTreeNode(5);
        BinaryTreeNode n6 = new BinaryTreeNode(6);
        BinaryTreeNode n7 = new BinaryTreeNode(7);
        BinaryTreeNode n8 = new BinaryTreeNode(8);
        BinaryTreeNode n9 = new BinaryTreeNode(9);

        n1.left = n2;
        n1.right = n3;
        n2.left = n4;
        n2.right = n5;
        n3.left = n6;
        n3.right = n7;
        n4.left = n8;
        n4.right = n9;

        print(n1);
        System.out.println();

        for (int i = 0; i <= 10; i++) {
            System.out.printf(kthNode(n1, i) + ", ");
        }

    }

    /**
     * 中序遍历一棵树
     * @param root
     */
    private static void print(BinaryTreeNode root) {
        if (root != null) {
            print(root.left);
            System.out.printf("%-3d", root.val);
            print(root.right);
        }
    }
}