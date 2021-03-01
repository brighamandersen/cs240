package models;

/**
 * Holds family server event data when it's in RAM (when it's not in database).
 */
public class Event {
    /**
     * Unique identifier for event
     */
    private String eventId;
    /**
     * Username of user to which event belongs
     */
    private String associatedUsername;
    /**
     * ID of person to which event belongs
     */
    private String personId;
    /**
     * Latitude coordinate of event location
     */
    private float latitude;
    /**
     * Longitude coordinate of event location
     */
    private float longitude;
    /**
     * Country where event occurred
     */
    private String country;
    /**
     * City where event occurred
     */
    private String city;
    /**
     * Type of event (birth, baptism, christening, marriage, death, etc.)
     */
    private String eventType;
    /**
     * Year when event occurred
     */
    private int year;

    /**
     * Constructor for making event model objects.
     * @param eventId Unique identifier for event
     * @param associatedUsername Username of user to which event belongs
     * @param personId ID of person to which event belongs
     * @param latitude Latitude coordinate of event location
     * @param longitude Longitude coordinate of event location
     * @param country Country where event occurred
     * @param city City where event occurred
     * @param eventType Type of event (birth, baptism, christening, marriage, death, etc.)
     * @param year Year when event occurred
     */
    public Event(String eventId, String associatedUsername, String personId, float latitude,
                 float longitude, String country, String city, String eventType, int year) {
        this.eventId = eventId;
        this.associatedUsername = associatedUsername;
        this.personId = personId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.country = country;
        this.city = city;
        this.eventType = eventType;
        this.year = year;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getAssociatedUsername() {
        return associatedUsername;
    }

    public void setAssociatedUsername(String associatedUsername) {
        this.associatedUsername = associatedUsername;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
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

    /**
     * Compares if two events are equal.
     * @param o Event object
     * @return Boolean signifying whether they are equal.
     */
    @Override
    public boolean equals(Object o) {
        if (o == null)
            return false;
        if (o instanceof Event) {
            Event oEvent = (Event) o;
            return oEvent.getEventId().equals(getEventId()) &&
                    oEvent.getAssociatedUsername().equals(getAssociatedUsername()) &&
                    oEvent.getPersonId().equals(getPersonId()) &&
                    oEvent.getLatitude() == (getLatitude()) &&
                    oEvent.getLongitude() == (getLongitude()) &&
                    oEvent.getCountry().equals(getCountry()) &&
                    oEvent.getCity().equals(getCity()) &&
                    oEvent.getEventType().equals(getEventType()) &&
                    oEvent.getYear() == (getYear());
        } else {
            return false;
        }
    }
}
