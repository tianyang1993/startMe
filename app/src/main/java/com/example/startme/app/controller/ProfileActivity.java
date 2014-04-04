package com.example.startme.app.controller;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.startme.app.R;
import com.example.startme.app.Util;

public class ProfileActivity extends Activity {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_SELECT_PICTURE = 2;

    EditText mNameEdit = null;
    ImageView mProfileImageView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mNameEdit = (EditText)findViewById(R.id.name_edit);
        mProfileImageView = (ImageView)findViewById(R.id.profile_imageview);

        Button btnPicTake = (Button)findViewById(R.id.takepic_button);
        btnPicTake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });

        Button btnOpenlib = (Button)findViewById(R.id.openlib_button);
        btnOpenlib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchOpenLibraryIntent();
            }
        });

        Button btnFromfb = (Button)findViewById(R.id.fromfb_button);
        btnFromfb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        Button btnReset = (Button)findViewById(R.id.reset_button);
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        TextView btnCancel = (TextView)findViewById(R.id.button_profile_cancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        TextView btnDone = (TextView)findViewById(R.id.button_profile_done);
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                done();
            }
        });
    }

    void done(){

        Intent intent = new Intent(this, TabBarActivity.class);
        startActivity(intent);
        finish();
    }

    void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    void dispatchOpenLibraryIntent(){

        Intent intent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Select File"),REQUEST_SELECT_PICTURE);

    }

    @Override
    protected void onResume() {
        super.onResume();

        overridePendingTransition(R.anim.anim_right_slide_in, R.anim.anim_left_slide_out);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK){
            if (requestCode == REQUEST_IMAGE_CAPTURE) {
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                mProfileImageView.setImageBitmap(imageBitmap);
            }else if (requestCode == REQUEST_SELECT_PICTURE){

                Uri selectedImageUri = data.getData();
                String tempPath = Util.getPath(selectedImageUri, this);
                BitmapFactory.Options btmapOptions = new BitmapFactory.Options();
                Bitmap bm = BitmapFactory.decodeFile(tempPath, btmapOptions);
                mProfileImageView.setImageBitmap(bm);
            }
        }
    }
}
