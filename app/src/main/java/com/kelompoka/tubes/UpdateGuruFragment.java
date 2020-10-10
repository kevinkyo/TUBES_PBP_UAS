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
import com.kelompoka.tubes.model.Guru;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;


public class UpdateGuruFragment extends Fragment {

    TextInputEditText nameText, numberText, ageText;
    TextInputLayout layoutName, layoutAge, layoutNum;
    Button saveBtn, deleteBtn, cancelBtn;
    Guru guru;

    LinearLayout ll;
    int darkM = AppCompatDelegate.MODE_NIGHT_YES;

    public UpdateGuruFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_guru_update, container, false);
        guru = (Guru) getArguments().getSerializable("guru");
        nameText = view.findViewById(R.id.input_name);
        numberText = view.findViewById(R.id.input_number);
        ageText = view.findViewById(R.id.input_age);

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

        saveBtn = view.findViewById(R.id.btn_update);
        deleteBtn = view.findViewById(R.id.btn_delete);
        cancelBtn = view.findViewById(R.id.btn_cancel);
        try {
                nameText.setText(guru.getFullName());
                ageText.setText(guru.getAge().toString());
                numberText.setText(guru.getNumber());

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

                if(nameText.getText().toString().isEmpty() || numberText.getText().toString().isEmpty() || ageText.getText().toString().isEmpty()){
                    layoutNum.setError("Please fill number correctly.");
                    layoutName.setError("Please fill name correctly.");
                    layoutAge.setError("Please fill age correctly.");
                }
                else
                {
                    guru.setFullName(nameText.getText().toString());
                    guru.setNumber(numberText.getText().toString());
                    guru.setAge(Integer.valueOf(ageText.getText().toString()));
                    update(guru);
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
                                    delete(guru);
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
                transaction.hide(UpdateGuruFragment.this).commit();
            }
        });
    }

    private void update(final Guru guru){
        class UpdateUser extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {

                DatabaseClient.getInstance(getActivity().getApplicationContext()).getDatabase()
                        .guruDao()
                        .update(guru);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Toast.makeText(getActivity().getApplicationContext(), "Guru updated", Toast.LENGTH_SHORT).show();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.hide(UpdateGuruFragment.this).commit();
            }
        }

        UpdateUser update = new UpdateUser();
        update.execute();
    }

    private void delete(final Guru guru){
        class DeleteUser extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {

                DatabaseClient.getInstance(getActivity().getApplicationContext()).getDatabase()
                        .guruDao()
                        .delete(guru);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Toast.makeText(getActivity().getApplicationContext(), "Guru deleted", Toast.LENGTH_SHORT).show();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.hide(UpdateGuruFragment.this).commit();
            }
        }

        DeleteUser delete = new DeleteUser();
        delete.execute();
    }
}