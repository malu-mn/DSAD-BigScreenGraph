/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bigscreengraph.Graph;

import java.util.LinkedList;

/**
 *
 * @author MNadara
 */
public class Adjacency {

    private Vertex head;

    public Vertex getHeadVertex() {
        return head;
    }

    private LinkedList<Vertex> adjacentVertices;

    public LinkedList<Vertex> getAdjacentVertices() {
        return this.adjacentVertices;
    }

    // ctor
    public Adjacency(Vertex head) {
        this.head = head;
        this.adjacentVertices = new LinkedList<>();
    }

    public void AddEdgeTo(Vertex v) {
        this.adjacentVertices.add(v);
    }

    public int EdgeCount() {
        return adjacentVertices.size();
    }
}
