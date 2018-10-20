package com.example.user.dollypassengerapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    // Firebase inits
    private FirebaseDatabase db;
    private DatabaseReference dbRef;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private static final String TAG = "MainActivity";

    private EditText email;
    private EditText passwd;
    private Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email = (EditText) findViewById(R.id.email);
        passwd = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.login);


        db = FirebaseDatabase.getInstance();
        dbRef = db.getReference("Users");
        mAuth = FirebaseAuth.getInstance();

        findViewById(R.id.signup).setOnClickListener(this);

       // dbRef.setValue("Hello World");

        // Allow for real time updates in database (db)

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                // Current user
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if(user!=null){
                    //user is signed in
                    Toast.makeText(MainActivity.this, "Signed In!",
                            Toast.LENGTH_LONG).show();
                    Log.d(TAG, "user is signed in");
                }
                else{
                    //user is signed out
                    Toast.makeText(MainActivity.this, "Not Signed In!",
                            Toast.LENGTH_LONG).show();
                }
            }
        };

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailStr = email.getText().toString();
                String pwd = passwd.getText().toString();

                if (!TextUtils.isEmpty(email.getText().toString()) && !TextUtils.isEmpty(passwd.getText().toString() )){
                    login(emailStr,pwd);
                }
                else{

                }

            }
        });
    }

    private void login (String email, String pwd){
        if (!email.equals("") && !pwd.equals("")){
            mAuth.signInWithEmailAndPassword(email, pwd).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (!task.isSuccessful()) {
                        Toast.makeText(MainActivity.this, "Sign in Failed",
                                Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(MainActivity.this, "Signed In!",
                                Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
//        updateUI(currentUser);//
        mAuth.addAuthStateListener(mAuthListener);
    }

    protected void onStop(){
        super.onStop();
        if (mAuthListener!=null){
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.signup:
                startActivity(new Intent(this, SignUpActivity.class));
                break;
        }
    }
}
