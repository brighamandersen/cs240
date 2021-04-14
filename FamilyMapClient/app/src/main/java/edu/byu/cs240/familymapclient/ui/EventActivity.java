package edu.byu.cs240.familymapclient.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import java.util.Objects;

import edu.byu.cs240.familymapclient.R;

public class EventActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        // Get event ID from person activity
        String eventID = getIntent().getStringExtra("EVENT_ID");

        // Embed map fragment (pass in event ID)
        Bundle bundle = new Bundle();
        bundle.putString("EventIDKey", eventID);
        MapFragment mapFragment = new MapFragment();
        mapFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().add(R.id.eventActivityFrameLayout, mapFragment).commit();
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
}