package springboot.algorithm.linkedlist.合并两个有序单链表;

import springboot.algorithm.linkedlist.ListNode;
import springboot.algorithm.linkedlist.Node;
import springboot.algorithm.linkedlist.排序.SortList;
/**
 * 
 * MergeSeqList.java
 * Description:参考文档https://blog.csdn.net/a1414345/article/details/52641794
 * <p>name: wen </p>
 * <p>Date:2018年8月10日 下午4:58:15</p>
 * @author Tony
 * @version 1.0
 *
 */
public class MergeSeqList {
	public static void main(String[] args) {
		Node head1 = SortList.insertSortList(ListNode.getSingleList());
		Node head2 = SortList.insertSortList(ListNode.getSingleList());
		ListNode.printList(head1);
		head1 = mergeTwoLists(head1, head2);
		ListNode.printList(head1);
		head2 = head1;
		head2.data = 2222;
		ListNode.printList(head1);

	}

	/**
	 * 
	 * @function 1.对空链表存在的情况进行处理，假如pHead1为空则返回pHead2，pHead2为空则返回pHead1。（两个都为空此情况在pHead1为空已经被拦截）
     * 2.在两个链表无空链表的情况下确定第一个结点，比较链表1和链表2的第一个结点的值，将值小的结点保存下来为合并后的第一个结点。并且把第一个结点为最小的链表向后移动一个元素。
     * 3.继续在剩下的元素中选择小的值，连接到第一个结点后面，并不断next将值小的结点连接到第一个结点后面，直到某一个链表为空。
     * 4.当两个链表长度不一致时，也就是比较完成后其中一个链表为空，此时需要把另外一个链表剩下的元素都连接到第一个结点的后面。
	 * @author Tony
	 * @creaetime 2018年8月10日 下午4:34:00
	 * @param pHead1
	 * @param pHead2
	 * @return
	 */
	public static Node mergeTwoLists(Node pHead1, Node pHead2) {
		Node head = null;
		//对空链表存在的情况进行处理，假如pHead1为空则返回pHead2，pHead2为空则返回pHead1。（两个都为空此情况在pHead1为空已经被拦截）
		if (pHead1 == null) {// 先判断两个链表是否为空
			return pHead2;
		}
		if (pHead2 == null) {
			return pHead1;
		}
		//在两个链表无空链表的情况下确定第一个结点，比较链表1和链表2的第一个结点的值，
		//将值小的结点保存下来为合并后的第一个结点。并且把第一个结点为最小的链表向后移动一个元素。
		if (pHead1.data <= pHead2.data) {
			head = pHead1;
			pHead1 = pHead1.next;
		} else {
			head = pHead2;
			pHead2 = pHead2.next;
		}
		Node temp = head;
		//继续在剩下的元素中选择小的值，连接到第一个结点后面，并不断next将值小的结点连接到第一个结点后面，直到某一个链表为空。
		while (pHead1 != null && pHead2 != null) {
			if (pHead1.data <= pHead2.data) {
				temp.next = pHead1;
				pHead1 = pHead1.next;
			} else {
				temp.next = pHead2;
				pHead2 = pHead2.next;
			}
			temp = temp.next;
		}
		//当两个链表长度不一致时，也就是比较完成后其中一个链表为空，此时需要把另外一个链表剩下的元素都连接到第一个结点的后面。
		if (pHead1 != null) {
			temp.next = pHead1;
		}
		if (pHead2 != null) {
			temp.next = pHead2;
		}
		return head;
	}
}