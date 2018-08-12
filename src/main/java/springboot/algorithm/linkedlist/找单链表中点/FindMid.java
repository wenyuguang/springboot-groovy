package springboot.algorithm.linkedlist.找单链表中点;

import springboot.algorithm.linkedlist.ListNode;
import springboot.algorithm.linkedlist.Node;

public class FindMid {

	public static Node findMiddle(Node start) {
		Node prev;
		if (start == null) {
			return null;
		} else {
			if (start.next != null) {
				prev = start.next;
			} else {
				return start;
			}
		}
		Node follow = start;
		while (prev.next != null) {
			prev = prev.next;
			if (prev.next != null) {
				prev = prev.next;
				follow = follow.next;
			} else {
				break;
			}
		}
		return follow;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Node node = ListNode.getSingleList();
		ListNode.printList(node);
		Node res = findMiddle(node);
		System.out.println(res.data);
	}

}