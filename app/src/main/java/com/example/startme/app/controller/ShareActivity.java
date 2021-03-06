package com.example.startme.app.controller;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.startme.app.R;

import org.w3c.dom.Text;

public class ShareActivity extends Activity {

    private static final int SHARE_TYPE_PICTURE = 0;
    private static final int SHARE_TYPE_VIDEO = 1;
    private static final int MAX_DETAIL_TEXT = 450;

    int mShareType = SHARE_TYPE_PICTURE;

    EditText editTitle = null;
    EditText editDetail = null;
    TextView textCount = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);

        TextView btnCancel = (TextView)findViewById(R.id.button_share_cancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        TextView btnSend = (TextView)findViewById(R.id.button_share_send);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                send();
            }
        });

        ImageButton btnAdd = (ImageButton)findViewById(R.id.button_share_add);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add();
            }
        });

        editTitle = (EditText)findViewById(R.id.edit_share_title);
        editTitle.setHint("Title's post");

        editDetail = (EditText)findViewById(R.id.edit_share_detail);
        editDetail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                textCount.setText(String.valueOf(MAX_DETAIL_TEXT - s.length()));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        textCount = (TextView)findViewById(R.id.text_share_count);
        textCount.setText(String.valueOf(MAX_DETAIL_TEXT));

    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();

    }

    @Override
    protected void onResume(){

        super.onResume();

        overridePendingTransition(R.anim.bottom_slide_in, 0);
    }

    void send(){
        finish();
    }

    void add(){

        final String items[] = { "Picture", "Video"};
        AlertDialog.Builder ab = new AlertDialog.Builder(this);
        ab.setTitle("Choose an attachment:");
        ab.setSingleChoiceItems(items, 0,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        mShareType = whichButton;
                    }
                }).setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                        switch (mShareType) {
                            case SHARE_TYPE_PICTURE:
                                dispatchTakePhoto();
                                break;
                            case SHARE_TYPE_VIDEO:
                                dispatchTakeVideo();
                                break;
                        }
                    }
                }).setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                });
        ab.show();
    }

    void dispatchTakeVideo(){

    }

    void dispatchTakePhoto(){

        Intent intent = new Intent(this, TakePictureActivity.class);
        startActivity(intent);

    }
}
