package springboot.algorithm.linkedlist.交换相邻节点对的值;

import springboot.algorithm.linkedlist.ListNode;
import springboot.algorithm.linkedlist.Node;

public class SwapPairs {

	public static void main(String[] args) {
		Node head = ListNode.getSingleList();
		ListNode.printList(head);
		head = new SwapPairs().swapPairs(head);
		ListNode.printList(head);
	}

	public Node swapPairs(Node head) {
		Node root = head;
		if (head != null && head.next != null) {
			root = head.next;
			Node pre = head;
			Node p = null;
			Node q = null;
			while (head != null && head.next != null) {

				p = head.next;
				q = p.next;

				pre.next = p;
				p.next = head;
				head.next = q;

				pre = head;
				head = q;
			}
		}
		return root;
	}
}