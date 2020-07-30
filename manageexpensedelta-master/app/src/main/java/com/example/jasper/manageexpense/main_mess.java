package com.example.jasper.manageexpense;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;



public class main_mess extends Activity {
TextView t,tt;
    ListView list;
    TabHistory_Week_Adapter adapter;
    List<TabHistory_Week_List> lists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_mess);
        list = (ListView) findViewById(R.id.listt);
        t=(TextView) findViewById(R.id.textView8);
        tt=(TextView) findViewById(R.id.textView19);
        DBHelper db = new DBHelper(getApplicationContext());
        lists = db.getMessWeek();
        adapter = new TabHistory_Week_Adapter(getApplicationContext(), (ArrayList<TabHistory_Week_List>) lists);
        list.setAdapter(adapter);
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z");
//        String currentDateandTime = sdf.format(new Date());
//        Log.d("Name: ", currentDateandTime);
//        String jk=currentDateandTime.substring(8,10);
//        int ui=Integer.parseInt(jk);
//        if(jk.equals("01"))
//        {
//            int gg=db.deleteAdd1Category("Mess");
//        }
     //   DBHelper db = new DBHelper(getApplicationContext());

      //  int gg=db.deleteAddCategory(h,g);
        Calendar c = Calendar.getInstance();
        int a=db.getContactsCount();
//        String jj=db.getContacts1Count();
//        String oo=jj.substring(3,5);
//        int yy=Integer.parseInt(oo);
//        String oo1=jj.substring(0,2);
//        int yy1=Integer.parseInt(oo1);
        int yy = c.get(Calendar.DAY_OF_MONTH);
        int yy1 = c.get(Calendar.MONTH);
        Log.d("Date", Integer.toString(yy));
        Log.d("Month", Integer.toString(yy1));

        yy1 = yy1 + 1;
        int month=0;
        switch(yy1)
        {
            case 1:{month=31;
            break;}
            case 2:{month=28;
                break;}
            case 3:{month=31;
                break;}
            case 4:{month=30;
                break;}
            case 5:{month=31;
                break;}
            case 6:{month=30;
                break;}
            case 7:{month=31;
                break;}
            case 8:{month=31;
                break;}
            case 9:{month=30;
                break;}
            case 10:{month=31;
                break;}
            case 11:{month=30;
                break;}
            case 12:{month=31;
                break;}

        }

        int gj=month-yy;
        int average=(3800-a)/gj;
        int amount_left = (average - 200) * gj;
        tt.setText(Integer.toString(amount_left),TextView.BufferType.EDITABLE);
        t.setText(Integer.toString(average), TextView.BufferType.EDITABLE);



    }
}
