package handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import daos.DataAccessException;
import results.Result;
import services.ClearService;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;

import static utils.JsonUtils.serializeJson;
import static utils.StringUtils.writeString;

/**
 * Processes requests that contain the "/clear" URL path.
 */
public class ClearHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        System.out.println("Clear handler");

        try {
            if (exchange.getRequestMethod().equalsIgnoreCase("post")) {
                ClearService clearService = new ClearService();
                Result result = clearService.clear();

                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                String resData = serializeJson(result);
                OutputStream resBody = exchange.getResponseBody();
                writeString(resData, resBody);
                resBody.close();

                System.out.println("Clear response sent back");
            } else {
                System.out.println("Wrong request method for clear");
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                exchange.getResponseBody().close();
            }
        }
        catch (IOException | DataAccessException e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            exchange.getResponseBody().close();

            e.printStackTrace();
        }
    }
}
