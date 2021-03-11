package services;

import results.Result;

import java.nio.file.Path;
import java.nio.file.Paths;

import static utils.StringUtils.countSlashes;
import static utils.StringUtils.urlToParamStr;

/**
 * Implements the fill functionality of the server's web API.
 */
public class FillService {
    /**
     * Populates the server's database with generated data for the specified username.
     * @param urlPath URL path
     * @return Fill response data
     */
    public Result fill(Path urlPath) {
        String username;
        int numGenerations = 4;

        if (urlPath.getNameCount() > 3 || urlPath.getNameCount() < 2) {
            return new Result("Invalid username or generations parameter");
        }

        username = String.valueOf(urlPath.getName(1));

        if (urlPath.getNameCount() == 3) {
            numGenerations = Integer.parseInt(String.valueOf(urlPath.getName(2)));
        }

        // FIXME get ancestor data

        return null;
    }
}
