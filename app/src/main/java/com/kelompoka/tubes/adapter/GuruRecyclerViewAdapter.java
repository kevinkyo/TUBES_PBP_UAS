package com.kelompoka.tubes.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.kelompoka.tubes.R;
import com.kelompoka.tubes.UpdateGuruFragment;
import com.kelompoka.tubes.model.Guru;

import java.util.ArrayList;
import java.util.List;

public class GuruRecyclerViewAdapter extends RecyclerView.Adapter<GuruRecyclerViewAdapter.UserViewHolder>  {

    private Context context;
    private List<Guru> guruList;
    private List<Guru> guruListFull;


    public GuruRecyclerViewAdapter(Context context, List<Guru> guruList) {
        this.context = context;
        this.guruList = guruList;
        guruListFull = new ArrayList<>(guruList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_guru, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        Guru guru = guruList.get(position);
        holder.textViewName.setText(guru.getFullName());
        holder.textViewAge.setText(Integer.toString(guru.getAge())+ " years old");
        holder.textViewNumber.setText(guru.getNumber());
    }

    @Override
    public int getItemCount() {
        return guruList.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textViewNumber,  textViewName, textViewAge;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.full_name_text);
            textViewNumber = itemView.findViewById(R.id.number_text);
            textViewAge = itemView.findViewById(R.id.age_text);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            AppCompatActivity activity = (AppCompatActivity) v.getContext();
            Guru guru = guruList.get(getAdapterPosition());
            Bundle data = new Bundle();
            data.putSerializable("guru", guru);
            UpdateGuruFragment updateFragment = new UpdateGuruFragment();
            updateFragment.setArguments(data);
            activity.getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frame_layout, updateFragment)
                    .commit();
        }
    }


/*
    @Override
    public Filter getFilter() {
        return userListFilter;
    }

    private Filter userListFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<Guru> filteredList = new ArrayList<>();

            if(charSequence == null || charSequence.length()==0){
                filteredList.addAll(guruListFull);
            }
            else
            {
                String filterPattern = charSequence.toString().toLowerCase().trim();

                for (Guru guru : guruListFull){
                    if(guru.getFullName().toLowerCase().contains(filterPattern)){
                        filteredList.add(guru);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values=filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults results) {
            guruList.clear();
            guruList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

 */
}


