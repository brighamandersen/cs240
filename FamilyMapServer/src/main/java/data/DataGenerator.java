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


    public Event generatePersonalEventData(String associatedUsername, String personId) {
        String eventId = UUID.randomUUID().toString();
        Location location = getRandomLocation();
        String eventType = "birth";
        int birthYear = getRandomNumber(2000, 2005);
        return new Event(eventId, associatedUsername, personId, location.getLatitude(),
                location.getLongitude(), location.getCountry(), location.getCity(), eventType, birthYear);
    }

    public PersonEventData generateParentData(int numGenerations, String fatherId, String motherId,
                                              String childUsername, int childBirthYear) {
        if (numGenerations == 0) {
            List<Person> emptyPersons = new ArrayList<>();
            List<Event> emptyEvents = new ArrayList<>();
            return new PersonEventData(emptyPersons, emptyEvents);
        }

        // Generate father and mother person objects
        String spouseId = UUID.randomUUID().toString();
        Person father = generateFather(fatherId, childUsername, spouseId);
        Person mother = generateMother(motherId, childUsername, spouseId);

        List<Person> persons = new ArrayList<>();
        persons.add(father);
        persons.add(mother);

        // Generate event data for father and mother
        Event fatherBirth = generateBirth(childUsername, fatherId, childBirthYear);
        Event motherBirth = generateBirth(childUsername, motherId, childBirthYear);
        int coupleMarriageYear = generateMarriageYear(childBirthYear);
        Event fatherMarriage = generateMarriage(childUsername, fatherId, coupleMarriageYear);
        Event motherMarriage = generateMarriage(childUsername, motherId, coupleMarriageYear);
        Event fatherDeath = generateDeath(childUsername, fatherId, childBirthYear);
        Event motherDeath = generateDeath(childUsername, motherId, childBirthYear);

        List<Event> events = new ArrayList<>();
        events.add(fatherBirth);
        events.add(motherBirth);
        events.add(fatherMarriage);
        events.add(motherMarriage);
        events.add(fatherDeath);
        events.add(motherDeath);

            // Recurse on father for number of generations
        PersonEventData fatherSide = generateParentData(numGenerations - 1, father.getFatherId(),
                father.getMotherId(), childUsername, fatherBirth.getYear());
        // Recurse on mother for number of generations
        PersonEventData motherSide = generateParentData(numGenerations - 1, mother.getFatherId(),
                mother.getMotherId(), childUsername, motherBirth.getYear());

        persons.addAll(fatherSide.getPersons());
        persons.addAll(motherSide.getPersons());
        events.addAll(fatherSide.getEvents());
        events.addAll(motherSide.getEvents());


        return new PersonEventData(persons, events);
    }

    private Person generateFather(String personId, String associatedUsername, String spouseId) {
        String fatherId = UUID.randomUUID().toString();
        String motherId = UUID.randomUUID().toString();
        return new Person(personId, associatedUsername, getRandomMaleName(), getRandomSurname(), "m",
                fatherId, motherId, spouseId);
    }

    private Person generateMother(String personId, String associatedUsername, String spouseId) {
        String fatherId = UUID.randomUUID().toString();
        String motherId = UUID.randomUUID().toString();
        return new Person(personId, associatedUsername, getRandomFemaleName(), getRandomSurname(), "f",
                fatherId, motherId, spouseId);
    }

    private Event generateBirth(String associatedUsername, String personId, int childBirthYear) {
        String eventId = UUID.randomUUID().toString();
        Location location = getRandomLocation();
        int birthYear = childBirthYear - getRandomNumber(18, 23);

        return new Event(eventId, associatedUsername, personId, location.getLatitude(), location.getLongitude(),
                location.getCountry(), location.getCity(), "birth", birthYear);
    }

    private int generateMarriageYear(int childBirthYear) {
        return childBirthYear - getRandomNumber(1, 5);
    }

    private Event generateMarriage(String associatedUsername, String personId, int coupleMarriageYear) {
        String eventId = UUID.randomUUID().toString();
        Location location = getRandomLocation();

        return new Event(eventId, associatedUsername, personId, location.getLatitude(), location.getLongitude(),
                location.getCountry(), location.getCity(), "marriage", coupleMarriageYear);
    }

    private Event generateDeath(String associatedUsername, String personId, int childBirthYear) {
        String eventId = UUID.randomUUID().toString();
        Location location = getRandomLocation();
        int deathYear = childBirthYear + getRandomNumber(40, 60);

        return new Event(eventId, associatedUsername, personId, location.getLatitude(), location.getLongitude(),
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
