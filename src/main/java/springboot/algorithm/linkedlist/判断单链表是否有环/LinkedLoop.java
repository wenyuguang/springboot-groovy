package springboot.algorithm.linkedlist.判断单链表是否有环;

public class LinkedLoop {
	// 内部静态类定义结点类
	static class Node {
		int val;
		Node next;

		public Node(int val) {
			this.val = val;
		}
	}

	/**
	 * 
	 * @function 定义两个指针p, q，其中p每次向前移动一步，
	 * q每次向前移动两步，所以就成p为慢指针，q为快指针。
	 * 那么如果单链表存在环，则p和q进入环后一定会在某一点相遇，
	 * 因为进入环后就会一直循环下去，否则q将首先遇到null，就说明不存在环。
	 * @author Tony
	 * @creaetime 2018年8月10日 下午3:31:55
	 * @param head
	 * @return
	 */
	public static boolean hasLoop(Node head) {
		Node p1 = head; // 定义一个引用指向头结点
		Node p2 = head.next; // 定义另一个引用指向头结点的下一个结点

		/**
		 * 因为引用p2要比p1走的快，所以要用它作为循环的结束标志，为了防止当链表中个数为
		 * 偶数时出现p2.next=null空指针异常，这时可以在循环中进行一下判断，如果这种情况 出现一定是无环的。
		 */
		while (p2 != null && p2.next != null) {
			p1 = p1.next;
			p2 = p2.next.next;
			if (p2 == null)
				return false;
			// 为了防止p2.val出现空指针异常，需要对p2进行判断
			int val1 = p1.val;
			int val2 = p2.val;
			if (val1 == val2)
				return true;
		}
		return false;
	}

	public static void main(String[] args) {
		Node n1 = new Node(1);
		Node n2 = new Node(3);
		Node n3 = new Node(6);
		Node n4 = new Node(4);
		Node n5 = new Node(5);
		Node n6 = new Node(10);
		n1.next = n2;
		n2.next = n3;
		n3.next = n4;
		n4.next = n5;
		n5.next = n6;
		n6.next = n5;
		System.out.println(hasLoop(n1));
	}
}