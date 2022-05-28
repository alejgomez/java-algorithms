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
 * For the two cases we place a left and right marker and expand until finding a palindrome
 * 1) aabbcc  even case
 * 2) amerema odd  case
 */
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Comparator;

class Palindrome{
    String text;
    int count;
    Palindrome(){}
    Palindrome(String text , int count){
        this.text = text;
        this.count = count;
    }
    public String getText() {
        return text;
    }
    public int getCount() {
        return count;
    }
}
public class PalindromeDetector {

    public static ArrayList<Palindrome> palindromesList = new ArrayList<Palindrome>();

    public static void main(String[] args) throws Exception {

        final String fileName = args.length > 0 ? args[0] : "./input.txt";
        String inputString = readFile(fileName);
        System.out.print("Reading from file " + fileName + "\n");

        inputString = inputString.toLowerCase();
        inputString = inputString.replaceAll("[^a-zA-Z0-9]"," ");
        inputString = " " + inputString + " ";

        System.out.println("InputString: " + inputString + "\n");
        printAllPalindromes(inputString);

    }

    private static void printAllPalindromes(String str) {

        if (str == null || str.length() == 0) return;

        for (int i = 0 ; i < str.length() ; i++){
            expandWindow(str, i, i); //odd
            expandWindow(str, i, i+1); //even
        }

        //sorting
        palindromesList.sort(Comparator.comparing(Palindrome::getCount).reversed().thenComparing(Palindrome::getText));

        System.out.println("Found Palindromes:");
        palindromesList.forEach((p)->{
            System.out.println(p.text + " > " + p.count);
        });
  
        return;
    }
   
    private static void expandWindow(String str, int left, int right){
        /** 
         * Expands the left right markers (window) boundaries over the string
         * to detect palindrome
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

    private static boolean isAlphaNumeric(String str){
        if (str == null)  return false; 

        return str.matches("^[a-zA-Z0-9]+$")
        ? true
        : false;
    
    }
    
    private static String readFile(String fileName)throws Exception
    {
      String data = "";
      data = new String(Files.readAllBytes(Paths.get(fileName)));
      return data;
    }
 
}








