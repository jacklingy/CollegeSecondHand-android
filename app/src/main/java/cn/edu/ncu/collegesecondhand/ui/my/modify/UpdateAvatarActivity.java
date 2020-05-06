package cn.edu.ncu.collegesecondhand.ui.my.modify;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.zxy.tiny.Tiny;
import com.zxy.tiny.callback.FileCallback;

import org.json.JSONException;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Date;

import cn.edu.ncu.collegesecondhand.R;
import cn.edu.ncu.collegesecondhand.util.FileUtil;
import cn.edu.ncu.collegesecondhand.util.UploadImageUtil;
import es.dmoral.toasty.Toasty;

public class UpdateAvatarActivity extends AppCompatActivity {
    private static final String TAG = "UpdateAvatarActivity";
    public static final int REQUEST_CODE_PICK = 1;
    public static final int REQUEST_CODE_CAPTURE = 2;
    public static final int REQUEST_CODE_AVATAR = 1;

    Toolbar toolbar;
    private Button button_capture;
    private Button button_pick;
    private Button button_finish;
    private SharedPreferences sharedPreferences;
    private ImageView imageView_avatar;


    //images
    private Uri capturedPhotoUri;
    private File outputImage;
    private File compressed;
    private Uri cameraPhotoUri;

    private String netPath = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_avatar);
        verifyStoragePermissions(this);
        Intent intent = getIntent();
        String avatar = intent.getStringExtra("avatar");
        final int id = intent.getIntExtra("id", 0);

        //Toasty.info(this,"id: "+id,Toast.LENGTH_SHORT).show();


        //   Log.e(TAG, "onCreate: "+getExternalCacheDir().toString() );

        toolbar = findViewById(R.id.update_avatar_toolbar);
        button_capture = findViewById(R.id.update_avatar_capture);
        button_pick = findViewById(R.id.update_avatar_pick);
        button_finish = findViewById(R.id.update_avatar_finish);
        imageView_avatar = findViewById(R.id.update_avatar_image);
        Glide.with(this)
                .load(avatar)
                .centerCrop()
                .into(imageView_avatar);

        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //
        autoObtainCameraPermission();

        //take photo directly

        button_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (netPath == null) {
                    //Toasty.error(getBaseContext(), "path null!", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    //  Toasty.success(getBaseContext(), "path: " + netPath, Toast.LENGTH_SHORT).show();


                    //update info
                    String url = getResources().getString(R.string.ip_address) + "/user/updateAvatar?id=" +
                            id + "&avatar=" + netPath;
                    RequestQueue queue = Volley.newRequestQueue(getBaseContext());
                    StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            if (response.contains("0")) {
                                Intent data = new Intent();
                                data.putExtra("avatarUrl", netPath);
                                Toasty.success(getBaseContext(), "修改头像成功！", Toast.LENGTH_SHORT).show();
                                setResult(RESULT_OK, data);

                                finish();
                            } else {
                                Toasty.error(getBaseContext(), "请重试！", Toast.LENGTH_SHORT).show();

                            }


                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toasty.error(getBaseContext(), "请检查网络后重试！", Toast.LENGTH_SHORT).show();

                        }
                    });
                    queue.add(request);
                }
            }
        });
        //pick
        button_pick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //verifyStoragePermissions(getParent());
                // autoObtainStoragePermission();

                /**
                 * todo 选取照片时 没有权限
                 */
                verifyStoragePermissions2(UpdateAvatarActivity.this);
                Intent intent2 = new Intent(Intent.ACTION_PICK, null);
                intent2.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent2, REQUEST_CODE_PICK);


            }
        });
        //take photo
        button_capture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //删除这个文件夹里的所有文件
            /*    try {
                    File[] files=getExternalCacheDir().listFiles();
                    for (File f:files){
                        f.delete();
                    }

                }catch (Exception e){
                    e.printStackTrace();

                }*/


                /**todo
                 * 上传成功后删掉文件夹里的文件 避免占用太多空间
                 */
                //permission
                if (!checkCameraPermission()) {
                   // Toasty.error(getBaseContext(),"相机权限未获取，请授予权限后再试！",Toast.LENGTH_SHORT).show();
                    autoObtainCameraPermission();
                }

                String time = System.currentTimeMillis() + "";
                time = time.substring(time.length() - 7, time.length() - 1);
                outputImage = new File(getExternalCacheDir(), time + "output_image.jpg");
                compressed = new File(getExternalCacheDir(), time + "compressed_image.jpg");


                //判断文件是否存在

                //判断当前Android版本
                capturedPhotoUri = FileProvider.getUriForFile(getBaseContext(),
                        "cn.edu.ncu.collegesecondhand.FileProvider", outputImage);


                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, capturedPhotoUri);

                startActivityForResult(intent, REQUEST_CODE_CAPTURE);


            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //  Toast.makeText(this, "666", Toast.LENGTH_SHORT).show();


        switch (requestCode) {

            case REQUEST_CODE_CAPTURE:
                //    Toast.makeText(this, "777", Toast.LENGTH_SHORT).show();
                if (resultCode == RESULT_OK) {
                    //  Toast.makeText(this, "666", Toast.LENGTH_SHORT).show();

                    cameraPhotoUri = Uri.fromFile(outputImage);
                    Glide.with(this)
                            .load(cameraPhotoUri)
                            .centerCrop()
                            .into(imageView_avatar);
                    String photoLocalPath = cameraPhotoUri.toString();
                    Log.e(TAG, "onActivityResult: captured photo local path:" + photoLocalPath);
                    String localPath_trim = photoLocalPath.substring(7);//去掉file前缀后的地址，也可以用getExternal..代替
                    Tiny.FileCompressOptions options_camera = new Tiny.FileCompressOptions();
                    Tiny.getInstance().source(localPath_trim)
                            .asFile()
                            .withOptions(options_camera)
                            .compress(new FileCallback() {
                                @Override
                                public void callback(boolean isSuccess, final String outfile, Throwable t) {
                                    if (isSuccess) {
                                        new Thread(new Runnable() {
                                            @Override
                                            public void run() {
                                                try {
                                                    netPath = UploadImageUtil.uploadImage(
                                                            getResources().
                                                                    getString(R.string.uploadInterface),
                                                            outfile);
                                                    netPath = getResources().
                                                            getString(R.string.avatar_address) + netPath;

                                                } catch (IOException e) {
                                                    Toasty.error(getBaseContext(), "图片上传失败，请检查网络后重试！", Toast.LENGTH_SHORT).show();

                                                    e.printStackTrace();
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }


                                            }
                                        }
                                        ).start();


                                    } else {
                                        Toasty.error(getBaseContext(), "图片压缩失败，请重试！",
                                                Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });


                }
                break;
            case REQUEST_CODE_PICK:
                autoObtainStoragePermission();
                Uri uri_pick = data.getData();
                //show the picked image
                Glide.with(getBaseContext())
                        .load(uri_pick)
                        .centerCrop()
                        .into(imageView_avatar);


                final String pickedPath = FileUtil.getFilePathByUri(this, uri_pick);
                //压缩
                final Tiny.FileCompressOptions options = new Tiny.FileCompressOptions();
                Tiny.getInstance()
                        .source(pickedPath)
                        .asFile()
                        .withOptions(options)
                        .compress(new FileCallback() {
                            @Override
                            public void callback(boolean isSuccess, final String outfile, Throwable t) {
                                if (isSuccess) {
                                    new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            try {
                                                netPath = UploadImageUtil.uploadImage(getResources()
                                                        .getString(R.string.uploadInterface), outfile);
                                                netPath = getResources()
                                                        .getString(R.string.avatar_address) + netPath;
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            } catch (JSONException e) {
                                                Toasty.error(getBaseContext(), "图片上传失败，请检查网络后重试！", Toast.LENGTH_SHORT).show();

                                                e.printStackTrace();
                                            }


                                        }
                                    }).start();


                                } else {
                                    Toasty.error(getBaseContext(), "图片压缩失败，请重新选择！" + t.toString(), Toast.LENGTH_SHORT).show();
                                }

                            }
                        });


                break;
        }
    }

    /**
     * 自动获取相机权限
     */


    private void autoObtainCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE}
                    , 1);
        } else { //有权限直接调用系统相机拍照
        }
    }

    private boolean checkCameraPermission() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;

    }

    private void autoObtainStoragePermission() {

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}
                , 2);

    }


    public void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission


        String[] PERMISSIONS_STORAGE = {
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };

        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE, 3);
        }
    }

    public static void verifyStoragePermissions2(Activity activity) {
        // Check if we have write permission
        String[] PERMISSIONS_STORAGE = {
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };
        int permission = ActivityCompat.checkSelfPermission(activity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);//缺少什么权限就写什么权限
        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE,
                    4);
        }
    }


}
