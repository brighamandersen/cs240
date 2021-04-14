package edu.byu.cs240.familymapclient.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;
import java.util.Objects;

import edu.byu.cs240.familymapclient.R;
import models.Event;
import models.Person;

public class SearchActivity extends AppCompatActivity {

    private static final int PERSON_ITEM_VIEW_TYPE = 0;
    private static final int EVENT_ITEM_VIEW_TYPE = 1;

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

//    public class SearchAdapter extends RecyclerView.Adapter<SearchViewHolder> {
//        private final List<Person> persons;
//        private final List<Event> events;
//
//        SearchAdapter(List<Person> persons, List<Event> events) {
//            this.persons = persons;
//            this.events = events;
//        }
//
//        @Override
//        public int getItemViewType(int position) {
//            return position < persons.size() ? PERSON_ITEM_VIEW_TYPE : EVENT_ITEM_VIEW_TYPE;
//        }
//
//        @NonNull
//        @Override
//        public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//            View view;
//
//            if (viewType == PERSON_ITEM_VIEW_TYPE) {
//                view = getLayoutInflater().inflate(R.layout.list_item);
//            }
//        }
//
//
//    }
//
//    public class SearchViewHolder extends RecyclerView.ViewHolder {
//
//        private static final int PERSON_VIEW_TYPE = 0;
//        private static final int EVENT_VIEW_TYPE = 1;
//
//        private final int viewType;
//
//        SearchViewHolder(View view, int viewType) {
//            super(view);
//            this.viewType = viewType;
//
//            view.setOnClickListener(v -> {
//
//            });
//
//            if (viewType == PERSON_VIEW_TYPE) {     // Person
//
//            } else {    // Event
//
//            }
//        }
//    }
}