import java.util.*; 
public class PlayfairCipher { 
private static char[][] table; 
private static Map<Character, int[]> position; 
public static void main(String[] args) { 
Scanner sc = new Scanner(System.in); 
// User Input 
System.out.print("Enter Key: "); 
String key = sc.nextLine(); 
System.out.print("Enter Plaintext: "); 
String plaintext = sc.nextLine(); 
createTable(key); 
System.out.println("\nKey Table:"); 
printTable();
String ciphertext = encrypt(plaintext); 
System.out.println("\nPlaintext : " + plaintext); 
System.out.println("Ciphertext : " + ciphertext); 
String decrypted = decrypt(ciphertext); 
System.out.println("Decrypted : " + decrypted); 
System.out.println("\nExecuted by ABDUL QADEER"); sc.close(); 
} 
private static void createTable(String key) { 
key = key.toUpperCase().replaceAll("[^A-Z]", "").replace("J", "I"); 
LinkedHashSet<Character> set = new LinkedHashSet<>(); for (char c : key.toCharArray()) { 
set.add(c); 
} 
for (char c = 'A'; c <= 'Z'; c++) { 
if (c == 'J') continue; 
set.add(c); 
} 
table = new char[5][5]; 
position = new HashMap<>(); 
Iterator<Character> it = set.iterator(); 
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
sb.append(a).append('X'); 
} else { 
sb.append(a); 
if (i + 1 < text.length()) { 
sb.append(b); 
i++; 
} else { 
sb.append('X'); 
} 
} 
} 
if (sb.length() % 2 != 0) { 
sb.append('X'); 
} 
return sb.toString(); 
} 
private static String encrypt(String plaintext) { 
    String text = prepareText(plaintext); 
    System.out.print("\nDigraphs : ");
    for(int i=0 ; i<text.length();i+=2){
        System.out.print(text.substring(i,i+2)+" ");
    }
StringBuilder result = new StringBuilder(); 
for (int i = 0; i < text.length(); i += 2) { 
char a = text.charAt(i); 
char b = text.charAt(i + 1); 
int[] posA = position.get(a); 
int[] posB = position.get(b); 
if (posA[0] == posB[0]) { 
result.append(table[posA[0]][(posA[1] + 1) % 5]); result.append(table[posB[0]][(posB[1] + 1) % 5]); } else if (posA[1] == posB[1]) { 
result.append(table[(posA[0] + 1) % 5][posA[1]]); result.append(table[(posB[0] + 1) % 5][posB[1]]); } else { 
result.append(table[posA[0]][posB[1]]); 
result.append(table[posB[0]][posA[1]]); 
} 
} 
return result.toString(); 
} 
private static String decrypt(String ciphertext) {
String text = ciphertext.toUpperCase().replaceAll("[^A-Z]", ""); StringBuilder result = new StringBuilder(); 
for (int i = 0; i < text.length(); i += 2) { 
char a = text.charAt(i); 
char b = text.charAt(i + 1); 
int[] posA = position.get(a); 
int[] posB = position.get(b); 
if (posA[0] == posB[0]) { 
result.append(table[posA[0]][(posA[1] + 4) % 5]); 
result.append(table[posB[0]][(posB[1] + 4) % 5]); 
} else if (posA[1] == posB[1]) { 
result.append(table[(posA[0] + 4) % 5][posA[1]]); 
result.append(table[(posB[0] + 4) % 5][posB[1]]); 
} else { 
result.append(table[posA[0]][posB[1]]); 
result.append(table[posB[0]][posA[1]]); 
} 
} 
String newStr = result.toString().replace("X","");
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
