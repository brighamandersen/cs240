package handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.nio.file.Files;

/**
 * Default handler for all requests that don't match URL paths of other handlers.
 * Converts the URL path to a directory on the server and returns back a file.
 */
public class FileHandler  implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        System.out.println("File handler");

        if (exchange.getRequestMethod().equalsIgnoreCase("get")) {
            String urlPath = exchange.getRequestURI().toString();

            if (urlPath == null || urlPath.equals("/")) {
                urlPath = "/index.html";
            }

            String filePath = "web" + urlPath;
            File file = new File(filePath);
            if (file.exists()) {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                OutputStream resBody = exchange.getResponseBody();
                Files.copy(file.toPath(), resBody);
            } else {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_NOT_FOUND, 0);
            }
            exchange.getResponseBody().close();
        } else {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_METHOD, 0);
            exchange.getResponseBody().close();

        }
    }
}
