package edu.byu.cs240.familymapclient.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import java.util.Objects;

import edu.byu.cs240.familymapclient.R;
import edu.byu.cs240.familymapclient.model.DataCache;

public class SettingsActivity extends AppCompatActivity {

    // FIXME -- RETURNS A BOOLEAN TO TELL MAIN ACTIVITY IF SETTINGS CHANGED

    private LinearLayout logoutDiv;
    private SwitchCompat spouseLinesSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Family Map: Settings");

        logoutDiv = findViewById(R.id.logoutDiv);
        logoutDiv.setOnClickListener(v -> logOut());

        spouseLinesSwitch = findViewById(R.id.spouseLinesSwitch);
        spouseLinesSwitch.setChecked(DataCache.getShowSpouseLines());
        spouseLinesSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            DataCache.setShowSpouseLines(isChecked);
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