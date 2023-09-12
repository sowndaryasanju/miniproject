import Jama.Matrix;
import java.util.ArrayList;

public class App {
    public static void main(String[] args) throws Exception {
        KeyGeneration k=new KeyGeneration();
        Object[] op=k.generateKeys(); 
        Encryption e=new Encryption((Matrix)op[1]);
        Encryption d=new Encryption((Matrix)op[0]);
        StringTransformer st=new StringTransformer();
        ArrayList<Matrix> plainText=st.readPlain("C:/Users/G.S.Neeraja Shree/cloudProject/miniProject/src/abc.txt");
        ArrayList<Matrix> cipherText=new ArrayList<>();
        ArrayList<Matrix> decryptedText=new ArrayList<>();
        System.out.println("plain text "+plainText.size()*4);
        for(Matrix m: plainText){
            Matrix ct;
            ct=e.encryptMessage(m);
            cipherText.add(ct);
        }
        System.out.println("cipher text "+cipherText.size()*4);
        st.writeEncrypted(cipherText, "Z:/Miniproject/efg.txt");
        //cipherText=st.readEncrypted("Z:/Miniproject/efg.txt");
        for(Matrix m: cipherText){
            Matrix ct;
            ct=d.encryptMessage(m);
            decryptedText.add(ct);
        }
        System.out.println("decrypted text "+decryptedText.size()*4);
        String s=st.matrixToString(decryptedText);
        System.out.println("Decrypted "+s.replace("%", "").replace("$", ""));
        System.exit(0);

    }
}
