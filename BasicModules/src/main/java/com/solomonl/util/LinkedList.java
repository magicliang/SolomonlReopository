package com.solomonl.util;

/**
 * Created by magicliang on 2016/8/2.
 */


/**
 * TODO:
 *       1. add unit test for this class, for now we use main instead
 *       2. encapsulate this into a util class
 *       3. synchronized?
 *       4. 接口化？
 * */

public class LinkedList implements List{
    private Node<String> head;

    public LinkedList() {
        super();
        head = new Node<String>("head");
        head.setNextNode(head);
        head.setPreviousNode(head);
    }

    public Node<String> getHead() {
        return head;
    }

    public void addNodeAtHead(Node<String> node) {
        Node<String> nextNode = head.getNextNode();
        node.setNextNode(nextNode);
        nextNode.setPreviousNode(node);
        head.setNextNode(node);
    }

    public void addNodeAtTail(Node<String> node) {
        Node<String> tail = head.getPreviousNode();
        node.setNextNode(head);
        head.setPreviousNode(node);
        tail.setNextNode(node);
        //Head 不能变，但tail总是可变
        tail = node;
    }

    @Override
    public String toString() {
        Node<String> node = head.getNextNode();
        StringBuilder sb = new StringBuilder("com.solomonl.util.LinkedList: \n" + head);
        while(node != head){
            sb.append("\n => " + node);
            node = node.getNextNode();
        }

        return sb.toString();
    }

    public static void main(String[] args) {
        testCase1();
        testCase2();
        testCase3();
    }

    private static void testCase1() {
        System.out.println("testCase1");
        LinkedList linkedList = new LinkedList();
        for (int i = 0; i < 10; i++) {
            linkedList.addNodeAtTail(new Node<String>((new Integer(i)).toString()));
        }
        System.out.println(linkedList);
    }
    private static void testCase2() {
        System.out.println("testCase2");
        LinkedList linkedList = new LinkedList();
        for (int i = 10; i > 0; i--) {
            linkedList.addNodeAtHead(new Node<String>((new Integer(i)).toString()));
        }
        System.out.println(linkedList);
    }

    private static void testCase3() {
        System.out.println("testCase3");
        LinkedList linkedList = new LinkedList();
        System.out.println(linkedList);
    }
}
