package edu.byu.cs240.familymapclient.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import edu.byu.cs240.familymapclient.R;

/**
 * Just embeds a fragment, either LoginFragment or MapFragment, depending if user is logged in (checked via callback).
 */
public class MainActivity extends AppCompatActivity {

    private LoginFragment loginFragment;
    private MapFragment mapFragment;

    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fm = this.getSupportFragmentManager();
        LoginFragment loginFragment = new LoginFragment();

        fm.beginTransaction().add(R.id.mainActivityFrameLayout, loginFragment).commit();

        // TODO - Add logic so that if callback from login returns success, swap out Login Fragment for Map Fragment
        // if user is logged in
        // else
//            replaceWithMapFragment();
    }

    // handleMsg
        // if user is logged in
}