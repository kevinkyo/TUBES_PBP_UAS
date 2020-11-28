package com.kelompoka.tubes;

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
import com.kelompoka.tubes.API.GuruAPI;
import com.kelompoka.tubes.API.StudentAPI;
import com.kelompoka.tubes.adapter.GuruRecyclerViewAdapter;
import com.kelompoka.tubes.adapter.StudentRecyclerViewAdapter;
import com.kelompoka.tubes.database.DatabaseClient;
import com.kelompoka.tubes.model.Guru;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.kelompoka.tubes.model.Student;
import com.shashank.sony.fancytoastlib.FancyToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.android.volley.Request.Method.GET;

public class GuruActivity extends AppCompatActivity {

    private TextInputEditText editText;
    private FloatingActionButton addBtn;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout refreshLayout;

    private GuruRecyclerViewAdapter adapter;
    private List<Guru> listGuru = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guru);
        editText = findViewById(R.id.input_name);
        addBtn = findViewById(R.id.add_member);

        getGuru();

        adapter = new GuruRecyclerViewAdapter(GuruActivity.this,listGuru);

        refreshLayout = findViewById(R.id.swipe_refresh);
        recyclerView = findViewById(R.id.user_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment AddFragment = new AddGuruFragment();

                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frame_layout, AddFragment)
                        .commit();
            }
        });

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getGuru();

                refreshLayout.setRefreshing(false);
            }
        });

    }

    public void getGuru()
    {
        //Tambahkan tampil buku disini
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());


        final JsonObjectRequest stringRequest = new JsonObjectRequest(GET, GuruAPI.URL_SELECT
                , null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray jsonArray = response.getJSONArray("data");

                    if(!listGuru.isEmpty())
                        listGuru.clear();

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = (JSONObject) jsonArray.get(i);

                        int id              = Integer.parseInt(jsonObject.optString("id"));
                        String number         = jsonObject.optString("number");
                        String fullname        = jsonObject.optString("fullname");
                        Integer age         = jsonObject.optInt("age");



                        Guru guru = new Guru( id, number, fullname, age);

                        listGuru.add(guru);

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
        class GetUsers extends AsyncTask<Void, Void, List<Guru>> {

            @Override
            protected List<Guru> doInBackground(Void... voids) {
                List<Guru> guruList = DatabaseClient
                        .getInstance(getApplicationContext())
                        .getDatabase()
                        .guruDao()
                        .getAll();
                return guruList;
            }

            @Override
            protected void onPostExecute(List<Guru> users) {
                super.onPostExecute(users);
                final GuruRecyclerViewAdapter adapter = new GuruRecyclerViewAdapter(GuruActivity.this, users);
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

