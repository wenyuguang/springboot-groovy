package springboot.algorithm.linkedlist.删除重复节点;

import springboot.algorithm.linkedlist.ListNode;
import springboot.algorithm.linkedlist.Node;

public class DeleteDuplecateNode {

	public static void main(String[] args) {
		Node head = ListNode.getSingleList();
		ListNode.printList(head);
		deleteNode(head);
		ListNode.printList(head);
		ListNode.printList(deleteDuplications());
		
	}

	public static void deleteNode(Node head) {
		while (head.next != null) {
			Node node = head;
			while (node != null && node.next != null) {
				if (node.next.data == head.data) {
					node.next = node.next.next;
				}
				node = node.next;
			}
			head = head.next;
		}
	}

	public static Node deleteDuplication(Node pHead) {
		Node first = new Node();
		first.next = pHead;
		Node last = first;
		Node p = pHead;

		while (p != null && p.next != null) {
			if (p.data == p.next.data) {
				int data = p.data;
				while (p != null && p.data == data) {
					p = p.next;
					last.next = p;
				}
			} else {
				last = p;
				p = p.next;
			}
		}
		return first.next;
	}
	
	private static Node deleteDuplications() {
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
		

//        // 只有一个结点
//        if (head.next == null) {
//            return head;
//        }

        // 临时的头结点
        Node root = new Node();
        root.next = head;
        // 记录前驱结点
        Node prev = root;
        // 记录当前处理的结点
        Node node = head;
        while (node != null && node.next != null) {
            // 有重复结点，与node值相同的结点都要删除
            if (node.data == node.next.data) {
                // 找到下一个不同值的节点，注意其有可能也是重复节点
                while (node.next != null && node.next.data == node.data) {
                    node = node.next;
                }
                // 指向下一个节点，prev.next也可能是重复结点
                // 所以prev不要移动到下一个结点
                prev.next = node.next;
            }
            // 相邻两个值不同，说明node不可删除，要保留
            else {
                prev.next = node;
                prev = prev.next;
            }
            node = node.next;
        }
        return root.next;

    }
}