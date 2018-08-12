package springboot.algorithm.binarytree.判断一棵树是否是BST;

import java.util.LinkedList;
import java.util.Queue;

import springboot.algorithm.binarytree.TreeNode;

/**
 * 要判断是否是二叉查询树，标准就是看每一个节点是否满足： 1、左节点及以下节点的值比它小； 2、右节点及以下节点的值比它大。
 * 当然，前提是子节点都存在的情况。 所以，我们需要从根节点不断向下递归，只要所有节点都满足，那么就是BST， 否则，就不是。
 * 
 * @author min
 *
 */
public class Test {

	public boolean checking(TreeNode root) {
		Queue<TreeNode> queue = new LinkedList<TreeNode>();
		boolean leaf = false; // 叶子结点
		TreeNode left;
		TreeNode right;
		queue.add(root);
		while (!queue.isEmpty()) {
			root = queue.poll();
			left = root.leftNode;
			right = root.rightNode;
			if ((leaf && (left != null || right != null)) || (left == null && right != null)) {
				// 如果之前层遍历的结点没有右孩子，且当前的结点有左或右孩子，直接返回false
				// 如果当前结点有右孩子却没有左孩子，直接返回false
				return false;
			}
			if (left != null) {
				queue.offer(root.leftNode);
			}
			if (right != null) {
				queue.offer(root.rightNode);
			} else {
				leaf = false; // 如果当前结点没有右孩子，那么之后层遍历到的结点必须为叶子结点
			}
		}
		return true;
	}
}
