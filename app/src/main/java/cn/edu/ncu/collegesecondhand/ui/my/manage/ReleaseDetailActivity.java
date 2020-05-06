package cn.edu.ncu.collegesecondhand.ui.my.manage;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import cn.edu.ncu.collegesecondhand.R;
import cn.edu.ncu.collegesecondhand.entity.ReleaseImage;

public class ReleaseDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_release_detail);
        ImageView imageView = findViewById(R.id.detail_image);
        Button button = findViewById(R.id.detail_button_delete);

        Intent intent = getIntent();
        String path = intent.getStringExtra("image");
        final int position = intent.getIntExtra("position", 0);

        Glide.with(this)
                .load(path)
                .into(imageView);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.putExtra("position", position);
                setResult(RESULT_OK, intent);
                finish();

            }
        });


    }
}
