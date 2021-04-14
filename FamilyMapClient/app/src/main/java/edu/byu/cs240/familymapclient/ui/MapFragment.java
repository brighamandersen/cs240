package edu.byu.cs240.familymapclient.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;

import java.util.ArrayList;
import java.util.List;

import edu.byu.cs240.familymapclient.R;
import edu.byu.cs240.familymapclient.model.DataCache;
import models.Event;
import models.Person;

import static edu.byu.cs240.familymapclient.helpers.IconUtils.getGenderIcon;
import static edu.byu.cs240.familymapclient.helpers.StringUtils.stringifyBarDetails;
import static edu.byu.cs240.familymapclient.helpers.StringUtils.stringifyFullLocation;

public class MapFragment extends Fragment {

    private GoogleMap gMap;
    private TextView mapDetailBar;

    private String eventID;
    private Event currentEvent;

    private List<Polyline> lines = new ArrayList<>();

    private final OnMapReadyCallback callback = new OnMapReadyCallback() {
        @Override
        public void onMapReady(GoogleMap googleMap) {
            gMap = googleMap;
            gMap.getUiSettings().setMapToolbarEnabled(false);

            addEventMarkers();
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        mapDetailBar = view.findViewById(R.id.tvMapDetailBar);
        mapDetailBar.setOnClickListener(v -> onDetailBarClick());

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            eventID = getArguments().getString("EventIDKey");
            setHasOptionsMenu(false);
        } else {
            setHasOptionsMenu(true);
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu, menu);
        // Add search icon
        menu.findItem(R.id.searchMenuItem).setIcon(
                new IconDrawable(getActivity(), FontAwesomeIcons.fa_search).colorRes(R.color.white).actionBarSize());
        // Add settings icon
        menu.findItem(R.id.settingsMenuItem).setIcon(
                new IconDrawable(getActivity(), FontAwesomeIcons.fa_gear).colorRes(R.color.white).actionBarSize());
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.searchMenuItem:
                goToSearch();
                return true;

            case R.id.settingsMenuItem:
                goToSettings();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void goToSearch() {
        Intent intent = new Intent(getActivity(), SearchActivity.class);
        startActivity(intent);
    }

    private void goToSettings() {
        Intent intent = new Intent(getActivity(), SettingsActivity.class);
        startActivity(intent);
    }

    /**
     * Start person activity with a person ID when marker is clicked.
     */
    private void onDetailBarClick() {
        currentEvent = (Event) mapDetailBar.getTag();
        if (currentEvent == null) return;

        String personID = currentEvent.getPersonID();

        Intent intent = new Intent(getActivity(), PersonActivity.class);
        intent.putExtra("PERSON_ID", personID);
        startActivity(intent);
    }

    private void addEventMarkers() {
        for (Event event : DataCache.getEvents().values()) {
            LatLng location = new LatLng(event.getLatitude(), event.getLongitude());

            addEventMarker(event, location);

            gMap.setOnMarkerClickListener(marker -> {
                currentEvent = (Event) marker.getTag();
                updateDetailBar();
                addLines();
                return false;
            });

            // Make map focus the logged-in user's birth
            if (eventID != null && event.getEventID().equals(eventID)) {
                gMap.moveCamera(CameraUpdateFactory.newLatLng(location));
                currentEvent = DataCache.getEvents().get(eventID);
                updateDetailBar();
                addLines();
            }
            if (eventID == null &&
                    event.getPersonID().equals(DataCache.getUser().getPersonID())) {
                gMap.moveCamera(CameraUpdateFactory.newLatLng(location));
            }
        }
    }

    private void addEventMarker(Event event, LatLng location) {
        float markerColor = DataCache.getEventColors().get(event.getEventType().toLowerCase());

        Marker marker = gMap.addMarker(new MarkerOptions()
                .position(location)
                .title(stringifyFullLocation(event))
                .icon(BitmapDescriptorFactory.defaultMarker(markerColor)));
        marker.setTag(event);
    }

    /**
     * Adds event details to map detail bar
     */
    private void updateDetailBar() {
        if (currentEvent == null) return;

        Person person = DataCache.getPersons().get(currentEvent.getPersonID());
        assert person != null;

        // Update icon
        Drawable icon = getGenderIcon(getActivity(), person.getGender());
        mapDetailBar.setCompoundDrawables(icon, null, null, null);

        // Update detail bar text
        mapDetailBar.setText(stringifyBarDetails(currentEvent, person));

        // Update detail bar tag
        mapDetailBar.setTag(currentEvent);
    }

    /**
     * Adds lines eminating from the marker
     */
    private void addLines() {
        if (currentEvent == null) return;

        clearLines();
        if (DataCache.getShowLifeStoryLines()) {
            addLifeStoryLines();
        }
        if (DataCache.getShowFamilyTreeLines()) {
            addFamilyTreeLines();
        }
        if (DataCache.getShowSpouseLines()) {
            addSpouseLine();
        }
    }

    private void addLifeStoryLines() {
        Person person = DataCache.getPersons().get(currentEvent.getPersonID());
        if (person == null) return;

        for (Event lifeEvent : DataCache.getPersonEvents().get(person.getPersonID())) {
            Polyline line = gMap.addPolyline(new PolylineOptions().add(
                    getLatLng(currentEvent),
                    getLatLng(lifeEvent)
            ).color(Color.BLUE));
            lines.add(line);
        }
    }

    private void addFamilyTreeLines() {
        Person person = DataCache.getPersons().get(currentEvent.getPersonID());
        if (person == null) return;

        float lineWidth = 40.0f;

        // Add line to father
        Person father = DataCache.getPersons().get(person.getFatherID());
        addParentLine(currentEvent, father, lineWidth);

        // Add line to mother
        Person mother = DataCache.getPersons().get(person.getMotherID());
        addParentLine(currentEvent, mother, lineWidth);
    }

    private void addParentLine(Event event, Person parent, float lineWidth) {
        if (parent == null) return;

        lineWidth = lineWidth / 2;

        Event firstParentEvent = DataCache.getPersonEvents().get(parent.getPersonID()).get(0);
        Polyline line = gMap.addPolyline(new PolylineOptions().add(
                getLatLng(event),
                getLatLng(firstParentEvent)
        ).color(Color.DKGRAY).width(lineWidth));
        lines.add(line);

        Person grandfather = DataCache.getPersons().get(parent.getFatherID());
        if (grandfather == null) return;
        addParentLine(firstParentEvent, grandfather, lineWidth);

        Person grandmother = DataCache.getPersons().get(parent.getMotherID());
        if (grandmother == null) return;
        addParentLine(firstParentEvent, grandmother, lineWidth);
    }

    private void addSpouseLine() {
        Person person = DataCache.getPersons().get(currentEvent.getPersonID());
        if (person == null) return;

        Person spouse = DataCache.getPersons().get(person.getSpouseID());
        if (spouse != null) {
            Event firstSpouseEvent = DataCache.getPersonEvents().get(spouse.getPersonID()).get(0);

            Polyline line = gMap.addPolyline(new PolylineOptions().add(
                    getLatLng(currentEvent),
                    getLatLng(firstSpouseEvent)
            ).color(Color.MAGENTA));
            lines.add(line);
        }
    }

    private void clearLines() {
        for (Polyline line : lines) {
            line.remove();
        }
        lines.clear();
    }

    private LatLng getLatLng (Event event) {
        return new LatLng(event.getLatitude(), event.getLongitude());
    }

    @Override
    public void onResume() {
        super.onResume();
        addLines();
    }
}