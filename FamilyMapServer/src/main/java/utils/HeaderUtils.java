package utils;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import daos.AuthTokenDao;
import daos.DataAccessException;
import daos.Database;

import java.sql.Connection;

public class HeaderUtils {
    public static boolean checkAuth(HttpExchange exchange) throws DataAccessException {
        Headers reqHeaders = exchange.getRequestHeaders();

        if (!reqHeaders.containsKey("Authorization")) {
            return false;
        }
        String reqToken = reqHeaders.getFirst("Authorization");

        Database db = new Database();
        Connection conn = db.getConnection();
        AuthTokenDao authTokenDao = new AuthTokenDao(conn);

        if (authTokenDao.find(reqToken) == null) {
            return false;
        }

        return true;
    }

}
