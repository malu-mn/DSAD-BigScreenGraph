/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bigscreengraph.Helper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author MNadara
 */
public class InputHelper {

    private final BSGraph bsGraph;

    // ctor
    public InputHelper() {
        bsGraph = new BSGraph();
    }

    public void LoadInitialActorMovieFile() {
        ReadActorMovieFile();
    }

    // Read file name from console or default to test.txt
    private String ReadFilePath() {
        String fileName;
        try {
            System.out.println("Enter file name to process : ");
            Scanner scanner = new Scanner(System.in);
            fileName = scanner.nextLine();

            // Check whether the file name is valid.
            File f = new File(fileName);
            if (!f.exists() || !f.isFile()) {
                System.out.println("Invalid file name. "
                        + "Using file name: input/test.txt");
                fileName = "input/test.txt";
            }
        } catch (Exception ex) {
            System.out.print(" Reading file name failed with exception"
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
            System.out.print(fileName + " Reading failed with exception"
                    + ex.getMessage());
        } finally {

        }

        return strList;
    }

    public void ShowMenu() {
        System.out.println("--- MENU ---");
        for (MenuOptions option : MenuOptions.values()) {
            System.out.println(" " + option.value + ". " + option);
        }
        System.out.println("Enter choice :");
    }

    public MenuOptions ReadMenuOption() {
        int option = ReadInt();
        return MenuOptions.ToEnum(option);
    }

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
                scanner = new Scanner(System.in);
            }

        } while (!validInput);

        return inputNum;
    }

    private static String ReadString() {

        String str = "";
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            // Reading data using readLine 
            str = reader.readLine();

        } catch (IOException ex) {
            System.out.println("Some error while data read");
        }
        return str;
    }

    public void Do(MenuOptions option) {
        if(option == null){
            System.err.println("Invalid option");
        }
        // If there is no valid graph, the allowed options should be Load file or Exit
        if (!bsGraph.hasValidGraph()
                && option != MenuOptions.LoadAnotherFile
                && option != MenuOptions.Exit) {
            System.out.println("No valid graph loaded. Please select " 
                    + MenuOptions.LoadAnotherFile + " or " + MenuOptions.Exit);
            return;
        }
        switch (option) {
            case LoadAnotherFile:
                ReadActorMovieFile();
                break;
            case DisplayActorsAndMovies:
                DisplayActorsAndMovies();
                break;
            case DisplayMovies:
                DisplayMovies();
                break;
            case DisplayActors:
                DisplayActors();
                break;
            case DisplayMoviesOfActor:
                DisplayMoviesOfActor();
                break;
            case DisplayActorsOfMovie:
                DisplayActorsOfMovie();
                break;
            case FindMovieRelation:
                FindMovieRelation();
                break;
            case FindMovieTransitiveRelation:
                FindMovieTraversalRelation();
                break;
            case Exit:
                // Nothing to do, we are exiting
                return;
            default:
                System.out.println("Invalid option selected");
        }
    }

    private void ReadActorMovieFile() {
        String fileName = ReadFilePath();
        ArrayList<String> fileContent = ReadContent(fileName);
        bsGraph.readActMovfile(fileContent);
    }

    private void DisplayActorsAndMovies() {
        bsGraph.displayActMov();
    }

    private void DisplayMovies() {
        bsGraph.displayActMov(VertexType.MOVIE);
    }

    private void DisplayActors() {
        bsGraph.displayActMov(VertexType.ACTOR);
    }

    private void DisplayMoviesOfActor() {
        System.out.println("Enter Actor Name :");
        String actorName = ReadString();

        bsGraph.displayMoviesOfActor(actorName);
    }

    private void DisplayActorsOfMovie() {
        System.out.println("Enter Movie Name :");
        String movieName = ReadString();

        bsGraph.displayActorsOfMovie(movieName);
    }

    private void FindMovieRelation() {
        System.out.println("Enter Movie 1 :");
        String movie1 = ReadString();
        System.out.println("Enter Movie 2 :");
        String movie2 = ReadString();

        try {
            bsGraph.findMovieRelatingActor(movie1, movie2);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

    }

    private void FindMovieTraversalRelation() {
        System.out.println("Enter Movie 1 :");
        String movie1 = ReadString();
        System.out.println("Enter Movie 2 :");
        String movie2 = ReadString();

        try {
            bsGraph.findMovieTransRelation(movie1, movie2);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public boolean GraphLoaded() {
        return bsGraph.hasValidGraph();
    }

}
