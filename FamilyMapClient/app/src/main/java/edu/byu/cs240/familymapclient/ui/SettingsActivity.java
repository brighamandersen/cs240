package edu.byu.cs240.familymapclient.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.LinearLayout;

import java.util.Objects;

import edu.byu.cs240.familymapclient.R;
import edu.byu.cs240.familymapclient.model.DataCache;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Family Map: Settings");

        LinearLayout logoutDiv = findViewById(R.id.logoutDiv);
        logoutDiv.setOnClickListener(v -> logOut());

        SwitchCompat lifeStoryLinesSwitch = findViewById(R.id.lifeStoryLinesSwitch);
        lifeStoryLinesSwitch.setChecked(DataCache.getShowLifeStoryLines());
        lifeStoryLinesSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            DataCache.setShowLifeStoryLines(isChecked);
        });

        SwitchCompat familyTreeLinesSwitch = findViewById(R.id.familyTreeLinesSwitch);
        familyTreeLinesSwitch.setChecked(DataCache.getShowFamilyTreeLines());
        familyTreeLinesSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            DataCache.setShowFamilyTreeLines(isChecked);
        });

        SwitchCompat spouseLinesSwitch = findViewById(R.id.spouseLinesSwitch);
        spouseLinesSwitch.setChecked(DataCache.getShowSpouseLines());
        spouseLinesSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            DataCache.setShowSpouseLines(isChecked);
        });

        SwitchCompat fathersSideSwitch = findViewById(R.id.fathersSideSwitch);
        fathersSideSwitch.setChecked(DataCache.getShowFathersSide());
        fathersSideSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            DataCache.setShowFathersSide(isChecked);
        });

        SwitchCompat mothersSideSwitch = findViewById(R.id.mothersSideSwitch);
        mothersSideSwitch.setChecked(DataCache.getShowMothersSide());
        mothersSideSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            DataCache.setShowMothersSide(isChecked);
        });

        SwitchCompat maleEventsSwitch = findViewById(R.id.maleEventsSwitch);
        maleEventsSwitch.setChecked(DataCache.getShowMaleEvents());
        maleEventsSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            DataCache.setShowMaleEvents(isChecked);
        });

        SwitchCompat femaleEventsSwitch = findViewById(R.id.femaleEventsSwitch);
        femaleEventsSwitch.setChecked(DataCache.getShowFemaleEvents());
        femaleEventsSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            DataCache.setShowFemaleEvents(isChecked);
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

    private void logOut() {
        DataCache.clear();
        finish();
    }
}