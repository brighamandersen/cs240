package daos;

import models.Person;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

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
    }

    /**
     * Finds person in database associated with given person ID.
     * @param personID ID of person to find
     * @return Person Person who has the username searched.
     * @throws DataAccessException Exception if person couldn't be found.
     */
    public Person findByPersonID(String personID) throws DataAccessException {
        return null;
    }

    /**
     * Finds event in database associated with given username.
     * @param username Username of user to whom event belongs
     * @return Event Event object associated with the user being searched.
     * @throws DataAccessException Exception if event couldn't be found.
     */
    public Person findByUsername(String username) throws DataAccessException {
        return null;
    }

    /**
     * Deletes event in database associated with given username.
     * @param username Username of User associated with person
     * @throws DataAccessException Exception if person couldn't be deleted.
     */
    public void deleteByUsername(String username) throws DataAccessException {
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
            throw new DataAccessException("SQL Error encountered while clearing tables");
        }
    }
}
