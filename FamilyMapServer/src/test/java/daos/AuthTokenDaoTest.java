package daos;

import models.AuthToken;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

public class AuthTokenDaoTest {
    private Database db;
    private AuthToken testAuthToken;
    private AuthTokenDao authTokenDao;

    @BeforeEach
    public void setUp() throws DataAccessException {
        db = new Database();

        testAuthToken = new AuthToken("auth123token456", "johndoe");

        Connection conn = db.getConnection();
        db.clearTables();
        authTokenDao = new AuthTokenDao(conn);
    }

    @AfterEach
    public void tearDown() throws DataAccessException {
        db.closeConnection(false);
    }

    @Test
    public void testInsertPass() throws DataAccessException {
        authTokenDao.insert(testAuthToken);
        AuthToken authTokenInserted = authTokenDao.find(testAuthToken.getAuthtoken());

        assertNotNull(authTokenInserted);
        assertEquals(testAuthToken, authTokenInserted);
    }

    @Test
    public void testInsertFail() throws DataAccessException {
        authTokenDao.insert(testAuthToken);     // Will throw error for inserting duplicate
        assertThrows(DataAccessException.class, ()-> authTokenDao.insert(testAuthToken));
    }

    @Test
    public void testFindPass() throws DataAccessException {
        authTokenDao.insert(testAuthToken);
        AuthToken authTokenFound = authTokenDao.find("auth123token456");

        assertNotNull(authTokenFound);
        assertEquals(authTokenFound, testAuthToken);
    }

    @Test
    public void testFindFail() throws DataAccessException {
        authTokenDao.insert(testAuthToken);
        String badToken = "bad_token";
        AuthToken authTokenFound = authTokenDao.find(badToken);

        assertNull(authTokenFound);
        assertNotEquals(badToken, testAuthToken.getAuthtoken());
    }

    @Test
    public void testClear() throws DataAccessException {
        authTokenDao.clear();
        AuthToken clearedAuthToken = authTokenDao.find(testAuthToken.getAuthtoken());

        assertNull(clearedAuthToken);
    }

}
