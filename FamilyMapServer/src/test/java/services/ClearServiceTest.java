package services;

import daos.DataAccessException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import requests.RegisterRequest;
import results.Result;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ClearServiceTest {
    private ClearService clearService;

    @BeforeEach
    void setUp() {
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