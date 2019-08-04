package com.horisca.albumus2;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import com.horisca.albumus2.BitmapHelper.BitmapHelper;
import com.horisca.albumus2.BitmapHelper.BitmapHelper2;
import java.io.IOException;

public class SecondActivity extends AppCompatActivity {

    ImageView imageView, imageView2, imageView3, imageView7;
    Button button, convert2bitmap, button2, exit1;
    private static final int picimage=100, picimage2=99;
    Uri imageUri, imageUri2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        imageView = findViewById(R.id.imageView);
        button = findViewById(R.id.button);
        imageView2 = findViewById(R.id.imageView2);
        convert2bitmap = findViewById(R.id.convert2bitmap);
        imageView3 = findViewById(R.id.imageView3);
        imageView7 = findViewById(R.id.imageView7);
        button2 = findViewById(R.id.button2);
        exit1 = findViewById(R.id.exit1);

        exit1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                System.exit(0);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery2();
            }
        });

        convert2bitmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    imageView2.setImageBitmap(imageView2Bitmap(imageView2));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    imageView7.setImageBitmap(imageView2Bitmap2(imageView7));
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    BitmapHelper.getInstance().setBitmap(imageView2Bitmap(imageView2));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    BitmapHelper2.getInstance().setBitmap(imageView2Bitmap2(imageView7));
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Intent intent = new Intent(SecondActivity.this, ThirdActivity.class);
                startActivity(intent);
            }
        });
    }

    private void openGallery(){
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, picimage);
    }

    private void openGallery2(){
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, picimage2);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == picimage) {
            imageUri = data.getData();
            imageView.setImageURI(imageUri);
        }

        if(resultCode == RESULT_OK && requestCode == picimage2) {
            imageUri2 = data.getData();
            imageView3.setImageURI(imageUri2);
        }
    }

    //FUNCTIE DE CONVERTIT DE LA IMAGEVIEW LA BITMAP, pentru fiecare imageUri care va fi folosit. ai incredere, valoarea returnata va fi bitmap
    private Bitmap imageView2Bitmap(ImageView view) throws IOException {
        return MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
    }

    private Bitmap imageView2Bitmap2(ImageView view) throws IOException {
        return MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri2);
    }
}


