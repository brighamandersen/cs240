package data;

import com.google.gson.Gson;
import models.Event;
import models.Person;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class DataGenerator {
    private NameData femaleNames;
    private NameData maleNames;
    private NameData surnames;
    private LocationData locations;
    private final int ZERO = 0;

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


    public Event generateOwnBirth(String associatedUsername, String personID) {
        String eventID = UUID.randomUUID().toString();
        Location location = getRandomLocation();
        String eventType = "birth";
        int birthYear = getRandomNumber(2000, 2005);
        return new Event(eventID, associatedUsername, personID, location.getLatitude(),
                location.getLongitude(), location.getCountry(), location.getCity(), eventType, birthYear);
    }

    public PersonEventData generateParentData(int numGenerations, String fatherID, String motherID,
                                              String childUsername, int childBirthYear) {
        if (numGenerations == 0) {
            List<Person> emptyPersons = new ArrayList<>();
            List<Event> emptyEvents = new ArrayList<>();
            return new PersonEventData(emptyPersons, emptyEvents);
        }

        // Generate father and mother person objects
        Person father = generateFather(fatherID, childUsername, motherID);
        Person mother = generateMother(motherID, childUsername, fatherID);

        List<Person> persons = new ArrayList<>();
        persons.add(father);
        persons.add(mother);

        // Generate event data for father and mother
        Event fatherBirth = generateBirth(childUsername, fatherID, childBirthYear);
        Event motherBirth = generateBirth(childUsername, motherID, childBirthYear);
        int coupleMarriageYear = generateMarriageYear(childBirthYear);
        Location marriageLocation = getRandomLocation();
        Event fatherMarriage = generateMarriage(childUsername, fatherID, marriageLocation, coupleMarriageYear);
        Event motherMarriage = generateMarriage(childUsername, motherID, marriageLocation, coupleMarriageYear);
        Event fatherDeath = generateDeath(childUsername, fatherID, childBirthYear);
        Event motherDeath = generateDeath(childUsername, motherID, childBirthYear);

        List<Event> events = new ArrayList<>();
        events.add(fatherBirth);
        events.add(motherBirth);
        events.add(fatherMarriage);
        events.add(motherMarriage);
        events.add(fatherDeath);
        events.add(motherDeath);

        // If last generation, don't have parents for the father and mother
        if (numGenerations == 1) {
            father.setFatherID(null);
            father.setMotherID(null);

            mother.setFatherID(null);
            mother.setMotherID(null);
        }

        // Recurse on father for number of generations
        PersonEventData fatherSide = generateParentData(numGenerations - 1, father.getFatherID(),
                father.getMotherID(), childUsername, fatherBirth.getYear());
        // Recurse on mother for number of generations
        PersonEventData motherSide = generateParentData(numGenerations - 1, mother.getFatherID(),
                mother.getMotherID(), childUsername, motherBirth.getYear());

        persons.addAll(fatherSide.getPersons());
        persons.addAll(motherSide.getPersons());
        events.addAll(fatherSide.getEvents());
        events.addAll(motherSide.getEvents());


        return new PersonEventData(persons, events);
    }

    private Person generateFather(String personID, String associatedUsername, String spouseID) {
        String fatherID = UUID.randomUUID().toString();
        String motherID = UUID.randomUUID().toString();
        return new Person(personID, associatedUsername, getRandomMaleName(), getRandomSurname(), "m",
                fatherID, motherID, spouseID);
    }

    private Person generateMother(String personID, String associatedUsername, String spouseID) {
        String fatherID = UUID.randomUUID().toString();
        String motherID = UUID.randomUUID().toString();
        return new Person(personID, associatedUsername, getRandomFemaleName(), getRandomSurname(), "f",
                fatherID, motherID, spouseID);
    }

    private Event generateBirth(String associatedUsername, String personID, int childBirthYear) {
        String eventID = UUID.randomUUID().toString();
        Location location = getRandomLocation();
        int birthYear = childBirthYear - getRandomNumber(18, 23);

        return new Event(eventID, associatedUsername, personID, location.getLatitude(), location.getLongitude(),
                location.getCountry(), location.getCity(), "birth", birthYear);
    }

    private int generateMarriageYear(int childBirthYear) {
        return childBirthYear - getRandomNumber(1, 5);
    }

    private Event generateMarriage(String associatedUsername, String personID,
                                   Location location, int year) {
        String eventID = UUID.randomUUID().toString();

        return new Event(eventID, associatedUsername, personID, location.getLatitude(), location.getLongitude(),
                location.getCountry(), location.getCity(), "marriage", year);
    }

    private Event generateDeath(String associatedUsername, String personID, int childBirthYear) {
        String eventID = UUID.randomUUID().toString();
        Location location = getRandomLocation();
        int deathYear = childBirthYear + getRandomNumber(40, 60);

        return new Event(eventID, associatedUsername, personID, location.getLatitude(), location.getLongitude(),
                location.getCountry(), location.getCity(), "death", deathYear);
    }

    private int getRandomNumber(int min, int max) {
        Random random = new Random();

        return random.nextInt(max - min) + min;
    }

    private String getRandomFemaleName() {
        int max = femaleNames.getData().size();
        int randomIndex = getRandomNumber(ZERO, max);

        return femaleNames.getData().get(randomIndex);
    }

    private String getRandomMaleName() {
        int max = maleNames.getData().size();
        int randomIndex = getRandomNumber(ZERO, max);

        return maleNames.getData().get(randomIndex);
    }

    private String getRandomSurname() {
        int max = surnames.getData().size();
        int randomIndex = getRandomNumber(ZERO, max);

        return surnames.getData().get(randomIndex);
    }

    private Location getRandomLocation() {
        int max = locations.getSize();
        int randomIndex = getRandomNumber(ZERO, max);

        return locations.getData()[randomIndex];
    }
}
