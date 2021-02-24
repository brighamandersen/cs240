package results;

/**
 * Houses response data returned from event ID API endpoint call.
 */
public class EventIDResult extends Result {
    /**
     * Username of user to whom event belongs
     */
    private String associatedUsername;
    /**
     * Event's unique ID
     */
    private String eventID;
    /**
     * Latitude of event's location
     */
    private float latitude;
    /**
     * Longitude of event's location
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
     * Generates a success response body for event ID result.
     * @param associatedUsername Username of user to whom event belongs
     * @param eventID Event's unique ID
     * @param latitude Latitude of event's location
     * @param longitude Longitude of event's location
     * @param country Country where event occurred
     * @param city City where event occurred
     * @param eventType Type of event (birth, baptism, christening, marriage, death, etc.)
     * @param year Year when event occurred
     */
    public EventIDResult(String associatedUsername, String eventID,
                         float latitude, float longitude, String country, String city, String eventType, int year) {
        super(null, true);
        this.associatedUsername = associatedUsername;
        this.eventID = eventID;
        this.latitude = latitude;
        this.longitude = longitude;
        this.country = country;
        this.city = city;
        this.eventType = eventType;
        this.year = year;
    }

    /**
     * Generates an error response body for event ID result.
     * @param message Error message
     */
    public EventIDResult(String message) {
        super(message);
    }

    public String getAssociatedUsername() {
        return associatedUsername;
    }

    public void setAssociatedUsername(String associatedUsername) {
        this.associatedUsername = associatedUsername;
    }

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
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
}

