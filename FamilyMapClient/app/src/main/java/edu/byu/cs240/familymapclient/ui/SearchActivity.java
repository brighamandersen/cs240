package edu.byu.cs240.familymapclient.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import edu.byu.cs240.familymapclient.R;
import edu.byu.cs240.familymapclient.model.DataCache;
import models.Event;
import models.Person;

import static edu.byu.cs240.familymapclient.helpers.IconUtils.getEventIcon;
import static edu.byu.cs240.familymapclient.helpers.IconUtils.getGenderIcon;
import static edu.byu.cs240.familymapclient.helpers.StringUtils.stringifyFullName;
import static edu.byu.cs240.familymapclient.helpers.StringUtils.stringifyLifeEventDetails;

public class SearchActivity extends AppCompatActivity {

    private static final int PERSON_ITEM_VIEW_TYPE = 0;
    private static final int EVENT_ITEM_VIEW_TYPE = 1;

    private SearchView searchBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Family Map: Search");

        RecyclerView recyclerView = findViewById(R.id.searchRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        searchBar = findViewById(R.id.etSearchBar);
        searchBar.setIconified(false);
        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                List<Person> persons = new ArrayList<>();
                List<Event> events = new ArrayList<>();

                for (Person person : DataCache.getPersons().values()) {
                    if (containsIgnoreCase(person.getFirstName(), newText) || containsIgnoreCase(person.getLastName(), newText)) {
                        persons.add(person);
                    }
                }

                for (Event event : DataCache.getEvents().values()) {
                    if (containsIgnoreCase(event.getCountry(), newText) ||
                            containsIgnoreCase(event.getCity(), newText) ||
                            containsIgnoreCase(event.getEventType(), newText) ||
                            containsIgnoreCase(String.valueOf(event.getYear()), newText)) {
                        events.add(event);
                    }
                }

                SearchAdapter searchAdapter = new SearchAdapter(persons, events);
                recyclerView.setAdapter(searchAdapter);

                return false;
            }
        });
    }

    private boolean containsIgnoreCase(String data, String query) {
        return data.toLowerCase().contains(query.toLowerCase());
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

            itemData = view.findViewById(R.id.listItem);
        }

        private void bind(Person person) {
            this.person = person;
            itemData.setText(stringifyFullName(person));

            Drawable icon = getGenderIcon(SearchActivity.this, person.getGender());
            itemData.setCompoundDrawables(icon, null, null, null);
        }

        private void bind(Event event) {
            this.event = event;
            Person person = DataCache.getPersons().get(event.getPersonID());
            itemData.setText(stringifyLifeEventDetails(event, person));

            Drawable icon = getEventIcon(SearchActivity.this);
            itemData.setCompoundDrawables(icon, null, null, null);
        }

        @Override
        public void onClick(View v) {
            if (viewType == PERSON_ITEM_VIEW_TYPE) {    // Person
                Intent intent = new Intent(SearchActivity.this, PersonActivity.class);
                intent.putExtra("PERSON_ID", person.getPersonID());
                startActivity(intent);
            } else {    // Event
                Intent intent = new Intent(SearchActivity.this, EventActivity.class);
                intent.putExtra("EVENT_ID", event.getEventID());
                startActivity(intent);
            }
        }
    }
}