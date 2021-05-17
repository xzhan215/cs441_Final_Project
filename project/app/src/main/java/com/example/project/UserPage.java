package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class UserPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_page);
    }

    public void switch_activity(View view) {
        Intent act_action = new Intent(this, User.class);
        startActivity(act_action);
    }

    public void switch_evaluation(View view) {
        Intent act_action = new Intent(this, Evaluation_User.class);
        startActivity(act_action);
    }

    public void log_out(View view) {
        Intent act_action = new Intent(this, MainActivity.class);
        startActivity(act_action);
    }
}