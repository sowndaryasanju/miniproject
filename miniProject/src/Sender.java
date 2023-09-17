import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;

public class Sender {
    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080),0);
        //server.bind(null, 8080); // Bind to port 8080 (you can use any available port)

        server.createContext("/data", new DataHandler());
        server.setExecutor(null); // Use the default executor

        server.start();
        System.out.println("Server is running on port 8080");
    }

    static class DataHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            // Simulate data processing on the server
            String responseData = "Hello from Server!";
            exchange.sendResponseHeaders(200, responseData.length());
            OutputStream os = exchange.getResponseBody();
            os.write(responseData.getBytes());
            os.close();
        }
    }
}
