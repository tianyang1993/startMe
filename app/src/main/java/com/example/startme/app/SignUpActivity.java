package com.example.startme.app;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SignUpActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        Button btnSignIn = (Button)findViewById(R.id.signup_btn1);
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });

        Button btnCancel = (Button)findViewById(R.id.cancel_btn1);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        overridePendingTransition(R.anim.anim_right_slide_in, R.anim.anim_left_slide_out);

    }
    void signUp(){

    }

    void cancel(){
        finish();
    }

}
