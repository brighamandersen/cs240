package services;

import requests.LoadRequest;
import results.LoadResult;

/**
 * Implements the load functionality of the server's web API.
 */
public class LoadService {
    /**
     * Clears all data from the database (just like the /clear API), and then loads the posted user, person, and event data into the database.
     * @param r Load request data
     * @return Load response data
     */
    public LoadResult load(LoadRequest r) {
        return null;
    }
}