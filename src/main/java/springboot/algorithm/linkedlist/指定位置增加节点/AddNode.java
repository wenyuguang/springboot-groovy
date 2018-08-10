package springboot.algorithm.linkedlist.指定位置增加节点;

import springboot.algorithm.linkedlist.ListNode;
import springboot.algorithm.linkedlist.Node;

public class AddNode {

	public static void main(String[] args) {
		Node head = ListNode.getSingleList();
		ListNode.printList(head);
		Node add = new Node(0);
		int id = 4;
		head = new AddNode().addNode(head, add, id);
		ListNode.printList(head);
	}

	public Node addNode(Node head, Node add, int id) {
		Node node = null;
		if (id == 0) {
			node = head;
			add.next = node;
			node = add;
		} else {
			while ((node = head).next != null && id > 1) {// 寻找节点增加的位置
				node = node.next;
				id--;
			}
			add.next = node.next;
			node.next = add;
		}
		return node;
	}
}