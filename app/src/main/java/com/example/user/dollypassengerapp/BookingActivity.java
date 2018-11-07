package com.example.user.dollypassengerapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

public class BookingActivity extends AppCompatActivity {

    private EditText origin;
    private EditText dest;
    private RadioButton pPal;
    private RadioButton dDosh;
    private Button nxt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        origin = (EditText) findViewById(R.id.pickupLoc);
        dest =(EditText) findViewById(R.id.dest);
        pPal = (RadioButton) findViewById(R.id.paypal);
        dDosh = (RadioButton) findViewById(R.id.dolDosh);
        nxt = (Button) findViewById(R.id.next);

        String or = origin.getText().toString();
        String d = dest.getText().toString();

        nxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String payOpt ="";

                if (pPal.isChecked()){
                    payOpt = pPal.getText().toString();
                }
                else {
                    payOpt = dDosh.getText().toString();
                }
                double km = 7;
                double cost = km*1;
                String payment = "Cost for this journey is: €" + cost +
                        ". Payment with be deducted from " +  payOpt + " account";
                openDialog(payment);


            }
        });
    }

    public void openDialog(String receipt) {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();

        // Setting Dialog Title
        alertDialog.setTitle("Transaction");

        // Setting Dialog Message
        alertDialog.setMessage(receipt);

        // Setting OK Button
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog,int which)
            {
                // Write your code here to execute after dialog    closed
                Intent intent = new Intent( BookingActivity.this,
                        MapsActivity.class );
                startActivity(intent);
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }
//
}
