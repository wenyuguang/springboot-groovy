package springboot.algorithm.linkedlist.查找倒数第K个节点;

import springboot.algorithm.linkedlist.ListNode;
import springboot.algorithm.linkedlist.Node;

/**
 * 
 * LastKNode.java
 * Description: 
 * <p>name: wen </p>
 * <p>Date:2018年8月10日 上午11:41:21</p>
 * @author Tony
 * @version 1.0
 *
 */
public class LastKNode {

	public static void main(String[] args) {
		Node head = ListNode.getSingleList();
		ListNode.printList(head);
		int k = 2;
		head = new LastKNode().getLastKNode(head, k);
		System.out.println(head.data);

	}

	/**
	 * 
	 * @function 先将node向右移动k，然后将node和head等间距右移直到node为null，此时head节点则为导数第K节点
	 * @author Tony
	 * @creaetime 2018年8月10日 上午11:41:45
	 * @param head
	 * @param k
	 * @return
	 */
	public Node getLastKNode(Node head, int k) {
		Node node = head;
		while (node.next != null && k > 0) {
			node = node.next;
			k--;
		}
		while (node != null) {
			node = node.next;
			head = head.next;
		}
		return head;
	}
}