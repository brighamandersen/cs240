package edu.byu.cs240.familymapclient.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import requests.LoginRequest;
import requests.RegisterRequest;
import results.EventFamilyResult;
import results.LoginResult;
import results.PersonFamilyResult;
import results.RegisterResult;
import results.Result;

import static utils.JsonUtils.deserializeJson;
import static utils.JsonUtils.serializeJson;
import static utils.StringUtils.readString;
import static utils.StringUtils.writeString;

/**
 * Creates the HTTP requests to send to the server
 */
public class ServerProxy {
    // Have user input for these two variables go straight here and be stored during usage
    private final String serverHostName;
    private final int serverPortNumber;
    // public static String authtoken;  // maybe just store auth token here is you have it immediately when you need it

    public ServerProxy(String serverHostName, int serverPortNumber) {
        this.serverHostName = serverHostName;
        this.serverPortNumber = serverPortNumber;
    }

    public LoginResult login(LoginRequest r) {
        String reqData = serializeJson(r);
        String resData = sendRequest("user/login", "POST", null, reqData);

        return deserializeJson(resData, LoginResult.class);
    }

    public RegisterResult register(RegisterRequest r) {
        String reqData = serializeJson(r);
        String resData = sendRequest("user/register", "POST", null, reqData);

        return deserializeJson(resData, RegisterResult.class);
    }

    public PersonFamilyResult fetchAllPersons(String reqToken) {
        String resData = sendRequest("person", "GET", reqToken, null);

        return deserializeJson(resData, PersonFamilyResult.class);
    }

    public EventFamilyResult fetchAllEvents(String reqToken) {
        String resData = sendRequest("event", "GET", reqToken, null);

        return deserializeJson(resData, EventFamilyResult.class);
    }

    private String sendRequest(String endpoint, String reqMethod, String authtoken, String reqData) {
        try {
            URL url = new URL("http://" + serverHostName + ":" + serverPortNumber + "/" + endpoint);

            HttpURLConnection http = (HttpURLConnection) url.openConnection();

            if (reqMethod.equalsIgnoreCase("get")) {
                assert reqData == null;
                http.setDoOutput(false);    // No request body
            } else if (reqMethod.equalsIgnoreCase("post")) {
                assert reqData != null;
                http.setDoOutput(true);     // Has a request body
            } else {
                throw new IOException("Bad request method");
            }
            http.setRequestMethod(reqMethod);

            if (authtoken != null) {
                http.addRequestProperty("Authorization", authtoken);
                http.addRequestProperty("Accept", "application/json");
            }

            http.connect();

            if (reqMethod.equalsIgnoreCase("post")) {
                OutputStream reqBody = http.getOutputStream();
                writeString(reqData, reqBody);
                reqBody.close();
            }

            InputStream resBody;
            if (http.getResponseCode()  == HttpURLConnection.HTTP_OK) {
                resBody = http.getInputStream();
            } else {
                resBody = http.getErrorStream();
            }

            return readString(resBody);
        } catch (IOException e) {
            Result badResult = new Result(e.getMessage());
            return serializeJson(badResult);
        }
    }
}
