package edu.byu.cs240.familymapclient.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.byu.cs240.familymapclient.R;
import edu.byu.cs240.familymapclient.model.DataCache;
import edu.byu.cs240.familymapclient.net.DataSyncTask;
import edu.byu.cs240.familymapclient.net.LoginTask;
import edu.byu.cs240.familymapclient.net.RegisterTask;
import requests.LoginRequest;
import requests.RegisterRequest;

import static java.lang.Integer.parseInt;

/**
 * Implements the login/register screens.
 */
public class LoginFragment extends Fragment {
    private EditText serverHostET;
    private EditText serverPortET;
    private EditText usernameET;
    private EditText passwordET;
    private EditText firstNameET;
    private EditText lastNameET;
    private EditText emailET;
    private RadioGroup genderRG;
    private RadioButton genderRB;
    private Button loginButton;
    private Button registerButton;

    private View.OnClickListener listener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        serverHostET = view.findViewById(R.id.etServerHost);
        serverHostET.addTextChangedListener(loginWatcher);
        serverHostET.addTextChangedListener(registerWatcher);

        serverPortET = view.findViewById(R.id.etServerPort);
        serverPortET.addTextChangedListener(loginWatcher);
        serverPortET.addTextChangedListener(registerWatcher);

        usernameET = view.findViewById(R.id.etUsername);
        usernameET.addTextChangedListener(loginWatcher);
        usernameET.addTextChangedListener(registerWatcher);

        passwordET = (EditText) view.findViewById(R.id.etPassword);
        passwordET.addTextChangedListener(loginWatcher);
        passwordET.addTextChangedListener(registerWatcher);

        firstNameET = (EditText) view.findViewById(R.id.etFirstName);
        firstNameET.addTextChangedListener(registerWatcher);

        lastNameET = (EditText) view.findViewById(R.id.etLastName);
        lastNameET.addTextChangedListener(registerWatcher);

        emailET = (EditText) view.findViewById(R.id.etEmail);
        emailET.addTextChangedListener(registerWatcher);

        genderRG = (RadioGroup) view.findViewById(R.id.rgGender);
        genderRG.setOnCheckedChangeListener((RadioGroup.OnCheckedChangeListener) (group, checkedId) -> {
            checkRegisterFilled();
            genderRB = (RadioButton) view.findViewById(checkedId);
        });

        loginButton = (Button) view.findViewById(R.id.btLogin);
        loginButton.setEnabled(false);
        loginButton.setOnClickListener(v -> onLoginClick());

        registerButton = (Button) view.findViewById(R.id.btRegister);
        registerButton.setEnabled(false);
        registerButton.setOnClickListener(v -> onRegisterClick());

        return view;
    }

    TextWatcher loginWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {}

        @Override
        public void afterTextChanged(Editable s) {
            checkLoginFilled();
        }
    };

    private void checkLoginFilled() {
        if (serverHostET.getText().toString().equals("") ||
                serverPortET.getText().toString().equals("") ||
                usernameET.getText().toString().equals("") ||
                passwordET.getText().toString().equals("")) {
            loginButton.setEnabled(false);
            return;
        }

        loginButton.setEnabled(true);
    }

    private void onLoginClick() {
        Handler uiLoginHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);

                Bundle bundle = msg.getData();
                String resUsername = bundle.getString("UsernameKey");
                String resAuthtoken = bundle.getString("AuthtokenKey");
                String userPersonID = bundle.getString("PersonIDKey");

                if (resUsername == null || resAuthtoken == null) {
                    Toast.makeText(getActivity(), "Login failed.", Toast.LENGTH_SHORT).show();
                    return;
                }

//                Toast.makeText(getActivity(), "Login successful!  Username: " +
//                        resUsername, Toast.LENGTH_SHORT).show();

                performDataSync(resAuthtoken, userPersonID);

//                renderMapFragment();
            }
        };

        LoginRequest loginRequest = new LoginRequest(
                usernameET.getText().toString(),
                passwordET.getText().toString()
        );

        LoginTask loginTask = new LoginTask(
                uiLoginHandler,
                serverHostET.getText().toString(),
                parseInt(serverPortET.getText().toString()),
                loginRequest
        );

        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(loginTask);
    }

    private void renderMapFragment() {
        FragmentManager fm = this.getParentFragmentManager();
        MapFragment mapFragment = new MapFragment();

        fm.beginTransaction().replace(R.id.mainActivityFrameLayout, mapFragment).commit();

        // Also call invalidateOptionsMenu() so that you can reset the menu bar
    }

    TextWatcher registerWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {}

        @Override
        public void afterTextChanged(Editable s) {
            checkRegisterFilled();
        }
    };

    private void checkRegisterFilled() {
        if (serverHostET.getText().toString().equals("") ||
                serverPortET.getText().toString().equals("") ||
                usernameET.getText().toString().equals("") ||
                passwordET.getText().toString().equals("") ||
                firstNameET.getText().toString().equals("") ||
                lastNameET.getText().toString().equals("") ||
                emailET.getText().toString().equals("") ||
                genderRG.getCheckedRadioButtonId() == -1)
        {
            registerButton.setEnabled(false);
            return;
        }

        registerButton.setEnabled(true);
    }

    private void onRegisterClick() {
        Handler uiRegisterHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);

                Bundle bundle = msg.getData();
                String resUsername = bundle.getString("UsernameKey");
                String resAuthtoken = bundle.getString("AuthtokenKey");
                String userPersonID = bundle.getString("PersonIDKey");

                if (resUsername == null || resAuthtoken == null) {
                    Toast.makeText(getActivity(), "Register failed.", Toast.LENGTH_SHORT).show();
                    return;
                }

//                Toast.makeText(getActivity(), "Register successful!  Username: " +
//                        resUsername, Toast.LENGTH_SHORT).show();

                performDataSync(resAuthtoken, userPersonID);

//                renderMapFragment();
            }
        };

        String genderAbbreviation = "m";
        if (genderRB.getText().toString().equals("Female")) {
            genderAbbreviation = "f";
        }

        RegisterRequest registerRequest = new RegisterRequest(
                usernameET.getText().toString(),
                passwordET.getText().toString(),
                emailET.getText().toString(),
                firstNameET.getText().toString(),
                lastNameET.getText().toString(),
                genderAbbreviation
        );

        RegisterTask registerTask = new RegisterTask(
                uiRegisterHandler,
                serverHostET.getText().toString(),
                parseInt(serverPortET.getText().toString()),
                registerRequest
        );

        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(registerTask);
    }

    private void performDataSync(String authtoken, String userPersonID) {
        Handler uiDataSyncHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);

                Bundle bundle = msg.getData();
                String firstName = bundle.getString("FirstNameKey");
                String lastName = bundle.getString("LastNameKey");

                Toast.makeText(getActivity(), "Your name: " + firstName +
                        " " + lastName, Toast.LENGTH_SHORT).show();
            }
        };

        DataSyncTask dataSyncTask = new DataSyncTask(
                uiDataSyncHandler,
                serverHostET.getText().toString(),
                parseInt(serverPortET.getText().toString()),
                authtoken,
                userPersonID
        );

        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(dataSyncTask);
    }
}