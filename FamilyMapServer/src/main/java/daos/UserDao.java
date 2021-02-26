package daos;

import models.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Interfaces with User database to provide specific operations.
 */
public class UserDao {
    private final Connection conn;

    /**
     * Constructor used to initialize database connection
     * @param conn Connection to database
     */
    public UserDao(Connection conn) {
        this.conn = conn;
    }

    /**
     * Adds new user to the database.
     * @param user User to add
     * @throws DataAccessException Exception if user couldn't be inserted.
     */
    public void insert(User user) throws DataAccessException {
    }

    /**
     * Finds user in database associated with given username.
     * @param username Username of User to find
     * @return User User who has the username searched.
     * @throws DataAccessException Exception if user couldn't be found.
     */
    public User find(String username) throws DataAccessException {
        return null;
    }

    /**
     * Clears all users from the database.
     * @throws DataAccessException Exception if event(s) couldn't be cleared.
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
