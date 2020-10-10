package com.kelompoka.tubes;

import android.app.AlertDialog;
import android.content.DialogInterface;
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


public class UpdateStudentFragment extends Fragment {

    TextInputEditText nameText, kelasText, ageText, alamatText;
    TextInputLayout layoutName, layoutAge, layoutKelas, layoutAlamat;
    Button saveBtn, deleteBtn, cancelBtn;
    Student student;

    LinearLayout ll;
    int darkM = AppCompatDelegate.MODE_NIGHT_YES;

    public UpdateStudentFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_student_update, container, false);
        student = (Student) getArguments().getSerializable("student");
        nameText = view.findViewById(R.id.input_nameS);
        kelasText = view.findViewById(R.id.input_kelas);
        ageText = view.findViewById(R.id.input_ageS);
        alamatText = view.findViewById(R.id.input_alamat);

        layoutName = view.findViewById(R.id.input_nameS_layout);
        layoutAge = view.findViewById(R.id.input_ageS_layout);
        layoutKelas = view.findViewById(R.id.input_kelas_layout);
        layoutAlamat = view.findViewById(R.id.input_alamat_layout);

        ll = (LinearLayout) view.findViewById(R.id.linearLayout);

        if (AppCompatDelegate.getDefaultNightMode() == darkM)
        {
            ll.setBackgroundColor(Color.parseColor("#141414"));
        }
        else
        {
            ll.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }

        saveBtn = view.findViewById(R.id.btn_update);
        deleteBtn = view.findViewById(R.id.btn_delete);
        cancelBtn = view.findViewById(R.id.btn_cancel);
        try {
                nameText.setText(student.getName());
                ageText.setText(student.getAge().toString());
                kelasText.setText(student.getKelas());
                alamatText.setText(student.getAlamat());

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(nameText.getText().toString().isEmpty() || kelasText.getText().toString().isEmpty() || ageText.getText().toString().isEmpty() || alamatText.getText().toString().isEmpty()){
                    layoutKelas.setError("Please fill Kelas correctly.");
                    layoutName.setError("Please fill name correctly.");
                    layoutAge.setError("Please fill age correctly.");
                    layoutAlamat.setError("Please fill age correctly.");
                }
                else
                {
                    student.setName(nameText.getText().toString());
                    student.setKelas(kelasText.getText().toString());
                    student.setAge(Integer.valueOf(ageText.getText().toString()));
                    student.setAlamat(alamatText.getText().toString());
                    update(student);
                }

            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Are you sure to delete?");

                        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                    delete(student);
                            }
                        }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                                .show();

            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.hide(UpdateStudentFragment.this).commit();
            }
        });
    }

    private void update(final Student student){
        class UpdateUser extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {

                DatabaseClient.getInstance(getActivity().getApplicationContext()).getDatabase2()
                        .studentDao()
                        .update(student);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Toast.makeText(getActivity().getApplicationContext(), "Student updated", Toast.LENGTH_SHORT).show();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.hide(UpdateStudentFragment.this).commit();
            }
        }

        UpdateUser update = new UpdateUser();
        update.execute();
    }

    private void delete(final Student student){
        class DeleteUser extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {

                DatabaseClient.getInstance(getActivity().getApplicationContext()).getDatabase2()
                        .studentDao()
                        .delete(student);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Toast.makeText(getActivity().getApplicationContext(), "Student deleted", Toast.LENGTH_SHORT).show();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.hide(UpdateStudentFragment.this).commit();
            }
        }

        DeleteUser delete = new DeleteUser();
        delete.execute();
    }
}