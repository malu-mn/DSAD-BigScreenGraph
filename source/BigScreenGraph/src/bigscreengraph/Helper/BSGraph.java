/*
 /**
 * @author Malu(2018AB04154), Sanjib(2018AB04153), Pradeep(2018AB04152)
 */
package bigscreengraph.Helper;

import bigscreengraph.Graph.Graph;
import bigscreengraph.Graph.Vertex;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Bigscreen graph helper to handle the graph operations
 */
public class BSGraph {
    private final String delimiter = "/";
    private Graph graph = null;
    
    //maximum number of actors per movie
    private maxActorsPerMovie = 2;

    /**
     * Constructor - initialize with the maximum number of actors allowed per movie
     */
    public BSGraph(int maxActorsPerMovie){
    	this.maxActorsPerMovie = maxActorsPerMovie;
    }
    
    /**
     * Process each line and created nodes and edges in the graph
     */
    private CreateGraph(ArrayList<String> movieList) throws Exception {
        // Initialize graph, with max number of vertex possible and the edge limited to 2
        graph = new Graph(movieList.size() * 3, 2);

        for (int index = 0; index < movieList.size(); index++) {
            // In each line, first will be movie, others will be actors.
            String[] movieInfo = movieList.get(index).split(delimiter);

            // check if the number of actors is as per the limit per movie.
            if (movieInfo.length > maxActorsPerMovie + 1 || movieInfo.length < 2) {
                throw new Exception("Invalid format in the file at line number " + (index+1));
            }

            Vertex movie = graph.AddVertex(movieInfo[0].trim(), VertexType.MOVIE.toString());
            
            for (int j = 1; j < movieInfo.length; j++){
                Vertex actor = graph.AddVertex(movieInfo[j].trim(), VertexType.ACTOR.toString());
	            if (graph.CanAddEdge(movie, actor)) {
	                graph.AddEdge(movie, actor);
	            } else {
	                throw new Exception("Can't add edges for line: " + movieList.get(index));
	            }
        	}
        }
    }

    public void DisplayGraph() {
        System.out.println("--GRAPH--");
        graph.displayGraph();
    }

    public void DisplayGraph(VertexType type) {
        System.out.println("--GRAPH-- ( Only " + type.toString() + ") vertices");
        graph.displayGraph(type.toString());
    }

    public void DisplayMoviesOfActor(String actor) {
        System.out.println("Movies of : " + actor);
        graph.displayEdges(actor, VertexType.ACTOR.toString());
    }

    public void DisplayActorsOfMovie(String movie) {
        System.out.println("Actors in : " + movie);
        graph.displayEdges(movie, VertexType.MOVIE.toString());
    }

    public void FindMovieRelatingActor(String movieA, String movieB) throws Exception {
        Vertex v1 = graph.getVertex(movieA, VertexType.MOVIE.toString());
        if (v1 == null) {
            throw new Exception(" Didn't find " + movieA + " in the graph");
        }

        Vertex v2 = graph.getVertex(movieB, VertexType.MOVIE.toString());
        if (v2 == null) {
            throw new Exception(" Didn't find " + movieB + " in the graph");
        }

        Vertex relatingActor = graph.findRelatingVertex(v1, v2);

        if (relatingActor != null) {
            System.out.println(v1 + " Related to " + v2 + " through :"
                    + relatingActor.toString());
        } else {
            System.out.println("Movies " + v1 + ", " + v2 
                    + " don't have a common actor.");
        }

    }

    public void findMovieTransRelation(String movieA, String movieB) throws Exception {
        Vertex v1 = graph.getVertex(movieA, VertexType.MOVIE.toString());
        if (v1 == null) {
            throw new Exception(" Didn't find " + movieA + " in the graph");
        }

        Vertex v2 = graph.getVertex(movieB, VertexType.MOVIE.toString());
        if (v2 == null) {
            throw new Exception(" Didn't find " + movieB + " in the graph");
        }

        LinkedList<Vertex> bsfPath = graph.getBsfPath(v1, v2);
        if (bsfPath != null) {
            System.out.print(" BFS Path :");
            displayPath(bsfPath);
        } else {
            System.out.print("No BFS Path :");
        }

        LinkedList<Vertex> dsfPath = graph.getDsfPath(v1, v2);
        if (dsfPath != null) {
            System.out.print(" DFS Path :");
            displayPath(dsfPath);
        } else {
            System.out.print("No DFS Path :");
        }

        LinkedList<Vertex> relation = graph.findTransitiveRelation(v1, v2);

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
        return graph != null;
    }
    
//  public void readActMovfile(ArrayList<String> fileContent) {
//  try {
//      graph = ProcessToGraph(fileContent);
//      System.out.println("Graph created.");
//  } catch (Exception ex) {
//      System.out.println(ex.getMessage());
//  }
//}
}
