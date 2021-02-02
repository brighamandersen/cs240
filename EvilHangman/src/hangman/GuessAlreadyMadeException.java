package hangman;

public class GuessAlreadyMadeException extends Exception {
    public GuessAlreadyMadeException(String errorMsg) {
        super(errorMsg);
    }
}
