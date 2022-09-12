package com.ardananjungkusuma.catkeu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class CashflowDetail extends AppCompatActivity {
    String[] listCashFlow;
    ListView listCashflowView;
    DatabaseHelper database;
    Cursor cursor;
    ArrayList<Model>modelArrayList = new ArrayList<>();
    FloatingActionButton backToMenu;

    private String formatRupiah(Double number){
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        return formatRupiah.format(number);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cashflow_detail);
        database = new DatabaseHelper(this);

        listCashflowView = (ListView)findViewById(R.id.listCashflow);
        backToMenu = findViewById(R.id.cdBackButton);

        SQLiteDatabase db = database.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM keuangan", null);
        while(cursor.moveToNext()){
            int id = cursor.getInt(0);
            String tanggal = cursor.getString(1);
            double nom = Double.parseDouble(cursor.getString(2));
            String keterangan = cursor.getString(3);
            String kategori = cursor.getString(4);
            String nominal = formatRupiah(nom);


            modelArrayList.add(new Model(id, tanggal, nominal, keterangan, kategori));
        }

        Custom adapter = new Custom(this,R.layout.singledata,modelArrayList);
        listCashflowView.setAdapter(adapter);

        backToMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loginIntent = new Intent(CashflowDetail.this, MainActivity.class);
                startActivity(loginIntent);
                finish();
            }
        });
    }

    private class Custom extends BaseAdapter {
        private Context context;
        private int layout;
        private ArrayList<Model>modelArrayList=new ArrayList<>();

        public Custom(Context context, int layout, ArrayList<Model> modelArrayList) {
            this.context = context;
            this.layout = layout;
            this.modelArrayList = modelArrayList;
        }

        private class ViewHolder{
            TextView symbol, money, keterangan, tanggal;
            ImageView imageCategory;
            CardView cardView;


        }

        @Override
        public int getCount() {
            return modelArrayList.size();
        }

        @Override
        public Object getItem(int i) {
            return modelArrayList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout,null);

            holder.symbol=view.findViewById(R.id.symbol);
            holder.money=view.findViewById(R.id.money);
            holder.keterangan=view.findViewById(R.id.descCashflow);
            holder.tanggal=view.findViewById(R.id.dateCashflow);
            holder.imageCategory = view.findViewById(R.id.imageCategory);
            holder.cardView= view.findViewById(R.id.cardView);

            view.setTag(holder);

            Model model=modelArrayList.get(i);
            holder.keterangan.setText(model.getKeterangan());
            holder.money.setText(model.getNominal());
            holder.tanggal.setText(model.getTanggal());
            if(model.getKategori().equalsIgnoreCase("Pemasukan")){
                holder.symbol.setText("[+]");
                holder.imageCategory.setImageResource(R.drawable.arrowgreen);
            } else {
                holder.symbol.setText("[-]");
            }

            return view;
        }
    }
}