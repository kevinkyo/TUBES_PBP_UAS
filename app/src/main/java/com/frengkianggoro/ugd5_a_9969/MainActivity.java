package com.frengkianggoro.ugd5_a_9969;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    TextInputEditText inputEmail,inputPassword;
    Button btnSignIn,btnSignUp;
    private FirebaseAuth mAuth;
//    private static final String TAG = "EmailPassword";

    private String CHANNEL_ID="Channel 1";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputEmail = findViewById(R.id.inputEmail);
        inputPassword = findViewById(R.id.inputPassword);
        btnSignIn = findViewById(R.id.btn_sign_in);
        btnSignUp = findViewById(R.id.btn_sign_up);
        mAuth= FirebaseAuth.getInstance();


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = inputEmail.getText().toString();
                String password= inputPassword.getText().toString();
                if(email.isEmpty() || !email.contains("@")){
                    Toast.makeText(MainActivity.this, "Email Invalid", Toast.LENGTH_SHORT).show();
                }
                else if(password.isEmpty()){
                    Toast.makeText(MainActivity.this, "Please enter Password", Toast.LENGTH_SHORT).show();
                }
                else if(password.length()<6){
                    Toast.makeText(MainActivity.this, "Password is too short", Toast.LENGTH_SHORT).show();
                }
                else{
                    mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                Toast.makeText(MainActivity.this, "Sign Up Unsuccessful", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(MainActivity.this, "Sign Up Successful", Toast.LENGTH_SHORT).show();
                                inputEmail.setText("");
                                inputPassword.setText("");
                            }
                        }
                    });
                }
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = inputEmail.getText().toString();
                String password= inputPassword.getText().toString();
                if(email.isEmpty() || !email.contains("@")){
                    Toast.makeText(MainActivity.this, "Email Invalid", Toast.LENGTH_SHORT).show();
                }
                else if(password.isEmpty()){
                    Toast.makeText(MainActivity.this, "Please enter Password", Toast.LENGTH_SHORT).show();
                }
                else if(password.length()<6){
                    Toast.makeText(MainActivity.this, "Password is too short", Toast.LENGTH_SHORT).show();
                }
                else{
                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_LONG).show();
                                        createNotificationChannel();
                                        addNotification();
                                        Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
                                        startActivity(intent);
                                    } else {
                                        Toast.makeText(MainActivity.this, "Authentication Failed.",Toast.LENGTH_SHORT).show();
                                        inputEmail.setText("");
                                        inputPassword.setText("");
                                    }
                                }
                            });
                }
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
                .setContentTitle("Hello")
                .setContentText("Welcome Back, Please Enjoy Your Stay")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        Intent notifcationIntent= new Intent(this,MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this,0, notifcationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);

        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0,builder.build());
    }


}