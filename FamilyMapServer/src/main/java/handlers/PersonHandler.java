package handlers;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;

import static utils.StringUtils.writeString;

/**
 * Processes requests that contain the "/person" URL path.
 * Handles logic in deciding to send to person ID or person family service based on query params.
 */
public class PersonHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        System.out.println("Person handler");

        boolean success = false;

        try {
            if (exchange.getRequestMethod().equalsIgnoreCase("get")) {
                Headers reqHeaders = exchange.getRequestHeaders();

                if (reqHeaders.containsKey("Authorization")) {
                    String authToken = reqHeaders.getFirst("Authorization");

                    if (authToken.equals("afj232hj2332")) {     // FIXME - Shouldn't be hard coded

                        // TODO Pass off to one of the two person services

                        // This is the JSON data we will return in the HTTP response body
                        // (this is unrealistic because it always returns the same answer).
                        String resData =
                                "{ \"game-list\": [" +
                                        "{ \"name\": \"fhe game\", \"player-count\": 3 }," +
                                        "{ \"name\": \"work game\", \"player-count\": 4 }," +
                                        "{ \"name\": \"church game\", \"player-count\": 2 }" +
                                        "]" +
                                        "}";

                        // Start sending the HTTP response to the client, starting with
                        // the status code and any defined headers.
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);

                        // Now that the status code and headers have been sent to the client,
                        // next we send the JSON data in the HTTP response body.

                        // Get the response body output stream.
                        OutputStream resBody = exchange.getResponseBody();
                        // Write the JSON string to the output stream.
                        writeString(resData, resBody);
                        // Close the output stream.  This is how Java knows we are done
                        // sending data and the response is complete/
                        resBody.close();

                        success = true;
                    }
                }
            }

            if (!success) {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                exchange.getResponseBody().close();
            }
        }
        catch (IOException e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            exchange.getResponseBody().close();

            e.printStackTrace();
        }
    }
}
