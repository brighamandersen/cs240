package daos;

import models.Person;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Interfaces with Person database to provide specific operations.
 */
public class PersonDAO {
    private final Connection conn;

    /**
     * Constructor used to initialize database connection
     * @param conn Connection to database
     */
    public PersonDAO(Connection conn) {
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
     * Finds person within the database.
     * @param personID ID of person to find
     * @return Person Person who has the username searched.
     * @throws DataAccessException Exception if person couldn't be found.
     */
    public Person find(String personID) throws DataAccessException {
        return null;
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
