package com.kelompoka.tubes;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.kelompoka.tubes.API.GuruAPI;
import com.kelompoka.tubes.API.StudentAPI;
import com.kelompoka.tubes.database.DatabaseClient;
import com.kelompoka.tubes.model.Guru;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.shashank.sony.fancytoastlib.FancyToast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.android.volley.Request.Method.POST;


public class AddGuruFragment extends Fragment {

    TextInputEditText nameText, numberText, ageText;
    TextInputLayout layoutName, layoutAge, layoutNum;
    Button addBtn, cancelBtn;
    Guru guru;

    LinearLayout ll;
    int darkM = AppCompatDelegate.MODE_NIGHT_YES;

    public AddGuruFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_guru_add, container, false);
        nameText = view.findViewById(R.id.input_name);
        numberText = view.findViewById(R.id.input_number);
        ageText = view.findViewById(R.id.input_age);
        cancelBtn = view.findViewById(R.id.btn_cancel);
        addBtn = view.findViewById(R.id.btn_add);
        layoutName = view.findViewById(R.id.input_name_layout);
        layoutAge = view.findViewById(R.id.input_age_layout);
        layoutNum = view.findViewById(R.id.input_number_layout);

        ll = (LinearLayout) view.findViewById(R.id.linearLayout);

        if (AppCompatDelegate.getDefaultNightMode() == darkM)
        {
            ll.setBackgroundColor(Color.parseColor("#141414"));
        }
        else
        {
            ll.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }

        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(nameText.getText().toString().isEmpty() || numberText.getText().toString().isEmpty() || ageText.getText().toString().isEmpty()){
                    layoutNum.setError("Please fill number correctly.");
                    layoutName.setError("Please fill name correctly.");
                    layoutAge.setError("Please fill age correctly.");
                }

                else{
                    final String name = nameText.getText().toString();
                    final String number = numberText.getText().toString();
                    final int age = Integer.valueOf(ageText.getText().toString());
                    tambahGuru(number, name, age);


                }

            }
        });
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.hide(AddGuruFragment.this).commit();
            }
        });
    }

    public void tambahGuru(final String number, final String fullname, final int age)
    {
        RequestQueue queue = Volley.newRequestQueue(getContext());


        StringRequest stringRequest = new StringRequest(POST, GuruAPI.URL_ADD, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject obj = new JSONObject(response);
                    if(obj.getString("message").equalsIgnoreCase("Add Guru Success"))
                    {
                        FragmentTransaction transaction = getFragmentManager().beginTransaction();
                        transaction.hide(AddGuruFragment.this).commit();
                    }

                    FancyToast.makeText(getContext(),obj.getString("message"),
                            FancyToast.LENGTH_SHORT, FancyToast.INFO,false).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                FancyToast.makeText(getContext(),error.getMessage(),
                        FancyToast.LENGTH_SHORT, FancyToast.INFO,false).show();
            }
        }){
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("number", number);
                params.put("fullname", fullname);
                params.put("age", String.valueOf(age));


                return params;
            }
        };

        queue.add(stringRequest);
    }

/*
    private void addUser(){



        class AddUser extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                Guru guru = new Guru();
                guru.setFullName(name);
                guru.setNumber(number);
                guru.setAge(age);

                DatabaseClient.getInstance(getContext()).getDatabase()
                        .guruDao()
                        .insert(guru);
                return null;
            }
        }
        AddUser add = new AddUser();
        add.execute();
    }

 */

}