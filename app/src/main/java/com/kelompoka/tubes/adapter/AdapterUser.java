package com.kelompoka.tubes.adapter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.kelompoka.tubes.API.UserAPI;
import com.kelompoka.tubes.model.User;
import com.kelompoka.tubes.R;
import com.kelompoka.tubes.Views.TambahEditMahasiswa;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.android.volley.Request.Method.DELETE;

public class AdapterUser extends RecyclerView.Adapter<AdapterUser.adapterUserViewHolder> {

    private List<User> userList;
    private List<User> userListFiltered;
    private Context context;
    private View view;
    private AdapterUser.deleteItemListener mListener;

    public AdapterUser(Context context, List<User> userList,
                            AdapterUser.deleteItemListener mListener) {
        this.context                =context;
        this.userList          = userList;
        this.userListFiltered  = userList;
        this.mListener              = mListener;
    }


    @NonNull
    @Override
    public adapterUserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        view = layoutInflater.inflate(R.layout.activity_myprofile, parent, false);
        return new adapterUserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull adapterUserViewHolder holder, int position) {
        final User user = userListFiltered.get(position);

        holder.txtNama.setText(user.getNama());

    }

    @Override
    public int getItemCount() {
        return (userListFiltered != null) ? userListFiltered.size() : 0;
    }

    public class adapterUserViewHolder extends RecyclerView.ViewHolder {
        private TextView txtNama;

        public adapterUserViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNama         = (TextView) itemView.findViewById(R.id.twNama);
        }
    }

    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String userInput = charSequence.toString().toLowerCase();
                if (userInput.isEmpty()) {
                    userListFiltered = userList;
                }
                else {
                    List<User> filteredList = new ArrayList<>();
                    for(User user : userList) {
                        if(User.getNama().toLowerCase().contains(userInput)  {
                            filteredList.add(user);
                        }
                    }
                    userListFiltered = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = userListFiltered;
                return filterResults;
            }
            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                userListFiltered = (ArrayList<User>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public void loadFragment(Fragment fragment) {
        AppCompatActivity activity = (AppCompatActivity) view.getContext();
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_view_user,fragment)
                .addToBackStack(null)
                .commit();
    }
}