

import java.util.*;

public class ProductCipher {

    // Caesar Cipher: shifts each letter by a fixed amount for encryption
    public static String caesarCipher(String text, int shift) {
        StringBuilder result = new StringBuilder();
        for (char i : text.toCharArray()) {
            if (Character.isLetter(i)) {
                char base = Character.isLowerCase(i) ? 'a' : 'A';
                result.append((char) ((i - base + shift) % 26 + base));
            } else {
                result.append(i); // non-alphabetic characters remain unchanged
            }
        }
        return result.toString();
    }

    // Caesar Cipher Decryption: shifts each letter by the inverse of the encryption shift
    public static String caesarDecipher(String text, int shift) {
        return caesarCipher(text, 26 - shift);  // Reverse the Caesar Cipher shift by 26 - shift
    }

    // Columnar Transposition Cipher: reorders the letters based on a key for encryption
    public static String columnarTransposition(String text, String key) {
        int length = text.length();
        int numColumns = key.length();
        int numRows = (int) Math.ceil((double) length / numColumns);
        char[][] grid = new char[numRows][numColumns];

        // Fill grid row by row
        int index = 0;
        for (int r = 0; r < numRows; r++) {
            for (int c = 0; c < numColumns; c++) {
                if (index < length) {
                    grid[r][c] = text.charAt(index++);
                } else {
                    grid[r][c] = 'X'; // Padding character
                }
            }
        }

        // Now, read the grid by columns based on the key order
        StringBuilder result = new StringBuilder();
        Integer[] order = getColumnOrder(key);
        for (int i : order) {
            for (int r = 0; r < numRows; r++) {
                result.append(grid[r][i]);
            }
        }
        return result.toString();
    }

    // Columnar Transposition Decryption: reverse the process of columnar transposition
    public static String columnarDecipher(String text, String key) {
        int length = text.length();
        int numColumns = key.length();
        int numRows = (int) Math.ceil((double) length / numColumns);
        char[][] grid = new char[numRows][numColumns];

        // Get the column order based on the key
        Integer[] order = getColumnOrder(key);

        // Fill the grid by columns (reversing the encryption process)
        int index = 0;
        for (int i : order) {
            for (int r = 0; r < numRows; r++) {
                if (index < length) {
                    grid[r][i] = text.charAt(index++);
                }
            }
        }

        // Now, read the grid row by row to recover the original text
        StringBuilder result = new StringBuilder();
        for (int r = 0; r < numRows; r++) {
            for (int c = 0; c < numColumns; c++) {
                result.append(grid[r][c]);
            }
        }

        // Remove any padding characters ('X')
        return result.toString().replace("X", "");
    }

    // Get the column order based on the key
    private static Integer[] getColumnOrder(String key) {
        Integer[] order = new Integer[key.length()];
        for (int i = 0; i < key.length(); i++) {
            order[i] = i;
        }

        // Sort the order based on the alphabetical order of the key
        Arrays.sort(order, Comparator.comparingInt(i -> key.charAt(i)));
        return order;
    }

    // Main method to demonstrate the product cipher
    public static void main(String[] args) {
        String plaintext = "ABDULQADEER";
        int caesarShift = 3;
        String transpositionKey = "VESIT";

        System.out.println("Plaintext: " + plaintext);

        // Step 1: Apply Caesar Cipher (Encryption)
        String caesarEncrypted = caesarCipher(plaintext, caesarShift);
        System.out.println("Caesar Cipher (Shift " + caesarShift + "): " + caesarEncrypted);

        // Step 2: Apply Columnar Transposition (Encryption)
        String transpositionEncrypted = columnarTransposition(caesarEncrypted, transpositionKey);
        System.out.println("Columnar Transposition Cipher (Key " + transpositionKey + "): " + transpositionEncrypted);

        // Step 3: Decrypt Columnar Transposition
        String transpositionDecrypted = columnarDecipher(transpositionEncrypted, transpositionKey);
        System.out.println("\n\n\nColumnar Transposition Decryption: " + transpositionDecrypted);

        // Step 4: Decrypt Caesar Cipher
        String caesarDecrypted = caesarDecipher(transpositionDecrypted, caesarShift);
        System.out.println("Caesar Cipher Decryption: " + caesarDecrypted);
        System.out.println("\nExecuted By Abdul Qadeer");
    }
}
