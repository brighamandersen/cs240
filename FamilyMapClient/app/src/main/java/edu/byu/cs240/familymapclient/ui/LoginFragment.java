package edu.byu.cs240.familymapclient.ui;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import edu.byu.cs240.familymapclient.R;

/**
 * Implements the login/register screens.
 */
public class LoginFragment extends Fragment {
    // Calls LoginTask to perform login/register and data retrieval
    // Notifies MainActivity when login succeeds or fails

    private Button loginButton;

    private View.OnClickListener listener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        loginButton = (Button) view.findViewById(R.id.btLogin);
        loginButton.setOnClickListener(v -> {
            Toast.makeText(getActivity(), "Login Button Clicked", Toast.LENGTH_SHORT).show();
            renderMapFragment();
        });
        return view;
    }

    public void renderMapFragment() {
        FragmentManager fm = this.getParentFragmentManager();
        MapFragment mapFragment = new MapFragment();

        fm.beginTransaction().replace(R.id.mainActivityFrameLayout, mapFragment).commit();
    }
}