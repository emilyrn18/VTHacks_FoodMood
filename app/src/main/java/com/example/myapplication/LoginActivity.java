package com.example.myapplication;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ProgressBar;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {


    // UI references.
    //AutoCompleteTextView
    private Button emailSignIn;
    private EditText mEmailView;
    private EditText mPasswordView;
    private ProgessBar progressBar;
    //private TextView textViewSignup;
    //private View mProgressView;
    //private View mLoginFormView;

    /**
     * sets up registeration info
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailSignIn = (Button) findViewById(R.id.email_sign_in_button);
        mEmailView =  (EditText) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.password);

        emailSignIn.setOnClickListener(this);

    }


    private void registerUser() {
        String email = mEmailView.getText().toString().trim();
        String password = mPasswordView.getText().toString().trim();
        //checking if both strings is empty
        if(mEmailView.getTextSize()==0){
            Toast.makeText("need info", "Plase Enter Email", Toast.LENGTH_SHORT).show();
            return;
        }
        if(mPasswordView.getTextSize()== 0){
            Toast.makeText("need info", "Please Enter Password", Toast.LENGTH_SHORT).show();
            return;
        }
        //if validations are ok
        //continue to link to firebase

    }
    /**
     * button register/ sign in should give funtion:
     * 1- if old user, login
     *      notify login successful
     * 2- else register
     *      notify new acc registered
     * @param view
     */
    @Override
    public void onClick(View view) {
        if(view == emailSignIn){
            registerUser();
        }
    }
}

