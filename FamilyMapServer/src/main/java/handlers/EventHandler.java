package handlers;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import daos.DataAccessException;
import results.EventFamilyResult;
import results.EventIdResult;
import results.PersonFamilyResult;
import results.PersonIdResult;
import services.EventFamilyService;
import services.EventIdService;
import services.PersonFamilyService;
import services.PersonIdService;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;

import static utils.JsonUtils.serializeJson;
import static utils.StringUtils.countSlashes;
import static utils.StringUtils.writeString;

/**
 * Processes requests that contain the "/event" URL path.
 * Handles logic in deciding to send to event ID or event family service based on query params.
 */
public class EventHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        System.out.println("Event handler");

        try {
            if (exchange.getRequestMethod().equalsIgnoreCase("get")) {
                String urlPath = exchange.getRequestURI().toString();
                String resData;

                if (countSlashes(urlPath) > 1) {
                    EventIdService eventIdService = new EventIdService();
                    EventIdResult eventIdResult = eventIdService.runEventId(exchange);
                    resData = serializeJson(eventIdResult);

                    if (eventIdResult.isSuccess()) {
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                    } else {
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                    }
                } else {
                    EventFamilyService eventFamilyService = new EventFamilyService();
                    EventFamilyResult eventFamilyResult = eventFamilyService.runEventFamily(exchange);
                    resData = serializeJson(eventFamilyResult);

                    if (eventFamilyResult.isSuccess()) {
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                    } else {
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                    }
                }

                OutputStream resBody = exchange.getResponseBody();
                writeString(resData, resBody);
                resBody.close();

                System.out.println("Event response sent back");
            } else {
                System.out.println("Wrong request method for event");
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                exchange.getResponseBody().close();
            }
        }
        catch (IOException | DataAccessException e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, 0);
            exchange.getResponseBody().close();

            e.printStackTrace();
        }
    }
}
