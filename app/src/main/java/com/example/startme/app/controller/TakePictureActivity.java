package com.example.startme.app.controller;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.net.Uri;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.startme.app.R;
import com.example.startme.app.Util;
import com.example.startme.app.other.gpucamera.CameraHelper;
import com.example.startme.app.other.gpucamera.GPUImageFilterTools;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import jp.co.cyberagent.android.gpuimage.GPUImage;
import jp.co.cyberagent.android.gpuimage.GPUImageFilter;

public class TakePictureActivity extends Activity {

    private static final int FLASH_MODE_ON = 1;
    private static final int FLASH_MODE_OFF = 2;
    private static final int FLASH_MODE_AUTO = 3;

    private static final int REQUEST_SELECT_PICTURE = 1;

    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;

    private GPUImage mGPUImage;
    private CameraHelper mCameraHelper;
    private CameraLoader mCamera;
    private GPUImageFilter mFilter;
    private GPUImageFilterTools.FilterAdjuster mFilterAdjuster;

    int filter_image_ids[] = {
        R.drawable.filter1, R.drawable.filter2, R.drawable.filter3, R.drawable.filter4, R.drawable.filter5,
        R.drawable.filter6, R.drawable.filter7, R.drawable.filter8, R.drawable.filter9, R.drawable.filter10
    };

    boolean isBlur = false;
    boolean isFilterOpened = false;
    int     nFlashMode = FLASH_MODE_OFF;

    ImageButton btnFilter = null;
    ImageButton btnFlash = null;
    ImageButton btnBlur = null;

    FrameLayout filterGallery = null;
    HorizontalScrollView filterScrollView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_picture);

        mGPUImage = new GPUImage(this);
        mGPUImage.setGLSurfaceView((GLSurfaceView) findViewById(R.id.surfaceView));

        mCameraHelper = new CameraHelper(this);
        mCamera = new CameraLoader();

        filterScrollView = (HorizontalScrollView)findViewById(R.id.horizontalScrollView);
        filterGallery = (FrameLayout) findViewById(R.id.gallery_filter);

        for (int i = 0; i < 10; i++ ){

            ImageView imageView = new ImageView(getApplicationContext());
            imageView.setId(i);
            FrameLayout.LayoutParams layoutParam = new FrameLayout.LayoutParams(100, FrameLayout.LayoutParams.MATCH_PARENT);
            layoutParam.gravity = Gravity.LEFT| Gravity.TOP;
            layoutParam.leftMargin = 20 + 120 * i;
            layoutParam.topMargin = 0;
            imageView.setLayoutParams(layoutParam);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setBackgroundResource(filter_image_ids[i]);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    GPUImageFilterTools.selectFilter(TakePictureActivity.this, view.getId(), new GPUImageFilterTools.OnGpuImageFilterChosenListener() {
                        @Override
                        public void onGpuImageFilterChosenListener(GPUImageFilter filter) {
                            switchFilterTo(filter);
                        }
                    });
                }
            });

            filterGallery.addView(imageView);
        }

        filterScrollView.setVisibility(isFilterOpened?View.VISIBLE:View.INVISIBLE);

        ImageButton btnClose = (ImageButton)findViewById(R.id.button_pic_close);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnBlur = (ImageButton)findViewById(R.id.button_pic_blur);
        btnBlur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isBlur){
                    isBlur = false;
                    btnBlur.setBackgroundResource(R.drawable.blur);

                }else {
                    isBlur = true;
                    btnBlur.setBackgroundResource(R.drawable.blur_on);
                }

            }
        });

        ImageButton btnCamera = (ImageButton)findViewById(R.id.button_pic_camera_change);
        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mCamera.switchCamera();
            }
        });

        btnFlash = (ImageButton)findViewById(R.id.button_pic_flash);
        btnFlash.setBackgroundResource(R.drawable.flash_off);
        btnFlash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(nFlashMode == FLASH_MODE_OFF){

                    nFlashMode = FLASH_MODE_ON;
                    btnFlash.setBackgroundResource(R.drawable.flash);
                }else if (nFlashMode == FLASH_MODE_ON){
                    nFlashMode = FLASH_MODE_AUTO;
                    btnFlash.setBackgroundResource(R.drawable.flash_auto);

                }else if (nFlashMode == FLASH_MODE_AUTO){

                    nFlashMode = FLASH_MODE_OFF;
                    btnFlash.setBackgroundResource(R.drawable.flash_off);
                }

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
                if (mCamera.mCameraInstance.getParameters().getFocusMode().equals(
                        Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE)) {
                    takePicture();
                } else {
                    mCamera.mCameraInstance.autoFocus(new Camera.AutoFocusCallback() {

                        @Override
                        public void onAutoFocus(final boolean success, final Camera camera) {
                            takePicture();
                        }
                    });
                }

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

                filterScrollView.setVisibility(isFilterOpened?View.VISIBLE:View.INVISIBLE);
                filterScrollView.invalidate();
            }
        });
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();

    }

    @Override
    protected void onResume() {
        super.onResume();
        mCamera.onResume();
    }

    @Override
    protected void onPause() {
        mCamera.onPause();
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

    private void takePicture() {
        // TODO get a size that is about the size of the screen
        Camera.Parameters params = mCamera.mCameraInstance.getParameters();
        params.setPictureSize(1280, 960);
        params.setRotation(90);
        mCamera.mCameraInstance.setParameters(params);
        for (Camera.Size size2 : mCamera.mCameraInstance.getParameters()
                .getSupportedPictureSizes()) {
            Log.i("ASDF", "Supported: " + size2.width + "x" + size2.height);
        }
        mCamera.mCameraInstance.takePicture(null, null,
                new Camera.PictureCallback() {

                    @Override
                    public void onPictureTaken(byte[] data, final Camera camera) {

                        final File pictureFile = getOutputMediaFile(MEDIA_TYPE_IMAGE);
                        if (pictureFile == null) {
                            Log.d("ASDF",
                                    "Error creating media file, check storage permissions");
                            return;
                        }

                        try {
                            FileOutputStream fos = new FileOutputStream(pictureFile);
                            fos.write(data);
                            fos.close();
                        } catch (FileNotFoundException e) {
                            Log.d("ASDF", "File not found: " + e.getMessage());
                        } catch (IOException e) {
                            Log.d("ASDF", "Error accessing file: " + e.getMessage());
                        }

                        data = null;
                        Bitmap bitmap = BitmapFactory.decodeFile(pictureFile
                                .getAbsolutePath());
                        // mGPUImage.setImage(bitmap);
                        final GLSurfaceView view = (GLSurfaceView) findViewById(R.id.surfaceView);
                        view.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
                        mGPUImage.saveToPictures(bitmap, "GPUImage",
                                System.currentTimeMillis() + ".jpg",
                                new GPUImage.OnPictureSavedListener() {

                                    @Override
                                    public void onPictureSaved(final Uri
                                                                       uri) {
                                        pictureFile.delete();
                                        camera.startPreview();
                                        view.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
                                    }
                                });
                    }
                });
    }

    private static File getOutputMediaFile(final int type) {
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "MyCameraApp");
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("MyCameraApp", "failed to create directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "IMG_" + timeStamp + ".jpg");
        } else if (type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "VID_" + timeStamp + ".mp4");
        } else {
            return null;
        }

        return mediaFile;
    }

    private void switchFilterTo(final GPUImageFilter filter) {
        if (mFilter == null
                || (filter != null && !mFilter.getClass().equals(filter.getClass()))) {
            mFilter = filter;
            mGPUImage.setFilter(mFilter);
            mFilterAdjuster = new GPUImageFilterTools.FilterAdjuster(mFilter);
        }
    }

    private class CameraLoader {
        private int mCurrentCameraId = 0;
        private Camera mCameraInstance;

        public void onResume() {
            setUpCamera(mCurrentCameraId);
        }

        public void onPause() {
            releaseCamera();
        }

        public void switchCamera() {
            releaseCamera();
            mCurrentCameraId = (mCurrentCameraId + 1) % mCameraHelper.getNumberOfCameras();
            setUpCamera(mCurrentCameraId);
        }

        private void setUpCamera(final int id) {
            mCameraInstance = getCameraInstance(id);
            Camera.Parameters parameters = mCameraInstance.getParameters();
            // TODO adjust by getting supportedPreviewSizes and then choosing
            // the best one for screen size (best fill screen)
            parameters.setPreviewSize(720, 480);
            if (parameters.getSupportedFocusModes().contains(
                    Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE)) {
                parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
            }
            mCameraInstance.setParameters(parameters);

            int orientation = mCameraHelper.getCameraDisplayOrientation(
                    TakePictureActivity.this, mCurrentCameraId);
            CameraHelper.CameraInfo2 cameraInfo = new CameraHelper.CameraInfo2();
            mCameraHelper.getCameraInfo(mCurrentCameraId, cameraInfo);
            boolean flipHorizontal = cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT
                    ? true : false;
            mGPUImage.setUpCamera(mCameraInstance, orientation, flipHorizontal, false);
        }

        /** A safe way to get an instance of the Camera object. */
        private Camera getCameraInstance(final int id) {
            Camera c = null;
            try {
                c = mCameraHelper.openCamera(id);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return c;
        }

        private void releaseCamera() {
            mCameraInstance.setPreviewCallback(null);
            mCameraInstance.release();
            mCameraInstance = null;
        }
    }
}
