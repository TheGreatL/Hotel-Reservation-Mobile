package com.example.hotel_reservation.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ImageUtils {
    public static byte[] getImageDataFromUri(Context context, Uri imageUri) {
        byte[] imageData = null;
        ContentResolver contentResolver = context.getContentResolver();

        try (InputStream inputStream = contentResolver.openInputStream(imageUri)) {
            if (inputStream != null) {
                // Convert the input stream into a bitmap
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

                // Convert the bitmap into a byte array
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                imageData = byteArrayOutputStream.toByteArray();
            }
        } catch (IOException e) {
            Log.e("ImageUtils", "Error reading image", e);
        }

        return imageData;
    }
    public  static Bitmap convertByteIntoBitmap(byte[] image){
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }
}
