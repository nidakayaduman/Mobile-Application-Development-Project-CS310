package com.myproject.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.content.Intent;

public class AddEventActivity extends AppCompatActivity {

    private EditText nameEditText, dateEditText, timeEditText, locationEditText, descriptionEditText;
    private Button addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        nameEditText = findViewById(R.id.nameEditText);
        dateEditText = findViewById(R.id.dateEditText);
        timeEditText = findViewById(R.id.timeEditText);
        locationEditText = findViewById(R.id.locationEditText);
        descriptionEditText = findViewById(R.id.descriptionEditText);
        addButton = findViewById(R.id.addButton);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameEditText.getText().toString();
                String date = dateEditText.getText().toString();
                String time = timeEditText.getText().toString();
                String location = locationEditText.getText().toString();
                String description = descriptionEditText.getText().toString();

                Event newEvent = new Event(null, name, date, time, location, description);

                Intent intent = new Intent();
                intent.putExtra("newEvent", newEvent);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}
