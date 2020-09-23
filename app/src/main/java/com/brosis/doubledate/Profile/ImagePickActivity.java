package com.brosis.doubledate.Profile;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.brosis.doubledate.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ImagePickActivity extends AppCompatActivity {

    private static final int MY_CAMERA_REQUEST_CODE = 100;
    private File imgUrl;
    private ImageView ivUploadImage;
    private Button tvTakePhoto;
    private TextView tvImagePath;
    //
    private static final int REQUEST_CODE = 1;
    private Bitmap bitmap;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_layut);
        tvImagePath=(findViewById(R.id.tvImagePath));
        tvTakePhoto=(findViewById(R.id.tvTakePhoto));
        ivUploadImage=(findViewById(R.id.ivUploadImage));
        tvTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        InputStream stream = null;
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            try {
                // recyle unused bitmaps
                if (bitmap != null) {
                    bitmap.recycle();
                }
                stream = getContentResolver().openInputStream(data.getData());
                bitmap = BitmapFactory.decodeStream(stream);


                ivUploadImage.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
