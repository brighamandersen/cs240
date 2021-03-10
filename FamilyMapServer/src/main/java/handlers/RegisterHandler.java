package handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import daos.DataAccessException;
import requests.RegisterRequest;
import results.RegisterResult;
import services.RegisterService;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;

import static utils.JsonUtils.deserializeJson;
import static utils.JsonUtils.serializeJson;
import static utils.StringUtils.readString;
import static utils.StringUtils.writeString;

/**
 * Processes requests that contain the "/user/register" URL path.
 */
public class RegisterHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        System.out.println("Register handler");

        try {
            if (exchange.getRequestMethod().equalsIgnoreCase("post")) {
                InputStream reqBody = exchange.getRequestBody();
                String reqData = readString(reqBody);
                RegisterRequest registerRequest = deserializeJson(reqData, RegisterRequest.class);

                RegisterService registerService = new RegisterService();
                RegisterResult registerResult = registerService.register(registerRequest);

                if (registerResult.isSuccess()) {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                } else {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                }

                String resData = serializeJson(registerResult);
                OutputStream resBody = exchange.getResponseBody();
                writeString(resData, resBody);
                resBody.close();

                System.out.println("Register response sent back");
            } else {
                System.out.println("Wrong request method for register");
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
