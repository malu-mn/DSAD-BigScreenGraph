/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bigscreengraph.Container;

/**
 *
 * @author MNadara Queue using doubly linked list
 */
public class Queue<T> {

    public Node<T> front;
    public Node<T> rear;
    public int size = 0;

    public void Queue() {
        front = null;
        rear = null;
    }

    public boolean isEmpty() {
        // if empty, front and rear will be empty.
        return front == null;
    }

    public void enqueue(T data) {
        Node<T> newNode = new Node<>(data);

        // If this is the first node, both front and read need to set.
        if (rear == null && front == null) {
            this.rear = this.front = newNode;
        } else { // not the first node. Only rear needs to change
            // Add link and make the new node as rear;
            this.rear.next = newNode;
            newNode.prev = rear;
            this.rear = newNode;
        }
        this.size++;
    }

    public Node<T> peek(T data) {
        if (isEmpty()) {
            System.err.println("Empty Queue");
            return null;
        } else {
            // return front element without changing queue
            return this.front;
        }
    }

    public T dequeue() {
        if (isEmpty()) {
            System.err.println("Empty Queue");
            return null;
        }
        // remove element from front and return.
        Node<T> element = this.front;

        // Is queue having single element?
        if (this.front.next == null) {
            // reset front and rear
            this.front = this.rear = null;

        } else { // Queue has mutiple elements
            this.front = this.front.next;
            this.front.prev = null;
        }

        return element.data;
    }

    public void display() {
        if (isEmpty()) {
            System.out.println("Queue is empty.");
            return;
        }
        Node<T> current = this.front;
        do {
            System.out.print(current.data.toString() + " ");
            current = current.next;
        } while (current != null);
        System.out.println();
    }
}
