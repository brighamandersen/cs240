package edu.byu.cs240.familymapclient.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Creates the HTTP requests to send to the server
 */
public class ServerProxy {
    // Have user input for these two variables go straight here and be stored during usage
    public static String serverHostName;
    public static int serverPortNumber;
    // public static String authtoken;  // maybe just store auth token here is you have it immediately when you need it

    private void sendRequest(String endpoint, String reqMethod, String authtoken /*, String reqBodyData*/) {
        try {
            URL url = new URL("http://" + serverHostName + ":" + serverPortNumber + "/" + endpoint);

            HttpURLConnection http = (HttpURLConnection) url.openConnection();

            if (reqMethod.equalsIgnoreCase("get")) {
                assert reqBody == null;
                http.setDoOutput(false);    // No request body
            } else if (reqMethod.equalsIgnoreCase("post")) {
                assert reqBody != null;
                http.setDoOutput(true);     // Has a request body
                // FIXME - add request body
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
                // FIXME - This should come from the request, deserialize??
                String reqData ="{\"route\": \"atlanta-miami\"}";

                OutputStream reqBody = http.getOutputStream();
                writeString(reqData, reqBody);
                reqBody.close();
            }

            if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream resBody = http.getInputStream();
                String resData = readString(resBody);

                System.out.println("Successful request made and response received");
            } else {
                System.out.println("Error: " + http.getResponseMessage());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
//    public LoginResult login(LoginRequest r) {
            // Serialize request as JSON string
            // Make HTTP request to server
            // Deserialize response body to LoginResult object
//    }

//    public RegisterResult register(RegisterRequest) {
//        // see above
//    }
//
//    public PersonFamilyResult fetchAllPeople(String reqToken) {
//
//    }
//
//    public EventFamilyResult fetchAllEvents(String reqToken) {
//
//    }


//    This class uses Java's HttpURLConnection class to call server methods
    // Login
    // Register
    // GetAllPeople
    // GetAllEvents

    // See Ticket To Ride Client.java example

    // If nothing else, stub this class out
}
