package cn.edu.ncu.collegesecondhand.ui.my.manage;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zxy.tiny.Tiny;
import com.zxy.tiny.callback.FileCallback;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import cn.edu.ncu.collegesecondhand.R;
import cn.edu.ncu.collegesecondhand.adapter.PopupCategoryAdapter;
import cn.edu.ncu.collegesecondhand.adapter.ReleaseImageAdapter;
import cn.edu.ncu.collegesecondhand.adapter.interf.OnPopupCateListener;
import cn.edu.ncu.collegesecondhand.adapter.interf.ReleaseImageClickListener;
import cn.edu.ncu.collegesecondhand.entity.Category;
import cn.edu.ncu.collegesecondhand.entity.Product;
import cn.edu.ncu.collegesecondhand.entity.ProductDetail;
import cn.edu.ncu.collegesecondhand.entity.ReleaseImage;
import cn.edu.ncu.collegesecondhand.util.FileUtil;
import cn.edu.ncu.collegesecondhand.util.UploadImageUtil;
import es.dmoral.toasty.Toasty;

public class ReleaseActivity extends AppCompatActivity {
    String edit;
    ProductDetail productDetail;

    RequestQueue queue;

    private static final String TAG = "ReleaseActivity";
    public static final int PICK_ONE_PHOTO = 1;
    public static final int DELETE_ONE_PHOTO = 11;
    private List<ReleaseImage> releaseImages = new ArrayList<>();
    private List<Category> categories = new ArrayList<>();


    private String name;
    private String description;
    private String images = "";
    private BigDecimal price = new BigDecimal("0");
    private BigDecimal originalPrice = new BigDecimal("0");
    private BigDecimal carryFee = new BigDecimal("0");
    private int userId;
    private int categoryId = 0;
    private String sourceAddress = "";
    private String location = "";
    private int status = 1;
    private String account;

    private File file1 = null;
    Context context;
    ReleaseImageAdapter adapter = null;
    RecyclerView recyclerView_images;
    NestedScrollView nestedScrollView;

    TextView release_district;
    LinearLayout release_input_src_address;
    TextView cate_select;

    //Amap
    //声明AMapLocationClient类对象
    String province, city, district;
    int intentCode;

    public AMapLocationClient mLocationClient = null;

    public AMapLocationListener mLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {
            if (aMapLocation != null) {
                if (aMapLocation.getErrorCode() == 0) {

                    aMapLocation.getAddress();
                    province = aMapLocation.getProvince();
                    city = aMapLocation.getCity();
                    district = aMapLocation.getDistrict();
                    sourceAddress = province + city + district;
                    location = sourceAddress;
                    release_district.setText(sourceAddress);
                    Log.d("Location", "aMapLocation.getAddress(): " + aMapLocation.getAddress() + "坐标" + aMapLocation.getLatitude());

                } else {
                    Log.e("AmapError", aMapLocation.getErrorCode() + aMapLocation.getErrorInfo());
                }
            }
        }
    };

    public void init() {
        //初始化定位
        mLocationClient = new AMapLocationClient(getApplicationContext());
//设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);


        AMapLocationClientOption mLocationOption = new AMapLocationClientOption();
        mLocationOption.setWifiScan(true);
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
//获取一次定位结果
        mLocationOption.setOnceLocation(true);


        mLocationClient.setLocationOption(mLocationOption);
        mLocationClient.startLocation();

    }

    public void initImageList() {
        ReleaseImage i1 = new ReleaseImage();
        i1.setLocalPath("/storage/emulated/0/DCIM/Camera/IMG_20200416_130259.jpg");
        releaseImages.add(i1);
        ReleaseImage i2 = new ReleaseImage();
        i2.setLocalPath("/storage/emulated/0/DCIM/Camera/IMG_20200416_130307.jpg");
        releaseImages.add(i2);
        ReleaseImage i3 = new ReleaseImage();
        i3.setLocalPath("/storage/emulated/0/DCIM/Camera/IMG_20200416_130307.jpg");
        releaseImages.add(i3);
        ReleaseImage i4 = new ReleaseImage();
        i4.setLocalPath("/storage/emulated/0/DCIM/Camera/IMG_20200416_130307.jpg");
        releaseImages.add(i4);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_release);
        autoObtainStoragePermission();
        init();

        queue = Volley.newRequestQueue(getBaseContext());
        getLocationPermission();
        //components
        context = getApplicationContext();

        Intent intent = getIntent();
        account = intent.getStringExtra("userAccount");


        // initImageList();


        //rrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1);
        final EditText edit_name;
        final EditText edit_description;
        LinearLayout layout_addImage;
        final EditText edit_price;
        final EditText edit_originalPrice;
        final EditText edit_carryFee;
        final CheckBox checkBox;


        final Button button_finish;
        final TextView test_text;

        edit_name = findViewById(R.id.release_edit_name);
        edit_description = findViewById(R.id.release_edit_description);
        layout_addImage = findViewById(R.id.release_layout_addImage);
        recyclerView_images = findViewById(R.id.release_recyclerView);
        edit_price = findViewById(R.id.release_edit_price);
        edit_originalPrice = findViewById(R.id.release_edit_originalPrice);
        edit_carryFee = findViewById(R.id.release_edit_carryFee);
        checkBox = findViewById(R.id.release_checkbox);
        cate_select = findViewById(R.id.cate_select);
        release_district = findViewById(R.id.release_district);
        button_finish = findViewById(R.id.release_button_finish);
        test_text = findViewById(R.id.test_text);
        nestedScrollView = findViewById(R.id.image_scroll_View);
        release_input_src_address = findViewById(R.id.release_input_src_address);


        edit = intent.getStringExtra("edit");
        productDetail = (ProductDetail) getIntent().getSerializableExtra("productDetail");

        intentCode = getIntent().getIntExtra("intentCode", 0);

        Log.e(TAG, "onCreate: intent code" + intentCode);


        if (intentCode == 1) {

            //init

            layout_addImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //点击添加图片的点击事件
                    if (releaseImages.size() > 8) {
                        Toasty.error(getBaseContext(), "不能添加超过9张图片！", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Intent intent = new Intent(Intent.ACTION_PICK, null);
                    intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                    startActivityForResult(intent, PICK_ONE_PHOTO);
                }
            });

            release_input_src_address.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //自己输入地址；
                    showAddressPop();

                }
            });

            cate_select.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    showCategoryPop();
                }
            });

            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        edit_carryFee.setText("0");
                        edit_carryFee.setFocusable(false);
                        edit_carryFee.setFocusableInTouchMode(false);

                    } else {
                        edit_carryFee.setText("");
                        edit_carryFee.setFocusableInTouchMode(true);
                        edit_carryFee.setFocusable(true);
                        edit_carryFee.requestFocus();
                    }
                }
            });

            //edit a product
            Product product = (Product) getIntent().getSerializableExtra("editProduct");
           /* Toast.makeText(context,
                    "get intent, productid= "+JSON.toJSONString(product),
                    Toast.LENGTH_SHORT).show();*/
            productDetail = new ProductDetail();
            productDetail.setName(product.getName());
            productDetail.setDescription(product.getDescription());
            productDetail.setPrice(product.getPrice());
            productDetail.setOriginalPrice(product.getOriginalPrice());
            productDetail.setCarryFee(product.getCarryFee());
            productDetail.setId(product.getId());
            productDetail.setSourceAddress(product.getSourceAddress());
            productDetail.setImages(product.getImages());


            edit_name.setText(productDetail.getName());
            edit_description.setText(productDetail.getDescription());
            edit_price.setText(productDetail.getPrice().toString());
            if (productDetail.getCarryFee().equals(new BigDecimal("0"))) {
                checkBox.setChecked(true);
            } else {
                edit_carryFee.setText(productDetail.getCarryFee().toString());
            }
            edit_originalPrice.setText(productDetail.getOriginalPrice().toString());

            String cateUrl = getResources().getString(R.string.ip_address)
                    + "/category/getCategoryById/" + productDetail.getCategoryId();

            StringRequest cateRequest = new StringRequest(Request.Method.POST,
                    cateUrl,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            Gson gson = new Gson();
                            Type type = new TypeToken<Category>() {
                            }.getType();
                            Category category = gson.fromJson(response, type);
                            Log.e(TAG, "onResponse: response:" + response);

                            //   cate_select.setText(category.getName());


                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {


                }
            });
            queue.add(cateRequest);


            release_district.setText(productDetail.getSourceAddress());
            //images;
            String images = productDetail.getImages();
            String[] imgs = images.split(";");
            List<ReleaseImage> editReleaseImages = new ArrayList<>();

            for (int i = 0; i < imgs.length; i++) {
                ReleaseImage image = new ReleaseImage(imgs[i]);
                releaseImages.add(image);
                //  editReleaseImages.add(image);
                Log.e(TAG, "onCreate: image:" + imgs[i]);

            }
            adapter = new ReleaseImageAdapter(context, releaseImages);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(getBaseContext(), 3);

            recyclerView_images.setLayoutManager(gridLayoutManager);
            recyclerView_images.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            adapter.setListener(new ReleaseImageClickListener() {
                @Override
                public void click(int position) {

                    Intent intent = new Intent(getBaseContext(), ReleaseDetailActivity.class);
                    intent.putExtra("image", releaseImages.get(position).getNetPath());
                    intent.putExtra("position", position);
                    startActivityForResult(intent, DELETE_ONE_PHOTO);

                }
            });
            nestedScrollView.setVisibility(View.VISIBLE);


            button_finish.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //check null
                    //check null
                    if (edit_name.getText().toString().equals("")) {
                        Toasty.info(getBaseContext(), "请输入商品名称！", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (edit_description.getText().toString().equals("")) {
                        Toasty.info(getBaseContext(), "请输入商品描述！", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (releaseImages.size() == 0) {
                        Toasty.info(getBaseContext(), "请添加商品图片！", Toast.LENGTH_SHORT).show();
                        return;
                    }


                    if (edit_price.getText().toString().equals("")) {
                        Toasty.info(getBaseContext(), "请输入商品价格！", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (edit_originalPrice.getText().toString().equals("")) {
                        Toasty.info(getBaseContext(), "请输入商品入手价！", Toast.LENGTH_SHORT).show();
                        return;
                    } else {
                        if (new BigDecimal(edit_price.getText().toString()).compareTo
                                (new BigDecimal(edit_originalPrice.getText().toString())) > 0) {
                            Toasty.info(getBaseContext(), "价格设置不合理！\n(卖出价大于入手价！)", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    if (edit_carryFee.getText().toString().equals("")) {
                        Toasty.info(getBaseContext(), "请输入商品运费！", Toast.LENGTH_SHORT).show();
                        return;

                    }
                    if (categoryId == 0) {
                        Toasty.info(getBaseContext(), "请选择商品分类！", Toast.LENGTH_SHORT).show();
                        return;

                    }
                    if (sourceAddress == null) {
                        Toasty.info(getBaseContext(), "请输入商品发货地！", Toast.LENGTH_SHORT).show();
                        return;

                    }


                    //get information

                    name = edit_name.getText().toString();
                    description = edit_description.getText().toString();
                    price = new BigDecimal(edit_price.getText().toString());
                    carryFee = new BigDecimal(edit_carryFee.getText().toString());
                    //     originalPrice = new BigDecimal(edit_originalPrice.getText().toString());
                    originalPrice = new BigDecimal(edit_originalPrice.getText().toString());
                    int id=productDetail.getId();

                    String updatedImages = "";
                    for (ReleaseImage img : releaseImages) {
                        String path = img.getNetPath() + ";";
                        updatedImages = updatedImages.concat(path);
                    }

                    final RequestQueue requestQueue = Volley.newRequestQueue(getBaseContext());
                    String url = getResources().getString(R.string.ip_address) + "/product/updateById" +
                            "?productId=" + id+ "&name=" + name + "&description=" + description +
                            "&images=" + updatedImages + "&price=" + price + "&originalPrice=" + originalPrice +
                            "&carryFee=" + carryFee + "&categoryId=" + categoryId + "&sourceAddress=" + sourceAddress;

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    if (response.contains("0")) {

                                        Toasty.success(getBaseContext(), "商品编辑成功！", Toast.LENGTH_SHORT).show();
                                        button_finish.setClickable(false);
                                        finish();

                                    }

                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    button_finish.setClickable(true);


                                }
                            });
                    requestQueue.add(stringRequest);

                    String text = "name : " + name + "\ndescription : " + description + "\nimages : " + updatedImages +
                            "\nprice : " + price.toString() + "\noriginal price : " + originalPrice.toString()
                            + "\ncarry fee : " + carryFee.toString() + "\ncategory id : " + categoryId
                            + "\nsource address : " + sourceAddress + "\nid: " + id;

                    test_text.setText(text);


                    Log.e(TAG, "onClick: button finish clicked");
                }
            });


        } else {
            //release a new product


            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        edit_carryFee.setText("0");
                        edit_carryFee.setFocusable(false);
                        edit_carryFee.setFocusableInTouchMode(false);

                    } else {
                        edit_carryFee.setText("");
                        edit_carryFee.setFocusableInTouchMode(true);
                        edit_carryFee.setFocusable(true);
                        edit_carryFee.requestFocus();
                    }
                }
            });
            //images
            GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);

            recyclerView_images.setLayoutManager(gridLayoutManager);
     /*   adapter.setListener(new ReleaseImageClickListener() {
            @Override
            public void click(int position) {
                *//*Intent intent = new Intent(getBaseContext(),
                        MainActivity_ReleaseImageDetail.class);
                intent.putExtra("imgPath", imageList.get(position).getLocalPath());
                startActivityForResult(intent, DELETE_ONE_PHOTO);*//*
                //此处传过去了
            }
        });*/

            layout_addImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //点击添加图片的点击事件
                    if (releaseImages.size() > 8) {
                        Toasty.error(getBaseContext(), "不能添加超过9张图片！", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Intent intent = new Intent(Intent.ACTION_PICK, null);
                    intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                    startActivityForResult(intent, PICK_ONE_PHOTO);
                }
            });

            release_input_src_address.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //自己输入地址；
                    showAddressPop();

                }
            });

            cate_select.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    showCategoryPop();
                }
            });

            button_finish.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onClick(View v) {


                    //check null
                    if (edit_name.getText().toString().equals("")) {
                        Toasty.info(getBaseContext(), "请输入商品名称！", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (edit_description.getText().toString().equals("")) {
                        Toasty.info(getBaseContext(), "请输入商品描述！", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (releaseImages.size() == 0) {
                        Toasty.info(getBaseContext(), "请添加商品图片！", Toast.LENGTH_SHORT).show();
                        return;
                    }


                    if (edit_price.getText().toString().equals("")) {
                        Toasty.info(getBaseContext(), "请输入商品价格！", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (edit_originalPrice.getText().toString().equals("")) {
                        Toasty.info(getBaseContext(), "请输入商品入手价！", Toast.LENGTH_SHORT).show();
                        return;
                    } else {
                        if (new BigDecimal(edit_price.getText().toString()).compareTo
                                (new BigDecimal(edit_originalPrice.getText().toString())) > 0) {
                            Toasty.info(getBaseContext(), "价格设置不合理！\n(卖出价大于入手价！)", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    if (edit_carryFee.getText().toString().equals("")) {
                        Toasty.info(getBaseContext(), "请输入商品运费！", Toast.LENGTH_SHORT).show();
                        return;

                    }
                    if (categoryId == 0) {
                        Toasty.info(getBaseContext(), "请选择商品分类！", Toast.LENGTH_SHORT).show();
                        return;

                    }
                    if (sourceAddress == null) {
                        Toasty.info(getBaseContext(), "请输入商品发货地！", Toast.LENGTH_SHORT).show();
                        return;

                    }

                    name = edit_name.getText().toString();
                    description = edit_description.getText().toString();
                    price = new BigDecimal(edit_price.getText().toString());
                    carryFee = new BigDecimal(edit_carryFee.getText().toString());
                    //     originalPrice = new BigDecimal(edit_originalPrice.getText().toString());
                    originalPrice = new BigDecimal(edit_originalPrice.getText().toString());


                    for (ReleaseImage img : releaseImages) {
                        String path = img.getNetPath() + ";";
                        images = images.concat(path);
                    }

                    final RequestQueue requestQueue = Volley.newRequestQueue(getBaseContext());
                    String url = getResources().getString(R.string.ip_address) + "/product/insertByAccount" +
                            "?account=" + account + "&name=" + name + "&description=" + description +
                            "&images=" + images + "&price=" + price + "&originalPrice=" + originalPrice +
                            "&carryFee=" + carryFee + "&categoryId=" + categoryId + "&sourceAddress=" + sourceAddress;

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    if (response.contains("0")) {

                                        Toasty.success(getBaseContext(), "发布成功！", Toast.LENGTH_SHORT).show();
                                        button_finish.setClickable(false);
                                        finish();

                                    }

                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    button_finish.setClickable(true);


                                }
                            });
                    requestQueue.add(stringRequest);

                    String text = "name : " + name + "\ndescription : " + description + "\nimages : " + images +
                            "\nprice : " + price.toString() + "\noriginal price : " + originalPrice.toString()
                            + "\ncarry fee : " + carryFee.toString() + "\ncategory id : " + categoryId
                            + "\nsource address : " + sourceAddress + "\naccount : " + account;

                    test_text.setText(text);


                }
            });
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PICK_ONE_PHOTO:
                    nestedScrollView.setVisibility(View.VISIBLE);
                    final ReleaseImage releaseImage = new ReleaseImage();

                    Uri uri = data.getData();
                    if (uri == null) {
                        Toast.makeText(getBaseContext(), "uri is null!", Toast.LENGTH_SHORT).show();
                    }
                    String imagePath = FileUtil.getFilePathByUri(this, uri);
                    //  Toast.makeText(context, "image path:" + imagePath, Toast.LENGTH_SHORT).show();
                    releaseImage.setLocalPath(imagePath);
                    releaseImage.setUri(uri);
                    releaseImages.add(releaseImage);

                    //upload image and return path

                    Tiny.FileCompressOptions options = new Tiny.FileCompressOptions();
                    Tiny.getInstance()
                            .source(releaseImage.getUri())
                            .asFile()
                            .withOptions(options)
                            .compress(new FileCallback() {
                                @Override
                                public void callback(boolean isSuccess, final String outfile, Throwable t) {

                                    if (isSuccess) {

                                        //upload
                                        new Thread() {
                                            @Override
                                            public void run() {
                                                try {
                                                    String uploadedName;
                                                    uploadedName = getResources().getString(R.string.image_address)
                                                            + UploadImageUtil.uploadImage(
                                                            getResources().getString(R.string.uploadInterface2),
                                                            outfile);

                                                    releaseImage.setNetPath(uploadedName);
                                                    Log.d("net path", releaseImage.getNetPath());
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        }.start();
                                    } else {
                                        Toasty.error(getBaseContext(), "图片压缩失败！\n请检查存储权限后重试！",
                                                Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });


                 /*
                    Tiny.getInstance()
                            .source(uri)
                            .asFile()
                            .withOptions(options)
                            .compress(new FileCallback() {
                                @Override
                                public void callback(boolean isSuccess, *//*outfile就是压缩后的路径*//*final String outfile, Throwable t) {
                                    if (!isSuccess) {
                                        //           Log.e("Compress Error! ",t.toString());

                                        Toast.makeText(getBaseContext(), "图片压缩失败！", Toast.LENGTH_SHORT).show();
                                       if (t!=null)
                                        Log.e(TAG, "callback: "+t.toString());
                                    } else {
                                        // Log.e("Compress Error! ",t.toString());

                                        Toast.makeText(getBaseContext(), "压缩 success", Toast.LENGTH_SHORT).show();

                                    }
                                    //上传


                                    new Thread() {
                                        @Override
                                        public void run() {
                                            try {
                                                String uploadedName;

//上传；
                                                uploadedName = getResources().getString(R.string.image_address)
                                                        +UploadImageUtil.uploadImage(
                                                        getResources().getString(R.string.uploadInterface2),
                                                        outfile);

                                                releaseImage.setNetPath(uploadedName);
                                               Log.d("net path", releaseImage.getNetPath());
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }.start();
                                }
                            });
*/


                    if (intentCode == 1) {

                    } else {
                        adapter = new ReleaseImageAdapter(context, releaseImages);
                        adapter.setListener(new ReleaseImageClickListener() {
                            @Override
                            public void click(int position) {
                                Intent intent = new Intent(getBaseContext(), ReleaseDetailActivity.class);
                                intent.putExtra("image", releaseImages.get(position).getUri().toString());
                                intent.putExtra("position", position);
                                startActivityForResult(intent, DELETE_ONE_PHOTO);
                            }
                        });


                    }
                    recyclerView_images.setAdapter(adapter);

                    //滑动到最新得一张photo
                    recyclerView_images.smoothScrollToPosition(releaseImages.size());
                    adapter.notifyDataSetChanged();

                 /*   adapter.setListener(new ReleaseImageClickListener() {
                        @Override
                        public void click(int position) {
                            Intent intent = new Intent(getBaseContext(), ReleaseDetailActivity.class);
                            intent.putExtra("image", releaseImages.get(position).getUri().toString());
                            intent.putExtra("position", position);
                            startActivityForResult(intent, DELETE_ONE_PHOTO);
                        }
                    });*/

                    //Toast.makeText(getBaseContext(), "release images list size" + releaseImages.size(), Toast.LENGTH_SHORT).show();

                    break;

                case DELETE_ONE_PHOTO:
                    int position = data.getIntExtra("position", 0);
                    releaseImages.remove(position);
                    adapter.notifyDataSetChanged();
                    if (releaseImages.size() == 0) {
                        nestedScrollView.setVisibility(View.GONE);

                    }
            }
        }

    }

    public void getLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION}, 12);


        }
    }

    private void darkenBackground(Float backgroundColor) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = backgroundColor;
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        getWindow().setAttributes(lp);
    }

    //手动填写发货地popup
    public void showAddressPop() {
        darkenBackground(0.7f);


        View contentView = LayoutInflater.from(getBaseContext())
                .inflate(R.layout.popup_input_address_manually, null);


        ImageView image_close = contentView.findViewById(R.id.img_close);
        Button button_useLocation = contentView.findViewById(R.id.button_use_location);
        final EditText editText = contentView.findViewById(R.id.edit);

        Button button_commit = contentView.findViewById(R.id.button_commit);

        final PopupWindow popupWindow = new PopupWindow(contentView, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setFocusable(true);

        popupWindow.setContentView(contentView);

        popupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.round_background));

        View rootView = LayoutInflater.from(getBaseContext())
                .inflate(R.layout.activity_release, null);

        popupWindow.setTouchable(true);
        popupWindow.setAnimationStyle(R.style.pop_animation);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                darkenBackground(1f);
            }
        });

//
//        Glide.with(getBaseContext())
//                .load(new File("/storage/emulated/0/DCIM/Camera/IMG_20200208_142217.jpg"))
//                .into(image_close);
        image_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        //  popupWindow.setFocusable(true);


        button_commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sourceAddress = editText.getText().toString();
                //Toast.makeText(getBaseContext(), src_address, Toast.LENGTH_SHORT).show();
                release_district.setText(sourceAddress);
                popupWindow.dismiss();
                //把新地址显示出来
            }
        });
        button_useLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sourceAddress = location;
                release_district.setText(sourceAddress);
                popupWindow.dismiss();

            }
        });
        popupWindow.showAtLocation(rootView, Gravity.BOTTOM, 0, 0);

    }

    //选择分类popup
    public void showCategoryPop() {
        darkenBackground(0.7f);


        //popup的view
        View contentView = LayoutInflater.from(getBaseContext())
                .inflate(R.layout.popup_choose_category, null);


        final RecyclerView recyclerView_cate;
        ImageView image_close = contentView.findViewById(R.id.img_close_cate);
        recyclerView_cate = contentView.findViewById(R.id.recycler_choose_cate);


// final EditText editText=contentView.findViewById(R.id.edit);

//  Button button_commit=contentView.findViewById(R.id.button_commit);

        final PopupWindow popupWindow = new PopupWindow(contentView, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);


        LinearLayoutManager layoutManager = new LinearLayoutManager(getBaseContext());
        recyclerView_cate.setLayoutManager(layoutManager);

        //get data


        String urlCate = getResources().getString(R.string.ip_address) +
                "/category/getAll";
        StringRequest request = new StringRequest(Request.Method.POST, urlCate,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        List<Category> categoryList;
                        categoryList = JSON.parseArray(response, Category.class);

                        PopupCategoryAdapter popupCategoryAdapter = new PopupCategoryAdapter(getBaseContext(), categoryList);
                        recyclerView_cate.setAdapter(popupCategoryAdapter);
                        popupCategoryAdapter.setListener(new OnPopupCateListener() {
                            @Override
                            public void click(Category category) {
                                categoryId = category.getId();
                                popupWindow.dismiss();
                                cate_select.setText(category.getName());
                            }
                        });


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


        popupWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setFocusable(true);

        popupWindow.setContentView(contentView);

        popupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.round_background));

        View rootView = LayoutInflater.from(getBaseContext())
                .inflate(R.layout.activity_release, null);

        popupWindow.setTouchable(true);
        popupWindow.setAnimationStyle(R.style.pop_animation);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                darkenBackground(1f);
            }
        });

//
//        Glide.with(getBaseContext())
//                .load(new File("/storage/emulated/0/DCIM/Camera/IMG_20200208_142217.jpg"))
//                .into(image_close);
        image_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        //  popupWindow.setFocusable(true);


//
//        button_commit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                String address=editText.getText().toString();
//                src_address=address;
//                Toast.makeText(getBaseContext(),src_address,Toast.LENGTH_SHORT).show();
//
//                text_release_district.setText(src_address);
//
//
//                popupWindow.dismiss();
//                //把地址显示出来
//            }
//        });
        popupWindow.showAtLocation(rootView, Gravity.BOTTOM, 0, 0);
    }

    //permission check
    private void autoObtainStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            Toasty.info(getBaseContext(), "未获取存取权限！请赋予权限！", Toast.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}
                    , 13);
        } else { //有权限
        }
    }
}
