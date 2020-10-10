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
import com.kelompoka.tubes.UpdateStudentFragment;
import com.kelompoka.tubes.model.Student;

import java.util.ArrayList;
import java.util.List;

public class StudentRecyclerViewAdapter extends RecyclerView.Adapter<StudentRecyclerViewAdapter.UserViewHolder> implements Filterable {

    private Context context;
    private List<Student> studentList;
    private List<Student> studentListFull;


    public StudentRecyclerViewAdapter(Context context, List<Student> studentList) {
        this.context = context;
        this.studentList = studentList;
        studentListFull = new ArrayList<>(studentList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_student, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        Student student = studentList.get(position);
        holder.textViewName.setText(student.getName());
        holder.textViewAge.setText(Integer.toString(student.getAge())+ " years old");
        holder.textViewKelas.setText(student.getKelas());
        holder.textViewAlamat.setText(student.getAlamat());
    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textViewKelas,  textViewName, textViewAge, textViewAlamat;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.name_text);
            textViewKelas = itemView.findViewById(R.id.kelas_text);
            textViewAge = itemView.findViewById(R.id.ageS_text);
            textViewAlamat = itemView.findViewById(R.id.alamat_text);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            AppCompatActivity activity = (AppCompatActivity) v.getContext();
            Student student = studentList.get(getAdapterPosition());
            Bundle data = new Bundle();
            data.putSerializable("student", student);
            UpdateStudentFragment updateFragment = new UpdateStudentFragment();
            updateFragment.setArguments(data);
            activity.getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frame_layout, updateFragment)
                    .commit();
        }
    }

    @Override
    public Filter getFilter() {
        return userListFilter;
    }

    private Filter userListFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<Student> filteredList = new ArrayList<>();

            if(charSequence == null || charSequence.length()==0){
                filteredList.addAll(studentListFull);
            }
            else
            {
                String filterPattern = charSequence.toString().toLowerCase().trim();

                for (Student student : studentListFull){
                    if(student.getName().toLowerCase().contains(filterPattern)){
                        filteredList.add(student);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values=filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults results) {
            studentList.clear();
            studentList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
}


