package utils;

import daos.AuthTokenDao;
import daos.DataAccessException;
import daos.Database;

import java.sql.Connection;

public class HeaderUtils {
    public static boolean isExistingToken(String token) throws DataAccessException {
        Database db = new Database();

        Connection conn = db.getConnection();
        AuthTokenDao authTokenDao = new AuthTokenDao(conn);

        if (authTokenDao.find(token) == null) {
            db.closeConnection(false);
            return false;
        }

        db.closeConnection(false);
        return true;
    }

}
