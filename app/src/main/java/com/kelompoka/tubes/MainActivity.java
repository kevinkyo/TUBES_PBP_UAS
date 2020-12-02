package com.kelompoka.tubes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.FragmentTransaction;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.kelompoka.tubes.API.GuruAPI;
import com.kelompoka.tubes.API.UserAPI;
import com.shashank.sony.fancytoastlib.FancyToast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.android.volley.Request.Method.POST;

public class MainActivity extends AppCompatActivity {
    TextInputEditText inputEmail,inputPassword, inputNama;
    Button btnSignIn,btnSignUp;
    private FirebaseAuth mAuth;
//    private static final String TAG = "EmailPassword";

    private String CHANNEL_ID="Channel 1";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputNama = findViewById(R.id.inputNama);
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
                String nama= inputNama.getText().toString();
                if(email.isEmpty() || !email.contains("@")){
                    FancyToast.makeText(MainActivity.this,"Email Invalid",
                            FancyToast.LENGTH_SHORT, FancyToast.INFO,false).show();
                }
                else if(nama.isEmpty()){
                    FancyToast.makeText(MainActivity.this,"Please Enter Nama",
                            FancyToast.LENGTH_SHORT, FancyToast.INFO,false).show();
                }
                else if(password.isEmpty()){
                    FancyToast.makeText(MainActivity.this,"Please Enter Password",
                            FancyToast.LENGTH_SHORT, FancyToast.INFO,false).show();

                }
                else if(password.length()<6){
                    FancyToast.makeText(MainActivity.this,"Password is too short",
                            FancyToast.LENGTH_SHORT, FancyToast.INFO,false).show();
                }
                else{

                    register( nama, email,  password);

                    /*
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

                     */
                }
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = inputEmail.getText().toString();
                String password= inputPassword.getText().toString();
                if(email.isEmpty() || !email.contains("@")){
                    FancyToast.makeText(MainActivity.this,"Email Invalid",
                            FancyToast.LENGTH_SHORT, FancyToast.INFO,false).show();
                }
                else if(password.isEmpty()){
                    FancyToast.makeText(MainActivity.this,"Please Enter Password",
                            FancyToast.LENGTH_SHORT, FancyToast.INFO,false).show();
                }
                else if(password.length()<6){
                    FancyToast.makeText(MainActivity.this,"Password is too short",
                            FancyToast.LENGTH_SHORT, FancyToast.INFO,false).show();
                }
                else{

                    login(email, password);

                    /*
                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_LONG).show();
                                        createNotificationChannel();
                                        addNotification();
                                        Intent intent = new Intent(MainActivity.this, MenuActivity.class);
                                        startActivity(intent);
                                    } else {
                                        Toast.makeText(MainActivity.this, "Authentication Failed.",Toast.LENGTH_SHORT).show();
                                        inputEmail.setText("");
                                        inputPassword.setText("");
                                    }
                                }
                            });

                     */
                }
            }
        });
    }

    public void register( String nama,  String email,  String password)
    {
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);


        StringRequest stringRequest = new StringRequest(POST, UserAPI.URL_REGISTER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject obj = new JSONObject(response);
                    if(obj.getString("message").equalsIgnoreCase("Register Success"))
                    {
                        FancyToast.makeText(MainActivity.this,obj.getString("message"),
                                FancyToast.LENGTH_SHORT, FancyToast.INFO,false).show();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                FancyToast.makeText(MainActivity.this,error.getMessage(),
                        FancyToast.LENGTH_SHORT, FancyToast.INFO,false).show();
            }
        }){
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("name", nama);
                params.put("email", email);
                params.put("password", password);
                return params;
            }
        };

        queue.add(stringRequest);
    }

    public void login( String email,  String password)
    {
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);


        StringRequest stringRequest = new StringRequest(POST, UserAPI.URL_LOGIN, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                try
                {
                    JSONObject obj = new JSONObject(response);
                    if(obj.getString("message").equalsIgnoreCase("Authenticated"))
                    {
                        JSONObject userObj = obj.getJSONObject("user");
                        FancyToast.makeText(MainActivity.this,obj.getString("message"),
                                FancyToast.LENGTH_SHORT, FancyToast.INFO,false).show();
                        createNotificationChannel();
                        addNotification();
                        startActivity(new Intent(MainActivity.this, MenuActivity.class));
                    }
                    else
                    {
                        FancyToast.makeText(MainActivity.this,obj.getString("message"),
                                FancyToast.LENGTH_SHORT, FancyToast.INFO,false).show();
                    }
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError e)
            {

                FancyToast.makeText(MainActivity.this,e.getMessage(),
                        FancyToast.LENGTH_SHORT, FancyToast.INFO,false).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();

                params.put("email", email);
                params.put("password", password);

                return params;
            }
        };

        queue.add(stringRequest);
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