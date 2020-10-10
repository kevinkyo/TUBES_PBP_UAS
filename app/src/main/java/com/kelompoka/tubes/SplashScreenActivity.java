package com.kelompoka.tubes;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

public class SplashScreenActivity extends AppCompatActivity {
    private int waktu_loading=4000, cekTema;

    SharedPreferences sharedPreferences;
    //4000=4 detik

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splas);

        sharedPreferences  = getSharedPreferences("SHARE", Context.MODE_PRIVATE);
        if(sharedPreferences!=null)
        {
            cekTema = sharedPreferences.getInt("Mode", 0);
            if(cekTema == 1)
            {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            }
            else
            {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                //setelah loading maka akan langsung berpindah ke home activity
                Intent home=new Intent(SplashScreenActivity.this, MainActivity.class);
                startActivity(home);
                finish();

            }
        },waktu_loading);
    }
}
