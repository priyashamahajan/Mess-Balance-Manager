package com.example.jasper.manageexpense;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.OnColorSelectedListener;
import com.flask.colorpicker.builder.ColorPickerClickListener;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;

import net.margaritov.preference.colorpicker.ColorPickerDialog;
import net.margaritov.preference.colorpicker.ColorPickerPreference;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jasper on 3/2/2017.
 */

public class Tab1 extends Activity {
    ListView listView;
    Button btnAdd;
    Button cancel;
    EditText inputLabel,inputLabel2;
    List<Tab1_ListView> listViews;
    Tab1_Adapter tab1_adapter;
    DBHelper dbHelper;

    @Override
    public void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab1);

        listView = (ListView) findViewById(R.id.listAddCategory);
        btnAdd = (Button) findViewById(R.id.btnAddCategory);
        cancel = (Button) findViewById(R.id.btnCancelAdd);
        inputLabel = (EditText) findViewById(R.id.week_view_category);
        //inputLabel2 = (EditText) findViewById(R.id.editText3);
        loadListView();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String category_name = inputLabel.getText().toString();
                //String budget = inputLabel2.getText().toString();
                if (category_name.trim().length() > 0) {
                    DBHelper db = new DBHelper(getApplicationContext());
                    db.insertCategory(category_name);


                    inputLabel.setText("");

                    InputMethodManager imm = (InputMethodManager)
                            getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(inputLabel.getWindowToken(), 0);

                    Toast.makeText(getApplication(), "Successfully Added!", Toast.LENGTH_SHORT).show();
                    loadListView();

                } else{
                    Toast.makeText(getApplication(), "Please enter category name",
                            Toast.LENGTH_SHORT).show();
                }

            }
        });

         cancel.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 finish();
             }
         });
    }

    public void loadListView(){
        dbHelper = new DBHelper(getApplicationContext());
        listViews = dbHelper.getCategoryName();
        tab1_adapter = new Tab1_Adapter(getApplicationContext(), listViews);
        listView.setAdapter(tab1_adapter);


    }



}


