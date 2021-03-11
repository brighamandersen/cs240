package data;

import com.google.gson.Gson;
import models.Person;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.Random;

public class DataGenerator {
    private NameData femaleNames;
    private NameData maleNames;
    private NameData surnames;
    private LocationData locations;

    public DataGenerator() {
        try {

            Gson gson = new Gson();

            femaleNames = gson.fromJson(new FileReader("json/fnames.json"), NameData.class);
            maleNames = gson.fromJson(new FileReader("json/mnames.json"), NameData.class);
            surnames = gson.fromJson(new FileReader("json/snames.json"), NameData.class);
            locations = gson.fromJson(new FileReader("json/locations.json"), LocationData.class);
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
    }

    public static List<Person> generateAncestorData(int numGenerations) {
//        for (int i = 0; i < numGenerations; i++) {
//            generateFatherData();
//            generateMotherData();
//        }
        return null;
    }
//
//    Person mother = new Person("motherid", "mymother", "Mother", "Mary", "f",
//            "father1", "mother1", "spouse1");
//
//    Person father = new Person("fatherid", "myfather", "Mother", "Mary", "f",
//            "father1", "father1", "spouse1");

    private int getRandomNumber(int max) {
        Random random = new Random();
        int min = 0;

        return random.nextInt(max - min) + min;
    }

    private String getRandomFemaleName() {
        int max = femaleNames.getData().size();
        int randomIndex = getRandomNumber(max);

        return femaleNames.getData().get(randomIndex);
    }

    private String getRandomMaleName() {
        int max = maleNames.getData().size();
        int randomIndex = getRandomNumber(max);

        return maleNames.getData().get(randomIndex);
    }

    private String getRandomSurname() {
        int max = surnames.getData().size();
        int randomIndex = getRandomNumber(max);

        return surnames.getData().get(randomIndex);
    }

    private Location getRandomLocation() {
        int max = locations.getSize();
        int randomIndex = getRandomNumber(max);

        return locations.getData()[randomIndex];
    }
}
