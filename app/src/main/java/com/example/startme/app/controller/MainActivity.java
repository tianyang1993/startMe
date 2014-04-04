package com.example.startme.app.controller;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.startme.app.R;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnFacebook = (Button)findViewById(R.id.button_facebook_login);
        btnFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        Button btnSignIn = (Button)findViewById(R.id.button_main_signin);
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goSignIn();
            }
        });

        Button btnSignUp = (Button)findViewById(R.id.button_main_signup);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goSignUp();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);

    }

    void goSignIn(){
        Intent intent = new Intent(this, SignInActivity.class);
        this.startActivity(intent);
    }

    void goSignUp(){
        Intent intent = new Intent(this, SignUpActivity.class);
        this.startActivity(intent);
    }

}
