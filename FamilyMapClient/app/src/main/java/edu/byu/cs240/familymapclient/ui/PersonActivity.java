package edu.byu.cs240.familymapclient.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import edu.byu.cs240.familymapclient.R;
import edu.byu.cs240.familymapclient.model.DataCache;
import models.Event;
import models.Person;

import static edu.byu.cs240.familymapclient.helpers.IconUtils.getGenderIcon;
import static edu.byu.cs240.familymapclient.helpers.IconUtils.getEventIcon;
import static edu.byu.cs240.familymapclient.helpers.StringUtils.stringifyFullName;
import static edu.byu.cs240.familymapclient.helpers.StringUtils.stringifyLifeEventDetails;
import static edu.byu.cs240.familymapclient.helpers.StringUtils.wordifyGender;

public class PersonActivity extends AppCompatActivity {

    private static final int LIFE_EVENT_POS = 0;
    private static final int FAMILY_POS = 1;

    private TextView firstNameTV;
    private TextView lastNameTV;
    private TextView genderTV;

    private String personID;
    private Person person;

    List<Event> lifeEvents = new ArrayList<>();
    List<Person> relatives = new ArrayList<>();

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

        expandableListView = (ExpandableListView) findViewById(R.id.lifeEventsExpListView);

        prepareListData();

        expandableListAdapter = new ExpandableListAdapter(this, headers, headerChildren);

        expandableListView.setAdapter(expandableListAdapter);

        expandableListView.setOnChildClickListener((parent, v, groupPosition, childPosition, id) -> {
            if (groupPosition == LIFE_EVENT_POS) {
                onLifeEventClick(childPosition);
            } else {
                onFamilyClick(childPosition);
            }
            return false;
        });
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

        headerChildren.put(headers.get(LIFE_EVENT_POS), lifeEventItems);
        headerChildren.put(headers.get(FAMILY_POS), familyItems);
    }

    private void onLifeEventClick(int childPosition) {
        Event event = lifeEvents.get(childPosition);

        Intent intent = new Intent(this, EventActivity.class);
        intent.putExtra("EVENT_ID", event.getEventID());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        finish();
        startActivity(intent);
    }

    private void onFamilyClick(int childPosition) {
        Person person = relatives.get(childPosition);

        Intent intent = new Intent(this, PersonActivity.class);
        intent.putExtra("PERSON_ID", person.getPersonID());
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
                    relatives.add(individual);
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

    private class ExpandableListAdapter extends BaseExpandableListAdapter {

        private Context _context;
        private List<String> _listDataHeader; // header titles
        // child data in format of header title, child title
        private HashMap<String, List<String>> _listDataChild;

        public ExpandableListAdapter(Context context, List<String> listDataHeader,
                                     HashMap<String, List<String>> listChildData) {
            this._context = context;
            this._listDataHeader = listDataHeader;
            this._listDataChild = listChildData;
        }

        @Override
        public Object getChild(int groupPosition, int childPosititon) {
            return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                    .get(childPosititon);
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public View getChildView(int groupPosition, final int childPosition,
                                 boolean isLastChild, View convertView, ViewGroup parent) {

            final String childText = (String) getChild(groupPosition, childPosition);

            if (convertView == null) {
                LayoutInflater infalInflater = (LayoutInflater) this._context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater.inflate(R.layout.list_item, null);
            }

            TextView txtListChild = (TextView) convertView
                    .findViewById(R.id.listItem);

            Drawable icon = assignIcon(groupPosition, childPosition);
            txtListChild.setCompoundDrawables(icon, null, null, null);

            txtListChild.setText(childText);
            return convertView;
        }

        private Drawable assignIcon(int groupPosition, int childPosition) {
            if (groupPosition == LIFE_EVENT_POS) {      // Life event
                return getEventIcon(PersonActivity.this);
            }       // Family
            return getGenderIcon(PersonActivity.this, relatives.get(childPosition).getGender());
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                    .size();
        }

        @Override
        public Object getGroup(int groupPosition) {
            return this._listDataHeader.get(groupPosition);
        }

        @Override
        public int getGroupCount() {
            return this._listDataHeader.size();
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded,
                                 View convertView, ViewGroup parent) {
            String headerTitle = (String) getGroup(groupPosition);
            if (convertView == null) {
                LayoutInflater infalInflater = (LayoutInflater) this._context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater.inflate(R.layout.list_group, null);
            }

            TextView lblListHeader = (TextView) convertView
                    .findViewById(R.id.lblListHeader);
            lblListHeader.setTypeface(null, Typeface.BOLD);
            lblListHeader.setText(headerTitle);

            return convertView;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }
    }
}