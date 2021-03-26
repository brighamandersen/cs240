package edu.byu.cs240.familymapclient;

import java.util.Map;

import edu.byu.cs240.familymapclient.model.DataCache;
import edu.byu.cs240.familymapclient.net.ServerProxy;
import models.Person;
import requests.LoginRequest;
import results.LoginResult;


public class HelloWorld {
    public static void main(String[] args) {
//        ServerProxy serverProxy = new ServerProxy("localhost", 8080);
//
//        LoginRequest loginRequest = new LoginRequest("brighamband", "password");
//        LoginResult loginResult = serverProxy.login(loginRequest);

        DataCache.initialize();

        Person test = new Person("p", "a", "f", "l",
                "g", "f", "m", "s");

        Map<String, Person> origMap = DataCache.getPersons();

        DataCache.addPerson(test.getPersonID(), test);

        Map<String, Person> map = DataCache.getPersons();

    }
}
