package edu.byu.cs240.familymapclient.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Objects;

import edu.byu.cs240.familymapclient.R;

public class SearchActivity extends AppCompatActivity {

    private EditText searchBar;
    private String searchText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Family Map: Search");

        searchBar = findViewById(R.id.etSearchBar);
        searchBar.addTextChangedListener(searchQueryWatcher);
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

    TextWatcher searchQueryWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            searchText = s.toString();
        }

        @Override
        public void afterTextChanged(Editable s) {
            updateResults();
        }
    };

    private void updateResults() {
        Toast.makeText(this, "Hello", Toast.LENGTH_SHORT).show();

    }
}