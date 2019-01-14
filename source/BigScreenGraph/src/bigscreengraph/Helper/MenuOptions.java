/**
 * @author Malu(2018AB04154), Sanjib(2018AB04153), Pradeep(2018AB04152)
 */
package bigscreengraph.Helper;

/**
 * Menu options enumeration used for interacting with the user to operate
 * on the graph.
 */
public enum MenuOptions {
    LoadAnotherFile(1),
    DisplayMoviesAndActors(2),
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