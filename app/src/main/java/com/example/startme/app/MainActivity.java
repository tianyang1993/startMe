package com.example.startme.app;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnFacebook = (Button)findViewById(R.id.facebook_btn);
        btnFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        Button btnSignIn = (Button)findViewById(R.id.signin_btn);
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goSignIn();
            }
        });

        Button btnSignUp = (Button)findViewById(R.id.signup_btn);
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
        overridePendingTransition(R.anim.anim_left_slide_in, R.anim.anim_right_slide_out);
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
