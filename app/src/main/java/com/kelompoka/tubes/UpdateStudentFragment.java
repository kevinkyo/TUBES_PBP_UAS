package com.kelompoka.tubes;

import android.app.AlertDialog;
import android.app.ProgressDialog;
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

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.kelompoka.tubes.API.StudentAPI;
import com.kelompoka.tubes.database.DatabaseClient;
import com.kelompoka.tubes.model.Student;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.shashank.sony.fancytoastlib.FancyToast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.android.volley.Request.Method.DELETE;
import static com.android.volley.Request.Method.POST;
import static com.android.volley.Request.Method.PUT;


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

    public void update(final Student student) {
        RequestQueue queue = Volley.newRequestQueue(getContext());

        StringRequest stringRequest = new StringRequest(POST, StudentAPI.URL_UPDATE + student.getId(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject obj = new JSONObject(response);
                    if(obj.getString("message").equalsIgnoreCase("Update Student Success"))
                    {
                        FancyToast.makeText(getContext(),"Student Updated",
                                FancyToast.LENGTH_SHORT, FancyToast.INFO,false).show();
                        FragmentTransaction transaction = getFragmentManager().beginTransaction();
                        transaction.hide(UpdateStudentFragment.this).commit();
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
                params.put("name", student.getName());
                params.put("kelas", student.getKelas());
                params.put("age", String.valueOf(student.getAge()));
                params.put("alamat", student.getAlamat());

                return params;
            }
        };

        queue.add(stringRequest);
    }


    public void delete(Student student){
        //Tambahkan hapus buku disini
        RequestQueue queue = Volley.newRequestQueue(getContext());


        //Memulai membuat permintaan request menghapus data ke jaringan
        StringRequest stringRequest = new StringRequest(POST, StudentAPI.URL_DELETE + student.getId(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Disini bagian jika response jaringan berhasil tidak terdapat ganguan/error

                try {
                    //Mengubah response string menjadi object
                    JSONObject obj = new JSONObject(response);

                    if(obj.getString("message").equalsIgnoreCase("Delete Student Success"))
                    {
                        FancyToast.makeText(getContext(),"Student Deleted",
                                FancyToast.LENGTH_SHORT, FancyToast.INFO,false).show();

                        FragmentTransaction transaction = getFragmentManager().beginTransaction();
                        transaction.hide(UpdateStudentFragment.this).commit();
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
                //Disini bagian jika response jaringan terdapat ganguan/error
                FancyToast.makeText(getContext(),error.getMessage(),
                        FancyToast.LENGTH_SHORT, FancyToast.INFO,false).show();
            }
        });

        //Disini proses penambahan request yang sudah kita buat ke reuest queue yang sudah dideklarasi
        queue.add(stringRequest);
    }

    /*

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

     */
}