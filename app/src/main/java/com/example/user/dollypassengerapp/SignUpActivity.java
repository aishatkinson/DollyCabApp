package com.example.user.dollypassengerapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {


    private EditText fname;
    private EditText lName;
    private EditText userEmail;
    private EditText password;
    private Button signUp;
    private Button bk;

    private ProgressDialog progLog;

    private FirebaseDatabase fDb;
    private DatabaseReference dbref;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        fDb = FirebaseDatabase.getInstance();
        dbref = fDb.getReference().child("Users");
        mAuth = FirebaseAuth.getInstance();

        progLog = new ProgressDialog(this);

        fname = (EditText) findViewById(R.id.firstName);
        lName = (EditText)findViewById(R.id.lastName);
        userEmail =(EditText) findViewById(R.id.userEmail);
        password =(EditText) findViewById(R.id.usrPwd);
        signUp = (Button) findViewById(R.id.signUp);
        bk = (Button) findViewById(R.id.btnBack);

        bk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( SignUpActivity.this,
                        MainActivity.class );
                startActivity(intent);
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                createAccount();
            }
        });
    }

    private void createAccount() {

        final String name = fname.getText().toString().trim();
        final String lname = lName.getText().toString().trim();
        String pwd = password.getText().toString().trim();
        String email = userEmail.getText().toString().trim();

        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(lname)
                && !TextUtils.isEmpty(email) &&!TextUtils.isEmpty(pwd)){
            progLog.setMessage("Creating Account...");
            progLog.show();

            mAuth.createUserWithEmailAndPassword(email, pwd)
                    .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            if (authResult != null){
                                String uid = mAuth.getCurrentUser().getUid();
                                DatabaseReference currentUserDb = dbref.child(uid);
                                currentUserDb.child("First Name").setValue(name);
                                currentUserDb.child("Last Name").setValue(lname);

                                progLog.dismiss();

                                //Opens next screen
                                Intent intent = new Intent(SignUpActivity.this,
                                        MapsActivity.class );
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                            }
                        }
                    });
        }
    }


}
