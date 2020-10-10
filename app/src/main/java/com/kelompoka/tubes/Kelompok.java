package com.kelompoka.tubes;

public class Kelompok extends AboutUsActivity {
    public String npm;
    public String nama;
    public String fakultas;
    public String jurusan;
    public double ipk;
    public String hobi;

    public Kelompok(String npm, String nama, String fakultas, String jurusan, double ipk, String hobi){
        this.npm = npm;
        this.nama = nama;
        this.fakultas = fakultas;
        this.jurusan = jurusan;
        this.ipk = ipk;
        this.hobi = hobi;
    }

    public String getNpm() { return npm; }

    public void setNpm(String npm) { this.npm = npm; }

    public String getNama() { return nama; }

    public void setNama(String nama) { this.nama = nama; }

    public String getFakultas() { return fakultas; }

    public void setFakultas(String fakultas) { this.fakultas = fakultas; }

    public String getJurusan() { return jurusan; }

    public void setJurusan(String jurusan) { this.jurusan = jurusan; }

    public double getIpk() { return ipk; }

    public void setIpk(double ipk) { this.ipk = ipk;}

    public String getHobi() { return hobi; }

    public void setHobi(String hobi) { this.hobi = hobi; }
}
