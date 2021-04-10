package edu.byu.cs240.familymapclient.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeModule;

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

        Iconify.with(new FontAwesomeModule());

        LoginFragment loginFragment = new LoginFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.mainActivityFrameLayout, loginFragment).commit();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

        if (DataCache.getUser() == null) {
            LoginFragment loginFragment = new LoginFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.mainActivityFrameLayout, loginFragment).commit();
        }
    }
}