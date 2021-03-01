package daos;

import models.AuthToken;

import java.sql.*;

/**
 * Interfaces with AuthToken database to provide specific operations.
 */
public class AuthTokenDao {
    private final Connection conn;

    /**
     * Constructor used to initialize database connection
     * @param conn Connection to database
     */
    public AuthTokenDao(Connection conn) {
        this.conn = conn;
    }

    /**
     * Adds new authToken to the database.
     * @param authToken Auth token to add
     * @throws DataAccessException Exception if auth token couldn't be inserted.
     */
    public void insert(AuthToken authToken) throws DataAccessException {
        String sql = "INSERT INTO AuthToken (token, associatedUsername) VALUES (?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, authToken.getToken());
            stmt.setString(2, authToken.getAssociatedUsername());

            stmt.executeUpdate();
        } catch ( SQLException e) {
            throw new DataAccessException("Error encountered while inserting auth token into the database");
        }
    }

    /**
     * Finds authToken in database.
     * @param token Auth token to find
     * @return AuthToken AuthToken which has the unique identifier.
     * @throws DataAccessException Exception if auth token couldn't be found.
     */
    public AuthToken find(String token) throws DataAccessException {
        AuthToken authToken;
        ResultSet rs = null;
        String sql = "SELECT * FROM AuthToken WHERE token = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, token);
            rs = stmt.executeQuery();
            if (rs.next()) {
                authToken = new AuthToken(rs.getString("token"),
                        rs.getString("associatedUsername"));
                return authToken;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding auth token with token: " + token);
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
     * Clears all tokens from the database.
     * @throws DataAccessException Exception if auth token(s) couldn't be cleared.
     */
    public void clear() throws DataAccessException {
        try (Statement stmt = conn.createStatement()){
            String sql = "DELETE FROM AuthToken";
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            throw new DataAccessException("SQL Error encountered while clearing auth token table");
        }
    }
}
