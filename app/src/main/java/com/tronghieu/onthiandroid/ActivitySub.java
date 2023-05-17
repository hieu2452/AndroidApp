package com.tronghieu.onthiandroid;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ActivitySub extends AppCompatActivity {

    private TextView txtId;
    private TextView txtPlate;
    private TextView txtRoad;
    private TextView txtPrice;
    private TextView txtDiscount;
    private Button btnDone;
    private Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_sub);

        txtId = findViewById(R.id.txtId1);
        txtPlate = findViewById(R.id.txtPlate1);
        txtRoad = findViewById(R.id.txtRoad1);
        txtPrice = findViewById(R.id.txtPrice1);
        txtDiscount = findViewById(R.id.txtDiscount1);
        btnDone = findViewById(R.id.btnDone);
        btnBack = findViewById(R.id.btnBack);

        Intent intent = getIntent();
        Bundle b = intent.getExtras();

        if(b != null){
            int id = b.getInt("Id");
            float price = b.getFloat("Price");
            String road = b.getString("Road");
            String plate = b.getString("Plate");
            int discount = b.getInt("Discount");
            txtId.setText(String.valueOf(id));
            txtDiscount.setText(String.valueOf(discount));
            Log.d("Kiem tra", ""+ discount);
            txtPlate.setText(plate);
            txtPrice.setText(Float.toString(price));
            txtRoad.setText(road);
            btnDone.setText("Save");
        }

        btnDone.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                int id =Integer.parseInt(txtId.getText().toString());
                float price = Float.parseFloat(txtPrice.getText().toString());
                String road = txtRoad.getText().toString();
                String plate = txtPlate.getText().toString();
                int discount = Integer.parseInt(txtDiscount.getText().toString());

                Intent intent1 = new Intent();
                Bundle bundle = new Bundle();
                bundle.putInt("Id",id);
                bundle.putString("Plate",plate);
                bundle.putInt("Discount", discount);
                bundle.putString("Road", road);
                bundle.putFloat("Price", price);

                intent1.putExtras(bundle);

                setResult(150,intent1);

                Log.d("kiem tra", ""+ intent1);

                finish();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivitySub.this , MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
