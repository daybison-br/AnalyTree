package com.daybison.analytree_plus.view;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.daybison.analytree_plus.R;
import com.daybison.analytree_plus.configs.DatabaseHelper;

public class SplashActivity extends AppCompatActivity {
    public static final int TIME_OUT_SPLASH = 3000;
    DatabaseHelper db;
    TextView txtVersionAPP;
    String VERSION_APP = "v.1.0.11";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        db = new DatabaseHelper(SplashActivity.this);
        setSplashScreenDuration();
        txtVersionAPP = findViewById(R.id.txtVersionAPP);



        txtVersionAPP.setText(VERSION_APP);
    }

    private void setSplashScreenDuration() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent MainScreen = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(MainScreen);
                finish();
            }
        }, TIME_OUT_SPLASH);

    }
}