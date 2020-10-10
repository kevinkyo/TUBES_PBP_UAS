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
import com.kelompoka.tubes.model.Student;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;


public class AddStudentFragment extends Fragment {

    TextInputEditText nameText, kelasText, ageText, alamatText;
    TextInputLayout layoutName, layoutAge, layoutKelas, layoutAlamat;
    Button addBtn, cancelBtn;
    Student student;

    LinearLayout ll;
    int darkM = AppCompatDelegate.MODE_NIGHT_YES;

    public AddStudentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_student_add, container, false);

        nameText = view.findViewById(R.id.input_nameS);
        kelasText = view.findViewById(R.id.input_kelas);
        ageText = view.findViewById(R.id.input_ageS);
        alamatText = view.findViewById(R.id.input_alamat);

        layoutName = view.findViewById(R.id.input_nameS_layout);
        layoutAge = view.findViewById(R.id.input_ageS_layout);
        layoutKelas = view.findViewById(R.id.input_kelas_layout);
        layoutAlamat = view.findViewById(R.id.input_alamat_layout);

        cancelBtn = view.findViewById(R.id.btn_cancel);
        addBtn = view.findViewById(R.id.btn_add);

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

                if(nameText.getText().toString().isEmpty() || kelasText.getText().toString().isEmpty() || ageText.getText().toString().isEmpty() || alamatText.getText().toString().isEmpty()){
                    layoutKelas.setError("Please fill Kelas correctly.");
                    layoutName.setError("Please fill name correctly.");
                    layoutAge.setError("Please fill age correctly.");
                    layoutAlamat.setError("Please fill age correctly.");
                }

                else{
                    addUser();
                    Toast.makeText(getActivity().getApplicationContext(), "Student Saved", Toast.LENGTH_SHORT).show();
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.hide(AddStudentFragment.this).commit();
                }

            }
        });
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.hide(AddStudentFragment.this).commit();
            }
        });
    }

    private void addUser(){

        final String name = nameText.getText().toString();
        final String kelas = kelasText.getText().toString();
        final int age = Integer.valueOf(ageText.getText().toString());
        final String alamat = alamatText.getText().toString();

        class AddUser extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                Student student = new Student();
                student.setName(name);
                student.setKelas(kelas);
                student.setAge(age);
                student.setAlamat(alamat);

                DatabaseClient.getInstance(getContext()).getDatabase2()
                        .studentDao()
                        .insert(student);
                return null;
            }
        }
        AddUser add = new AddUser();
        add.execute();
    }

}