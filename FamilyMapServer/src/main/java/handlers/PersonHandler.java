package handlers;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import daos.DataAccessException;
import results.PersonFamilyResult;
import results.PersonIdResult;
import services.PersonFamilyService;
import services.PersonIdService;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.nio.file.Path;
import java.nio.file.Paths;

import static utils.JsonUtils.serializeJson;
import static utils.StringUtils.writeString;

/**
 * Processes requests that contain the "/person" URL path.
 * Handles logic in deciding to send to person ID or person family service based on query params.
 */
public class PersonHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        System.out.println("Person handler");

        try {
            if (exchange.getRequestMethod().equalsIgnoreCase("get")) {
                Path urlPath = Paths.get(exchange.getRequestURI().toString());
                String resData;

                // Get auth token from request headers
                Headers reqHeaders = exchange.getRequestHeaders();
                if (!reqHeaders.containsKey("Authorization")) {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                    exchange.getResponseBody().close();
                    return;
                }
                String reqToken = reqHeaders.getFirst("Authorization");

                if (urlPath.getNameCount() > 1) {
                    PersonIdService personIdService = new PersonIdService();
                    PersonIdResult personIdResult = personIdService.runPersonId(reqToken, urlPath);
                    resData = serializeJson(personIdResult);

                    if (personIdResult.isSuccess()) {
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                    } else {
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                    }
                } else {
                    PersonFamilyService personFamilyService = new PersonFamilyService();
                    PersonFamilyResult personFamilyResult = personFamilyService.runPersonFamily(reqToken);
                    resData = serializeJson(personFamilyResult);

                    if (personFamilyResult.isSuccess()) {
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                    } else {
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                    }
                }

                OutputStream resBody = exchange.getResponseBody();
                writeString(resData, resBody);
                resBody.close();

                System.out.println("Person response sent back");
            } else {
                System.out.println("Wrong request method for person");
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