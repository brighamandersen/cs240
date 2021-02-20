package daos;

import models.AuthToken;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Interfaces with AuthToken database to provide specific operations.
 */
public class AuthTokenDAO {
    private final Connection conn;

    public AuthTokenDAO(Connection conn) {
        this.conn = conn;
    }

    /**
     * Adds new authToken to the database.
     * @param authToken Unique token which allows user access to program.
     * @throws DataAccessException
     */
    public void insert(AuthToken authToken) throws DataAccessException {
    }

    /**
     * Finds authToken within the database.
     * @param token Unique identifier for a token.
     * @return AuthToken AuthToken which has the unique identifier.
     * @throws DataAccessException
     */
    public AuthToken find(String token) throws DataAccessException {
        return null;
    }

    /**
     * Clears all tokens from the database.
     */
    public void clear() throws DataAccessException {
        try (Statement stmt = conn.createStatement()){
            String sql = "DELETE FROM Event";
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            throw new DataAccessException("SQL Error encountered while clearing tables");
        }
    }
}
