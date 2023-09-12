import Jama.Matrix;
//import java.util.Arrays;
public class Encryption {
    Matrix e;
    public Encryption(Matrix key){
        e=key;
    }
    public Matrix encryptMessage(Matrix msg){
        return msg.times(e);
       
    }
}
