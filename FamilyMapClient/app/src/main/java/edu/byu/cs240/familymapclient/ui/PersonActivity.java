package edu.byu.cs240.familymapclient.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import edu.byu.cs240.familymapclient.R;
import edu.byu.cs240.familymapclient.helpers.ExpandableListAdapter;
import edu.byu.cs240.familymapclient.model.DataCache;
import models.Event;
import models.Person;

import static edu.byu.cs240.familymapclient.helpers.StringUtils.stringifyFullName;
import static edu.byu.cs240.familymapclient.helpers.StringUtils.stringifyLifeEventDetails;
import static edu.byu.cs240.familymapclient.helpers.StringUtils.wordifyGender;

public class PersonActivity extends AppCompatActivity {

    private TextView firstNameTV;
    private TextView lastNameTV;
    private TextView genderTV;

    private String personID;
    private Person person;

    List<Event> lifeEvents;
//    List<Person>

    ExpandableListAdapter expandableListAdapter;
    ExpandableListView expandableListView;
    List<String> headers;
    HashMap<String, List<String>> headerChildren;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Family Map: Person Details");

        personID = getIntent().getStringExtra("PERSON_ID");
        person = DataCache.getPersons().get(personID);

        firstNameTV = findViewById(R.id.tvFirstName);
        firstNameTV.setText(person.getFirstName());

        lastNameTV = findViewById(R.id.tvLastName);
        lastNameTV.setText(person.getLastName());

        genderTV = findViewById(R.id.tvGender);
        genderTV.setText(wordifyGender(person.getGender()));

        // FIXME -- Customize this below

        expandableListView = (ExpandableListView) findViewById(R.id.lifeEventsExpListView);

        prepareListData();

        expandableListAdapter = new ExpandableListAdapter(this, headers, headerChildren);

        expandableListView.setAdapter(expandableListAdapter);

        expandableListView.setOnChildClickListener((parent, v, groupPosition, childPosition, id) -> {
            goToEvent();
            return false;
        });

//        for (int i = 0; i < expandableListView.getChildCount(); i++) {
//            TextView tv = expandableListView.getChildAt(i).findViewById(R.id.lblListItem);
//            tv.setTag("hello");
//        }

//        TextView listItem = findViewById(R.id.lblListItem);
//        Drawable locationIcon = new IconDrawable(this, FontAwesomeIcons.fa_map_marker)
//                .colorRes(R.color.black).sizeDp(40);;
//        listItem.setCompoundDrawables(locationIcon, null, null, null);
    }

    /**
     * Makes up button functional (returns to MainActivity)
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void prepareListData() {
        headers = new ArrayList<String>();
        headerChildren = new HashMap<String, List<String>>();

        // Adding headers
        headers.add("LIFE EVENTS");
        headers.add("FAMILY");

        // Adding life events
        List<String> lifeEventItems = addLifeEventItems(personID);

        // Adding family members
        List<String> familyItems = addFamilyItems();

        headerChildren.put(headers.get(0), lifeEventItems);
        headerChildren.put(headers.get(1), familyItems);
    }

    private void goToEvent() {
        String EVENT_ID_HARDCODED = "HARD_CODED";

        Intent intent = new Intent(this, EventActivity.class);
        intent.putExtra("EVENT_ID", EVENT_ID_HARDCODED);
        finish();
        startActivity(intent);
    }

    private List<String> addLifeEventItems(String personID) {
        List<String> items = new ArrayList<>();
        lifeEvents = DataCache.getPersonEvents().get(personID);

        for (Event event : lifeEvents) {
            Person person = DataCache.getPersons().get(event.getPersonID());
            items.add(stringifyLifeEventDetails(event, person));
        }

        return items;
    }

    private List<String> addFamilyItems() {
        List<String> items = new ArrayList<>();

        // Find relatives besides the current person
        for (Person individual : DataCache.getPersons().values()) {
            if (individual.getAssociatedUsername().equals(person.getAssociatedUsername()) &&
                    !individual.getPersonID().equals(personID)) {
                if (!stringifyRelation(individual).equals("")) {
                    items.add(stringifyFullName(individual) + stringifyRelation(individual));
                }
            }
        }

        return items;
    }

    private String stringifyRelation(Person relative) {
        if (person.getFatherID() != null && relative.getPersonID() != null &&
                person.getFatherID().equals(relative.getPersonID())) return "\nFather";
        if (person.getMotherID() != null && relative.getPersonID() != null &&
                person.getMotherID().equals(relative.getPersonID())) return "\nMother";
        if (person.getSpouseID() != null && relative.getPersonID() != null &&
                person.getSpouseID().equals(relative.getPersonID())) return "\nSpouse";
        if ((relative.getFatherID() != null && relative.getFatherID().equals(personID)) ||
                (relative.getMotherID() != null && relative.getMotherID().equals(personID))) {
            return "\nChild";
        }
        return "";
    }
}