package springboot.algorithm.linkedlist.删除当前节点;

import springboot.algorithm.linkedlist.ListNode;
import springboot.algorithm.linkedlist.Node;

public class DeleteCurrentNode {

	public static void main(String[] args) {
		Node head = ListNode.getSingleList();
		ListNode.printList(head);
		
		deleteNode(head);
		ListNode.printList(head);
	}

	public static void deleteNode(Node node) {
        node.data=node.next.data;
        node.next=node.next.next;
    }
}
