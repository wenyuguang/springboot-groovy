package springboot.algorithm.linkedlist.顺序查找;

import springboot.algorithm.linkedlist.ListNode;
import springboot.algorithm.linkedlist.Node;

/*
 * 查找值为num的元素位置，没有返回-1
 * 
 */
public class SeqSearch {

	public static void main(String[] args) {
		Node head = ListNode.getSingleList();
		ListNode.printList(head);
		int num = 2;
		int id = new SeqSearch().searchNumId(head, num);
		System.out.println("要查找的元素位置为：" + id);
	}

	public int searchNumId(Node head, int num) {
		int id = 1;
		while (head != null && head.data != num) {
			head = head.next;
			id++;
		}
		if (head == null)
			id = -1;
		return id;
	}
}