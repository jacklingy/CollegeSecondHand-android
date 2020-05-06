package cn.edu.ncu.collegesecondhand.ui.my;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;

import cn.edu.ncu.collegesecondhand.R;
import cn.edu.ncu.collegesecondhand.entity.User;
import cn.edu.ncu.collegesecondhand.ui.my.modify.UpdateAvatarActivity;
import cn.edu.ncu.collegesecondhand.ui.my.modify.UpdateBirthdayActivity;
import cn.edu.ncu.collegesecondhand.ui.my.modify.UpdateCollegeActivity;
import cn.edu.ncu.collegesecondhand.ui.my.modify.UpdateGenderActivity;
import cn.edu.ncu.collegesecondhand.ui.my.modify.UpdateNameActivity;
import cn.edu.ncu.collegesecondhand.ui.my.modify.UpdatePhoneActivity;
import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;


/**
 * todo
 * 修改了之后返回界面，然后又马上修改，这时的数据仍是是myFragment提供的，并没有更新，修改界面的数据需要从网络获取
 * 这样才能实时更新；
 * <p>
 * 本界面的Activity的数据先通过传递来的更新，之后通过Handler再从网络更新一次；
 * ，但不用更新显示，只需要传递到下一个界面即可
 */
public class UserInformationActivity extends AppCompatActivity {
    public static final int REQUEST_CODE_AVATAR = 1;
    public static final int REQUEST_CODE_NAME = 2;
    public static final int REQUEST_CODE_GENDER = 3;
    public static final int REQUEST_CODE_PHONE = 4;
    public static final int REQUEST_CODE_BIRTHDAY = 5;
    public static final int REQUEST_CODE_COLLEGE = 6;

    private CircleImageView imageView_avatar;
    private TextView name;
    private TextView gender;
    private TextView phone;
    private TextView birthday;
    private TextView registerTime;
    private TextView college;
    private TextView currentAccount;

    private LinearLayout layout_avatar;
    private LinearLayout layout_name;
    private LinearLayout layout_gender;
    private LinearLayout layout_phone;
    private LinearLayout layout_birthday;
    private LinearLayout layout_college;
    private Toolbar toolbar;


    private User user;

    private void init() {
        imageView_avatar = findViewById(R.id.modify_avatar);
        currentAccount = findViewById(R.id.current_account);
        name = findViewById(R.id.modify_name);
        gender = findViewById(R.id.modify_gender);
        phone = findViewById(R.id.modify_phone);
        birthday = findViewById(R.id.modify_birthday);
        registerTime = findViewById(R.id.current_register);
        college = findViewById(R.id.modify_college);

        layout_avatar = findViewById(R.id.update_avatar);
        layout_name = findViewById(R.id.update_name);
        layout_gender = findViewById(R.id.update_gender);
        layout_phone = findViewById(R.id.update_phone);
        layout_birthday = findViewById(R.id.update_birthday);
        layout_college = findViewById(R.id.update_college);

        toolbar = findViewById(R.id.information_toolbar);

        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("user");
        if (user != null) {

            //show information
            Glide.with(this)
                    .load(user.getAvatar())
                    .into(imageView_avatar);
            currentAccount.setText(user.getAccount());
            name.setText(user.getNickName());

            //null值判断
            if (user.getGender() == null) {
                gender.setText("");
            } else {
                gender.setText(user.getGender());

            }
            if (user.getPhone()==null){
                phone.setText("");
            }else {
                phone.setText(user.getPhone());
            }
            if (user.getBirthday()==null){
                birthday.setText("");
            }else {
                birthday.setText(new SimpleDateFormat("yyyy年MM月dd日").format(user.getBirthday()));

            }

            registerTime.setText(user.getRegister_time());
            if (user.getCollege()==null){
                college.setText("");
            }
            else {
                college.setText(user.getCollege());

            }
        } else {
            finish();
        }

    }


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_information);
        init();
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        layout_avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserInformationActivity.this, UpdateAvatarActivity.class);
                intent.putExtra("avatar", user.getAvatar());
                intent.putExtra("id", user.getId());
                startActivityForResult(intent, REQUEST_CODE_AVATAR);

            }
        });

        layout_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserInformationActivity.this, UpdateNameActivity.class);
                intent.putExtra("name", user.getNickName());
                intent.putExtra("id", user.getId());
                startActivityForResult(intent, REQUEST_CODE_NAME);
            }
        });

        layout_gender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserInformationActivity.this, UpdateGenderActivity.class);
                intent.putExtra("gender", user.getGender());
                intent.putExtra("id", user.getId());
                startActivityForResult(intent, REQUEST_CODE_GENDER);


            }
        });

        layout_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserInformationActivity.this, UpdatePhoneActivity.class);
                intent.putExtra("phone", user.getPhone());
                intent.putExtra("id", user.getId());
                startActivityForResult(intent, REQUEST_CODE_PHONE);


            }
        });

        layout_college.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserInformationActivity.this, UpdateCollegeActivity.class);
                intent.putExtra("college", user.getCollege());
                intent.putExtra("id", user.getId());
                startActivityForResult(intent, REQUEST_CODE_COLLEGE);


            }
        });


        layout_birthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserInformationActivity.this, UpdateBirthdayActivity.class);
                @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
               if (user.getBirthday()!=null) {
                   String birthday = simpleDateFormat.format(user.getBirthday());

                   intent.putExtra("birthday", birthday);
                   intent.putExtra("id", user.getId());
                   startActivityForResult(intent, REQUEST_CODE_BIRTHDAY);
               }else {
                   intent.putExtra("id", user.getId());
                   startActivityForResult(intent, REQUEST_CODE_BIRTHDAY);

               }

            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_AVATAR:

                    String path = data.getStringExtra("avatarUrl");
                    Glide.with(getBaseContext())
                            .load(path)
                            .into(imageView_avatar);
                    break;
                case REQUEST_CODE_NAME:
                    String editedName = data.getStringExtra("name");
                    name.setText(editedName);

                    break;
                case REQUEST_CODE_GENDER:
                    String checkedGender = data.getStringExtra("gender");
                    gender.setText(checkedGender);

                    break;
                case REQUEST_CODE_PHONE:
                    String editedPhone = data.getStringExtra("phone");
                    phone.setText(editedPhone);
                    // Toast.makeText(this, editedPhone, Toast.LENGTH_SHORT).show();

                    break;
                case REQUEST_CODE_BIRTHDAY:
                    String editedDate = data.getStringExtra("birthday");
                    birthday.setText(editedDate);
                    break;
                case REQUEST_CODE_COLLEGE:
                    String editedCollege = data.getStringExtra("college");
                    //Toast.makeText(this, editedCollege, Toast.LENGTH_SHORT).show();

                    college.setText(editedCollege);
                    break;
            }

        }


    }
}
