package com.example.roees.treasurehunt;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class InstructorLogin extends AppCompatActivity {

    Button submit;
    EditText password;
    EditText email;
    TextView response;
    private GameDB db = FirebaseDB.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor_screen);

        password = findViewById(R.id.password);
        password.setHint(db.getLanguageImp().enterPassword());

        email = findViewById(R.id.email);
        email.setHint(db.getLanguageImp().enterEmail());

        submit = findViewById(R.id.submit);
        submit.setText(db.getLanguageImp().submit());

        response = findViewById(R.id.response);

        final Intent instructorMapScreen = new Intent(InstructorLogin.this, InstructorMap.class);
        if(db.isInstructor()){
            db.login();
            startActivity(instructorMapScreen);
        }

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            db.instructorEntrance(email.getText().toString(),password.getText().toString());
            response.setText(db.actionFeedback());
            if(db.isLoggedIn()) startActivity(instructorMapScreen);
            }
        });
    }
}
