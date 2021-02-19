package services;

import requests.LoadRequest;
import results.LoadResult;

/**
 * Implements the load functionality of the server's web API.
 * Clears all data from the database (just like the /clear API), and then loads the posted user, person, and event data into the database.
 */
public class LoadService {
    LoadResult load(LoadRequest r) {
        return null;
    }
}