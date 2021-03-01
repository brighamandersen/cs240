package daos;

import models.Event;
import models.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

public class UserDaoTest {
    private Database db;
    private User testUser;
    private UserDao userDao;

    @BeforeEach
    public void setUp() throws DataAccessException {
        db = new Database();

        testUser = new User("johndoe", "password123", "johndoe@gmail.com",
                "John", "Doe", "m", "person_id14");

        Connection conn = db.getConnection();
        db.clearTables();
        userDao = new UserDao(conn);
    }

    @AfterEach
    public void tearDown() throws DataAccessException {
        db.closeConnection(false);
    }

    @Test
    public void testInsertPass() throws DataAccessException {
        userDao.insert(testUser);
        User userInserted = userDao.find(testUser.getUsername());

        assertNotNull(userInserted);
        assertEquals(testUser, userInserted);
    }

    @Test
    public void testInsertFail() throws DataAccessException {
        userDao.insert(testUser);     // Will throw error for inserting duplicate
        assertThrows(DataAccessException.class, ()-> userDao.insert(testUser));
    }

    @Test
    public void testFindPass() throws DataAccessException {
        userDao.insert(testUser);
        User userFound = userDao.find("johndoe");

        assertNotNull(userFound);
        assertEquals(userFound, testUser);
    }

    @Test
    public void testFindFail() throws DataAccessException {
        userDao.insert(testUser);
        String badUsername = "bad_username";
        User userFound = userDao.find(badUsername);

        assertNull(userFound);
        assertNotEquals(badUsername, testUser.getUsername());
    }

    @Test
    public void testClear() throws DataAccessException {
        userDao.clear();
        User clearedUser = userDao.find(testUser.getUsername());

        assertNull(clearedUser);
    }
}
