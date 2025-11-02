
import java.util.*;

public class PlayfairCipher {
    private static char[][] table;
    private static Map<Character, int[]> position;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // User input
        System.out.print("Enter Key: ");
        String key = sc.nextLine();
        System.out.print("Enter Plaintext: ");
        String plaintext = sc.nextLine();

        // Create key table & map character positions
        createTable(key);

        System.out.println("\nKey Table:");
        printTable();

        // Encrypt and decrypt
        String ciphertext = encrypt(plaintext);
        System.out.println("\nPlaintext : " + plaintext);
        System.out.println("Ciphertext : " + ciphertext);

        String decrypted = decrypt(ciphertext);
        System.out.println("Decrypted : " + decrypted);

        System.out.println("\nExecuted by Chaitanya D. 24");
        sc.close();
    }

    private static void createTable(String key) {
        // Clean key: uppercase, remove non-letters, replace J with I
        key = key.toUpperCase().replaceAll("[^A-Z]", "").replace("J", "I");

        LinkedHashSet<Character> set = new LinkedHashSet<>();

        // Add key characters without duplicates
        for (char c : key.toCharArray()) {
            set.add(c);
        }

        // Add remaining letters A-Z except J
        for (char c = 'A'; c <= 'Z'; c++) {
            if (c == 'J') continue;
            set.add(c);
        }

        table = new char[5][5];
        position = new HashMap<>();
        Iterator<Character> it = set.iterator();

        // Fill 5x5 matrix and record each character’s position
        for (int r = 0; r < 5; r++) {
            for (int c = 0; c < 5; c++) {
                char ch = it.next();
                table[r][c] = ch;
                position.put(ch, new int[]{r, c});
            }
        }
    }

    private static String prepareText(String text) {
        text = text.toUpperCase().replaceAll("[^A-Z]", "").replace("J", "I");
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < text.length(); i++) {
            char a = text.charAt(i);
            char b = (i + 1 < text.length()) ? text.charAt(i + 1) : 'X';

            if (a == b) {
                // If two letters are same, insert 'X' between
                sb.append(a).append('X');
            } else {
                sb.append(a);
                if (i + 1 < text.length()) {
                    sb.append(b);
                    i++; // Skip next char because already processed
                } else {
                    sb.append('X'); // Append 'X' if odd length
                }
            }
        }

        // Make sure length is even
        if (sb.length() % 2 != 0) {
            sb.append('X');
        }

        return sb.toString();
    }

    private static String encrypt(String plaintext) {
        String text = prepareText(plaintext);
            System.out.println();
            System.out.println("Digraph");
            for(int i=0;i<text.length();i+=2)
            {
                System.out.print(text.substring(i,i+2)+" ");
            }
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < text.length(); i += 2) {
            char a = text.charAt(i);
            char b = text.charAt(i + 1);

            int[] pos1 = position.get(a);
            int[] pos2 = position.get(b);

            if (pos1[0] == pos2[0]) {
                // Same row: shift right
                result.append(table[pos1[0]][(pos1[1] + 1) % 5]);
                result.append(table[pos2[0]][(pos2[1] + 1) % 5]);
            } else if (pos1[1] == pos2[1]) {
                // Same column: shift down
                result.append(table[(pos1[0] + 1) % 5][pos1[1]]);
                result.append(table[(pos2[0] + 1) % 5][pos2[1]]);
            } else {
                // Rectangle: swap columns
                result.append(table[pos1[0]][pos2[1]]);
                result.append(table[pos2[0]][pos1[1]]);
            }
        }
        return result.toString();
    }

    private static String decrypt(String ciphertext) {
        String text = ciphertext.toUpperCase().replaceAll("[^A-Z]", "");
        StringBuilder result = new StringBuilder();
String newStr ;
        for (int i = 0; i < text.length(); i += 2) {
            char a = text.charAt(i);
            char b = text.charAt(i + 1);

            int[] pos1 = position.get(a);
            int[] pos2 = position.get(b);

            if (pos1[0] == pos2[0]) {
                // Same row: shift left
                result.append(table[pos1[0]][(pos1[1] + 4) % 5]);
                result.append(table[pos2[0]][(pos2[1] + 4) % 5]);
            } else if (pos1[1] == pos2[1]) {
                // Same column: shift up
                result.append(table[(pos1[0] + 4) % 5][pos1[1]]);
                result.append(table[(pos2[0] + 4) % 5][pos2[1]]);
            } else {
                // Rectangle: swap columns
                result.append(table[pos1[0]][pos2[1]]);
                result.append(table[pos2[0]][pos1[1]]);
            }
        }
         newStr = result.toString().replace("X", "");
        return newStr;
    }

    private static void printTable() {
        for (int r = 0; r < 5; r++) {
            for (int c = 0; c < 5; c++) {
                System.out.print(table[r][c] + " ");
            }
            System.out.println();
        }
    }
}
