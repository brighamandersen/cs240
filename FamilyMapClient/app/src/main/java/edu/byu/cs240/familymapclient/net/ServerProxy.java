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

import static utils.JsonUtils.deserializeJson;
import static utils.JsonUtils.serializeJson;
import static utils.StringUtils.readString;
import static utils.StringUtils.writeString;

/**
 * Creates the HTTP requests to send to the server
 */
public class ServerProxy {
    // Have user input for these two variables go straight here and be stored during usage
    public static String serverHostName;
    public static int serverPortNumber;
    // public static String authtoken;  // maybe just store auth token here is you have it immediately when you need it

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

    public PersonFamilyResult fetchAllPeople(String reqToken) {
        return null;
    }

    public EventFamilyResult fetchAllEvents(String reqToken) {
        return null;
    }

    private String sendRequest(String endpoint, String reqMethod, String authtoken, String reqData) {
        String resData = null;

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

            if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream resBody = http.getInputStream();
                resData = readString(resBody);

                System.out.println("Successful request made and response received");
            } else {
                System.out.println("Error: " + http.getResponseMessage());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resData;
    }

    public static String getServerHostName() {
        return serverHostName;
    }

    public static void setServerHostName(String serverHostName) {
        ServerProxy.serverHostName = serverHostName;
    }

    public static int getServerPortNumber() {
        return serverPortNumber;
    }

    public static void setServerPortNumber(int serverPortNumber) {
        ServerProxy.serverPortNumber = serverPortNumber;
    }
}
