package com.brosis.doubledate.Profile;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.brosis.doubledate.CodeClasses.Functions;
import com.brosis.doubledate.Profile.EditProfile.EditProfile_F;
import com.brosis.doubledate.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;

public class EditProfileNew extends AppCompatActivity {

    private static final int MY_CAMERA_REQUEST_CODE = 100;
    private File imgUrl;
    private ImageView ivUploadImage;
    private Button tvTakePhoto;
    private TextView tvImagePath,tvImageBitmapPath;
    private Bitmap bitmap;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_layut);
        tvImagePath=(findViewById(R.id.tvImagePath));
        tvImageBitmapPath=(findViewById(R.id.tvImageBitmapPath));
        tvTakePhoto=(findViewById(R.id.tvTakePhoto));
        ivUploadImage=(findViewById(R.id.ivUploadImage));
        tvTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });
    }


    /*Upload Event Image */

    private void selectImage() {
        if (ContextCompat.checkSelfPermission(EditProfileNew.this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.CAMERA},
                        MY_CAMERA_REQUEST_CODE);
            }
        }
        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(EditProfileNew.this);
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                try {
                    if (options[item].equals("Take Photo")) {
                        //  EasyImage.openCamera(getActivity(), 100);
                        EasyImage.openCamera(EditProfileNew.this, 100);
                        //EasyImage.openCamera(getFragmentManager().findFragmentById(R.id.containerView), 100);
                    } else if (options[item].equals("Choose from Gallery")) {
                        EasyImage.openGallery(EditProfileNew.this, 200);
                        //  EasyImage.openGallery(getFragmentManager().findFragmentById(R.id.containerView), 200);
                    } else if (options[item].equals("Cancel")) {
                        dialog.dismiss();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        builder.show();

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {

            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            handleCrop(result.getUri());
        }

        EasyImage.handleActivityResult(requestCode, resultCode, data, EditProfileNew.this, new DefaultCallback() {
            @Override
            public void onImagePickerError(Exception e, EasyImage.ImageSource source, int type) {
                //Some error handling
                e.printStackTrace();
            }

            @Override
            public void onImagePicked(File imageFile, EasyImage.ImageSource source, int type) {
                InputStream stream = null;
                if (type == 100) {

                    imgUrl = new File(imageFile.getAbsolutePath());
                    Log.e("imgUrl", "100:" + imgUrl);
                    tvImagePath.setText(imgUrl+"");

                    Uri selectedImage = Uri.fromFile(imgUrl);
                    Log.e("selectedImage", "Uri:" + Uri.fromFile(imgUrl).toString());
                    beginCrop(selectedImage);


                    Log.e("selectedImage", "Uri:" + Uri.fromFile(imgUrl).toString());
                    Log.e("selectedImage", "100:" + bitmap);
                    Toast.makeText(EditProfileNew.this,""+imgUrl,Toast.LENGTH_SHORT).show();
                    //imageFile.getAbsolutePath()
                    //Bitmap photo = (Bitmap) data.getExtras().get("data");

                    // CALL THIS METHOD TO GET THE URI FROM THE BITMAP
                    //Uri tempUri = getImageUri(getApplicationContext(), photo);

                    //Show Uri path based on Image
                    //Toast.makeText(EditProfileNew.this, "Here " + tempUri, Toast.LENGTH_LONG).show();

                    /*Glide.with(EditProfileNew.this)
                            .load(bitmap)
                            .apply(new RequestOptions())
                            .into(ivUploadImage);*/
                } else {
                    imgUrl = new File(imageFile.getAbsolutePath());
                    //Uri selectedImage = data.getData();
                    Log.e("imgUrl", "200:" + imgUrl);
                    Toast.makeText(EditProfileNew.this,""+imgUrl,Toast.LENGTH_SHORT).show();
                    //Log.e("selectedImage", "200:" + selectedImage);
                    Glide.with(EditProfileNew.this)
                            .load(imageFile.getAbsolutePath())
                            .apply(new RequestOptions())
                            .into(ivUploadImage);
                }
            }

            @Override
            public void onCanceled(EasyImage.ImageSource source, int type) {
                //Cancel handling, you might wanna remove taken photo if it was canceled
                if (source == EasyImage.ImageSource.CAMERA) {
                    File photoFile = EasyImage.lastlyTakenButCanceledPhoto(EditProfileNew.this);
                    if (photoFile != null) photoFile.delete();
                }
            }
        });
    }


    // botoom there function are related to crop the image
    private void beginCrop(Uri source) {
        CropImage.activity(source)
                .start(EditProfileNew.this);
    }

    private void handleCrop( Uri userimageuri) {

        InputStream imageStream = null;
        try {
            imageStream =EditProfileNew.this.getContentResolver().openInputStream(userimageuri);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        final Bitmap imagebitmap = BitmapFactory.decodeStream(imageStream);

        String path=userimageuri.getPath();
        Matrix matrix = new Matrix();
        android.media.ExifInterface exif = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            try {
                exif = new android.media.ExifInterface(path);
                int orientation = exif.getAttributeInt(android.media.ExifInterface.TAG_ORIENTATION, 1);
                switch (orientation) {
                    case android.media.ExifInterface.ORIENTATION_ROTATE_90:
                        matrix.postRotate(90);
                        break;
                    case android.media.ExifInterface.ORIENTATION_ROTATE_180:
                        matrix.postRotate(180);
                        break;
                    case android.media.ExifInterface.ORIENTATION_ROTATE_270:
                        matrix.postRotate(270);
                        break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Bitmap rotatedBitmap = Bitmap.createBitmap(imagebitmap, 0, 0, imagebitmap.getWidth(), imagebitmap.getHeight(), matrix, true);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        rotatedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);

        String image_bas64= Functions.Bitmap_to_base64(EditProfileNew.this,rotatedBitmap);
        // image_byteArray = out.toByteArray();
        /*Glide.with(EditProfileNew.this)
                .load(image_bas64)
                .apply(new RequestOptions())
                .into(ivUploadImage);*/
        ivUploadImage.setImageBitmap(rotatedBitmap);
        tvImageBitmapPath.setText("Bitmap:"+rotatedBitmap);

        // SavePicture();
       // Call_Api_For_uploadLink();
    }

    private Uri getImageUri(Context applicationContext, Bitmap photo)
    {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        photo.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(EditProfileNew.this.getContentResolver(), photo, "Title", null);
        return Uri.parse(path);
    }

}
