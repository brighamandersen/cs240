package edu.byu.cs240.familymapclient.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.byu.cs240.familymapclient.R;
import edu.byu.cs240.familymapclient.model.DataCache;
import edu.byu.cs240.familymapclient.net.DataSyncTask;
import edu.byu.cs240.familymapclient.net.LoginTask;

import static java.lang.Integer.parseInt;

/**
 * Implements the login/register screens.
 */
public class LoginFragment extends Fragment {
    private EditText serverHostET;
    private EditText serverPortET;
    private EditText usernameET;
    private EditText passwordET;
//    private Button loginButton;

    private View.OnClickListener listener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        serverHostET = view.findViewById(R.id.etServerHost);
        serverPortET = view.findViewById(R.id.etServerPort);
        usernameET = view.findViewById(R.id.etUsername);
        passwordET = view.findViewById(R.id.etPassword);
        Button loginButton = view.findViewById(R.id.btLogin);
        loginButton.setOnClickListener(v -> onLoginClick());
        return view;
    }

    private void renderMapFragment() {
        FragmentManager fm = this.getParentFragmentManager();
        MapFragment mapFragment = new MapFragment();

        fm.beginTransaction().replace(R.id.mainActivityFrameLayout, mapFragment).commit();
    }

    private void onLoginClick() {
        Handler uiThreadMsgHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);

                Bundle bundle = msg.getData();
                String resUsername = bundle.getString("UsernameKey");
                String resAuthtoken = bundle.getString("AuthtokenKey");
                String resPersonID = bundle.getString("PersonIDKey");

                if (resUsername == null || resAuthtoken == null) {
                    Toast.makeText(getActivity(), "Login failed.", Toast.LENGTH_SHORT).show();
                    return;
                }

                Toast.makeText(getActivity(), "Login successful!  Username: " +
                        resUsername, Toast.LENGTH_SHORT).show();
                renderMapFragment();

                DataSyncTask dataSyncTask = new DataSyncTask(
                        uiThreadMsgHandler,
                        serverHostET.getText().toString(),
                        parseInt(serverPortET.getText().toString()),
                        resAuthtoken,
                        resPersonID
                );

                ExecutorService executor = Executors.newSingleThreadExecutor();
                executor.submit(dataSyncTask);
            }
        };

        LoginTask loginTask = new LoginTask(
                uiThreadMsgHandler,
                serverHostET.getText().toString(),
                parseInt(serverPortET.getText().toString()),
                usernameET.getText().toString(),
                passwordET.getText().toString()
        );

        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(loginTask);
    }
}