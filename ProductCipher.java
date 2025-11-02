import java.util.*;

public class ProductCipher {

    // Caesar Cipher: shifts each letter by a fixed amount

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

    // Columnar Transposition Cipher: reorders the letters based on a key

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
    System.out.println("\nFilled Matrix:");
    for (int r = 0; r < numRows; r++) {
        for (int c = 0; c < numColumns; c++) {
            System.out.print(grid[r][c] + " ");
        }
        System.out.println(); // Newline after each row
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
        String plaintext = "CHAITANYA";
        int caesarShift = 3;
        String transpositionKey = "INFT";

        System.out.println("Plaintext: " + plaintext);

        // Step 1: Apply Caesar Cipher
        String caesarEncrypted = caesarCipher(plaintext, caesarShift);
        System.out.println("Caesar Cipher (Shift " + caesarShift + "): " + caesarEncrypted);

        // Step 2: Apply Columnar Transposition
        String transpositionEncrypted = columnarTransposition(caesarEncrypted, transpositionKey);
        System.out.println("Columner Transposition Cipher (Key " + transpositionKey + "): " + transpositionEncrypted);
    }
}
