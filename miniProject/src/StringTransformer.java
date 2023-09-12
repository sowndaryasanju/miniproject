import Jama.Matrix;
import java.io.File;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
public class StringTransformer {
    public ArrayList<Matrix> readPlain(String fileName){
        //read file
        try{
            File f=new File(fileName);
            Scanner fReader = new Scanner(f);
            int index=1;
            double[][] arr={{37,37},{37,37}};
            ArrayList<Matrix> plainText=new ArrayList<>();
            while(fReader.hasNext()){
                String c=fReader.next();
                for(int i=0;i<c.length();i++){
                    char x=c.charAt(i);
                   // System.out.println(x+" "+index+" "+(int)x);
                    if(index%4==0){
                        arr[1][1]=(double)(int)x;
                        Matrix m=new Matrix(arr);
                       // System.out.println("arr : "+arr[0][0]+" "+arr[0][1]+" "+arr[1][0]+" "+arr[1][1]);
                        plainText.add(m);
                        arr=new double[][]{{37,37},{37,37}};
                    }
                    else if(index%4==3){
                        arr[1][0]=(double)(int)x;
                    }
                    else if(index%4==2){
                        arr[0][1]=(double)(int)x;
                    }
                    else{
                        arr[0][0]=(double)(int)x;
                    }
                    index++;}
                if(index%4==0){
                        arr[1][1]=(double)(int) ' ';//ASCII value of space
                        Matrix m=new Matrix(arr);
                        //System.out.println("arr : "+arr[0][0]+" "+arr[0][1]+" "+arr[1][0]+" "+arr[1][1]);
                        plainText.add(m);
                        arr=new double[][]{{37,37},{37,37}};
                    }
                else if(index%4==3){
                        arr[1][0]=(double)(int) ' ';
                    }
                else if(index%4==2){
                        arr[0][1]=(double)(int) ' ';
                    }
                else{
                        arr[0][0]=(double)(int) ' ';
                    }
                    index++;
            }
            if(index%4!=1){
                Matrix m=new Matrix(arr);
                plainText.add(m);
            }
            fReader.close();
            System.out.println(index);
           /*  for(Matrix m:plainText){
                System.out.println("Matrix");
                m.print(5, 2);
            }*/
            return plainText;
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
    public String matrixToString(ArrayList<Matrix> l){
        String s="";
        for(Matrix m:l){
            s=s+(char)m.get(0,0)+(char)m.get(0,1)+(char)m.get(1,0)+(char)m.get(1,1);
        }
        return s;
    }
   public void writeEncrypted(ArrayList<Matrix> l,String fileName){
    try(PrintWriter pw=new PrintWriter(new FileWriter(fileName))){
        for(Matrix m:l){
            pw.print(" "+m.get(0,0)+" "+m.get(0,1)+" "+m.get(1,0)+" "+m.get(1,1)+" ");
        }
    }
    catch(Exception e){
        e.printStackTrace();
    }
   }
   public ArrayList<Matrix> readEncrypted(String fileName){
        //read file
        try{
            File f=new File(fileName);
            Scanner fReader = new Scanner(f);
            int index=1;
            double[][] arr={{37,37},{37,37}};
            ArrayList<Matrix> plainText=new ArrayList<>();
            while(fReader.hasNext()){
                double x=fReader.nextDouble();
                //String s=fReader.next();
                //x=Double.parseDouble(s);
                if(index%4==0){
                        arr[1][1]=(double)(int)x;
                        Matrix m=new Matrix(arr);
                        plainText.add(m);
                        arr=new double[][]{{37,37},{37,37}};
                    }
                else if(index%4==3){
                        arr[1][0]=(double)(int)x;
                    }
                else if(index%4==2){
                        arr[0][1]=(double)(int)x;
                    }
                else{
                        arr[0][0]=(double)(int)x;
                    }
                    index++;
            }
            if(index%4!=1){
                Matrix m=new Matrix(arr);
                plainText.add(m);
            }
            fReader.close();
            System.out.println(index);
            return plainText;
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
 
}
