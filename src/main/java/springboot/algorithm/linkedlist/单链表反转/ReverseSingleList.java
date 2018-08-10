package springboot.algorithm.linkedlist.单链表反转;

import springboot.algorithm.linkedlist.ListNode;
import springboot.algorithm.linkedlist.Node;

public class ReverseSingleList {
	public static void main(String args[]) {
		Node head = ListNode.getSingleList();
		ListNode.printList(head);
		head = new ReverseSingleList().reverseSingleList(head);
		ListNode.printList(head);
	}

	public Node reverseSingleList(Node head) {
		if (head == null || head.next == null) {
			return head;
		}
		Node preNode = head;
		Node pNode = head.next;
		Node markNode = head.next;
		head.next = null; // 一定要断开head节点的next节点，不然形成了循环
		while (markNode != null) {
			markNode = markNode.next;
			pNode.next = preNode;
			preNode = pNode;
			pNode = markNode;
		}
		return preNode;
	}
}