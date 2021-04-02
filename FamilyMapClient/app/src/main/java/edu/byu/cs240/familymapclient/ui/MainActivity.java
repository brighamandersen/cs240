package edu.byu.cs240.familymapclient.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import edu.byu.cs240.familymapclient.R;
import edu.byu.cs240.familymapclient.model.DataCache;

/**
 * Embeds a fragment, either LoginFragment or MapFragment, depending if user is logged in (checked via callback).
 */
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DataCache.initialize();

        FragmentManager fm = this.getSupportFragmentManager();
        LoginFragment loginFragment = new LoginFragment();

        // renders login fragment
//        fm.beginTransaction().add(R.id.mainActivityFrameLayout, loginFragment).commit();
        // renders map fragment
        MapFragment mapFragment = new MapFragment();
        fm.beginTransaction().replace(R.id.mainActivityFrameLayout, mapFragment).commit();
    }
}