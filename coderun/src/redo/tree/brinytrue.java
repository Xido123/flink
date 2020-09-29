package redo.tree;

public class brinytrue {
/**
 * 二叉树的遍历练习
 *
 */
    Node head = null;

    public brinytrue(Node head) {
        this.head = head;
    }

    private static class Node{
        Object val ;
        Node leftChild;
        Node rightChild;

        public Node(Object val) {
            this.val = val;
        }
    }

    /**
     * 二叉树的前序遍历
     * 先访问根节点 然后前序遍历左子树，再前序遍历右子树
      */

    public static void preOrderTraveral(Node head){
        if(head == null)
            return;
    System.out.println(head.val+" ");
    preOrderTraveral(head.leftChild);
    preOrderTraveral(head.rightChild);
    }
    /**
     * 二叉树的中序遍历
     * 二叉树的中序遍历
     */
    public static void midOrderTraveral(Node head){
        if(head == null)
            return;
        preOrderTraveral(head.leftChild);
        System.out.println(head.val+ " ");
        preOrderTraveral(head.rightChild);

    }




}
