import Jama.Matrix;
import java.util.ArrayList;

public class App {
    public static void main(String[] args) throws Exception {
        Receiver r=new Receiver();
        Matrix d=r.receiveKey();
        Encryption decrypt=new Encryption(d);
        StringTransformer st=new StringTransformer();
        /* 
        ArrayList<Matrix> cipherText=st.readEncrypted("");
        ArrayList<Matrix>decryptedText=new ArrayList<>();
        for(Matrix m:cipherText){
            Matrix ct=decrypt.encryptMessage(m);
            decryptedText.add(ct);
        }
        String s=st.matrixToString(decryptedText);
        System.out.println(s);
*/
        System.exit(0);
    }
}
