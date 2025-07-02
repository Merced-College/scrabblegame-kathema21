import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.Random;
import java.io.FileNotFoundException;
import java.io.File;


public class ScrabbleGame {
    private ArrayList<Word> wordList;
    private ArrayList<Character> letterList;
    private String[][] letterValList;
    private Scanner input;
    private int score;
    private int lettersDrawn;

    public static void main(String[] args) { // Main method to start the game
        ScrabbleGame game = new ScrabbleGame("Collins Scrabble Words (2019).txt", "LetterVals.txt"); // Initialize the game with the word file and letter values file
        game.playGame();
    }

    public ScrabbleGame(String wordFileName, String letterValFileName) { //constructor to initialize the game
        wordList = new ArrayList<>();
        letterList = new ArrayList<>();
        letterValList = new String[26][2]; // 26 letters, each with a letter and its value
        input = new Scanner(System.in);
        score = 0;
        lettersDrawn = 4; 

        wordLoad(wordFileName); // Load the words from the file
        letterValLoad(letterValFileName); // Load the letter values from the file
        randLetterStart(); // Start with 4 random letters
    } // end of constructor

    private void wordLoad(String filename) {   
        try { // Load words from Collins Scrabble Words file
            File wordfile = new File(filename);
            Scanner scanner = new Scanner(wordfile);

            while (scanner.hasNext()) {
                String newWord = scanner.next(); // Read each word from the file and adds it to wordList
                wordList.add(new Word(newWord));
            }
            Collections.sort(wordList); // Sort the word list for binary search
            System.out.println("All words loaded :)");
            scanner.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("File not found, unfortunately.");
        }
    } // End of wordLoad method

    private void letterValLoad(String filename) { // Load letter values from the LetterVals file into a 2D array
        try {
            File letterfile = new File(filename);
            Scanner scanner = new Scanner(letterfile);

            int i = 0;
            while (scanner.hasNext() && i < 26) { // Read each letter and its value from the file
                String letter = scanner.next();
                letterValList[i][0] = letter;
                String value = scanner.next();
                letterValList[i][1] = value; // Store the letter and its value in the 2D array ^
                i++;
            }
            System.out.println("All letter vals loaded :)");
        }
        catch (FileNotFoundException e) {
            System.out.println("File not found, unfortunately.");
        }
    } //end of letterValLoad method

    private void randLetterStart() { // Generate 4 random letters to start the game
        Random rand = new Random();
        for (int i = 0; i < 4; i++) {
            char randomLetter = (char) (rand.nextInt(26) + 'A'); // Generate a random letter from A to Z
            letterList.add(randomLetter); // Add the random letter to the letterList
        }
    } //end of randLetterStart method

    private void addRandLetter() { // Add a random letter to the letterList
        Random rand = new Random();
        char randomLetter = (char) (rand.nextInt(26) + 'A');
        letterList.add(randomLetter);
        lettersDrawn += 1;
    }

    private void playInputWord() {
        while (true) {
            System.out.println("In all caps, using the letters you have, only using each letter ONCE,");
            System.out.println("write a word you wish to play.");
            System.out.println();
            System.out.println("If you can't find a word, or wish to be dealt another letter, type: '123'.");
            System.out.println();
            displayLetters();

            String word = input.nextLine().trim().toUpperCase();

            if (word.equals("123")) { // User input to get a new letter if they cannot form a word
                System.out.println("Dealing new letter.");
                addRandLetter();
                continue;
            }

            if (validUserInput(word)) { // Check if the word is valid (in Collins) and can be formed with letterList
                for (int i = 0; i < word.length(); i++) { // Iterate through the word
                    char c = word.charAt(i);
                    for (int j = 0; j < letterList.size(); j++) { // Check if the letter is in letterList
                        if (letterList.get(j) == c) {
                            tallyValue(c);
                            letterList.remove(j); // Remove the letter from letterList after using it
                            System.out.println("Using letter... " + c);
                            break;
                        } 
                    } //end of inner for loop
                } // end of outer for loop
                if (!hasWon()) { // Check so not to add letters if the player has already won
                    System.out.println("Adding " + (word.length()) + " letters."); //adding letters equal to the length of the word, 
                    System.out.println();
                    for (int i = 0; i < word.length() - 1; i++) { // minus one because addRandLetter() is called in game loop
                        addRandLetter();  
                    } // Add letters equal to the length of the word minus one
                }

                break; // Exit the loop if the word is valid and processed
            }
            else {
                System.out.println("Invalid word. Try again."); // prompt user to try again if the word is invalid
            }
        } //end of while loop
    } // end of playInputWord method
    
    private boolean validUserInput(String userWord) {
        int index = Collections.binarySearch(wordList, new Word(userWord));
        if (index < 0) {
            return false;
        } // Check if the word exists in the wordList using binary search
        ArrayList<Character> tempLetterList = new ArrayList<>(letterList); // templist to prevent modifying the original letterList
        for (int i = 0; i < userWord.length(); i++) { // Check if the userWord can be formed with the letters in letterList
            char c = userWord.charAt(i); 
            if (!tempLetterList.contains(c)) { // If the letter is not in the letterList, return false 
                return false; 
            }
            tempLetterList.remove((Character) c); // removes letter from templist to ensure no redundant use of letters
        } //end of for loop
        return true; // The word is valid and can be formed with the letters in letterList
    } //end of validUserInput method
    
    private void tallyValue(char inputChar) {
        for (int i = 0; i < 26; i++) {
            if (letterValList[i][0].charAt(0) == inputChar) {
                score += Integer.parseInt(letterValList[i][1]);
                return;
            } // If the letter matches, add its value to the score
        } //end of for loop, iterates through the letterValList
    } //end of tallyValue method 

    private void displayLetters() { // Display the current letters in letterList
        System.out.println("Your current letters are:");
        for (char c: letterList) {
            System.out.print(c + " ");
        }
        System.out.println();
    } //end of displayLetters method

    private boolean hasWon() {
        return score >= 30;
    } // win condition, checks if the score is 30 or more


    public void playGame() { // Main game loop
        System.out.println("Hello! Welcome to a basic Scrabble game! Here are the rules: ");
        System.out.println();
        System.out.println("You will be dealt four letters. Then, you will be asked to create a");
        System.out.println("word from those letters. If you cannot create a word, or want another ");
        System.out.println("letter, follow the given instructions. Each word will count towards ");
        System.out.println("your score, with each letter in the word having its own individual scoring ");
        System.out.println("as standardized by Scrabble. Reach 30 or more points in as few letter additions as possible.");
        System.out.println();
        System.out.println();
        System.out.println("Have fun!");

        while (!hasWon()) { // Loop until the player wins
            playInputWord(); // Ask the player for a word, check if it's valid, and update the score

            if (hasWon()) {
                break;
            } // If the player has won, break out of the loop to prevent adding more letters

            addRandLetter(); // Add a new random letter after the player plays a word
            System.out.println();
            System.out.println("Current score: " + score);
            System.out.println();
        }


        System.out.println("Congrats! You won in: " + lettersDrawn + " letters drawn."); // Display the win message and the number of letters drawn
    } //end of playGame method
} // end of ScrabbleGame class