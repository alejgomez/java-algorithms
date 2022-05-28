/**
 * PalindromeDetector by Alejandro
 * This detector reads an input file and detects the palindrome words and the number of times they appear.
 * 
 * Assumes:
 * 1) Single letter words can be a palindrom
 * 2) Only finding words not phrases
 * 3）Special characters are considered as word separators
 * 4) These are not considered palindroms:
 *  -Sequence of characters expanding across two different words.  "and did" i.e "dd" is not palindrome  
 *  -Single or multiple letter substrings of a word. "door" "oo" is not a palindrome
 * 
 * There are two cases to handle, an even and odd word:
 * 1) aabbcc  even case
 * 2) amerema odd  case
 */
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Collections;

class Palindrome{
    String text;
    int count;
    Palindrome(){}
    Palindrome(String text , int count){
        this.text = text;
        this.count = count;
    }
}
public class PalindromeDetector {

    // public static ArrayList<String> palindromes = new ArrayList<String>();
    // public static ArrayList<Integer> palindromesCount = new ArrayList<Integer>();
    public static ArrayList<Palindrome> palindromesList = new ArrayList<Palindrome>();

    public static void main(String[] args) throws Exception {

        final String fileName = args.length > 0 ? args[0] : "./input2.txt";
        System.out.print("Reading from file " + fileName + "\n");

        String inputString = readFile(fileName);
        inputString = inputString.toLowerCase();
        inputString = inputString.replaceAll("[^a-zA-Z0-9]"," "); //non alphanumeric becomes space
        inputString = " " + inputString + " ";

        System.out.println("InputString: " + inputString + "\n");
        printAllPalindromes(inputString);

    }

    public static void printAllPalindromes(String str) {

        if (str == null || str.length() == 0) return;

        for (int i = 0 ; i < str.length() ; i++){
            expandWindow(str, i, i); //odd
            expandWindow(str, i, i+1); //even
        }
        
        // System.out.println("palindromes: "+ palindromes);
        // System.out.println("Count: "+ palindromesCount);

        palindromesList.forEach((p)->{
            System.out.println(p.text + " --> " + p.count);
        });
  
        return;
    }
    /*
    private static void printSortedPalindromes() {
        int size = palindromes.size();
        Palindrome array[] = new Palindrome[size];

        System.out.println("Sorted");
        for (int i = 0; i < size; i++){
            array[i] = new Palindrome(palindromes.get(i), palindromesCount.get(i));
            System.out.println(array[i].text + " --> " + array[i].count );
        }
        
        // Collections.sort(palindromesCount);
        // System.out.println("Sorted Count: "+ palindromesCount);
    }
    */
   
    public static void expandWindow(String str, int left, int right){
        /** 
         * Expands the left right markers (window) boundaries over the string
         * to detect palindrom
         */
        if (str == null || left > right){
            return;
        }

        while (left >= 0 && right < str.length() 
            && str.charAt(left) == str.charAt(right)){
            left --;
            right++;
        }

        //correct boundary, since we expanded one last time before breaking
        left ++; 
        right--;

        extractPalindrome(str, left, right);
        
        return; 
    }
    /*
    private static void extractPalindrome(String str, int left, int right) {
        String palindrome = str.substring(left, right+1);
        palindrome = palindrome.trim(); 

        if (isAlphaNumeric(palindrome)){
                
            char leftNeighbor = str.charAt(left);
            char rightNeighbor = str.charAt(right);
            
            if (!isAlphaNumeric(String.valueOf(leftNeighbor)) 
            && !isAlphaNumeric(String.valueOf(rightNeighbor)) ){
                
                int index = palindromes.indexOf(palindrome);
                if (index >=0) {
                    palindromesCount.set(index, palindromesCount.get(index) + 1);
                }else{
                    palindromes.add(palindrome);
                    palindromesCount.add(1);
                }
            }
        }
    }
    */
    private static void extractPalindrome(String str, int left, int right) {
        String text = str.substring(left, right+1);
        text = text.trim(); 

        if (isAlphaNumeric(text)){
                
            char leftNeighbor = str.charAt(left);
            char rightNeighbor = str.charAt(right);
            
            if (!isAlphaNumeric(String.valueOf(leftNeighbor)) 
            && !isAlphaNumeric(String.valueOf(rightNeighbor)) ){
                
                int index = indexOfPalindromeInList(text);
                if (index >=0) {
                    palindromesList.get(index).count++;
                }else{
                    Palindrome newPalindrome = new Palindrome(text, 1);
                    palindromesList.add(newPalindrome);

                }
            }
        }
    }

    private static int indexOfPalindromeInList(String text) {
        
        for (int i = 0 ; i < palindromesList.size() ; i++){

            if (palindromesList.get(i).text.equals(text)) {
                return i;
            }
        }

        return -1;
    }

    public static boolean isAlphaNumeric(String str){
        if (str == null)  return false; 

        return str.matches("^[a-zA-Z0-9]+$")
        ? true
        : false; //alphanumeric
    
    }
    
    public static String readFile(String fileName)throws Exception
    {
      String data = "";
      data = new String(Files.readAllBytes(Paths.get(fileName)));
      return data;
    }
 
}








