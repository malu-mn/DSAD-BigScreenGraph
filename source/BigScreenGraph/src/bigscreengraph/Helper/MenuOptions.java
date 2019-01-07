/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bigscreengraph.Helper;

/**
 *
 * @author MNadara
 */
public enum MenuOptions {
    LoadAnotherFile(1),
    DisplayActorsAndMovies(2),
    DisplayMovies(3),
    DisplayActors(4),
    DisplayMoviesOfActor(5),
    DisplayActorsOfMovie(6),
    FindMovieRelation(7),
    FindMovieTransitiveRelation(8),
    Exit(9);
    
    public int value;

    private MenuOptions(int value) {
        this.value = value;
    }

    public static MenuOptions ToEnum(int id) {
        for (MenuOptions type : values()) {
            if (type.value == id) {
                return type;
            }
        }
        return null;
    }
}
