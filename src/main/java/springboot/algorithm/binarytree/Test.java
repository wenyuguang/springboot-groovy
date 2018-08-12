package springboot.algorithm.binarytree;

import java.util.LinkedList;

public class Test {

	// 查询结点
	public TreeNode BSTsearch(TreeNode root, int Key) {
		TreeNode node = root;
		// 当节点值不等于要查找的结点值就循环，如果没有找到则返回null
		while (node.keyValue != Key) {
			if (Key < node.keyValue) {
				node = node.leftNode;
			} else {
				node = node.rightNode;
			}
			if (node == null) {
				return null;
			}
		}
		return node;
	}

	// 插入结点，插入结点的过程就是先查询再插入
	public void BSTinsert(TreeNode root, int Key) {
		TreeNode node = new TreeNode(Key);
		// 插入结点之前先找到要插入的位置，这样就要记住插入结点的父节点
		// 让父节点的左右指针指向要添加的结点
		if (root == null) {
			root = node;
		} else {
			TreeNode currentNode = root;// 定义当前节点为根节点
			TreeNode parentNode;
			while (true) {
				parentNode = currentNode;
				if (Key < currentNode.keyValue) {
					currentNode = currentNode.leftNode;
					if (currentNode == null) {
						parentNode.leftNode = node;
						return;
					}
				} else {
					currentNode = currentNode.rightNode;
					if (currentNode == null) {
						parentNode.rightNode = node;
						return;
					}
				}
			}
		}
	}

	// 遍历树，主要分为中序遍历、前序遍历和后序遍历
	// 下面以中序遍历做例子
	public void BSTdisplay(TreeNode node) {
		if (node != null) {
			BSTdisplay(node.leftNode);
			System.out.println(node.keyValue + ",");
			BSTdisplay(node.rightNode);
		}
	}
	
	  public void levelIterator(TreeNode root) {
		  if(root == null) {
			  return ;
		  }
		  LinkedList<TreeNode> queue = new LinkedList<TreeNode>();
		  TreeNode current = null;
		  queue.offer(root);//将根节点入队
		  while(!queue.isEmpty()) {
			  current = queue.poll();//出队队头元素并访问
			  System.out.print(current.keyValue +"-->");
			  //如果当前节点的左节点不为空入队
			  if(current.leftNode != null) {
				  queue.offer(current.leftNode);
			  }
			  //如果当前节点的右节点不为空，把右节点入队
			  if(current.rightNode != null) {
				  queue.offer(current.rightNode);
			  }
		  }
	  }

	// 最大值
	public int BSTmax(TreeNode root) {
		TreeNode node = root;
		TreeNode parent = null;
		while (node != null) {
			parent = node;
			node = node.rightNode;
		}
		return parent.keyValue;

	}

	// 最小值
	public int BSTmin(TreeNode root) {
		TreeNode node = root;
		TreeNode parent = null;
		while (node != null) {
			parent = node;
			node = node.leftNode;
		}
		return parent.keyValue;

	}

	// 删除节点分三种方式删除节点
	// 1、删除没有子节点的节点，直接让该节点的父节点的左节点或右节点指向空
	// 2、删除有一个子节点的节点，直接让该节点的父节点指向被删除节点的剩余节点
	// 3、删除有三个节点的子节点，找到要删除节点的后继节点， 用该节点替代删除的节点
	public boolean BSTdelete(TreeNode root, int Key) {
		// 首先查找节点，并记录该节点的父节点引用
		TreeNode current = root;
		TreeNode parent = root;
		boolean isLeftNode = true;
		while (current.keyValue != Key) {
			parent = current;
			if (Key < current.keyValue) {
				isLeftNode = true;
				current = current.leftNode;
			} else {
				isLeftNode = false;
				current = current.rightNode;
			}
		}
		if (root == null) {
			System.out.println("没有找到要删除的节点！");
			return false;
		}
		// 下面分三种情况删除节点
		if (current.leftNode == null && current.rightNode == null) { // 要删除的节点没有子节点
			if (current == root) { // 根节点就删除整棵树
				root = null;
			} else if (isLeftNode) { // 如果是左节点，做节点指向空
				parent.leftNode = null;
			} else { // 如果是右节点，右节点指向空
				parent.rightNode = null;
			}
		} else if (current.leftNode == null) { // 要删除的节点只有右节点
			if (current == root) {
				root = current.rightNode;
			} else if (isLeftNode) {
				parent.leftNode = current.rightNode;
			} else {
				parent.rightNode = current.rightNode;
			}
		} else if (current.rightNode == null) { // 要删除的节点只有左节点
			if (current == root) {
				root = current.leftNode;
			} else if (isLeftNode) {
				parent.leftNode = current.leftNode;
			} else {
				parent.rightNode = current.leftNode;
			}
		} else { // 要删除的节点有两个节点
			TreeNode successor = findSuccessor(current);
			if (current == root) {
				root = successor;
			} else if (isLeftNode) {
				parent.leftNode = successor;
			} else {
				parent.rightNode = successor;
			}
			successor.leftNode = current.leftNode;
		}
		return true;
	}

	private TreeNode findSuccessor(TreeNode delNode) {
		TreeNode parent = delNode;
		TreeNode successor = delNode;
		TreeNode current = delNode.rightNode;
		while (current != null) {
			parent = successor;
			successor = current;
			current = current.leftNode;
		}

		if (successor != delNode.rightNode) {
			parent.leftNode = successor.rightNode;
			successor.rightNode = delNode.rightNode;
		}
		return successor;
	}
}
