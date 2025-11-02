import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;

public class HashHmacDemo {

    // Generate hash using SHA-256
    public static String sha256(String input) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(input.getBytes("UTF-8"));
        return bytesToHex(hash);
    }

    // Generate HMAC using SHA-256
    public static String hmacSha256(String key, String message) throws Exception {
        Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
        SecretKeySpec secret_key = new SecretKeySpec(key.getBytes("UTF-8"), "HmacSHA256");
        sha256_HMAC.init(secret_key);
        byte[] hash = sha256_HMAC.doFinal(message.getBytes("UTF-8"));
        return bytesToHex(hash);
    }

    // Convert byte array to hex string
    private static String bytesToHex(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte b : bytes) {
            result.append(String.format("%02x", b));
        }
        return result.toString();
    }

    // Demonstration of collision (using truncated hash to 16-bit for feasibility)
    public static Map<String, Object> collisionDemo() throws Exception {
        Map<String, Object> result = new HashMap<>();
        String msg1 = "HelloWorld";
        String msg2 = "HelloWorld!";

        String hash1 = sha256(msg1).substring(0, 4); // take first 4 hex chars (16-bit)
        String hash2 = sha256(msg2).substring(0, 4);

        result.put("msg1", msg1);
        result.put("msg2", msg2);
        result.put("hash1", hash1);
        result.put("hash2", hash2);
        result.put("found", Boolean.valueOf(hash1.equals(hash2))); // FIX: wrap in Boolean

        return result;
    }

    // Main
    public static void main(String[] args) throws Exception {
        String message = "This is a secret message";
        String key = "mySecretKey";

        // SHA-256 Hash
        String hashValue = sha256(message);
        System.out.println("Original Message: " + message);
        System.out.println("SHA-256 Hash: " + hashValue);

        // HMAC
        String hmacValue = hmacSha256(key, message);
        System.out.println("HMAC (SHA-256) with key '" + key + "': " + hmacValue);

        // Collision Demo
        Map<String, Object> collision = collisionDemo();
        System.out.println("\nCollision Demo (Truncated 16-bit Hash):");
        System.out.println("Message 1: " + collision.get("msg1"));
        System.out.println("Hash 1: " + collision.get("hash1"));
        System.out.println("Message 2: " + collision.get("msg2"));
        System.out.println("Hash 2: " + collision.get("hash2"));

        boolean found = (Boolean) collision.get("found");  // FIX: cast explicitly
        System.out.println("Collision result: " + (found ? "FOUND" : "NOT FOUND"));
    }
}
