package com.thecyberian.instagram_clone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnKeyListener {

    EditText username;
    EditText password;

    TextView loginTextView;

    boolean signUpModeActive = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginTextView = findViewById(R.id.loginTextView);
        loginTextView.setOnClickListener(this);

        username = findViewById(R.id.usernameEditText);
        password = findViewById(R.id.passwordEditText);

        ImageView logo = findViewById(R.id.logoImageView);
        RelativeLayout backgroundLayout = findViewById(R.id.backgroundLayout);

        logo.setOnClickListener(this);
        backgroundLayout.setOnClickListener(this);

        password.setOnKeyListener(this);

        if(ParseUser.getCurrentUser().getUsername() != null){
            Log.i("Current User", ParseUser.getCurrentUser().getUsername());
            showUserList();
        }

        ParseAnalytics.trackAppOpenedInBackground(getIntent());
    }


    public void signUpClicked(View view) {

        if (username.getText().toString().isEmpty() || password.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please enter a username and a password.", Toast.LENGTH_SHORT).show();
        } else {
            if (signUpModeActive) {
                ParseUser user = new ParseUser();

                user.setUsername(username.getText().toString());
                user.setPassword(password.getText().toString());

                user.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            Log.i("Sign Up", "Successful");
                            showUserList();
                        } else {
                            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
            } else {
                //Login here
                ParseUser.logInInBackground(username.getText().toString(), password.getText().toString(), new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {
                        if (user != null) {
                            Log.i("User logged In", "Yes");
                            showUserList();
                        } else {
                            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }

        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.loginTextView) {
            Button signUpButton = findViewById(R.id.signUpButton);

            if (signUpModeActive) {
                signUpModeActive = false;
                signUpButton.setText("Login");
                loginTextView.setText("Or, SignUp");
            } else {

                signUpModeActive = true;
                signUpButton.setText("Sign Up");
                loginTextView.setText("Or, Login");
            }
        } else if (view.getId() == R.id.logoImageView || view.getId() == R.id.backgroundLayout) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    @Override
    public boolean onKey(View view, int i, KeyEvent keyEvent) {

        if (i == KeyEvent.KEYCODE_ENTER && keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
            signUpClicked(view);
        }

        return false;
    }

    public void showUserList(){
        Intent intent = new Intent(getApplicationContext(), UserListActivity.class);
        startActivity(intent);

    }
}
