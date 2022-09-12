package com.ardananjungkusuma.catkeu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper database;
    Cursor cursor, cursor2;
    TextView editPengeluaran, editPemasukan;
    ImageView imgSetting,tambahPemasukan,tambahPengeluaran,dataKeuangan;

    private String formatRupiah(Double number){
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        return formatRupiah.format(number);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        database = new DatabaseHelper(this);

        tambahPemasukan = (ImageView)findViewById(R.id.imageIncome);
        tambahPengeluaran = (ImageView)findViewById(R.id.imageOutcome);
        dataKeuangan = (ImageView)findViewById(R.id.detailImage);
        editPemasukan = (TextView)findViewById(R.id.editPemasukan);
        editPengeluaran = (TextView)findViewById(R.id.editPengeluaran);
        imgSetting = (ImageView)findViewById(R.id.settingImage);

        SQLiteDatabase db = database.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM keuangan WHERE kategori = 'Pemasukan'", null);
        cursor2 = db.rawQuery("SELECT * FROM keuangan WHERE kategori = 'Pengeluaran'", null);
        double pemasukan = 0;
        double pengeluaran = 0;
        while(cursor.moveToNext()){
            double nominal = Double.parseDouble(cursor.getString(2));
            pemasukan = pemasukan + nominal;
        }
        while(cursor2.moveToNext()){
            double nominal = Double.parseDouble(cursor2.getString(2));
            pengeluaran = pengeluaran + nominal;
        }

        String luaran = "Pengeluaran : " + formatRupiah(pengeluaran);
        String masukan = "Pemasukan : " + formatRupiah(pemasukan);

        editPemasukan.setText(String.valueOf(masukan));
        editPengeluaran.setText(String.valueOf(luaran));

//        Log.d("ADebugTag", "Value: " + String.valueOf(pemasukan) + " - " + String.valueOf(pengeluaran));

        // Session check
        Boolean checkSession = database.sessionCheck("kosong");
        if(checkSession == true){
            Intent loginIntent = new Intent(MainActivity.this,Login.class);
            startActivity(loginIntent);
            finish();
        }

        // tambah pemasukan
        tambahPemasukan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,TPemasukan.class);
                startActivity(intent);
                finish();
            }
        });

        // tambah pemasukan
        tambahPengeluaran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,TPengeluaran.class);
                startActivity(intent);
                finish();
            }
        });

        // tambah pemasukan
        dataKeuangan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,CashflowDetail.class);
                startActivity(intent);
                finish();
            }
        });

        // setting
        imgSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,Setting.class);
                startActivity(intent);
                finish();
            }
        });
    }
}