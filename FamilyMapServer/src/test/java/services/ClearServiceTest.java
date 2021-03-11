package services;

import daos.DataAccessException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import requests.RegisterRequest;
import results.RegisterResult;
import results.Result;

import static org.junit.jupiter.api.Assertions.*;

class ClearServiceTest {
    private ClearService clearService;

    @BeforeEach
    void setUp() throws DataAccessException {
        clearService = new ClearService();
    }

    @Test
    void testClear() throws DataAccessException {
        Result result = clearService.clear();

        assertTrue(result.isSuccess());
        assertEquals("Clear succeeded.", result.getMessage());
    }

    @Test
    void testDoubleClear() throws DataAccessException {
        Result result = clearService.clear();
        result = clearService.clear();

        assertTrue(result.isSuccess());
        assertEquals("Clear succeeded.", result.getMessage());
    }
}