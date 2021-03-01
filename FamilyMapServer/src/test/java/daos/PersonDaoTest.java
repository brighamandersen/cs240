package daos;

import models.Person;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

public class PersonDaoTest {
    private Database db;
    private Person testPerson;
    private PersonDao personDao;

    @BeforeEach
    public void setUp() throws DataAccessException {
        db = new Database();

        testPerson = new Person("person_id14", "johndoe", "John",
                "Doe", "m", "person_id56", "person_id568",
                "person_id432");

        Connection conn = db.getConnection();
        db.clearTables();
        personDao = new PersonDao(conn);
    }

    @AfterEach
    public void tearDown() throws DataAccessException {
        db.closeConnection(false);
    }

    @Test
    public void testInsertPass() throws DataAccessException {
        personDao.insert(testPerson);
        Person personInserted = personDao.findByPersonID(testPerson.getPersonId());

        assertNotNull(personInserted);
        assertEquals(testPerson, personInserted);
    }

    @Test
    public void testInsertFail() throws DataAccessException {
        personDao.insert(testPerson);     // Will throw error for inserting duplicate
        assertThrows(DataAccessException.class, ()-> personDao.insert(testPerson));
    }

    @Test
    public void testFindByPersonIdPass() throws DataAccessException {
        personDao.insert(testPerson);
        Person personFound = personDao.findByPersonID("person_id14");

        assertNotNull(personFound);
        assertEquals(personFound, testPerson);
    }

    @Test
    public void testFindByPersonIdFail() throws DataAccessException {
        personDao.insert(testPerson);
        String badId = "bad_id";
        Person personFound = personDao.findByPersonID(badId);

        assertNull(personFound);
        assertNotEquals(badId, testPerson.getPersonId());
    }

    @Test
    public void testFindByUsernamePass() throws DataAccessException {
        personDao.insert(testPerson);
        Person personFound = personDao.findByUsername("johndoe");

        assertNotNull(personFound);
        assertEquals(personFound, testPerson);
    }

    @Test
    public void testFindByUsernameFail() throws DataAccessException {
        personDao.insert(testPerson);
        String badUsername = "bad_username";
        Person personFound = personDao.findByUsername(badUsername);

        assertNull(personFound);
        assertNotEquals(badUsername, testPerson.getAssociatedUsername());
    }

    @Test
    public void testDeleteByUsername() throws DataAccessException {
        personDao.insert(testPerson);
        String validUsername = "johndoe";
        personDao.deleteByUsername(validUsername);

        assertNull(personDao.findByUsername(validUsername));
    }

    @Test
    public void testClear() throws DataAccessException {
        personDao.clear();
        Person clearedPerson = personDao.findByPersonID(testPerson.getPersonId());

        assertNull(clearedPerson);
    }
}
