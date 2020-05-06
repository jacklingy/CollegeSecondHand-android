package cn.edu.ncu.collegesecondhand.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class BitmapBase64Util {
    public static String bitToBase(Bitmap bitmap) {

        String result = "";
        ByteArrayOutputStream byteArrayOutputStream = null;

        try {
            if (bitmap != null) {
                byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);

                byteArrayOutputStream.flush();
                byteArrayOutputStream.close();

                byte[] bitmapBytes = byteArrayOutputStream.toByteArray();
                result = Base64.encodeToString(bitmapBytes, Base64.DEFAULT);


            }


        } catch (IOException e) {
            e.printStackTrace();

        }
        return result;


    }
    public static Bitmap base64ToBitmap(String base64Data){
        byte[] bytes=Base64.decode(base64Data,Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes,0,bytes.length);


    }
}
