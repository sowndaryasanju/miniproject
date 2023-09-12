import java.util.ArrayList;
import java.util.List;
import java.util.stream.DoubleStream;

import Jama.Matrix;

import java.math.BigInteger;
import java.security.SecureRandom;

public class KeyGeneration {
    public double[][] UserInputLatticeGenerator(int n,int m) {
        List<double[]> lattice = generateLattice(n, m);
        // Print the generated lattice vectors
        double[][] ret=new double[3][];
        int i=0;
        int c=0;
        for (double[] vector : lattice) {
            if(isPrimitiveNTuple(vector)  ){//&& !alreadyPresentInArray(ret,vector)){
                ret[i]=vector;
                System.out.println("Taken : "+java.util.Arrays.toString(vector));
                i++;
            }
            else{
                c++;
            }
            if(i==3){
                break;
            }
        }
        System.out.println("Values skipped : "+c);
        return ret;  

    }

    // Function to generate all lattice vectors in n-dimensional space
    public static List<double[]> generateLattice(int n, int maxValue) {
        List<double[]> lattice = new ArrayList<>();

        // Use a recursive function to generate lattice vectors
        generateLatticeRecursive(n, new double[n], 0, maxValue, lattice);

        return lattice;
    }

    // Recursive function to generate lattice vectors
    public static void generateLatticeRecursive(int n, double[] currentVector, int dimension, int maxValue, List<double[]> lattice) {
        if (dimension == n) {
            // Add the generated vector to the lattice
            lattice.add(java.util.Arrays.copyOf(currentVector, currentVector.length));
        } else {
            for (int value = -maxValue; value <= maxValue; value++) {
                currentVector[dimension] = value;
                generateLatticeRecursive(n, currentVector, dimension + 1, maxValue, lattice);
            }
        }
    }
     public boolean isPrimitiveNTuple(double[] nTuple) {
        // Check if the n-tuple is empty or has only one element
        if (nTuple == null || nTuple.length < 2) {
            return false;
        }

        // Calculate the GCD of all pairs of elements
        for (int i = 0; i < nTuple.length; i++) {
            for (int j = i + 1; j < nTuple.length; j++) {
                if(Math.abs(nTuple[i])==Math.abs(nTuple[j])){
                    return false;
                }
                BigInteger gcdResult = BigInteger.valueOf((int)Math.abs(nTuple[i]))
                        .gcd(BigInteger.valueOf((int)Math.abs(nTuple[j])));
                if (!gcdResult.equals(BigInteger.ONE)) {
                    // If any pair is not relatively prime, return false
                    return false;
                }
            }
        }

        // All pairs are relatively prime, return true
        return true;
    }
   /* public boolean alreadyPresentInArray(int[][] outer,int[] inner){
        try{
        for(int i=0;i<outer.length ;i++){
            boolean flag=false;
            for(int j=0;j<inner.length;i++){
                if(outer[i][j]!=inner[j]){
                    flag=true;
                    break;}}
            if(!flag){
                return true;
            }
        }
        return false;}
        catch(Exception e){
            return false;
        }
    }*/
     public static double vectorLength(Matrix vector) {
        return vector.normF(); // Frobenius norm as vector length
    }

    public static Matrix bruteForceSVP(Matrix basisMatrix) {
        int numDimensions = basisMatrix.getColumnDimension();
        double minNorm = Double.MAX_VALUE;
        Matrix shortestVector = null;

        for (int i = 0; i < numDimensions; i++) {
            Matrix vi = basisMatrix.getMatrix(0, numDimensions - 1, i, i); // Get the i-th column as a vector
            double norm = vectorLength(vi);

            if (norm < minNorm) {
                minNorm = norm;
                shortestVector = vi;
            }
        }

        return shortestVector;
    }
     public static Matrix crossProduct(Matrix v1, Matrix v2) {
      /*  if (v1.getRowDimension() != 3 || v2.getRowDimension() != 3) {
            throw new IllegalArgumentException("Both vectors must have 3 elements each.");
        } */

        double[] vector1 = v1.getColumnPackedCopy();
        double[] vector2 = v2.getColumnPackedCopy();

        if (vector1.length != vector2.length) {
            throw new IllegalArgumentException("Vectors must have the same dimension.");
        }

        int dimension = vector1.length;
        double[] result = new double[dimension];

        for (int i = 0; i < dimension; i++) {
            int nextIndex = (i + 1) % dimension;
            int prevIndex = (i + dimension - 1) % dimension;

            double product = vector1[nextIndex] * vector2[prevIndex] - vector1[prevIndex] * vector2[nextIndex];
            result[i] = product;
        }
        return new Matrix(result, 3);
    }
    public double euclideanNorm(Matrix vector){
        int result=0;
        double[] d=vector.getColumnPackedCopy();
        for(int i=0;i<d.length;i++){
            result+=Math.pow(d[i], 2);
        }
        return Math.sqrt(result);
    }
    public static double vectorValue(double[] vector) {
        return Math.sqrt(DoubleStream.of(vector)
                .map(i -> i * i)
                .sum());
    }
    public double theta(Matrix n0, Matrix x){
        double dotProduct = n0.transpose().times(x).get(0, 0);
        double magnitudeN0 = vectorValue(n0.getColumnPackedCopy());
        double magnitudeX = vectorValue(x.getColumnPackedCopy());

        double cosTheta = dotProduct / (magnitudeN0 * magnitudeX);

        // Calculate the angle in radians
        double angleRad = Math.acos(cosTheta);

        // Convert radians to degrees
        double angleDeg = Math.toDegrees(angleRad);

        return angleDeg;
    }
    public int eulerTotient(double x) {
        int result =(int) x; // Initialize result as x

        // Check for prime factors of x
        for (int i = 2; i * i <= x; i++) {
            if (x % i == 0) {
                // i is a prime factor of x
                while (x % i == 0) {
                    x /= i;
                }
                // Subtract the count of multiples of i from result
                result -= result / i;
                 }
        }

        // If x is still greater than 1, it's a prime number
        if (x > 1) {
            result -= result / x;
        }

        return result;
    }
     public Matrix rotateVector(double y, double angleDegrees, Matrix n1) {
     

        double angleRad = Math.toRadians(angleDegrees);
        double cosTheta = Math.cos(angleRad);
        double sinTheta = Math.sin(angleRad);

        // Create a rotation matrix
        double[][] rotationMatrixData = {
            {cosTheta, -sinTheta},
            {sinTheta, cosTheta}
        };
        Matrix rotationMatrix = new Matrix(rotationMatrixData);
        return rotationMatrix;
    }
    public  Matrix convertToVector(double y, double angleDegrees, Matrix n1) {
     

        double angleRad = Math.toRadians(angleDegrees);
        double cosTheta = Math.cos(angleRad);

        // Calculate the components of the resulting vector c1
        double[] c1Components = new double[60];
        for (int i = 0; i < 60; i++) {
            c1Components[i] = y * n1.get(i, 0)*cosTheta;
        }

        // Create a 60x1 vector c1
        Matrix c1 = new Matrix(c1Components, 60);

        return c1;
    }   
    public Matrix inverseMatrix(Matrix matrix){
        try {
            // Find the inverse of the matrix
            return matrix.inverse();

           
         
        } catch (RuntimeException e) {
            // Handle the case where the matrix is singular (non-invertible)
            System.out.println("Matrix is singular and cannot be inverted.");
            return matrix;
        }
    }    
    public BigInteger maxPrime(int bitLength) {
        SecureRandom random = new SecureRandom();
        BigInteger primeCandidate;

        do {
            // Generate a random BigInteger of the specified bit length
            primeCandidate = new BigInteger(bitLength, random);

            // Ensure the number is odd (to increase chances of being prime)
            if (!primeCandidate.testBit(0)) {
                primeCandidate = primeCandidate.setBit(0);
            }

            // Perform a probabilistic primality test (Miller-Rabin)
            boolean isPrime = primeCandidate.isProbablePrime(100);

            if (isPrime) {
                return primeCandidate; // Found a prime, return it
            }
        } while (true);
    }
    public Object[] generateKeys(){
        KeyGeneration k=new KeyGeneration();
       // int[] x={24,7,13,3};
        //boolean b=k.isPrimitiveNTuple(x);
        //System.out.println(b);
        double[][] basisArray=k.UserInputLatticeGenerator(3,3);
        Matrix basisMatrix = new Matrix(basisArray);

        // Find the shortest vector using the brute-force SVP algorithm
        Matrix shortestVector = bruteForceSVP(basisMatrix);

        // Print the shortest vector
        System.out.println("Shortest Vector (SVP solution):");
        shortestVector.print(10, 2); 
        double[][] d={basisArray[0]};
        Matrix v1=new Matrix(d);
        Matrix crossProd = crossProduct(v1.transpose(),shortestVector);
       // Matrix crossProd=v1.times(shortestVector);
        // Print the shortest vector
        System.out.println("Cross product :");
        crossProd.print(10, 2); 

        System.out.println("Euclidean norm");
        double enorm=k.euclideanNorm(crossProd);
        System.out.println(enorm);

        System.out.println("Theta");
        double theta=k.theta(shortestVector,crossProd);
        System.out.println(theta);
        System.out.println("Eulier totent");
        int et=k.eulerTotient(enorm);
        System.out.println(et);

        System.out.println("Rotation");
        Matrix rotated=k.rotateVector(et, theta, crossProd);
        rotated.print(10,2);
        Matrix dKey=rotated.transpose();
        Matrix ekey=k.inverseMatrix(dKey);
        System.out.println("Keys");
        dKey.print(10,2);
        ekey.print(10,2);
        BigInteger lprime=k.maxPrime(10);
        System.out.println(lprime);
        Object[] ret={dKey,ekey,lprime};
        return ret;
    }
    public static void main(String[] args){
        KeyGeneration k=new KeyGeneration();
        k.generateKeys(); 
        System.exit(0);

    }
}