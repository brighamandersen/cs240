package edu.byu.cs240.familymapclient;

import edu.byu.cs240.familymapclient.net.ServerProxy;
import requests.LoginRequest;
import results.LoginResult;

public class HelloWorld {
    public static void main(String[] args) {
        ServerProxy serverProxy = new ServerProxy();
        ServerProxy.setServerHostName("localhost");
        ServerProxy.setServerPortNumber(8080);

        LoginRequest loginRequest = new LoginRequest("brighamband", "password");
        LoginResult loginResult = serverProxy.login(loginRequest);

        System.out.println("ran");
    }
}
