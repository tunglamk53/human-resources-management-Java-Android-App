package com.example.hrmanagement.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.hrmanagement.MainActivity;
import com.example.hrmanagement.R;

public class EditEmployeeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_employee);




        Button btnFooter = findViewById(R.id.btnFooter);
        btnFooter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(EditEmployeeActivity.this, MainActivity.class);
                startActivity(i);
            }
        });


    }
}
