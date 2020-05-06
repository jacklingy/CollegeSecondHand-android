package cn.edu.ncu.collegesecondhand.util;

import android.util.Log;

import org.json.JSONException;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

;

public class UploadImageUtil {

    /**
     * 上传图片
     *
     * @param url
     * @param imagePath 图片路径
     * @return 新图片的路径
     * @throws IOException
     * @throws JSONException
     */
    public static String uploadImage(String url, String imagePath) throws IOException, JSONException {
        OkHttpClient okHttpClient = new OkHttpClient();
       // Log.d("imagePath", imagePath);
        File file = new File(imagePath);
        RequestBody image = RequestBody.create(MediaType.parse("image/png"), file);
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", imagePath, image)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Connection","close")
                .post(requestBody)

                .build();

        Response response = okHttpClient.newCall(request).execute();
      //  Log.d("*************:", response.body().string());
        String res=response.body().string();
        Log.d("上传后的名字是： ",res);

        return res;

//        JSONObject jsonObject = new JSONObject(response.body().string());
//        Log.d("*******", jsonObject.optString("image"));

//        return jsonObject.optString("image");
    }


}
