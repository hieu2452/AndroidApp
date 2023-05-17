package com.tronghieu.onthiandroid;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class Database extends SQLiteOpenHelper {

    public static final String TableName="TaxiTable";
    public static final String Id="Id";
    public static final String Plate="Plate";
    public static final String Road="Road";
    public static final String Price="Price";
    public static final String Discount="Discount";


    public Database(@Nullable Context context,
                    @Nullable String name,
                    @Nullable SQLiteDatabase.CursorFactory factory,
                    int version) {
        super(context, name, factory, version);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sqlCreate="Create table if not exists "+TableName+ "("+ Id+" Integer Primary key,"
                +Plate+ " Text," + Road + " Text, "+Price + " Text, "+Discount+" Text)";
        sqLiteDatabase.execSQL(sqlCreate);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("Drop table if exists "+TableName);
        //Tạo lại
        onCreate(sqLiteDatabase);
    }

    public ArrayList<Taxi> getAllTaxi(){
        ArrayList<Taxi> list=new ArrayList<>();
        //CÂU TRUY VẤN
        String sql="select * from "+TableName;
        //lấy đối tượng csdl
        SQLiteDatabase db= this.getReadableDatabase();
        //chạy câu truy vấn
        Cursor cursor=db.rawQuery(sql,null);
        //Tạo arraylist contact để trả về
        if(cursor!=null){
            while (cursor.moveToNext()){
                Taxi taxi= new Taxi(cursor.getInt(0),
                        cursor.getString(1),cursor.getString(2)
                        ,cursor.getFloat(3),cursor.getInt(4));
                list.add(taxi);
            }
        }
        return list;
    }

    public void addTaxi(Taxi taxi){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Id,taxi.getId());
        contentValues.put(Plate,taxi.getPlate());
        contentValues.put(Road,taxi.getRoad());
        contentValues.put(Price,taxi.getPrice());
        contentValues.put(Discount,taxi.getDiscount());
        db.insert(TableName,null ,contentValues);
        db.close();
    }

    public void updateTaxi(Taxi taxi){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Id,taxi.getId());
        contentValues.put(Plate,taxi.getPlate());
        contentValues.put(Road,taxi.getRoad());
        contentValues.put(Price,taxi.getPrice());
        contentValues.put(Discount,taxi.getDiscount());
        db.update(TableName ,contentValues,Id + "=?",new String[]{String.valueOf(taxi.getId())});
        db.close();
    }


    public void deleteTaxi(Taxi taxi){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TableName ,Id + "=?",new String[]{String.valueOf(taxi.getId())});
        db.close();
    }
}
