package services;

import daos.DataAccessException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import requests.LoadRequest;
import results.LoadResult;

import static org.junit.jupiter.api.Assertions.*;

class LoadServiceTest {
    private LoadService loadService;

    @BeforeEach
    void setUp() throws DataAccessException {
        ClearService clearService = new ClearService();
        clearService.clear();

        loadService = new LoadService();
    }

    @Test
    void testLoadPass() {
    }

    /**
     * Pass in null values
     */
    @Test
    void testLoadFail() throws DataAccessException {
        LoadRequest badLoadRequest = new LoadRequest(null, null, null);
        LoadResult loadResult = loadService.load(badLoadRequest);

        assertFalse(loadResult.isSuccess());
        assertEquals("Error: Invalid request data (missing values, invalid values, etc.)",
                loadResult.getMessage());
    }
}