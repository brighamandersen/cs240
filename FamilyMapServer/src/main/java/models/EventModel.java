package models;

/**
 * Holds family server event data when it's in RAM (when it's not in database).
 */
public class EventModel {
    /**
     * Unique identifier for event.
     */
    private String eventID;
    /**
     * Username of user to which event belongs.
     */
    private String associatedUsername;
    /**
     * ID of person to which event belongs.
     */
    private String personID;
    /**
     * Latitude coordinate of where event took place.
     */
    private double latitude;
    /**
     * Longitude coordinate of where event took place.
     */
    private double longitude;
    /**
     * Country in which event occurred.
     */
    private String country;
    /**
     * City in which event occurred.
     */
    private String city;
    /**
     * Type of event (birth, baptism, christening, marriage, death, etc.).
     */
    private String eventType;
    /**
     * Year in which event occurred.
     */
    private int year;

    public EventModel(String eventID, String associatedUsername, String personID, double latitude,
                      double longitude, String country, String city, String eventType, int year) {
        this.eventID = eventID;
        this.associatedUsername = associatedUsername;
        this.personID = personID;
        this.latitude = latitude;
        this.longitude = longitude;
        this.country = country;
        this.city = city;
        this.eventType = eventType;
        this.year = year;
    }

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public String getAssociatedUsername() {
        return associatedUsername;
    }

    public void setAssociatedUsername(String associatedUsername) {
        this.associatedUsername = associatedUsername;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
