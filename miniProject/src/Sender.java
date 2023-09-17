import com.sun.net.httpserver.HttpServer;

import Jama.Matrix;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class Sender {
    public  void sendKey(Matrix m)  {
        try{
        HttpServer server = HttpServer.create(new InetSocketAddress("192.168.43.149", 8080),0);
        //server.bind(null, 8080); // Bind to port 8080 (you can use any available port)

        server.createContext("/data", new DataHandler(m));
        server.setExecutor(null); // Use the default executor

        server.start();
        System.out.println("Server is running on port 8080");}
        catch(Exception e){
            System.out.println(e.toString());
        }
    }

    static class DataHandler implements HttpHandler {
        private Matrix msg;
        DataHandler(Matrix m){
            super();
            msg=m;
        }
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            // Simulate data processing on the server
           // String responseData = "Hello from Server!";
            SecureTransfer st=new SecureTransfer();
            double[][] matrixData = msg.getArray();
            Matrix originalMatrix = new Matrix(matrixData);
            // Encrypt the matrix and send as a string
            String encryptedString = st.encryptMatrix(originalMatrix);
            System.out.println("Encrypted String: " + encryptedString);

            exchange.sendResponseHeaders(200, encryptedString.length());
            OutputStream os = exchange.getResponseBody();
            os.write(encryptedString.getBytes());
            os.close();
        }
    }
}
