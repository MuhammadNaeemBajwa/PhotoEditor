package com.smlab;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class UploadPhoto extends AppCompatActivity {

    Button btnCamera, btnGallery;
    private static final int CAMERA_REQUEST = 52;
    private static final int PICK_REQUEST = 53;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_photo);

        initialize();
        setIds();
        setListener();
    }

    private void initialize() {
    }

    private void setIds() {
        btnCamera = findViewById(R.id.open_camera_button);
        btnGallery = findViewById(R.id.upload_image_button);

    }

    private void setListener() {

//        btnCamera.setOnClickListener(v -> startActivity(new Intent(UploadPhoto.this, EditImageActivity.class)));
        btnCamera.setOnClickListener(v -> {
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, CAMERA_REQUEST);
        });
        btnGallery.setOnClickListener(v -> {
            Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(galleryIntent, PICK_REQUEST);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");

            Intent intent = new Intent(UploadPhoto.this, GetStarted.class);
            intent.putExtra("image", photo);
            startActivity(intent);
        } else if (requestCode == PICK_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            Intent intent = new Intent(UploadPhoto.this, GetStarted.class);
            intent.putExtra("imageUri", selectedImageUri.toString());
            startActivity(intent);
        }
    }
}