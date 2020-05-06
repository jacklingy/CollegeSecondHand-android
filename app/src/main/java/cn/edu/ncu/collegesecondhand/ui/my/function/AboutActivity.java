package cn.edu.ncu.collegesecondhand.ui.my.function;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import cn.edu.ncu.collegesecondhand.R;

public class AboutActivity extends AppCompatActivity {

    LinearLayout layout_share;
    LinearLayout layout_update;
    Context mContext;
    boolean whetherUpdate = false;

    int latestVersionCode;
    String latestVersionName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        mContext=getApplicationContext();


        layout_share = findViewById(R.id.layout_share);
        layout_update = findViewById(R.id.check_update);
        layout_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                darkenBackground(0.7f);
                int currentVersionCode = getLocalVersionCode(mContext);
                String currentVersionName = getLocalVersionName(mContext);
                showUpdatePop(currentVersionName, currentVersionCode);
            }
        });
        layout_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent textIntent = new Intent(Intent.ACTION_SEND);
                textIntent.setType("text/plain");
                textIntent.putExtra(Intent.EXTRA_TEXT,getResources().getString(R.string.share_link));
                startActivity(Intent.createChooser(textIntent, "分享高校二手"));
            }
        });
    }


    public static int getLocalVersionCode(Context ctx) {
        int localVersion = 0;
        try {
            PackageInfo packageInfo = ctx.getApplicationContext()
                    .getPackageManager()
                    .getPackageInfo(ctx.getPackageName(), 0);
            localVersion = packageInfo.versionCode;
            Log.d("TAG", "当前版本号：" + localVersion);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return localVersion;
    }

    /**
     * 获取本地软件版本号名称
     */
    public static String getLocalVersionName(Context ctx) {
        String localVersion = "";
        try {
            PackageInfo packageInfo = ctx.getApplicationContext()
                    .getPackageManager()
                    .getPackageInfo(ctx.getPackageName(), 0);
            localVersion = packageInfo.versionName;
            Log.d("TAG", "当前版本名称：" + localVersion);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return localVersion;
    }

    public void showUpdatePop(final String currentVersionName, final int currentVersionCode) {


        //popup的view
        View contentView = LayoutInflater.from(getBaseContext())
                .inflate(R.layout.popup_layout_check_update, null);


        final TextView text_currentVersionName = contentView.findViewById(R.id.text_current_version_name);
        final TextView text_latestVersionName = contentView.findViewById(R.id.text_latest_version_name);
        final TextView text_hint_string = contentView.findViewById(R.id.text_hint_string);
        final Button button_checkUpdate = contentView.findViewById(R.id.button_check_update);

        ImageView img_close = contentView.findViewById(R.id.img_close_check_update);


        // final EditText editText=contentView.findViewById(R.id.edit);

        //  Button button_commit=contentView.findViewById(R.id.button_commit);

        final PopupWindow popupWindow = new PopupWindow(contentView, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

//
//

//        popupWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
//        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setFocusable(true);

        popupWindow.setContentView(contentView);

        popupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.round_background));

        View rootView = LayoutInflater.from(getBaseContext())
                .inflate(R.layout.activity_about, null);

        popupWindow.setTouchable(true);
        popupWindow.setAnimationStyle(R.style.pop_animation);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                darkenBackground(1f);
            }
        });


        img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                darkenBackground(1f);
            }
        });


        RequestQueue queue = Volley.newRequestQueue(getBaseContext());

        String urlCode = getResources().getString(R.string.version_address) +
                "latestCode.txt";
        StringRequest request = new StringRequest(Request.Method.GET, urlCode,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        latestVersionCode = Integer.parseInt(response);
                        Log.e("latest code", latestVersionCode + "");

                        if (latestVersionCode > currentVersionCode) {

                            whetherUpdate = true;

                        } else {
                            whetherUpdate = false;
                        }


                        RequestQueue queue = Volley.newRequestQueue(getBaseContext());

                        String urlCode = getResources().getString(R.string.version_address) +
                                "latestName.txt";
                        StringRequest request = new StringRequest(Request.Method.GET, urlCode,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        latestVersionName = response;


                                        text_currentVersionName.setText("当前版本为："+currentVersionName);
                                        text_latestVersionName.setText("最新版本为："+latestVersionName);



                                        if (whetherUpdate) {
                                            text_hint_string.setText("检查到新版本，请点击下方按钮更新！");
                                            button_checkUpdate.setText("立即更新");
                                            button_checkUpdate.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    //Toast start update
                                                    //pop dismiss
                                                    //notification download progress

                                                    Toast.makeText(getBaseContext(), "已开始更新，请到通知栏查看进度！",
                                                            Toast.LENGTH_SHORT).show();
                                                    popupWindow.dismiss();
                                                    darkenBackground(1f);
                                                    String url="http://116.62.47.202/apk/latest.apk";

                                                    downLoadApk("高校二手",url,"高校二手APP正在下载");
                                                    //
//                                                    String path = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator;
//                                                    // mDownloadUrl为JSON从服务器端解析出来的下载地址
//                                                    RequestParams requestParams = new RequestParams(url);
//                                                    // 为RequestParams设置文件下载后的保存路径
//                                                    requestParams.setSaveFilePath(path);
//                                                    // 下载完成后自动为文件命名
//                                                    requestParams.setAutoRename(true);

                                                }
                                            });
                                        } else {
                                            text_hint_string.setText("已是最新版本无需更新！");
                                            button_checkUpdate.setText("我知道了");
                                            button_checkUpdate.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    //dismiss
                                                    popupWindow.dismiss();
                                                    darkenBackground(1f);

                                                }
                                            });


                                        }
                                        Log.e("latest code", latestVersionName);
                                        // textView.setText("最新版本为：" + latestVersionName);


                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(getBaseContext(), "error", Toast.LENGTH_SHORT).show();
                            }
                        });
                        try {
                            queue.add(request);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getBaseContext(), "error", Toast.LENGTH_SHORT).show();
            }
        });
        try {
            queue.add(request);
        } catch (Exception e) {
            e.printStackTrace();
        }


        // text_currentVersionName.setText("当前版本为：" + currentVersion);
        //  getLatestName(text_latestVersionName);
        //  text_latestVersionName.setText("最新版本为：" + latestVersion);



        popupWindow.showAtLocation(rootView, Gravity.BOTTOM, 0, 0);

    }


    private void darkenBackground(Float backgroundColor) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = backgroundColor;
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        getWindow().setAttributes(lp);
    }

    public void getLatestCode() {

        RequestQueue queue = Volley.newRequestQueue(getBaseContext());

        String urlCode = getResources().getString(R.string.version_address) +
                "latestCode.txt";
        StringRequest request = new StringRequest(Request.Method.GET, urlCode,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        latestVersionCode = Integer.parseInt(response);
                        Log.e("latest code", latestVersionCode + "");


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getBaseContext(), "error", Toast.LENGTH_SHORT).show();
            }
        });
        try {
            queue.add(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getLatestName(final TextView textView) {
        RequestQueue queue = Volley.newRequestQueue(getBaseContext());

        String urlCode = getResources().getString(R.string.version_address) +
                "latestName.txt";
        StringRequest request = new StringRequest(Request.Method.GET, urlCode,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        latestVersionName = response;
                        Log.e("latest code", latestVersionName);
                        textView.setText("最新版本为：" + latestVersionName);


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getBaseContext(), "error", Toast.LENGTH_SHORT).show();
            }
        });
        try {
            queue.add(request);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    private long downLoadApk(String title,String url, String description) {
        //创建request对象
        DownloadManager.Request request=new DownloadManager.Request(Uri.parse(url));
        //设置什么网络情况下可以下载
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI);
        //设置通知栏的标题
        request.setTitle(title);
        //设置通知栏的message
        request.setDescription(description);
        //设置漫游状态下是否可以下载
        request.setAllowedOverRoaming(false);
        //设置文件存放目录
        request.setDestinationInExternalFilesDir(this, Environment.DIRECTORY_DOWNLOADS,"update.apk");
        //获取系统服务
        DownloadManager
                downloadManager= (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        //进行下载

        return downloadManager.enqueue(request);
    }

}
