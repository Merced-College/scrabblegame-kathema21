[![Open in Codespaces](https://classroom.github.com/assets/launch-codespace-2972f46106e565e64193e422d61a12cf1da4916b45550586e14ef0a7c637dd04.svg)](https://classroom.github.com/open-in-codespaces?assignment_repo_id=19881125)
# scrabbleGame

Katelynn Prater - 7/1/25 - Letter Addition Scrabble

This game functions as a single-player Scrabble where the goal of the game is to reach 30 points, as calculated by basic Scrabble letter/word value rules, in as few letters drawn as possible. You begin with four letters, and as you play, you will play words that you can construct with the given letters. If at any turn you can't make a word, you can draw a new letter. Once you reach 30 points, the total letters drawn is outputted. 

The required reflection is inside the GitHub repo as well. The required substantive improvements include a gameloop, scoring, and letter dealing. More specifics are below.

# Additions
These has been implemented:

  Word Class
  
        - A constructor
        - A mutator, an accessor
        - A compare to function
        - Comparable implementation for binary search (via Collections)
    
  ScrabbleGame Class
  
        - Game loop logic in main
        - Input word handling & validity logic, including update to player's letter list
        - Constructor, including loading of .txt files for vals and Collins Words
        - Other helper methods (letter dealing, tallying score, letter display, etc.)
        

# How to run
  Run in terminal:
  
        - javac ScrabbleGame.java
        - java ScrabbleGame
