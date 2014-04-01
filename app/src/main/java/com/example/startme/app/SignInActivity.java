package com.example.startme.app;

import android.app.Activity;

/**
 * Created by Tian on 4/1/14.
 */
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SignInActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_signin);

        Button btnSignIn = (Button)findViewById(R.id.signin_btn1);
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

        Button btnCancel = (Button)findViewById(R.id.cancel_btn);
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

    @Override
    protected void onStop() {
        super.onStart();

    }

    void signIn(){

    }

    void cancel(){


        finish();
    }
}
