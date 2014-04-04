package com.example.startme.app.controller;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.startme.app.R;

public class SignUpActivity extends Activity {

    EditText txtFirstName = null;
    EditText txtLastName = null;
    EditText txtEmail = null;
    EditText txtConfirmEmail = null;
    EditText txtNewPass = null;
    EditText txtConfirmPass = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        Button btnSignIn = (Button)findViewById(R.id.button_signup);
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });

        Button btnCancel = (Button)findViewById(R.id.button_signup_cancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
            }
        });

        txtFirstName = (EditText)findViewById(R.id.edit_signup_firstname);
        txtLastName = (EditText)findViewById(R.id.edit_signup_lastname);
        txtEmail = (EditText)findViewById(R.id.edit_signup_email);
        txtConfirmEmail = (EditText)findViewById(R.id.edit_signup_confirm_email);
        txtNewPass = (EditText)findViewById(R.id.edit_signup_new_pass);
        txtConfirmPass = (EditText)findViewById(R.id.edit_signup_confirm_pass);

    }

    @Override
    protected void onStart() {
        super.onStart();
        overridePendingTransition(R.anim.anim_right_slide_in, R.anim.anim_left_slide_out);
    }

    void signUp(){

        String strFirstName = txtFirstName.getText().toString();
        String strLastName = txtLastName.getText().toString();
        String strEmail = txtEmail.getText().toString();
        String strConfirmEmail = txtConfirmEmail.getText().toString();
        String strNewPass = txtNewPass.getText().toString();
        String strConfirmPass = txtConfirmPass.getText().toString();

        boolean isWarning = false;
        int str_id = 0;

        if (strFirstName.compareTo("") == 0){
            isWarning = true;
            str_id = R.string.message_insert_vaild_name;
        }else if (strLastName.compareTo("") == 0){
            isWarning = true;
            str_id = R.string.message_insert_vaild_surname;
        }else if (strEmail.compareTo("") == 0){
            isWarning = true;
            str_id = R.string.message_insert_vaild_email;
        }else if (strConfirmEmail.compareTo("") == 0){
            isWarning = true;
            str_id = R.string.message_inequal_email;
        }

        if (isWarning == true){

            AlertDialog.Builder alt_bld = new AlertDialog.Builder(this);
            alt_bld.setMessage(str_id).setCancelable(
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

        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
        finish();
    }

    void cancel(){

        finish();
    }
}
