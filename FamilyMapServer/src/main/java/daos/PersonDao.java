package daos;

import models.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Interfaces with Person database to provide specific operations.
 */
public class PersonDao {
    private final Connection conn;

    /**
     * Constructor used to initialize database connection
     * @param conn Connection to database
     */
    public PersonDao(Connection conn) {
        this.conn = conn;
    }

    /**
     * Adds new person to the database.
     * @param person Person to add
     * @throws DataAccessException Exception if person couldn't be inserted.
     */
    public void insert(Person person) throws DataAccessException {
        String sql = "INSERT INTO Person (personID, associatedUsername, firstName, lastName, gender, " +
                "fatherID, motherID, spouseID) VALUES (?,?,?,?,?,?,?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, person.getPersonID());
            stmt.setString(2, person.getAssociatedUsername());
            stmt.setString(3, person.getFirstName());
            stmt.setString(4, person.getLastName());
            stmt.setString(5, person.getGender());
            stmt.setString(6, person.getFatherID());
            stmt.setString(7, person.getMotherID());
            stmt.setString(8, person.getSpouseID());

            stmt.executeUpdate();
        } catch ( SQLException e) {
            throw new DataAccessException("Error encountered while inserting person into the database");
        }
    }

    /**
     * Finds person in database associated with given person ID.
     * @param personID ID of person to find
     * @return Person Person who has the username searched.
     * @throws DataAccessException Exception if person couldn't be found.
     */
    public Person findByPersonID(String personID) throws DataAccessException {
        Person person;
        ResultSet rs = null;
        String sql = "SELECT * FROM Person WHERE personID = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, personID);
            rs = stmt.executeQuery();
            if (rs.next()) {
                person = new Person(rs.getString("personID"), rs.getString("associatedUsername"),
                        rs.getString("firstName"), rs.getString("lastName"),
                        rs.getString("gender"), rs.getString("fatherID"),
                        rs.getString("motherID"), rs.getString("spouseID"));
                return person;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding person with personID: " + personID);
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
    public Person findByUsername(String username) throws DataAccessException {
        Person person;
        ResultSet rs = null;
        String sql = "SELECT * FROM Person WHERE associatedUsername = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            rs = stmt.executeQuery();
            if (rs.next()) {
                person = new Person(rs.getString("personID"), rs.getString("associatedUsername"),
                        rs.getString("firstName"), rs.getString("lastName"),
                        rs.getString("gender"), rs.getString("fatherID"),
                        rs.getString("motherID"), rs.getString("spouseID"));
                return person;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding person with username: " + username);
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
    public List<Person> multiFindByUsername(String username) throws DataAccessException {
        List<Person> persons = new ArrayList<>();
        Person person;
        ResultSet rs = null;
        String sql = "SELECT * FROM Person WHERE associatedUsername = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            rs = stmt.executeQuery();
            while (rs.next()) {
                person = new Person(rs.getString("personID"), rs.getString("associatedUsername"),
                        rs.getString("firstName"), rs.getString("lastName"),
                        rs.getString("gender"), rs.getString("fatherID"),
                        rs.getString("motherID"), rs.getString("spouseID"));
                persons.add(person);
            }
            return persons;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding person with username: " + username);
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
     * @param username Username of User associated with person
     * @throws DataAccessException Exception if person couldn't be deleted.
     */
    public void deleteByUsername(String username) throws DataAccessException {
        try (Statement stmt = conn.createStatement()){
            String sql = "DELETE FROM Person WHERE associatedUsername = \"" + username + "\"";
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            throw new DataAccessException(
                    "SQL Error encountered while deleting person with associatedUsername: " + username);
        }
    }

    /**
     * Clears all persons from the database.
     * @throws DataAccessException Exception if person(s) couldn't be cleared.
     */
    public void clear() throws DataAccessException {
        try (Statement stmt = conn.createStatement()){
            String sql = "DELETE FROM Person";
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            throw new DataAccessException("SQL Error encountered while clearing person table");
        }
    }
}
