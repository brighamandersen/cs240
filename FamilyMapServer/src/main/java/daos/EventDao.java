package daos;

import models.Event;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Interfaces with Event database to provide specific operations.
 */
public class EventDao {
    private final Connection conn;

    /**
     * Constructor used to initialize database connection
     * @param conn Connection to database
     */
    public EventDao(Connection conn) {
        this.conn = conn;
    }

    /**
     * Adds new event to the database.
     * @param event Event to add
     * @throws DataAccessException Exception if event couldn't be inserted.
     */
    public void insert(Event event) throws DataAccessException {
        String sql = "INSERT INTO Event (eventId, associatedUsername, personId, latitude, longitude, " +
                "country, city, eventType, year) VALUES (?,?,?,?,?,?,?,?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, event.getEventId());
            stmt.setString(2, event.getAssociatedUsername());
            stmt.setString(3, event.getPersonId());
            stmt.setFloat(4, event.getLatitude());
            stmt.setFloat(5, event.getLongitude());
            stmt.setString(6, event.getCountry());
            stmt.setString(7, event.getCity());
            stmt.setString(8, event.getEventType());
            stmt.setInt(9, event.getYear());

            stmt.executeUpdate();
        } catch ( SQLException e) {
            throw new DataAccessException("Error encountered while inserting event into the database");
        }
    }

    /**
     * Finds event in database associated with given event ID.
     * @param eventId ID of event to find
     * @return Event Event object associated with the event ID given.
     * @throws DataAccessException Exception if event couldn't be found.
     */
    public Event findByEventId(String eventId) throws DataAccessException {
        Event event;
        ResultSet rs = null;
        String sql = "SELECT * FROM Event WHERE eventId = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, eventId);
            rs = stmt.executeQuery();
            if (rs.next()) {
                event = new Event(rs.getString("eventId"), rs.getString("associatedUsername"),
                        rs.getString("personId"), rs.getFloat("latitude"),
                        rs.getFloat("longitude"), rs.getString("country"),
                        rs.getString("city"), rs.getString("eventType"),
                        rs.getInt("year"));
                return event;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding event with eventId: " + eventId);
        } finally {
            if(rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * Finds single event in database associated with given username.
     * @param username Username of user to whom event belongs
     * @return Event Event object associated with the user being searched.
     * @throws DataAccessException Exception if event couldn't be found.
     */
    public Event findByUsername(String username) throws DataAccessException {
        Event event;
        ResultSet rs = null;
        String sql = "SELECT * FROM Event WHERE associatedUsername = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            rs = stmt.executeQuery();
            if (rs.next()) {
                event = new Event(rs.getString("eventId"), rs.getString("associatedUsername"),
                        rs.getString("personId"), rs.getFloat("latitude"),
                        rs.getFloat("longitude"), rs.getString("country"),
                        rs.getString("city"), rs.getString("eventType"),
                        rs.getInt("year"));
                return event;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding event with username: " + username);
        } finally {
            if(rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * Finds multiple events in database associated with given username.
     * @param username Username of user to whom event belongs
     * @return Event(s) associated with the user being searched.
     * @throws DataAccessException Exception if event couldn't be found.
     */
    public List<Event> multiFindByUsername(String username) throws DataAccessException {
        List<Event> events = new ArrayList<>();
        Event event;
        ResultSet rs = null;
        String sql = "SELECT * FROM Event WHERE associatedUsername = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            rs = stmt.executeQuery();
            while (rs.next()) {
                event = new Event(rs.getString("eventId"), rs.getString("associatedUsername"),
                        rs.getString("personId"), rs.getFloat("latitude"),
                        rs.getFloat("longitude"), rs.getString("country"),
                        rs.getString("city"), rs.getString("eventType"),
                        rs.getInt("year"));
                events.add(event);
            }
            return events;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding event with username: " + username);
        } finally {
            if(rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Deletes event in database associated with given username.
     * @param username Username of user to whom event belongs
     * @throws DataAccessException Exception if event couldn't be deleted.
     */
    public void deleteByUsername(String username) throws DataAccessException {
        try (Statement stmt = conn.createStatement()){
            String sql = "DELETE FROM Event WHERE associatedUsername = \"" + username + "\"";
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            throw new DataAccessException(
                    "SQL Error encountered while deleting event with associatedUsername: " + username);
        }
    }

    /**
     * Clears all events from the database.
     * @throws DataAccessException Exception if event(s) couldn't be cleared.
     */
    public void clear() throws DataAccessException {
        try (Statement stmt = conn.createStatement()){
            String sql = "DELETE FROM Event";
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            throw new DataAccessException("SQL Error encountered while clearing event table");
        }
    }
}
