import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import Jama.Matrix;

public class SecureTransfer {
    // Replace with your own secret key (must be 16/24/32 bytes long for AES-128/AES-192/AES-256)
    private static final String secretKey = "1649850479327950";

    public static String encryptMatrix(Matrix matrix) throws Exception {
        // Convert Jama matrix to byte array
        byte[] matrixBytes = matrixToString(matrix).getBytes("UTF-8");

        // Initialize AES encryption
        SecretKey key = new SecretKeySpec(secretKey.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, key);

        // Encrypt the matrix byte array
        byte[] encryptedBytes = cipher.doFinal(matrixBytes);

        // Encode the encrypted byte array to Base64
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public static Matrix decryptMatrix(String encryptedMatrix) throws Exception {
        // Decode Base64 string back to byte array
        byte[] encryptedBytes = Base64.getDecoder().decode(encryptedMatrix);

        // Initialize AES decryption
        SecretKey key = new SecretKeySpec(secretKey.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, key);

        // Decrypt the byte array
        byte[] matrixBytes = cipher.doFinal(encryptedBytes);
        // Convert byte array back to Jama matrix
        return stringToMatrix(new String(matrixBytes, "UTF-8"));
    }

    // Helper function to convert Jama matrix to string
    private static String matrixToString(Matrix matrix) {
         if (matrix.getRowDimension() != 2 || matrix.getColumnDimension() != 2) {
        return null; // Handle incorrect matrix size as needed
    }

    StringBuilder matrixStr = new StringBuilder();
    matrixStr.append(matrix.get(0, 0)).append(",").append(matrix.get(0, 1)).append("\n");
    matrixStr.append(matrix.get(1, 0)).append(",").append(matrix.get(1, 1));
    return matrixStr.toString();
    }

    // Helper function to convert string back to Jama matrix
    private static Matrix stringToMatrix(String matrixStr) {
        String[] rows = matrixStr.trim().split("\n");
    if (rows.length != 2) {
        return null; // Handle incorrect number of rows as needed
    }

    String[] row1Elements = rows[0].trim().split(",");
    String[] row2Elements = rows[1].trim().split(",");
    if (row1Elements.length != 2 || row2Elements.length != 2) {
        return null; // Handle incorrect number of elements in rows as needed
    }

    try {
        double[][] matrixData = {
            {Double.parseDouble(row1Elements[0]), Double.parseDouble(row1Elements[1])},
            {Double.parseDouble(row2Elements[0]), Double.parseDouble(row2Elements[1])}
        };
        return new Matrix(matrixData);
    } catch (NumberFormatException e) {
        return null; // Handle parsing errors as needed
    }
    }

    public static void main(String[] args) throws Exception {
        // Create a sample Jama matrix
        double[][] matrixData = {{1.0, 2.0}, {4.0, 5.0}};
        Matrix originalMatrix = new Matrix(matrixData);

        // Encrypt the matrix and send as a string
        String encryptedString = encryptMatrix(originalMatrix);
        System.out.println("Encrypted String: " + encryptedString);

        // Decrypt the string back to the original matrix
        Matrix decryptedMatrix = decryptMatrix(encryptedString);
        System.out.println("Decrypted Matrix:");
        decryptedMatrix.print(10, 2); // Adjust formatting as needed
        System.exit(0);
    }
}
