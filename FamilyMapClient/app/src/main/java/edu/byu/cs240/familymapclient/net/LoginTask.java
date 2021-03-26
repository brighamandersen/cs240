package edu.byu.cs240.familymapclient.net;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import requests.LoginRequest;
import results.LoginResult;

/**
 * Calls server asynchronously on background thread to login/register.
 */
public class LoginTask implements Runnable {
    private final Handler messageHandler;
    private final String serverHostName;
    private final int serverPortNumber;
    private final String username;
    private final String password;

    public LoginTask(Handler messageHandler, String serverHostName,
                     int serverPortNumber, String username, String password) {
        this.messageHandler = messageHandler;
        this.serverHostName = serverHostName;
        this.serverPortNumber = serverPortNumber;
        this.username = username;
        this.password = password;
    }

    @Override
    public void run() {
        ServerProxy serverProxy = new ServerProxy(serverHostName, serverPortNumber);

        LoginRequest loginRequest = new LoginRequest(username, password);
        LoginResult loginResult = serverProxy.login(loginRequest);

        sendMessage(loginResult.getUsername(), loginResult.getAuthtoken(), loginResult.getPersonID());
    }

    private void sendMessage(String resUsername, String resAuthtoken, String resPersonID) {
        Message message = Message.obtain();

        Bundle messageBundle = new Bundle();
        messageBundle.putString("UsernameKey", resUsername);
        messageBundle.putString("AuthtokenKey", resAuthtoken);
        messageBundle.putString("PersonIDKey", resPersonID);

        message.setData(messageBundle);

        messageHandler.sendMessage(message);
    }
}