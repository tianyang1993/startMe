package com.example.startme.app.controller;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import com.example.startme.app.R;
import com.example.startme.app.Util;
import com.example.startme.app.view.CameraPreview;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class TakePictureActivity extends Activity {

    private static final int FLASH_MODE_ON = 1;
    private static final int FLASH_MODE_OFF = 2;
    private static final int FLASH_MODE_AUTO = 3;

    private static final int REQUEST_SELECT_PICTURE = 1;

    private static Camera mCamera;
    private CameraPreview mPreview;
    private Camera.Parameters mCameraParameters;

    boolean isFilterOpened = false;
    boolean isFrontCamera = false;
    int     nFlashMode = FLASH_MODE_OFF;

    ImageButton btnFilter = null;
    ImageButton btnFlash = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_picture);

        // Create an instance of Camera
        mCamera = getCameraInstance();
        mCameraParameters = mCamera.getParameters();
        mCameraParameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
        mCamera.setParameters(mCameraParameters);

        // Create our Preview view and set it as the content of our activity.
        mPreview = new CameraPreview(this, mCamera);
        FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
        preview.addView(mPreview);

        ImageButton btnClose = (ImageButton)findViewById(R.id.button_pic_close);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ImageButton btnBlur = (ImageButton)findViewById(R.id.button_pic_blur);
        btnBlur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        ImageButton btnCamera = (ImageButton)findViewById(R.id.button_pic_camera_change);
        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isFrontCamera){
                    isFrontCamera = false;
                }else{
                    isFrontCamera = true;
                }
            }
        });

        btnFlash = (ImageButton)findViewById(R.id.button_pic_flash);
        btnFlash.setBackgroundResource(R.drawable.flash_off);
        btnFlash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(nFlashMode == FLASH_MODE_OFF){

                    nFlashMode = FLASH_MODE_ON;
                    mCameraParameters.setFlashMode(Camera.Parameters.FLASH_MODE_ON);
                    btnFlash.setBackgroundResource(R.drawable.flash);
                }else if (nFlashMode == FLASH_MODE_ON){
                    nFlashMode = FLASH_MODE_AUTO;
                    mCameraParameters.setFlashMode(Camera.Parameters.FLASH_MODE_AUTO);
                    btnFlash.setBackgroundResource(R.drawable.flash_auto);

                }else if (nFlashMode == FLASH_MODE_AUTO){

                    nFlashMode = FLASH_MODE_OFF;
                    mCameraParameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                    btnFlash.setBackgroundResource(R.drawable.flash_off);
                }

                mCamera.setParameters(mCameraParameters);
            }
        });

        ImageButton btnOpenLib = (ImageButton)findViewById(R.id.button_pic_gallery);
        btnOpenLib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchOpenLibraryIntent();
            }
        });

        ImageButton btnShot = (ImageButton)findViewById(R.id.button_pic_shot);
        btnShot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnFilter = (ImageButton)findViewById(R.id.button_pic_filter);
        btnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isFilterOpened){
                    isFilterOpened = false;
                    btnFilter.setBackgroundResource(R.drawable.filter_open);
                }else{
                    isFilterOpened = true;
                    btnFilter.setBackgroundResource(R.drawable.filter_close);
                }
            }
        });
    }

    @Override
    protected void onPause() {

        if (mCamera != null){
            mCamera.release();
        }

        super.onPause();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK){
            if(requestCode == REQUEST_SELECT_PICTURE){

                Uri selectedImageUri = data.getData();
                String tempPath = Util.getPath(selectedImageUri, this);
                BitmapFactory.Options btmapOptions = new BitmapFactory.Options();
                Bitmap bm = BitmapFactory.decodeFile(tempPath, btmapOptions);
                //mProfileImageView.setImageBitmap(bm);
            }
        }
    }

    void dispatchOpenLibraryIntent(){

        Intent intent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Select File"),REQUEST_SELECT_PICTURE);

    }

    public static Camera getCameraInstance(){
        Camera c = null;
        try {
            c = Camera.open(); // attempt to get a Camera instance
        }
        catch (Exception e){
            // Camera is not available (in use or does not exist)
        }
        return c; // returns null if camera is unavailable
    }

    private Camera.PictureCallback mPicture = new Camera.PictureCallback() {

        @Override
        public void onPictureTaken(byte[] data, Camera camera) {

//            File pictureFile = getOutputMediaFile(MEDIA_TYPE_IMAGE);
//            if (pictureFile == null){
//                Log.d(TAG, "Error creating media file, check storage permissions: " +
//                        e.getMessage());
//                return;
//            }
//
//            try {
//                FileOutputStream fos = new FileOutputStream(pictureFile);
//                fos.write(data);
//                fos.close();
//            } catch (FileNotFoundException e) {
//                Log.d(TAG, "File not found: " + e.getMessage());
//            } catch (IOException e) {
//                Log.d(TAG, "Error accessing file: " + e.getMessage());
//            }
        }
    };
}
