package com.kelompoka.tubes;

import java.util.ArrayList;

public class ListKelompok {
    public ArrayList<Kelompok> KELOMPOK;
    public ListKelompok(){
        KELOMPOK = new ArrayList();
        KELOMPOK.add(KEVIN);
        KELOMPOK.add(ELBERT);
        KELOMPOK.add(FRENGKI);
    }
    public static final Kelompok KEVIN = new Kelompok("180709600", "Kevin",
            "FTI", "Teknik Informatika", 3.5, "Adventure");
    public static final Kelompok ELBERT = new Kelompok("180709744", "Elbert Hendrata",
            "FTI", "Teknik Informatika", 3.3, "Makan");
    public static final Kelompok FRENGKI = new Kelompok("180709969", "Frengki Anggoro",
            "FTI", "Teknik Informatika", 3.2, "Desain");
}