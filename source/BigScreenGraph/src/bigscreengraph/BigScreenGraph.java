/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bigscreengraph;

import bigscreengraph.Container.Queue;
import bigscreengraph.Helper.InputHelper;
import bigscreengraph.Helper.MenuOptions;

/**
 *
 * @author MNadara
 */
public class BigScreenGraph {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        try {
            InputHelper helper = new InputHelper();

            // Ensure file is loaded before menu options are displayed.
            helper.LoadInitialActorMovieFile();

            MenuOptions choice;

            do {

                helper.ShowMenu();

                choice = helper.ReadMenuOption();

                helper.Do(choice);

            } while (choice != MenuOptions.Exit);

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
        }
    }

}
