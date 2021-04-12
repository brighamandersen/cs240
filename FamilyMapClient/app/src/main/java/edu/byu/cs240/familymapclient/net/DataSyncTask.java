package edu.byu.cs240.familymapclient.net;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import java.util.List;

import edu.byu.cs240.familymapclient.model.DataCache;
import models.Event;
import models.Person;
import results.EventFamilyResult;
import results.PersonFamilyResult;

import static edu.byu.cs240.familymapclient.model.DataCache.addEventColor;

/**
 * Calls server asynchronously on background thread to retrieve family and event data.
 */
public class DataSyncTask implements Runnable {
    private final Handler messageHandler;
    private final String serverHostName;
    private final int serverPortNumber;
    private final String authtoken;
    private final String userPersonID;

    public DataSyncTask(Handler messageHandler, String serverHostName, int serverPortNumber, String authtoken, String userPersonID) {
        this.messageHandler = messageHandler;
        this.serverHostName = serverHostName;
        this.serverPortNumber = serverPortNumber;
        this.authtoken = authtoken;
        this.userPersonID = userPersonID;
    }

    @Override
    public void run() {
        ServerProxy serverProxy = new ServerProxy(serverHostName, serverPortNumber);

        PersonFamilyResult personFamilyResult = serverProxy.fetchAllPersons(authtoken);
        List<Person> persons = personFamilyResult.getPersons();

        for (Person person : persons) {
            DataCache.addPerson(person.getPersonID(), person);

            if (person.getPersonID().equals(userPersonID)) {
                DataCache.setUser(person);
            }
        }

        EventFamilyResult eventFamilyResult = serverProxy.fetchAllEvents(authtoken);
        List<Event> events = eventFamilyResult.getEvents();

        for (Event event : events) {
            DataCache.addEvent(event.getEventID(), event);

            DataCache.addPersonEvent(event.getPersonID(), event);

            // If unique event, add a different color
            if (!DataCache.getEventColors().containsKey(event.getEventType().toLowerCase())) {
                addEventColor(event.getEventType().toLowerCase());
            }
        }

        sendMessage(DataCache.getUser().getFirstName(), DataCache.getUser().getLastName());
    }

    private void sendMessage(String firstName, String lastName) {
        Message message = Message.obtain();

        Bundle messageBundle = new Bundle();
        messageBundle.putString("FirstNameKey", firstName);
        messageBundle.putString("LastNameKey", lastName);

        message.setData(messageBundle);

        messageHandler.sendMessage(message);
    }
}
