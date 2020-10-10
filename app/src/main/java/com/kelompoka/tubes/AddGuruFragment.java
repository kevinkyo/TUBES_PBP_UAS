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

import com.kelompoka.tubes.database.DatabaseClient;
import com.kelompoka.tubes.model.Guru;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;


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
                    addUser();
                    Toast.makeText(getActivity().getApplicationContext(), "Guru Saved", Toast.LENGTH_SHORT).show();
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.hide(AddGuruFragment.this).commit();
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

    private void addUser(){

        final String name = nameText.getText().toString();
        final String number = numberText.getText().toString();
        final int age = Integer.valueOf(ageText.getText().toString());

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

}