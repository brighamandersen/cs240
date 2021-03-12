package daos;

import models.Event;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

public class EventDaoTest {
    private Database db;
    private Event testEvent;
    private EventDao eventDao;

    @BeforeEach
    public void setUp() throws DataAccessException {
        db = new Database();

        testEvent = new Event("Biking_123A", "Gale", "Gale123A",
                35.9f, 140.1f, "Japan", "Ushiku",
                "Biking_Around", 2016);

        Connection conn = db.getConnection();
        db.clearTables();
        eventDao = new EventDao(conn);
    }

    @AfterEach
    public void tearDown() throws DataAccessException {
        db.closeConnection(false);
    }

    @Test
    public void testInsertPass() throws DataAccessException {
        eventDao.insert(testEvent);
        Event eventInserted = eventDao.findByEventId(testEvent.getEventID());

        assertNotNull(eventInserted);
        assertEquals(testEvent, eventInserted);
    }

    @Test
    public void testInsertFail() throws DataAccessException {
        eventDao.insert(testEvent);     // Will throw error for inserting duplicate
        assertThrows(DataAccessException.class, ()-> eventDao.insert(testEvent));
    }

    @Test
    public void testFindByEventIdPass() throws DataAccessException {
        eventDao.insert(testEvent);
        Event eventFound = eventDao.findByEventId("Biking_123A");

        assertNotNull(eventFound);
        assertEquals(eventFound, testEvent);
    }

    @Test
    public void testFindByEventIdFail() throws DataAccessException {
        eventDao.insert(testEvent);
        String badId = "bad_id";
        Event eventFound = eventDao.findByEventId(badId);

        assertNull(eventFound);
        assertNotEquals(badId, testEvent.getEventID());
    }

    @Test
    public void testFindByUsernamePass() throws DataAccessException {
        eventDao.insert(testEvent);
        Event eventFound = eventDao.findByUsername("Gale");

        assertNotNull(eventFound);
        assertEquals(eventFound, testEvent);
    }

    @Test
    public void testFindByUsernameFail() throws DataAccessException {
        eventDao.insert(testEvent);
        String badUsername = "bad_username";
        Event eventFound = eventDao.findByUsername(badUsername);

        assertNull(eventFound);
        assertNotEquals(badUsername, testEvent.getAssociatedUsername());
    }

    @Test
    public void testDeleteByUsername() throws DataAccessException {
        eventDao.insert(testEvent);
        String validUsername = "Gale";
        eventDao.deleteByUsername(validUsername);

        assertNull(eventDao.findByUsername(validUsername));
    }

    @Test
    public void testClear() throws DataAccessException {
        eventDao.clear();
        Event clearedEvent = eventDao.findByEventId(testEvent.getEventID());

        assertNull(clearedEvent);
    }
}
