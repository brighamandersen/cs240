package services;

import data.DataGenerator;
import data.PersonEventData;
import results.Result;

import java.nio.file.Path;
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

            if (numGenerations < 0) {
                return new Result("Invalid username or generations parameter");
            }
        }

//        DataGenerator dataGenerator = new DataGenerator();
//        PersonEventData personEventData = dataGenerator.generateParentData(numGenerations);
        return null;
//        return new Result("Successfully added " + personEventData.getPersonsSize() + " persons and "
//                + personEventData.getEventsSize() + " events to the database.", true);
    }
}
