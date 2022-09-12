package com.ardananjungkusuma.catkeu;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

public class TPemasukan extends AppCompatActivity {
    EditText editDate, formNominal, formKeterangan;
    DatePickerDialog.OnDateSetListener setListener;
    Button backToMenu, btnSimpanPemasukan;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_t_pemasukan);

        db = new DatabaseHelper(this);
        editDate = findViewById(R.id.formDatePicker);
        formNominal = findViewById(R.id.formNominal);
        formKeterangan = findViewById(R.id.formKeterangan);
        backToMenu = findViewById(R.id.btnKembaliKeMenu);
        btnSimpanPemasukan = findViewById(R.id.btnSimpanPemasukan);

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        editDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        TPemasukan.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        month = month+1;
                        String date = day+"/"+month+"/"+year;
                        editDate.setText(date);
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });

        // back to menu
        backToMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loginIntent = new Intent(TPemasukan.this,MainActivity.class);
                startActivity(loginIntent);
                finish();
            }
        });

        btnSimpanPemasukan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strDate = editDate.getText().toString();
                String strNominal = formNominal.getText().toString();
                String strKeterangan = formKeterangan.getText().toString();
                if(strDate != "" && strNominal != "" && strKeterangan != ""){
                    Boolean addPemasukan = db.insertDataKeuangan(strDate, strNominal, strKeterangan, "Pemasukan");
                    if(addPemasukan == true){
                        Toast.makeText(getApplicationContext(), "Pemasukan Berhasil Ditambah!",Toast.LENGTH_SHORT).show();
                        Intent refreshPage = new Intent(TPemasukan.this, TPemasukan.class);
                        startActivity(refreshPage);
                        finish();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Data tidak bisa kosong!",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}