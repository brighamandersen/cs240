package edu.byu.cs240.familymapclient.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import edu.byu.cs240.familymapclient.R;
import edu.byu.cs240.familymapclient.model.DataCache;
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

        RecyclerView recyclerView = findViewById(R.id.searchRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        searchBar = findViewById(R.id.etSearchBar);
        searchBar.addTextChangedListener(searchQueryWatcher);

        List<Person> persons = new ArrayList<>(DataCache.getPersons().values());
        List<Event> events = new ArrayList<>(DataCache.getEvents().values());

        SearchAdapter searchAdapter = new SearchAdapter(persons, events);
        recyclerView.setAdapter(searchAdapter);
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

    private class SearchAdapter extends RecyclerView.Adapter<SearchViewHolder> {
        private final List<Person> persons;
        private final List<Event> events;

        SearchAdapter(List<Person> persons, List<Event> events) {
            this.persons = persons;
            this.events = events;
        }

        @Override
        public int getItemViewType(int position) {
            return position < persons.size() ? PERSON_ITEM_VIEW_TYPE : EVENT_ITEM_VIEW_TYPE;
        }

        @NonNull
        @Override
        public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.list_item, parent, false);

            return new SearchViewHolder(view, viewType);
        }

        @Override
        public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
            if(position < persons.size()) {
                holder.bind(persons.get(position));
            } else {
                holder.bind(events.get(position - persons.size()));
            }
        }

        @Override
        public int getItemCount() {
            return persons.size() + events.size();
        }
    }

    public class SearchViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView itemData;

        private final int viewType;
        private Person person;
        private Event event;

        SearchViewHolder(View view, int viewType) {
            super(view);
            this.viewType = viewType;

            view.setOnClickListener(this);

            itemData = itemView.findViewById(R.id.listItem);
        }

        private void bind(Person person) {
            this.person = person;
            itemData.setText(person.getFirstName());    // FIXME
        }

        private void bind(Event event) {
            this.event = event;
            itemData.setText(event.getEventType());     // FIXME
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            if (viewType == PERSON_ITEM_VIEW_TYPE) {    // Person
                intent.putExtra("PERSON_ID", person.getPersonID());
            } else {    // Event
                intent.putExtra("EVENT_ID", event.getEventID());
            }
            startActivity(intent);
        }
    }
}