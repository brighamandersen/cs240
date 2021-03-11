package handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import results.Result;
import services.FillService;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.nio.file.Path;
import java.nio.file.Paths;

import static utils.JsonUtils.serializeJson;
import static utils.StringUtils.writeString;

/**
 * Processes requests that contain the "/fill" URL path.
 */
public class FillHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        System.out.println("Fill handler");

        try {
            if (exchange.getRequestMethod().equalsIgnoreCase("post")) {
                Path urlPath = Paths.get(exchange.getRequestURI().toString());

                FillService fillService = new FillService();
                Result result = fillService.fill(urlPath);

                if (result.isSuccess()) {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                } else {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                }

                String resData = serializeJson(result);
                OutputStream resBody = exchange.getResponseBody();
                writeString(resData, resBody);
                resBody.close();

                System.out.println("Fill response sent back");
            } else {
                System.out.println("Wrong request method for fill");
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                exchange.getResponseBody().close();
            }
        }
        catch (IOException e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, 0);
            exchange.getResponseBody().close();

            e.printStackTrace();
        }
    }
}
