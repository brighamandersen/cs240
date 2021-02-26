package daos;

import org.junit.jupiter.api.*;

public class DatabaseTest {
    private Database db;

    @BeforeEach
    public void setUp() throws DataAccessException {
        db = new Database();
        db.openConnection();
        db.createTables();
//        db.fillDictionary();
    }

    @AfterEach
    public void tearDown() throws DataAccessException {
        db.closeConnection(false);
        db = null;
    }

    @Test
    public void testLoadDictionaryFromDatabase() throws DataAccessException {
//        Set<String> words = db.loadDictionary();
//
//        assertEquals(4, words.size());
//        assertTrue(words.contains("fred"));
//        assertTrue(words.contains("barney"));
//        assertTrue(words.contains("betty"));
//        assertTrue(words.contains("wilma"));
    }
}

