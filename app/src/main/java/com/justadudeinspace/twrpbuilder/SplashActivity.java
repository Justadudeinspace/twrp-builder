package com.justadudeinspace.twrpbuilder;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;

public class SplashActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen); // Make sure this matches your XML filename (res/layout/splash_screen.xml)

        ImageView splashImage = findViewById(R.id.splash_image);

        try {
            InputStream is = getAssets().open("splash.png");
            Drawable d = Drawable.createFromStream(is, null);
            splashImage.setImageDrawable(d);
        } catch (IOException e) {
            e.printStackTrace();
        }

        new Handler().postDelayed(() -> {
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
            finish();
        }, 2000); // Show splash for 2 seconds
    }
}
