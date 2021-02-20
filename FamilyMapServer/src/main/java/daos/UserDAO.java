package daos;

import models.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Interfaces with User database to provide specific operations.
 */
public class UserDAO {
    private final Connection conn;

    public UserDAO(Connection conn) {
        this.conn = conn;
    }

    /**
     * Adds new user to the database.
     * @param user User of the Family Map.
     * @throws DataAccessException
     */
    public void insert(User user) throws DataAccessException {
    }

    /**
     * Finds user within the database.
     * @param username Username of user of the Family Map.
     * @return User User who has the username searched.
     * @throws DataAccessException
     */
    public User find(String username) throws DataAccessException {
        return null;
    }

    /**
     * Clears all users from the database.
     */
    public void clear() throws DataAccessException {
        try (Statement stmt = conn.createStatement()){
            String sql = "DELETE FROM User";
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            throw new DataAccessException("SQL Error encountered while clearing tables");
        }
    }
}
