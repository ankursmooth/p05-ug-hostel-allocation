package com.example.ankurshukla.hostel;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Feedback extends AppCompatActivity {
    EditText subject,message;
    Button send;
    String esubject,emessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        subject = (EditText)findViewById(R.id.editTextSubject);
        message = (EditText)findViewById(R.id.editTextMessage);
        send = (Button)findViewById(R.id.buttonSend);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                esubject = subject.getText().toString().toLowerCase();
                emessage = message.getText().toString().toLowerCase();

                Intent email = new Intent(Intent.ACTION_SEND);
                email.putExtra(Intent.EXTRA_EMAIL,new String[]{"lnmiit@hostel.16mb.com"});
                email.putExtra(Intent.EXTRA_SUBJECT,esubject);
                email.putExtra(Intent.EXTRA_TEXT,emessage);
                startActivity(Intent.createChooser(email,"Choose an Email Client"));
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_feedback, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
