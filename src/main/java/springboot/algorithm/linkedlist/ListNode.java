package springboot.algorithm.linkedlist;

public class ListNode {
	/**
	 * 获取非环形单链表
	 * @return
	 */
	public static Node getSingleList() {
		Node head = new Node(3);
		Node node1 = new Node(6);
		Node node2 = new Node(8);
		Node node3 = new Node(6);
		Node node4 = new Node(2);
		Node node5 = new Node(12);
		Node node6 = new Node(32);
		Node node7 = new Node(11);
		Node node8 = new Node(32);
		Node node9 = new Node(22);
		Node node10 = new Node(26);
		Node node11 = new Node(31);
		Node node12 = new Node(32);
		head.next = node1;
		node1.next = node2;
		node2.next = node3;
		node3.next = node4;
		node4.next = node5;
		node5.next = node6;
		node6.next = node7;
		node7.next = node8;
		node8.next = node9;
		node9.next = node10;
		node10.next= node11;
		node11.next= node12;
		return head;
	}
	/**
	 * 获取环形链表
	 * @return
	 */
	public static Node getCircleList() {
		Node head = new Node(3);
		Node node1 = new Node(6);
		Node node2 = new Node(8);
		Node node3 = new Node(6);
		Node node4 = new Node(2);
		Node node5 = new Node(12);
		Node node6 = new Node(32);
		Node node7 = new Node(11);
		Node node8 = new Node(32);
		Node node9 = new Node(22);
		Node node10 = new Node(26);
		Node node11 = new Node(3);
		head.next = node1;
		node1.next = node2;
		node2.next = node3;
		node3.next = node4;
		node4.next = node5;
		node5.next = node6;
		node6.next = node7;
		node7.next = node8;
		node8.next = node9;
		node9.next = node10;
		node10.next= node11;
		node11.next = node1;
		return head;
	}

	public static void printList(Node node) {
		System.out.print("List:");
		while (node != null) {
			System.out.print(node.data + "->");
			node = node.next;
		}
		System.out.println();
	}
}