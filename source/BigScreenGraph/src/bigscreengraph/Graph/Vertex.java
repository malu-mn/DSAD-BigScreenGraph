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
public class Vertex {

    private String name;

    public String getName() {
        return this.name;
    }

    private String type;

    public String getType() {
        return this.type;
    }

    private boolean visited;

    public boolean getVisited() {
        return this.visited;
    }

    public void setVisited(boolean value) {
        this.visited = value;
    }

    // ctor
    public Vertex(String name, String type) {
        this.name = name;
        this.type = type;
        this.visited = false;
    }

    public String toString() {
        return name + " (" + type + ")";
    }
}
