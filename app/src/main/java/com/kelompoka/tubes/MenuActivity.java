package com.kelompoka.tubes;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.mapbox.mapboxsdk.maps.Style;


public class MenuActivity extends AppCompatActivity implements SensorEventListener {

    Button btnLogout,btnGeo, btnStudent, btnGuru, btnAboutUs;
    FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private String CHANNEL_ID="Channel 1";

    private SensorManager mSensorManager = null;
    private Sensor mSensor = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mSensor=mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);

        btnLogout = findViewById(R.id.btn_logout);
        btnGuru = findViewById(R.id.btn_guru);
        btnStudent = findViewById(R.id.btn_murid);
        btnAboutUs = findViewById(R.id.btn_about_us);

        btnStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuActivity.this, StudentActivity.class));
            }
        });

        btnGuru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuActivity.this, GuruActivity.class));
            }
        });

        btnAboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuActivity.this, AboutUsActivity.class));
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                createNotificationChannel();
                addNotification();
                Intent intent = new Intent(MenuActivity.this,MainActivity.class);
                startActivity(intent);

            }
        });

        btnGeo = (Button) findViewById(R.id.btn_geo);

        btnGeo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGeoActivity();

            }
        });

    }

    private void addNotification() {
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
            CharSequence name = "Channel 1";
            String description = "This is Channel 1";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,name,importance);
            channel.setDescription(description);


            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void createNotificationChannel() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("Goodbye")
                .setContentText("Comeback Again...")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        Intent notifcationIntent= new Intent(this,MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this,0, notifcationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);

        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0,builder.build());
    }

    public void openGeoActivity(){
        Intent intent = new Intent(MenuActivity.this, GeoActivity.class);
        startActivity(intent);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent){
        Sensor mySensor = sensorEvent.sensor;

        if(mySensor.getType() == Sensor.TYPE_PROXIMITY){
            if (sensorEvent.values[0]==0){
                Toast.makeText(this, "About Us", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MenuActivity.this, AboutUsActivity.class));
            }

        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i){

    }

    protected void onResume(){
        super.onResume();
        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    protected void onPause(){
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    //Menu

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_settings:
                startActivity(new Intent(MenuActivity.this, SettingsActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}