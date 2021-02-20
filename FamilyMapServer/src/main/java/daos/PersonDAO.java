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

    public PersonDAO(Connection conn) {
        this.conn = conn;
    }

    /**
     * Adds new person to the database.
     * @param person New person to add.
     * @throws DataAccessException
     */
    public void insert(Person person) throws DataAccessException {
    }

    /**
     * Finds person within the database.
     * @param personID Unique identifier for person.
     * @return Person Person who has the username searched.
     * @throws DataAccessException
     */
    public Person find(String personID) throws DataAccessException {
        return null;
    }

    /**
     * Clears all persons from the database.
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
