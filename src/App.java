import java.util.*;

public class App {
    public static void main(String[] args) throws Exception {
        Hangman game = new Hangman();
        game.startGame(); // Start the game
    }

    static class Hangman {
        // List of words to be guessed
        private static final List<String> WORDS = Arrays.asList("SCALA", "TYPESCRIPT", "JAVA", "PYTHON", "JAVASCRIPT","KOTLIN","SWIFT","MATLAB");
        private String currentWord; // The current word to be guessed
        private char[] guessedWord; // Array representing the current guessed letters
        private List<Character> guessedLetters; // List of letters already guessed
        private int guessesRemaining; // Number of remaining guesses
        private final Scanner scanner = new Scanner(System.in);

        public void startGame() {
            selectRandomWord(); // Select a random word from the list
            playGame(); // Start playing the game
        }

        private void selectRandomWord() {
            Random random = new Random();
            this.currentWord = WORDS.get(random.nextInt(WORDS.size())); // Select a random word
            this.guessedWord = new char[this.currentWord.length()]; // Initialize the guessed word array
            Arrays.fill(this.guessedWord, '-'); // Fill the guessed word array with '-'
        }

        // Method to check if user wants to play again
        private void playAgain() {
            System.out.print("Would you like to play again? (yes/no): ");
            String input = scanner.nextLine().toLowerCase().trim();

            // If user wants to play again, start a new game
            if ("yes".equals(input) || "y".equals(input)) {
                System.out.println("Starting a new game...");
                startGame();
            } else {
                System.out.println("Thanks for playing!");
            }
        }

        private void playGame() {
            this.guessedLetters = new ArrayList<>(); // List of letters already guessed
            this.guessesRemaining = 8; // Number of remaining guesses

            // Continue the game as long as there are guesses remaining
            while (guessesRemaining > 0) {
                printGameStatus(); // Print the current game status
                char guess = getGuessFromUser(); // Get the guess from the user

                // If the letter has already been guessed, inform the user and continue
                if (isLetterAlreadyGuessed(guess)) {
                    System.out.println("You've already guessed that letter. Try again.");
                    continue;
                }

                guessedLetters.add(guess); // Add the guessed letter to the list of guessed letters
                processGuess(guess); // Process the user's guess

                // If the word has been guessed, congratulate the user and end the game
                if (isWordGuessed()) {
                    System.out.println("Congratulations! You've guessed the word: " + this.currentWord);
                    break;
                }
            }

            // If no guesses remain, inform the user of the correct word
            if (guessesRemaining == 0) {
                System.out.println("You're out of guesses. The word was: " + this.currentWord);
            }
            playAgain();
        }

        private void printGameStatus() {
            // Print the current status of the guessed word and the number of remaining guesses
            System.out.println("The word now looks like this : " + new String(guessedWord));
            System.out.println("You have " + guessesRemaining + " guesses left.");
        }

        private char getGuessFromUser() {
            String input = "";
            // Ask for a guess from the user
            System.out.print("Your guess: ");
            while (input.isEmpty()) {
                input = scanner.nextLine().trim();
                if (input.isEmpty()) {
                    System.out.print("Invalid input. Please enter a guess: ");
                }
            }
            return input.toUpperCase().charAt(0);
        }
        

        private boolean isLetterAlreadyGuessed(char letter) {
            // Check if a letter has already been guessed
            return guessedLetters.contains(letter);
        }

        private void processGuess(char guess) {
            // If the guess is in the word, update the guessed word
            if (currentWord.contains(String.valueOf(guess))) {
                updateGuessedWord(guess);
            } else {
                // If the guess is not in the word, decrease the remaining guesses and inform the user
                guessesRemaining--;
                System.out.println("There are no " + guess + "'s in the word.");
            }
        }

        private void updateGuessedWord(char letter) {
            // Update the guessed word with the correct guess
            for (int i = 0; i < currentWord.length(); i++) {
                if (currentWord.charAt(i) == letter) {
                    guessedWord[i] = letter;
                }
            }
        }

        private boolean isWordGuessed() {
            // Check if the word has been completely guessed
            return new String(guessedWord).equals(currentWord);
        }
    }
}

