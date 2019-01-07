/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bigscreengraph.Container;

/**
 *
 * @author MNadara
 */
public class Node<T> {

    public T data;
    public Node<T> next = null;
    public Node<T> prev = null;

    public Node(T data) {
        this.data = data;
    }
}
