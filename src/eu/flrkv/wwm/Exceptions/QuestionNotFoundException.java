package eu.flrkv.wwm.Exceptions;

/**
 * Exception which is thrown when a question is accessed that does not exist
 */
public class QuestionNotFoundException extends java.lang.Exception {

    /**
     * Constructs a new QuestionNotFoundException object
     * @param s A String which contains a message
     */
    public QuestionNotFoundException(String s) {
        super(s);
    }
}
