import daos.DataAccessException;
import data.DataGenerator;
import data.Location;

import java.io.IOException;

public class HelloWorld {
    public static void main(String[] args) throws DataAccessException, IOException {
        System.out.println("Hello world!\n");



        DataGenerator dataGenerator = new DataGenerator();
//        Location test = dataGenerator.getRandomLocation();

        System.out.println("test");
    }
}
