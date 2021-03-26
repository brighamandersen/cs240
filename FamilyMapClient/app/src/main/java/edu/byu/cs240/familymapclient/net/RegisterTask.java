package edu.byu.cs240.familymapclient.net;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import requests.RegisterRequest;
import results.RegisterResult;

public class RegisterTask implements Runnable {
    private final Handler messageHandler;
    private final String serverHostName;
    private final int serverPortNumber;
    private final RegisterRequest registerRequest;

    public RegisterTask(Handler messageHandler, String serverHostName,
                        int serverPortNumber, RegisterRequest registerRequest) {
        this.messageHandler = messageHandler;
        this.serverHostName = serverHostName;
        this.serverPortNumber = serverPortNumber;
        this.registerRequest = registerRequest;
    }

    @Override
    public void run() {
        ServerProxy serverProxy = new ServerProxy(serverHostName, serverPortNumber);

        RegisterResult registerResult = serverProxy.register(registerRequest);

        sendMessage(registerResult.getUsername(), registerResult.getAuthtoken(), registerResult.getPersonID());
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
