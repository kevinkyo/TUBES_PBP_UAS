package com.kelompoka.tubes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;



import com.kelompoka.tubes.databinding.ActivityAboutUsBinding;

import java.util.ArrayList;

public class AboutUsActivity extends AppCompatActivity {

    ArrayList<Kelompok> ListKelompok;
    ActivityAboutUsBinding binding;

    Button nextBtn;
    int i=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView( this, R.layout.activity_about_us);

        ListKelompok = new ListKelompok().KELOMPOK;
        nextBtn = findViewById(R.id.nextBtn);
        binding.setKlp(ListKelompok.get(i));

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i=i+1;
                if(i==3)
                {
                    i=i-3;
                    binding.setKlp(ListKelompok.get(i));
                }
                else
                {
                    binding.setKlp(ListKelompok.get(i));
                }

            }
        });

    }
}