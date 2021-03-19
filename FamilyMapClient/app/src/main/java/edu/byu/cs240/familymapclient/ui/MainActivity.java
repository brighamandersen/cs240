package edu.byu.cs240.familymapclient.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import edu.byu.cs240.familymapclient.R;

/**
 * Just embeds a fragment, either LoginFragment or MapFragment, depending if user is logged in (checked via callback).
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // TODO - Add logic so that if callback from login returns success, swap out Login Fragment for Map Fragment

        // FIXME - Not sure if this is correct
//        if (savedInstanceState == null) {
//            getSupportFragmentManager().beginTransaction()
//                    .setReorderingAllowed(true)
//                    .add(R.id.fragment_container_view, MapFragment.class, null)
//                    .commit();
//        }
    }

}