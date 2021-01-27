package hangman;

public class EvilHangman {

    public static void main(String[] args) {


        IEvilHangmanGame evilHangmanGame = new EvilHangmanGame();
//        evilHangmanGame.startGame();

        try {
            evilHangmanGame.makeGuess('q');
        } catch (GuessAlreadyMadeException ex) {
            ex.printStackTrace();
        }
        System.out.println(evilHangmanGame.getGuessedLetters());
    }

}
