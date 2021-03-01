package daos;

import models.User;

import java.sql.*;

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
        String sql = "INSERT INTO User (username, password, email, firstName, " +
                "lastName, gender, personId) VALUES (?,?,?,?,?,?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getFirstName());
            stmt.setString(5, user.getLastName());
            stmt.setString(6, user.getGender());
            stmt.setString(7, user.getPersonId());

            stmt.executeUpdate();
        } catch ( SQLException e) {
            throw new DataAccessException("Error encountered while inserting user into the database");
        }
    }

    /**
     * Finds user in database associated with given username.
     * @param username Username of User to find
     * @return User User who has the username searched.
     * @throws DataAccessException Exception if user couldn't be found.
     */
    public User find(String username) throws DataAccessException {
        User user;
        ResultSet rs = null;
        String sql = "SELECT * FROM User WHERE username = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            rs = stmt.executeQuery();
            if (rs.next()) {
                user = new User(rs.getString("username"), rs.getString("password"),
                        rs.getString("email"), rs.getString("firstName"),
                        rs.getString("lastName"), rs.getString("gender"),
                        rs.getString("personId"));
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding user with username: " + username);
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
     * Clears all users from the database.
     * @throws DataAccessException Exception if event(s) couldn't be cleared.
     */
    public void clear() throws DataAccessException {
        try (Statement stmt = conn.createStatement()){
            String sql = "DELETE FROM User";
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            throw new DataAccessException("SQL Error encountered while clearing user table");
        }
    }
}
