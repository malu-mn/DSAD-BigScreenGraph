/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bigscreengraph.Graph;

import bigscreengraph.Container.Queue;
import bigscreengraph.Container.Stack;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;

/**
 *
 * @author MNadara Generic implementation of graph
 */
public class Graph {

    // This class implementation uses adjacency list representation
    ArrayList<Adjacency> adjacencyList;
    // Number of vertices currently present in the graph.
    int size = 0;
    int edgeLimit = 0;

    // ctor - Initialize graph with given number of vertices.
    public Graph(int maxVertexCount, int edgeLimit) {
        adjacencyList = new ArrayList<>(maxVertexCount);
        this.edgeLimit = edgeLimit;
    }

    public int VertexCount() {
        return size;
    }

    // Creates new vertes if the vertex doesn't exists in the graph, 
    // else returns existing vertex
    public Vertex AddVertex(String name, String type) {
        Vertex exisingVertex = findVertex(name, type);

        if (exisingVertex != null) {
            return exisingVertex;
        } else {
            // If the  given  vertex doesn't exist.
            // Create new vertex and initialise adjacency list for that vertex
            Vertex head = new Vertex(name, type);
            this.adjacencyList.add(new Adjacency(head));

            // increment vertex count
            this.size++;
            return head;
        }
    }

    public boolean CanAddEdge(Vertex v1, Vertex v2) {
        Adjacency adjV1 = findAdjacency(v1.getName(), v1.getType());
        if (adjV1.EdgeCount() == this.edgeLimit) {
            System.out.println("Vertex : " + v1.toString()
                    + " reached edge limit of " + this.edgeLimit + ".");
            return false;
        }

        Adjacency adjV2 = findAdjacency(v2.getName(), v2.getType());
        if (adjV2.EdgeCount() == this.edgeLimit) {
            System.out.println("Vertex : " + v2.toString()
                    + " reached edge limit of " + this.edgeLimit + ".");
            return false;
        }

        // Both validation passed.
        return true;
    }

    public void AddEdge(Vertex v1, Vertex v2) {
        // Find adjacency with given head vertex and add new edge to graph
        // Since it is undirected graph, should add for both vertices
        Adjacency adjV1 = findAdjacency(v1.getName(), v1.getType());
        adjV1.AddEdgeTo(v2);
        Adjacency adjV2 = findAdjacency(v2.getName(), v2.getType());
        adjV2.AddEdgeTo(v1);
    }

    public Vertex getVertex(String name, String type) {
        return findVertex(name, type);
    }

    private Vertex findVertex(String name, String type) {
        Adjacency adj = findAdjacency(name, type);
        return adj == null ? null : adj.getHeadVertex();
    }

    private Adjacency findAdjacency(String name, String type) {
        for (Adjacency item : adjacencyList) {
            Vertex head = item.getHeadVertex();
            if (head.getName().equalsIgnoreCase(name)
                    && head.getType().equalsIgnoreCase(type)) {
                return item;
            }
        }
        return null;
    }

    private Adjacency findAdjacency(Vertex headerVertex) {
        for (Adjacency item : adjacencyList) {
            if (item.getHeadVertex().equals(headerVertex)) {
                return item;
            }
        }
        return null;
    }

    // Display all vertexes
    public void displayGraph() {
        for (Adjacency adj : adjacencyList) {
            displayAdjacency(adj);
        }
    }

    // Display vertex of given type
    public void displayGraph(String type) {
        for (Adjacency adj : adjacencyList) {
            if (adj.getHeadVertex().getType().equalsIgnoreCase(type)) {
                displayAdjacency(adj);
            }
        }
    }

    private void displayAdjacency(Adjacency adj) {
        System.out.println("Vertex :" + adj.getHeadVertex().toString());
        System.out.println("  ---   Having edges to : "
                + adj.getAdjacentVertices().toString());
    }

    public void displayEdges(String name, String type) {
        Adjacency adj = findAdjacency(name, type);
        if (adj == null) {
            System.out.println(name + "(" + type + ") not found in the graph");
        } else {
            displayAdjacency(adj);
        }
    }

    private boolean hasEdgeBetween(Vertex v1, Vertex v2) {
        Adjacency adj = findAdjacency(v1);
        ListIterator<Vertex> itr = adj.getAdjacentVertices().listIterator();
   
        while (itr.hasNext()) {
            // if an edge exists, v2 will be present in adj.
            if (v2 == itr.next()) {
                return true;
            }
        }
        // Didn't find edge
        return false;
    }

    // Find relationship R(M1, M2) defined by:
    // M1-> A1 and M2 -> A1, then M1 and M2 are related. R(M1,M2)
    public Vertex findRelatingVertex(Vertex v1, Vertex v2) {
        // Find adjacent vertices of v1
        ListIterator<Vertex> itr = findAdjacency(v1)
                .getAdjacentVertices().listIterator();
        
        while (itr.hasNext()) {
            // Since it is in the adjacency, there is an edge between v1 and current.
            Vertex current = itr.next();
            if(hasEdgeBetween(current, v2)){
                // This is the relating vertex.
                return current;
            }
        }
        return null;
    }

    public LinkedList<Vertex> getBsfPath(Vertex v1, Vertex v2) {
        // To find path between v1 & v2, we can use BFS with the help of a queue.
        Queue<Vertex> queue = new Queue();
        //queue = new LinkedList<>();
        LinkedList<Vertex> path = new LinkedList<>();

        // Mark all vertex as not visited
        resetVertices();

        // Mark v1 as the starting node
        //queue.add(v1);
        queue.enqueue(v1);
        Vertex current = null, next = null;

        while (!queue.isEmpty()) {
            current = queue.dequeue();
            // Mark vertex as visited, once all the adjacent nodes are pushed to queue
            current.setVisited(true);
            // To maintain the order we visited the vertex add it in the path.
            path.add(current);
            // Find vertices
            Adjacency adj = findAdjacency(current.getName(), current.getType());
            if (adj != null) {

                Iterator<Vertex> itr = adj.getAdjacentVertices().listIterator();
                while (itr.hasNext()) {
                    next = itr.next();
                    // Is this the destination node?
                    if (next == v2) {
                        path.add(next);
                        return path;
                    }
                    // Add to queue if not already visited
                    if (!next.getVisited()) {
                        queue.enqueue(next);
                    }
                }
            }
        }

        return null;
    }

    public LinkedList<Vertex> getDsfPath(Vertex v1, Vertex v2) {
        // To find path between vi and v2, we can use DFS with the help of a stack.
        Stack<Vertex> stack;
        LinkedList<Vertex> path;

        stack = new Stack<>();
        path = new LinkedList<>();

        // Mark all vertex as not visited
        resetVertices();

        // Mark v1 as the starting node
        stack.push(v1);
        Vertex current = null, next = null;

        while (!stack.isEmpty()) {
            current = stack.pop();
            // Mark vertex as visited, once all the adjacent nodes are pushed to queue
            current.setVisited(true);
            // To maintain the order we visited the vertex add it in the path.
            path.add(current);
            // Find vertices
            Adjacency adj = findAdjacency(current.getName(), current.getType());
            if (adj != null) {

                Iterator<Vertex> itr = adj.getAdjacentVertices().listIterator();
                while (itr.hasNext()) {
                    next = itr.next();
                    // Is this the destination node?
                    if (next == v2) {
                        path.add(next);
                        return path;
                    }
                    // Add to queue if not already visited
                    if (!next.getVisited()) {
                        stack.push(next);
                    }
                }
            }
        }

        return null;
    }

    public boolean isReachable(Vertex v1, Vertex v2) {
        // To find path between vi and v2, we can use BFS with the help of a queue.
        Queue<Vertex> queue = new Queue<>();

        // Mark all vertex as not visited
        resetVertices();

        // Mark v1 as the starting node
        queue.enqueue(v1);
        Vertex current = null, next = null;

        while (!queue.isEmpty()) {
            current = queue.dequeue();
            // Mark vertex as visited
            current.setVisited(true);
            // Find vertices
            Adjacency adj = findAdjacency(current.getName(), current.getType());
            if (adj != null) {
                Iterator<Vertex> itr = adj.getAdjacentVertices().listIterator();
                while (itr.hasNext()) {
                    next = itr.next();
                    // Is this the destination node?
                    if (next == v2) {
                        return true;
                    }
                    // Add to queue if not already visited
                    if (!next.getVisited()) {
                        queue.enqueue(next);
                    }
                }
            }
        }
        // No path between v1 and v2
        return false;
    }

    public LinkedList<Vertex> findPath(Vertex v1, Vertex v2) {
        LinkedList<Vertex> path = new LinkedList<>();

        Vertex parent = null, child = null;

        parent = v1;

        if (isReachable(v1, v2)) {
            // For sure there exists a path
            path.add(v1);
            do {
                // Get adjacent vertices
                Iterator<Vertex> itr = findAdjacency(parent).getAdjacentVertices().listIterator();
                while (itr.hasNext()) {
                    child = itr.next();
                    if (child == v2) {
                        // we reached destination node.
                        path.add(v2);
                        return path;
                    }
                    if (isReachable(child, v2)) {
                        // Found the vertex which will connect to destination.
                        // Not interested in other adjacent vertex, hence break.
                        path.add(child);
                        parent = child;
                        break;
                    }
                }

            } while (parent != v2);

        }
        return null;
    }

    private void resetVertices() {
        for (Adjacency adj : adjacencyList) {
            adj.getHeadVertex().setVisited(false);
        }
    }

    // Find transitive relation between to vertices.
    // M1-> A1 and M2 -> A1, then M1 and M2 are related. R(M1,M2)
    // R(M1,M2) and R(M2,M3), then T(M1, M3)
    public LinkedList<Vertex> findTransitiveRelation(Vertex v1, Vertex v2) {

        // Find BFS path between v1 to v2 and v2 to v1.
        // Take the common items alone, will give the relation between v1 & v2
        LinkedList<Vertex> path1 = getBsfPath(v1, v2);
        LinkedList<Vertex> path2 = getBsfPath(v2, v1);

        if (path1 != null && path2 != null) {
            LinkedList<Vertex> commonPath = new LinkedList<>();
            for (Vertex vx : path1) {
                if (vx != v1 && vx != v2 && path2.contains(vx)) {
                    commonPath.add(vx);
                }
            }
            return commonPath.size() > 0 ? commonPath : null;
        }
        return null;
    }
}
