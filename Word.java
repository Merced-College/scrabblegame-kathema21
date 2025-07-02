public class Word implements Comparable<Word> { // word class implements Comparable interface so binary search is easier to implement
    
    private String word;

    public Word(String word) {
        this.word = word.trim().toUpperCase(); // constructor 
    }

    public String getWord() {
        return word; //accessor 
    }

    public void setWord(String word) {
        this.word = word; //mutator
    }

    @Override
    public int compareTo(Word other) {
        return this.word.compareTo(other.word);  //compareTo method for sorting words alphabetically
    } 
} // end class