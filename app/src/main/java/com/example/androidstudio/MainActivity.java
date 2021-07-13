package com.example.androidstudio;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.bumptech.glide.Glide;

import java.io.File;
import java.io.FileOutputStream;

public class MainActivity  extends AppCompatActivity {

    Button captureBtn,saveBtn;
    ImageView imageView;
    Bitmap photo;
    File output;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        captureBtn=findViewById(R.id.captureBtn);
        imageView=findViewById(R.id.imageView);
        saveBtn=findViewById(R.id.saveBtn);

        //run time permissions
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)  !=PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE},100);
        }



        captureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent cameraIntent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent,101);
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (photo != null) {

                    // external storage
//                    File file1=new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),"androidExample2.jpg");
//                    File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "dileep.jpg");

                    //internal storage

                    File internal=new File(getApplicationContext().getFilesDir(),"internalStoargeImage.jpg");
//                    File mediaStorageDir = new File(getApplicationContext().getFilesDir(), "VishwamFR");


                    try {
                        FileOutputStream outjhjdf = new FileOutputStream(internal);

                        photo.compress(Bitmap.CompressFormat.JPEG, 100, outjhjdf);


                        outjhjdf.flush();
                        outjhjdf.close();
                        Toast.makeText(MainActivity.this, "File is saved", Toast.LENGTH_SHORT).show();
                        Log.e("filePath:",internal.getPath());

                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(MainActivity.this, "Photo is empty", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==101){

            photo = (Bitmap) data.getExtras().get("data");

//            imageView.setImageBitmap(photo);
            Glide.with(MainActivity.this)
         //   Glide.with(MainActivity.this)
                    .load(photo)
                    .into(imageView);

        }



    }
}