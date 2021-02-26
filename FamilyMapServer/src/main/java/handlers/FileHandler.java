package handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

/**
 * Default handler for all requests that don't match URL paths of other handlers.
 * Converts the URL path to a directory on the server and returns back a file.
 */
public class FileHandler  implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        System.out.println("File handler");
    }
}
