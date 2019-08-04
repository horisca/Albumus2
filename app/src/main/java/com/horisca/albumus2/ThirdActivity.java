package com.horisca.albumus2;

import android.Manifest;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import com.horisca.albumus2.BitmapHelper.BitmapHelper;
import com.horisca.albumus2.BitmapHelper.BitmapHelper2;
import java.io.IOException;

public class ThirdActivity extends AppCompatActivity {

    ImageView imageView5, imageView8, imageView13;
    Button exit, join;
    Bitmap bitmap1, bitmap2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        ActivityCompat.requestPermissions(ThirdActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);

        bitmap1 = BitmapHelper.getInstance().getBitmap();
        bitmap2 = BitmapHelper2.getInstance().getBitmap();
        imageView13 = findViewById(R.id.imageView13);
        join = findViewById(R.id.join);
        exit = findViewById(R.id.exit);
        imageView5 = findViewById(R.id.imageView5);
        imageView5.setImageBitmap(bitmap1);
        imageView8 = findViewById(R.id.imageView8);
        imageView8.setImageBitmap(bitmap2);

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAffinity();
                System.exit(0);
            }
        });

        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bitmap1.getWidth() < bitmap1.getHeight())
                {
                    imageComb(bitmap1, bitmap2);
                }
                else
                {
                    imageComb2(bitmap1, bitmap2);
                }
                try {
                    savePhoto();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    //DE AICI INCEPE JOACAAAAAAAAAAAAAAAAAAAA

    private void savePhoto() throws IOException {

        AlertDialog.Builder builder = new AlertDialog.Builder(ThirdActivity.this);
        builder.setMessage("Photo has been saved successfully. ");
        builder.setTitle("Done! ");
        builder.setCancelable(false);

        builder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id)
            {
                dialog.cancel();
            }
        });
        Bitmap bitmap = imageView2Bitmap(imageView13);
     /*   File path = Environment.getExternalStorageDirectory();
        //create a new folder to save our photo in
        File dir = new File(path+"/savedPHOTO/");
        dir.mkdirs();
        //rename photo
        File file = new File(dir, "Photo.png"); */

        MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "photo" , "thePHOTO");

        /* OutputStream out;
        try {
            out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }*/
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public Bitmap imageComb(Bitmap bitmap1, Bitmap bitmap2) {

        Bitmap canvas;
        int width, height;
        if(bitmap1.getWidth() > bitmap2.getWidth()) {               //CHANGE THISSSSSSSSSSS
            width = bitmap1.getWidth() + bitmap2.getWidth();
            height = bitmap1.getHeight();
        } else {
            width = bitmap1.getWidth() + bitmap2.getWidth();
            height = bitmap2.getHeight(); //    alerta am schimbat asta din bitmap1 in bitmap2!!!!!!!!!!!!!!!!1 baga in update!!!!!!!!!!!1
        }

        canvas = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_4444);
        Canvas comboImage = new Canvas(canvas);

        if(bitmap1.getHeight()>bitmap1.getWidth()){
            comboImage.drawBitmap(bitmap1, 0f, 0f, null);
            comboImage.drawBitmap(bitmap2, bitmap1.getWidth(), 0f, null);
        }

        imageView13.setImageDrawable(new BitmapDrawable(getResources(), canvas));
        return canvas;
    }

    public Bitmap imageComb2(Bitmap bitmap1, Bitmap bitmap2) {

        Bitmap canvas;
        int width, height;

        if(bitmap1.getWidth() > bitmap2.getWidth()) {
            width = bitmap1.getWidth();
            height = bitmap1.getHeight() + bitmap2.getHeight();
        } else {
            width = bitmap2.getWidth();
            height = bitmap1.getHeight() + bitmap2.getHeight();
        }

        canvas = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_4444);
        Canvas comboImage = new Canvas(canvas);

        if(bitmap1.getWidth()>bitmap1.getHeight()){
            comboImage.drawBitmap(bitmap1, 0f, 0f, null);
            comboImage.drawBitmap(bitmap2, 0f, bitmap1.getHeight(), null);
        }

        imageView13.setImageDrawable(new BitmapDrawable(getResources(), canvas));
        return canvas;
    }

    //function below returns a bitmap value
    private Bitmap imageView2Bitmap(ImageView view) throws IOException {
        return ((BitmapDrawable)view.getDrawable()).getBitmap();
    }
}