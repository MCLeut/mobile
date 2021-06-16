package com.gamecodeschool.simplephoto;

import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import androidx.core.content.FileProvider;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static android.os.Environment.getExternalStoragePublicDirectory;

public class MainActivity extends Activity {

    private static final int CAMERA_REQUEST = 123;

    private String currentPhotoPath;
    private Uri photoURI;

    private LinearLayout linLayout;

    private ArrayList<ImageView> imageViews = new ArrayList<>();

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        linLayout = findViewById(R.id.linLayout);

        Button photoButton = findViewById(R.id.button1);
        photoButton.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                File photoFile = null;
                try {
                    photoFile = createImageFile();
                } catch (IOException ex) {
                    // Error occurred while creating the File
                    Log.e("camera app", "oh no", ex);
                }

                if (photoFile != null) {
                    photoURI = FileProvider.getUriForFile(MainActivity.this,
                            "com.gamecodeschool.simplephoto.fileprovider",
                            photoFile);
                    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                    startActivityForResult(cameraIntent, CAMERA_REQUEST);;
                }



            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {

            // create new imageview to add to ScrollView's linear layout
            ImageView iv = new ImageView(this);
            iv.setImageURI(photoURI);

            // create layout parameters for ImageView - center iv in the linear layout of the scroll view
            LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            layoutParams.width = linLayout.getWidth(); // there is probably a more elegant way but this works for now
            iv.setLayoutParams(layoutParams);

            // creating a humanly well readable timestamp
            String timeStamp = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy").format(new Date());

            // create text view and timestamp to display the pic's timestamp
            TextView tv = new TextView(this);
            tv.setLayoutParams(layoutParams);
            tv.setGravity(Gravity.CENTER); // center the text view
            tv.setText("Timestamp: " + timeStamp);

            // finally add both to the scroll view
            linLayout.addView(iv);
            linLayout.addView(tv);

            // for good practice, add the iv to an arraylist of all ImageViews
            imageViews.add(iv);

            //add picture to gallery
            galleryAddPic();
        }
    }
    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(currentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }

    // no options menu needed - no actionbar to display it in
}
