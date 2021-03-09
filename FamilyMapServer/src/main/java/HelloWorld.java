import daos.DataAccessException;
import daos.Database;
import results.LoadResult;
import results.Result;

import java.sql.Connection;

import static utils.JsonUtils.deserializeJson;
import static utils.JsonUtils.serializeJson;

public class HelloWorld {
    public static void main(String[] args) throws DataAccessException {
        System.out.println("Hello world!\n");

//        Result result = new Result("Success!", null);
//        LoadResult loadResult = new LoadResult(1 ,2, 3);
        Result result = new Result("Failed! oh no");
        String jsonStr = serializeJson(result);

        System.out.println(jsonStr);

        Result javaObj = deserializeJson(jsonStr, Result.class);
        System.out.println(javaObj);


        Database database = new Database();
        Connection conn = database.getConnection();

        database.createTables();

        database.closeConnection(true);
    }
}
