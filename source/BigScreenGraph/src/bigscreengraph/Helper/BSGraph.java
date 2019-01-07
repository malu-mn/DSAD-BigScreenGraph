/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bigscreengraph.Helper;

import bigscreengraph.Graph.Graph;
import bigscreengraph.Graph.Vertex;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 *
 * @author MNadara
 */
public class BSGraph {

    private final String delimiter = "/";
    private Graph _graph = null;

    public void readActMovfile(ArrayList<String> fileContent) {
        try {
            _graph = ProcessToGraph(fileContent);
            System.out.println("Graph formed.");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    private Graph ProcessToGraph(ArrayList<String> fileContent) throws Exception {
        Graph graph;

        // Initialize graph, with max number of vertex possible and the edge limited to 2
        graph = new Graph(fileContent.size() * 3, 2);

        for (int index = 0; index < fileContent.size(); index++) {
            // In each line, first will be movie, other 2 will be actors.
            String[] movieActors = fileContent.get(index).split(delimiter);

            if (movieActors.length != 3) {
                // line number starts at 1, where as index at 0.
                throw new Exception("Invalid format in the file at line number " + ++index);
            }

            Vertex movie = graph.AddVertex(movieActors[0].trim(), VertexType.MOVIE.toString());
            Vertex actor1 = graph.AddVertex(movieActors[1].trim(), VertexType.ACTOR.toString());
            Vertex actor2 = graph.AddVertex(movieActors[2].trim(), VertexType.ACTOR.toString());

            if (graph.CanAddEdge(movie, actor1) && graph.CanAddEdge(movie, actor2)) {
                graph.AddEdge(movie, actor1);
                graph.AddEdge(movie, actor2);
            } else {
                throw new Exception(" Can't add edges for line : " + fileContent.get(index));
            }
        }
        return graph;
    }

    public void displayActMov() {
        System.out.println("--GRAPH--");
        _graph.displayGraph();
    }

    public void displayActMov(VertexType type) {
        System.out.println("--GRAPH-- ( Only " + type.toString() + ") vertices");
        _graph.displayGraph(type.toString());
    }

    public void displayMoviesOfActor(String actor) {
        System.out.println("Movies of : " + actor);
        _graph.displayEdges(actor, VertexType.ACTOR.toString());
    }

    public void displayActorsOfMovie(String movie) {
        System.out.println("Actors in : " + movie);
        _graph.displayEdges(movie, VertexType.MOVIE.toString());
    }

    public void findMovieRelatingActor(String movieA, String movieB) throws Exception {
        Vertex v1 = _graph.getVertex(movieA, VertexType.MOVIE.toString());
        if (v1 == null) {
            throw new Exception(" Didn't find " + movieA + " in the graph");
        }

        Vertex v2 = _graph.getVertex(movieB, VertexType.MOVIE.toString());
        if (v2 == null) {
            throw new Exception(" Didn't find " + movieB + " in the graph");
        }

        Vertex relatingActor = _graph.findRelatingVertex(v1, v2);

        if (relatingActor != null) {
            System.out.println(v1 + " Related to " + v2 + " through :"
                    + relatingActor.toString());
        } else {
            System.out.println("Movies " + v1 + ", " + v2 
                    + " don't have a common actor.");
        }

    }

    public void findMovieTransRelation(String movieA, String movieB) throws Exception {
        Vertex v1 = _graph.getVertex(movieA, VertexType.MOVIE.toString());
        if (v1 == null) {
            throw new Exception(" Didn't find " + movieA + " in the graph");
        }

        Vertex v2 = _graph.getVertex(movieB, VertexType.MOVIE.toString());
        if (v2 == null) {
            throw new Exception(" Didn't find " + movieB + " in the graph");
        }

        LinkedList<Vertex> bsfPath = _graph.getBsfPath(v1, v2);
        if (bsfPath != null) {
            System.out.print(" BFS Path :");
            displayPath(bsfPath);
        } else {
            System.out.print("No BFS Path :");
        }

        LinkedList<Vertex> dsfPath = _graph.getDsfPath(v1, v2);
        if (dsfPath != null) {
            System.out.print(" DFS Path :");
            displayPath(dsfPath);
        } else {
            System.out.print("No DFS Path :");
        }

        LinkedList<Vertex> relation = _graph.findTransitiveRelation(v1, v2);

        if (relation != null) {
            System.out.println(v1 + " Related to " + v2 + " through :");
            displayPath(relation);
        } else {
            System.out.println(v1 + " not related to " + v2);
        }
    }

    private void displayPath(LinkedList<Vertex> path) {
        if (path != null) {
            System.out.println(" -- Path :" + path);
        }
    }

    boolean hasValidGraph() {
        return _graph != null;
    }
}
