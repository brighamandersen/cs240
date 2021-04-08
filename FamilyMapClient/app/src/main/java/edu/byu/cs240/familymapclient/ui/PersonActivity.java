package edu.byu.cs240.familymapclient.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import edu.byu.cs240.familymapclient.R;
import edu.byu.cs240.familymapclient.helpers.ExpandableListAdapter;

public class PersonActivity extends AppCompatActivity {

    ExpandableListAdapter expandableListAdapter;
    ExpandableListView expandableListView;
    List<String> headers; // = Arrays.asList("header1", "header2", "header3");
    HashMap<String, List<String>> headerChildren;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Family Map: Person Details");

        expandableListView = (ExpandableListView) findViewById(R.id.lifeEventsExpListView);

        prepareListData();

        expandableListAdapter = new ExpandableListAdapter(this, headers, headerChildren);

        expandableListView.setAdapter(expandableListAdapter);
    }

    /**
     * Makes up button functional (returns to MainActivity)
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
//            Intent intent = new Intent(this, MainActivity.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//            startActivity(intent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void prepareListData() {
        headers = new ArrayList<String>();
        headerChildren = new HashMap<String, List<String>>();

        // Adding child data
        headers.add("Top 250");
        headers.add("Now Showing");
        headers.add("Coming Soon..");

        // Adding child data
        List<String> top250 = new ArrayList<String>();
        top250.add("The Shawshank Redemption");
        top250.add("The Godfather");
        top250.add("The Godfather: Part II");
        top250.add("Pulp Fiction");
        top250.add("The Good, the Bad and the Ugly");
        top250.add("The Dark Knight");
        top250.add("12 Angry Men");

        List<String> nowShowing = new ArrayList<String>();
        nowShowing.add("The Conjuring");
        nowShowing.add("Despicable Me 2");
        nowShowing.add("Turbo");
        nowShowing.add("Grown Ups 2");
        nowShowing.add("Red 2");
        nowShowing.add("The Wolverine");

        List<String> comingSoon = new ArrayList<String>();
        comingSoon.add("2 Guns");
        comingSoon.add("The Smurfs 2");
        comingSoon.add("The Spectacular Now");
        comingSoon.add("The Canyons");
        comingSoon.add("Europa Report");

        headerChildren.put(headers.get(0), top250); // Header, Child data
        headerChildren.put(headers.get(1), nowShowing);
        headerChildren.put(headers.get(2), comingSoon);
    }
}