/**
 * @author Malu(2018AB04154), Sanjib(2018AB04153), Pradeep(2018AB04152)
 */
package bigscreengraph.Helper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;

/*
 * Helper class to handle all user input anf file reading
 */
public class InputHelper {

    private final BSGraph bsGraph;

    // constructor
    public InputHelper() {
        bsGraph = new BSGraph(2);
    }

    // Read file name from console or default to test.txt
    private String ReadFilePath() {
        String fileName;
        
        try {
            System.out.println("Enter file name to process: ");
            Scanner scanner = new Scanner(System.in);
            fileName = scanner.nextLine();

            // Check whether the file name is valid.
            File f = new File(fileName);
            if (!f.exists() || !f.isFile()) {
                System.out.println("Invalid file name.\nUsing the file: input/test.txt");
                fileName = "input/test.txt";
            }
        } catch (Exception ex) {
            System.out.print("Reading the file failed with exception: "
                    + ex.getMessage());
            System.out.println("Using file name: input/test.txt");
            fileName = "input/test.txt";
        }
        
        return fileName;
    }

    // Read file content and return as a collection of lines
    private ArrayList<String> ReadContent(String fileName) {
        ArrayList<String> strList;
        strList = new ArrayList<>();
        BufferedReader reader;

        try {
            reader = new BufferedReader(new FileReader(fileName));
            String line;
            while ((line = reader.readLine()) != null) {
                strList.add(line);
            }
            reader.close();
        } catch (IOException ex) {
            System.out.print(fileName + "Reading failed with exception: " + ex.getMessage());
        } finally {
            reader.close();
        }

        return strList;
    }

    /*
     * Display the menu options
     */
    public void ShowMenu() {
        System.out.println("--- MENU ---");
        for (MenuOptions option : MenuOptions.values()) {
            System.out.println(option.value + ". " + option);
        }
        System.out.println("Enter choice: ");
    }

    /*
     * Read the user choice from the menu options
     */
    public MenuOptions ReadMenuOption() {
        int option = ReadInt();
        return MenuOptions.ToEnum(option);
    }

    /*
     * Read the choice number entered by the user
     */
    private static int ReadInt() {
        Scanner scanner = new Scanner(System.in);
        boolean validInput;
        int inputNum = 0;
        
        do {
            try {
                inputNum = scanner.nextInt();
                validInput = true;
            } catch (Exception ex) {
                // invalid input
                System.out.print("Bad input. Retry:");
                validInput = false;
                scanner = new Scanner(System.in); //TODO: is this needed again?
            }
        } while (!validInput);

        return inputNum;
    }

    /*
     * Read the string value entered by the user.
     */
    private static String ReadString() {
        String str = "";
        
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            // Reading data using readLine 
            str = reader.readLine();
        } catch (IOException ex) {
            System.out.println("Some error while reading data.");
        }
        
        return str;
    }

    /*
     * Handle the option selected by the user
     */
    public void Do(MenuOptions selectedOption) {
        if(selectedOption == null){
            System.err.println("Invalid option.");
        }
        
        // If there is no valid graph, the allowed options should be Load file or Exit
        if (!bsGraph.hasValidGraph()
                && selectedOption != MenuOptions.LoadAnotherFile
                && selectedOption != MenuOptions.Exit) {
            System.out.println("No valid graph loaded. Please select " 
                    + MenuOptions.LoadAnotherFile + " or " + MenuOptions.Exit);
            return;
        }
        
        switch (selectedOption) {
            case LoadAnotherFile:
            	//load movie data from the file
                ReadActorMovieFile();
                break;
            case DisplayMoviesAndActors:
            	//display movies and actors in the graph
            	bsGraph.DisplayGraph();
                break;
            case DisplayMovies:
            	//display movies in the graph
                bsGraph.DisplayGraph(VertexType.MOVIE);
                break;
            case DisplayActors:
            	//display actors in the graph
            	bsGraph.DisplayGraph(VertexType.ACTOR);
                break;
            case DisplayMoviesOfActor:
            	//display the movies an actor performed in
            	DisplayMoviesOfActor();
                break;
            case DisplayActorsOfMovie:
            	//display the actoris in a movie
            	DisplayActorsOfMovie();
                break;
            case FindMovieRelation:
            	//check if the movies are related R(A,B)
                FindMovieRelation();
                break;
            case FindMovieTransitiveRelation:
            	//check if the movies are related via another movie. T(A,B), if R(A,C) and R(C,B)
                FindMovieTraversalRelation();
                break;
            case Exit:
                // Nothing to do, we are exiting
                return;
            default:
                System.out.println("Invalid option selected.");
        }
    }

    /*
     * Load the movie data from file and generate the graph
     */
    private void ReadActorMovieFile() {
        String fileName = ReadFilePath();
        ArrayList<String> fileContent = ReadContent(fileName);
        bsGraph.CreateGraph(fileContent);
    }

    /*
     * Display the list of actors and movies in the graph
     */
//    private void DisplayMoviesAndActors() {
//        bsGraph.DisplayMoviesAndActors();
//    }

    /*
     * Display the list of movies in the graph
     */
//    private void DisplayMovies() {
//        bsGraph.displayActMov(VertexType.MOVIE);
//    }

    /*
     * Display the list of actors in the graph
     */
//    private void DisplayActors() {
//        bsGraph.displayActMov(VertexType.ACTOR);
//    }

    /*
     * Display the movies an actor performed in
     */
    private void DisplayMoviesOfActor() {
        System.out.println("Enter Actor Name :");
        String actorName = ReadString();

        bsGraph.DisplayMoviesOfActor(actorName);
    }

    /*
     * Display the list of actors who perform in a given movie
     */
    private void DisplayActorsOfMovie() {
        System.out.println("Enter Movie Name :");
        String movieName = ReadString();

        bsGraph.DisplayActorsOfMovie(movieName);
    }

    /*
     * Find if two movies are related. R(A,B) with there is atleast one actor in common
     * between movie A and B.
     */
    private void FindMovieRelation() {
        System.out.println("Enter Movie 1:");
        String movie1 = ReadString();
        System.out.println("Enter Movie 2:");
        String movie2 = ReadString();

        try {
            bsGraph.FindMovieRelatingActor(movie1, movie2);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    /*
     * Find we two movies are connected via a movie. T(A,B) provide R(A,C) and R(C,B) exists.
     */
    private void FindMovieTraversalRelation() {
        System.out.println("Enter Movie 1 :");
        String movie1 = ReadString();
        System.out.println("Enter Movie 2 :");
        String movie2 = ReadString();

        try {
            bsGraph.FindMovieTransRelation(movie1, movie2);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    /*
     * Check if the graph is loaded or not.
     */
//    public boolean GraphLoaded() {
//        return bsGraph.hasValidGraph();
//    }

//	public void LoadInitialActorMovieFile() {
//  ReadActorMovieFile();
//}

}
