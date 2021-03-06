package com.example.myapplication;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
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
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

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
    private ProgressDialog pDiag;
    private FirebaseAuth firebaseAuth;
    private Button emailReg;
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

        pDiag = new ProgressDialog(this);

        firebaseAuth = FirebaseAuth.getInstance();

        if( firebaseAuth.getCurrentUser() != null){

        }

        emailSignIn = (Button) findViewById(R.id.email_sign_in_button);
        mEmailView =  (EditText) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.password);
        emailReg = (Button) findViewById(R.id.Register_button);

        emailSignIn.setOnClickListener(this);
        emailReg.setOnClickListener(this);

    }


    /**
     * registers user to
     * FirebaseAuth Database
     */
    private void registerUser() {
        String email = mEmailView.getText().toString().trim();
        String password = mPasswordView.getText().toString().trim();
        //checking if both strings is empty
        if (mEmailView.getTextSize() == 0) {
            Toast.makeText(getApplicationContext(), "Please Enter Email", Toast.LENGTH_SHORT).show();
            return;
        }
        if (mPasswordView.getTextSize() == 0) {
            Toast.makeText(getApplicationContext(), "Please Enter Password", Toast.LENGTH_SHORT).show();
            return;
        }

        //if validations are ok
        //continue to link to firebase
        pDiag.setMessage("Processing. Please Wait");
        pDiag.show();

        //regieters new user
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //user is successfully registered
                            // changes to next screen

                            Toast.makeText(getApplicationContext(), "Registeration Successful!", Toast.LENGTH_SHORT).show();
                            Log.i("My App", "Changes to next screen!");
                            startActivity(new Intent(LoginActivity.this, HomePage.class));
                        }

                        else {
                            pDiag.dismiss();
                            Toast.makeText(getApplicationContext(), "Registeration Failed!", Toast.LENGTH_SHORT).show();
                            Log.i("My App", "Resgisteration failed");

                        }
                    }
                });
    }


        /**
         * looks for user on database to see if user exists
         * and logs them in if the user does exist
         */
        public void checkLogIn(){
            String email = mEmailView.getText().toString().trim();
            String password = mPasswordView.getText().toString().trim();
            //checking if both strings is empty
            if (mEmailView.getTextSize() == 0) {
                Toast.makeText(getApplicationContext(), "Please Enter Email", Toast.LENGTH_SHORT).show();
                return;
            }
            if (mPasswordView.getTextSize() == 0) {
                Toast.makeText(getApplicationContext(), "Please Enter Password", Toast.LENGTH_SHORT).show();
                return;
            }

            pDiag.setMessage("Processing. Please Wait");
            pDiag.show();

            firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            pDiag.dismiss();
                            if (task.isSuccessful()){
                                Toast.makeText(getApplicationContext(), "Sign In Successful!", Toast.LENGTH_SHORT).show();
                                Log.i("My App", "Changes to next screen!");
                                startActivity(new Intent(LoginActivity.this, HomePage.class));
                            }
                            else if (firebaseAuth.getCurrentUser() == null){
                                Toast.makeText(getApplicationContext(), "User Not Registered!", Toast.LENGTH_SHORT).show();
                                Log.i("My App", "User not Registered");
                            }
                            else {
                                Toast.makeText(getApplicationContext(), "Sign In failed. Check email or password", Toast.LENGTH_SHORT).show();
                                Log.i("My App", "Sign in fail ");

                            }
                        }
                    });
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
        if(view == emailReg){
            registerUser();
        }
        else if(view == emailSignIn){
            checkLogIn();
        }
    }
}

