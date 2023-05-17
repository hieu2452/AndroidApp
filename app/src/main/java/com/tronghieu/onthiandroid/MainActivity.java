package com.tronghieu.onthiandroid;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity {

    public ArrayList<Taxi> taxiLst;
    private EditText txtSearch;
    private ListView lstTaxi;
    private Database db;
    private Adapter adapter;
    private FloatingActionButton btnAdd;
    static public int SelectedItemId;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtSearch = findViewById(R.id.txtSearch);
        lstTaxi = findViewById(R.id.lstTaxi);
        btnAdd = findViewById(R.id.btnAdd);

        taxiLst = new ArrayList<>();

        db = new Database(this,"TaxiTable", null,1);

//        db.addTaxi(new Taxi(1,"28A2-234","14.3 km",10000,5));
//        db.addTaxi(new Taxi(2,"28C2-234","11.3 km",30000,11));
//        db.addTaxi(new Taxi(3,"28E2-234","10.3 km",20000,10));
//        db.addTaxi(new Taxi(4,"28B2-234","9.3 km",90000,20));
//        db.addTaxi(new Taxi(5,"28D2-234","9.3 km",90000,30));

        taxiLst = db.getAllTaxi();
//        Log.d("Kiem tra"+ taxiLst.get(0).getPlate());

        adapter = new Adapter(taxiLst,this);
        lstTaxi.setAdapter(adapter);

        registerForContextMenu(lstTaxi);


        lstTaxi.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                SelectedItemId=position;
                String plate = taxiLst.get(SelectedItemId).getPlate();
                float total = taxiLst.get(SelectedItemId).getTotal();
                int d= 0;
                for(Taxi c:taxiLst){
                    if(c.getTotal()>total) d++;
                }
                Toast.makeText(MainActivity.this,"Bien so xe " + plate + " " + " Co " + d + " hoa don lon hon hoa don hien tai",Toast.LENGTH_LONG).show();
                return false;
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ActivitySub.class);
                startActivityForResult(intent,50);
            }
        });

        txtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                String priceInput = txtSearch.getText().toString();
//                if (!priceInput.matches("\\d+")) {
//                    // Show an error message or handle the invalid input
//                    Toast.makeText(MainActivity.this, "Invalid price input. Only numeric values are allowed.", Toast.LENGTH_SHORT).show();
//                    txtSearch.setText("");
//
//                }else{
//                    adapter.getFilter().filter(s.toString());
//                    adapter.notifyDataSetChanged();
//                }
                adapter.getFilter().filter(s.toString());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater= new MenuInflater(this);
        inflater.inflate(R.menu.contextmenu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.menuSortByPrice:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    taxiLst.sort(Comparator.comparing(Taxi::getTotal).reversed());
                }
                break;
            case R.id.menuSortByDistance:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                    taxiLst.sort(Comparator.comparing(Taxi::getRoad));
                    taxiLst.sort(Comparator.comparing(Taxi::getPlate));
                }
        }
        adapter.notifyDataSetChanged();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuInflater inflater= new MenuInflater(this);
        inflater.inflate(R.menu.optionsmenu,menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.optionSua:
                Intent intent = new Intent(MainActivity.this ,ActivitySub.class);
                Bundle b = new Bundle();
                Taxi taxi = taxiLst.get(SelectedItemId);
                b.putInt("Id",taxi.getId());
                b.putString("Plate",taxi.getPlate());
                b.putInt("Discount", taxi.getDiscount());
                b.putString("Road", taxi.getRoad());
                b.putFloat("Price", taxi.getPrice());
                intent.putExtras(b);
                startActivityForResult(intent,100);
                break;
            case R.id.optionXoa:
                Taxi taxi1 = taxiLst.get(SelectedItemId);
                db.deleteTaxi(taxi1);
                taxiLst.remove(SelectedItemId);
                adapter.notifyDataSetChanged();
                break;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        db = new Database(this,"TaxiTable", null,1);

        Bundle b = data.getExtras();

        int id = b.getInt("Id");
        float price = b.getFloat("Price");
        String road = b.getString("Road");
        String plate = b.getString("Plate");
        int discount = b.getInt("Discount");

        Taxi taxi = new Taxi(id,plate,road,price,discount);

        if(requestCode == 100 && resultCode == 150){
            db.updateTaxi(taxi);
            taxiLst.remove(SelectedItemId);
            taxiLst.add(SelectedItemId,taxi);
        }else if(requestCode == 50 && resultCode == 150){
            db.addTaxi(taxi);
            taxiLst.add(taxi);
            Toast.makeText(MainActivity.this,"Them thanh cong",Toast.LENGTH_LONG).show();

        }

        adapter.notifyDataSetChanged();

    }
}