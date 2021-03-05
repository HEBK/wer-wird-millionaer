package eu.flrkv.wwm.Exceptions;

/**
 * Exception which is thrown when a game is accessed that does not exist
 */
public class GameNotFoundException extends java.lang.Exception {

    /**
     * Constructs a new GameNotFoundException object
     * @param s A String which contains a message
     */
    public GameNotFoundException(String s) {
        super(s);
    }
}
