package com.example.cardview;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageView;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_detail);

        ImageView img = findViewById(R.id.img);

        Intent intent = getIntent();
        Bundle image_bundle = intent.getBundleExtra("image_bundle");
        if(image_bundle != null){
            Bitmap bitmap = (Bitmap) image_bundle.get("data");
            img.setImageBitmap(bitmap);
        }

    }
}
