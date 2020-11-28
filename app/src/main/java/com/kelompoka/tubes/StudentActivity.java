package com.kelompoka.tubes;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.kelompoka.tubes.API.StudentAPI;
import com.kelompoka.tubes.adapter.StudentRecyclerViewAdapter;
import com.kelompoka.tubes.database.DatabaseClient;
import com.kelompoka.tubes.model.Student;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.shashank.sony.fancytoastlib.FancyToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.android.volley.Request.Method.GET;
import static java.security.AccessController.getContext;

public class StudentActivity extends AppCompatActivity {

    private TextInputEditText editText;
    private FloatingActionButton addBtn;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout refreshLayout;
    private StudentRecyclerViewAdapter adapter;
    private List<Student> listStudent = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        editText = findViewById(R.id.input_name);
        addBtn = findViewById(R.id.add_member);

        getStudent();

        adapter = new StudentRecyclerViewAdapter(StudentActivity.this,listStudent);

        refreshLayout = findViewById(R.id.swipe_refresh);
        recyclerView = findViewById(R.id.user_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment AddFragment = new AddStudentFragment();

                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frame_layout, AddFragment)
                        .commit();
            }
        });

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getStudent();
                refreshLayout.setRefreshing(false);
            }
        });




    }



    public void getStudent()
    {
        //Tambahkan tampil buku disini
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());


        final JsonObjectRequest stringRequest = new JsonObjectRequest(GET, StudentAPI.URL_SELECT
                , null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray jsonArray = response.getJSONArray("data");

                    if(!listStudent.isEmpty())
                        listStudent.clear();

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = (JSONObject) jsonArray.get(i);

                        int id              = Integer.parseInt(jsonObject.optString("id"));
                        String name         = jsonObject.optString("name");
                        String kelas        = jsonObject.optString("kelas");
                        Integer age         = jsonObject.optInt("age");
                        String alamat       = jsonObject.optString("alamat");

                        Student student = new Student( id, name, kelas, age, alamat);

                        listStudent.add(student);

                    }
                    adapter.notifyDataSetChanged();
                }catch (JSONException e){
                    e.printStackTrace();
                }
                FancyToast.makeText(getApplicationContext(),response.optString("message"),
                        FancyToast.LENGTH_SHORT, FancyToast.INFO,false).show();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                FancyToast.makeText(getApplicationContext(),error.getMessage(),
                        FancyToast.LENGTH_SHORT,FancyToast.INFO,false).show();

            }
        });

        queue.add(stringRequest);
    }


    /*
    private void getUsers(){
        class GetUsers extends AsyncTask<Void, Void, List<Student>> {

            @Override
            protected List<Student> doInBackground(Void... voids) {
                List<Student> studentList = DatabaseClient
                        .getInstance(getApplicationContext())
                        .getDatabase2()
                        .studentDao()
                        .getAll();
                return studentList;
            }

            @Override
            protected void onPostExecute(List<Student> users) {
                super.onPostExecute(users);
                final StudentRecyclerViewAdapter adapter = new StudentRecyclerViewAdapter(StudentActivity.this, users);
                recyclerView.setAdapter(adapter);
                if (users.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Empty List", Toast.LENGTH_SHORT).show();
                }
                SearchView searchView = findViewById(R.id.search);
                searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String s) {
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String s) {
                        adapter.getFilter().filter(s);
                        return false;
                    }
                });



            }
        }

        GetUsers get = new GetUsers();
        get.execute();
    }
*/



}

