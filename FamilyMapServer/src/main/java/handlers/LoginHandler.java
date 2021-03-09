package handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import daos.DataAccessException;
import requests.LoginRequest;
import results.LoginResult;
import services.LoginService;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;

import static utils.JsonUtils.deserializeJson;
import static utils.JsonUtils.serializeJson;
import static utils.StringUtils.readString;
import static utils.StringUtils.writeString;

/**
 * Processes requests that contain the "/user/login" URL path.
 */
public class LoginHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        System.out.println("Login handler");

        try {
            if (exchange.getRequestMethod().equalsIgnoreCase("post")) {
                InputStream reqBody = exchange.getRequestBody();
                String reqData = readString(reqBody);

                LoginRequest loginRequest = deserializeJson(reqData, LoginRequest.class);
                LoginService loginService = new LoginService();
                LoginResult loginResult = loginService.login(loginRequest);

                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                String resData = serializeJson(loginResult);
                OutputStream resBody = exchange.getResponseBody();
                writeString(resData, resBody);
                resBody.close();

                System.out.println("Login response sent back");
            } else {
                System.out.println("Wrong request method for login");
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
