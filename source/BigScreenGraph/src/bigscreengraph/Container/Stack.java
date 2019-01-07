/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bigscreengraph.Container;

/**
 *
 * @author MNadara Stack using singly linked list
 * @param <T>
 */
public class Stack<T> {

    private Node<T> top = null;
    private int currentSize = 0;

    public boolean isEmpty() {
        return top == null;
    }

    public void push(T data) {
        Node<T> node = new Node(data);
        // Insert new node at top.
        if (!isEmpty()) {
            node.next = top;
        }
        top = node;
        this.currentSize++;
    }

    public T pop() {
        if (isEmpty()) {
            System.err.println("Empty stack");
            return null;
        }

        //Save top node reference to another variable.
        Node<T> topNode = top;
        // If stack has only one element
        if (top.next == null) {
            top = null;
            currentSize = 0;
            return topNode.data;
        }
        // More than one node
        top = top.next;
        this.currentSize--;
        return topNode.data;
    }

    public T peek() {
        if (isEmpty()) {
            System.err.println("Empty stack");
        }

        return top.data;
    }

    public void display() {
        if (isEmpty()) {
            System.out.println("Stack is empty.");
            return;
        }
        Node<T> current = top;
        do {
            System.out.print(current.data.toString() + " ");
            current = current.next;
        } while (current != null);
        System.out.println();
    }
}
