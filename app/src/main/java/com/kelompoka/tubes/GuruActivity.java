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

import com.kelompoka.tubes.adapter.GuruRecyclerViewAdapter;
import com.kelompoka.tubes.database.DatabaseClient;
import com.kelompoka.tubes.model.Guru;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

public class GuruActivity extends AppCompatActivity {

    private TextInputEditText editText;
    private FloatingActionButton addBtn;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout refreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guru);
        editText = findViewById(R.id.input_name);
        addBtn = findViewById(R.id.add_member);
        refreshLayout = findViewById(R.id.swipe_refresh);
        recyclerView = findViewById(R.id.user_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

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
                getUsers();
                refreshLayout.setRefreshing(false);
            }
        });

        getUsers();
    }

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

}

