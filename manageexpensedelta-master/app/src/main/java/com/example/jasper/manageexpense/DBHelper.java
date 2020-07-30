package com.example.jasper.manageexpense;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.Calendar;

/**
 * Created by Jasper on 2/24/2017.
 */

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "ExpenseManager.db";
    public static final String EXPENSE_TABLE_NAME = "Category";
    public static final String EXPENSE_COLUMN_ID = "id";
    public static final String EXPENSE_COLUMN_CATEGORY_NAME = "category_name";
    //public static final String EXPENSE_COLUMN_BUDGET = "budget";
    // public static final String EXPENSE_COLUMN_CATEGORY_IDENTIFIER = "identifier";

    public static final String EXPENSE_TABLE_ADD = "Add_Expense";
    public static final String EXPENSE_ADD_COLUMN_ID = "add_id";
    public static final String EXPENSE_ADD_COLUMN_CATEGORY_ADD = "category_add";
    public static final String EXPENSE_ADD_COLUMN_AMOUNT = "amount";
    public static final String EXPENSE_ADD_COLUMN_DATE = "date";
    public static final String EXPENSE_ADD_COLUMN_NOTE = "note";


    private HashMap hp;
    private Context con;

    public DBHelper(Context context) {
        super(context, "ExpenseManager", null, 1);
        con = context;

    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        String createTable = "create table Category (id integer primary key AUTOINCREMENT, category_name, identifier);";
        db.execSQL(createTable);


        String createTableAdd = "create table Add_Expense (add_id integer primary key AUTOINCREMENT, category_add, amount, date, note);";
        String createTablemesse= "create table Add_Mess (add_id integer primary key AUTOINCREMENT,amount, date, note);";
        db.execSQL(createTableAdd);

        db.execSQL(createTablemesse);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        onCreate(db);

    }

    public void insertCategory(String category_name) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(EXPENSE_COLUMN_CATEGORY_NAME, category_name);//column name, column value
        //values.put(EXPENSE_COLUMN_BUDGET, budget);//column name, column value


        // Inserting Row
        db.insert(EXPENSE_TABLE_NAME, null, values);//tableName, nullColumnHack, ContentValues
        db.close(); // Closing database connection

    }

    public void insertAdd_Expense(String category_add, String amount, String date, String note) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(EXPENSE_ADD_COLUMN_CATEGORY_ADD, category_add);
        values.put(EXPENSE_ADD_COLUMN_AMOUNT, amount);
        values.put(EXPENSE_ADD_COLUMN_DATE, date);
        values.put(EXPENSE_ADD_COLUMN_NOTE, note);
        db.insert(EXPENSE_TABLE_ADD, null, values);
        db.close();
    }

    public Cursor getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from Category where id=" + id + "", null);
        return res;
    }










    public int deleteAddCategory(String value) {
        int val = Integer.parseInt(value);
        Log.d("to the DBHElper", value);
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(EXPENSE_TABLE_ADD, EXPENSE_ADD_COLUMN_ID + "="+val, null) ;
    }
    public int deleteAdd1Category(String value) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(EXPENSE_TABLE_ADD, EXPENSE_ADD_COLUMN_CATEGORY_ADD + "='"+value+"'",null) ;
    }





    public ArrayList<String> getAllCategory() {
        ArrayList<String> array_list = new ArrayList<String>();
        hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from Category", null);
        res.moveToFirst();

        while (res.isAfterLast() == false) {
            array_list.add(res.getString(res.getColumnIndex(EXPENSE_COLUMN_CATEGORY_NAME)));
            res.moveToNext();
        }
        return array_list;
    }

    public List<TabHistory_Week_List> getHistoryWeek() {
        TabHistory_Week_List sample = null;

        List<TabHistory_Week_List> sampleList = new ArrayList<>();
        hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from Add_Expense  ", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            sample = new TabHistory_Week_List(cursor.getInt(0), cursor.getString(1), cursor.getInt(2), cursor.getString(3), cursor.getString(4));
            sampleList.add(sample);
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return sampleList;
    }
    public List<TabHistory_Week_List> getMessWeek() {
        TabHistory_Week_List sample = null;

        List<TabHistory_Week_List> sampleList = new ArrayList<>();
        hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Calendar c = Calendar.getInstance();
        int mon = c.get(Calendar.MONTH);
        mon = mon + 1;
        String month = Integer.toString(mon);
        String date = month + "%";
        String query = "select * from Add_Expense where category_Add='Mess' and date like '" + date + "';";
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            sample = new TabHistory_Week_List(cursor.getInt(0), cursor.getString(1), cursor.getInt(2), cursor.getString(3), cursor.getString(4));
            sampleList.add(sample);
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return sampleList;
    }

    public int getContactsCount() {

        Calendar c = Calendar.getInstance();
        int month = c.get(Calendar.MONTH);
        month = month + 1;
        String mon = Integer.toString(month);
        String date = mon + "%";
        String countQuery = "SELECT  amount FROM Add_Expense where category_Add='Mess' and date like '" + date + "';" ;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.moveToFirst();
        int y=0;
        int k=0;
        while (!cursor.isAfterLast()) {
            y =y+cursor.getInt(0);
            k++;

            cursor.moveToNext();
        }
      //  int y=cursor.getCount();
        cursor.close();

        // return count
        return y;
    }


    public String getContacts1Count() {
        String countQuery = "SELECT  date FROM Add_Expense where category_Add='Mess' order by date DESC" ;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
    //    String f=cursor.getString(0);
        cursor.moveToFirst();
        String y="";
        while (!cursor.isAfterLast()) {
            y =y+cursor.getString(0);
            break;

           // cursor.moveToNext();
        }
        //  int y=cursor.getCount();
        cursor.close();

        // return count
        return y;
    }

    public List<Graph_all_List> getPieGrapgListView() {
        Graph_all_List overList = null;

        List<Graph_all_List> listOverview = new ArrayList<>();
        hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT category_add, SUM(amount) AS total  FROM Add_Expense GROUP BY category_add", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            overList = new Graph_all_List(cursor.getInt(0), cursor.getString(cursor.getColumnIndexOrThrow("category_add")), cursor.getInt(cursor.getColumnIndexOrThrow("total")));
            listOverview.add(overList);
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return listOverview;
    }

    public List<Tab1_ListView> getCategoryName() {
        Tab1_ListView overList = null;

        List<Tab1_ListView> listOverview = new ArrayList<>();
        hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Category", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            overList = new Tab1_ListView(cursor.getInt(0), cursor.getString(1));
            listOverview.add(overList);
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return listOverview;
    }






}
