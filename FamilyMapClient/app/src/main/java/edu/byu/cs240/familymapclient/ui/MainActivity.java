package edu.byu.cs240.familymapclient.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.Menu;

import edu.byu.cs240.familymapclient.R;
import edu.byu.cs240.familymapclient.model.DataCache;

/**
 * Embeds a fragment, either LoginFragment or MapFragment, depending if user is logged in (checked via callback).
 */
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        setContentView(R.layout.activity_settings);

//        DataCache.initialize();
//
//        FragmentManager fm = this.getSupportFragmentManager();
//        LoginFragment loginFragment = new LoginFragment();

        // renders login fragment
//        fm.beginTransaction().add(R.id.mainActivityFrameLayout, loginFragment).commit();
        // renders map fragment
//        MapFragment mapFragment = new MapFragment();
//        fm.beginTransaction().replace(R.id.mainActivityFrameLayout, mapFragment).commit();



        // When the app starts
            // check to see if user is logged in
            // make sure the appropriate fragment is active
            // call invalidateOptionsMenu() to force onCreateOptionsMenu(...) to be called again
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // if user is logged in
            // instead search/settings menu
        // else
            // don't install a menu

        return super.onCreateOptionsMenu(menu);
    }
}