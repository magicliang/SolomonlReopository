package com.solomonl.util;

/**
 * Created by magicliang on 2016/8/2.
 */
// 虽然是一个单独的文件，但它依然是只是包可见的
// 泛型化
class Node<T> {
    // 还是放在这里吧，在内存铺排的时候，这些field反正都是null了
    private Node nextNode;
    private Node previousNode;
    private T value;

    public Node() {
    }

    public Node(T value) {
        this.value = value;
    }

    public Node getNextNode() {
        return nextNode;
    }

    public void setNextNode(Node nextNode) {
        this.nextNode = nextNode;
    }

    public Node getPreviousNode() {
        return previousNode;
    }

    public void setPreviousNode(Node previousNode) {
        this.previousNode = previousNode;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Node{" +
                "value='" + value + '\'' +
                '}';
    }
}
