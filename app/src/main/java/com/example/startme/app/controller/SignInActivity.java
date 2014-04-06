package com.example.startme.app.controller;

import android.app.Activity;

/**
 * Created by Tian on 4/1/14.
 */
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.startme.app.R;

public class SignInActivity extends Activity {

    EditText txtUserName = null;
    EditText txtPassword = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_signin);

        Button btnSignIn = (Button)findViewById(R.id.button_signin);
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

        Button btnCancel = (Button)findViewById(R.id.button_signin_cancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
            }
        });

        txtUserName = (EditText)findViewById(R.id.edit_signin_username);
        txtPassword = (EditText)findViewById(R.id.edit_signin_password);
    }

    @Override
    protected void onStart() {
        super.onStart();
        overridePendingTransition(R.anim.anim_right_slide_in, R.anim.anim_left_slide_out);
    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    void signIn(){


        String strUserName = txtUserName.getText().toString();
        String strPassword = txtPassword.getText().toString();

        if (strUserName.compareTo("") == 0 || strPassword.compareTo("") == 0){

            AlertDialog.Builder alt_bld = new AlertDialog.Builder(this);
            alt_bld.setMessage(R.string.message_wrong_email_pass).setCancelable(
                    false).setPositiveButton("OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                        }
                    });

            AlertDialog alert = alt_bld.create();
            alert.setTitle(R.string.app_name);
            alert.show();

            return;
        }

        Intent intent = new Intent(this, TabBarActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();

    }

    void cancel(){

        finish();
    }
}
