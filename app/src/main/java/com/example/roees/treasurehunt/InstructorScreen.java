package com.example.roees.treasurehunt;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class InstructorScreen extends AppCompatActivity {

    LanguageImp languageImp = new HebrewImp();
    Button submit;
    EditText password;
    EditText email;
    TextView response;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor_screen);

        password = findViewById(R.id.password);
        password.setHint(languageImp.EnterPassword());

        email = findViewById(R.id.email);
        email.setHint(languageImp.EnterEmail());

        submit = findViewById(R.id.submit);
        submit.setText(languageImp.Submit());

        response = findViewById(R.id.response);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDB.getInstance().instructorEntrance(email.getText().toString(),password.getText().toString());
                response.setText(FirebaseDB.getInstance().logFeedback());
            }
        });
    }
}
