package com.example.android_mswe2;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchActivity extends AppCompatActivity {
    private DatabaseHelper dbHelper;
    private static List<String> school=new ArrayList<>();
    private static List<String> school_status=new ArrayList<>();

    private static List<String> work=new ArrayList<>();
    private static List<String> work_status=new ArrayList<>();

    private static List<String> daily=new ArrayList<>();
    private static List<String> daily_status=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        final EditText editText=(EditText)findViewById(R.id.et);
        Button search=(Button)findViewById(R.id.enter);
        final ListView school_list=(ListView)findViewById(R.id.school);
        final ListView work_list=(ListView)findViewById(R.id.work);
        final ListView daily_list=(ListView)findViewById(R.id.daily);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String key=editText.getText().toString();
                dbHelper = new DatabaseHelper(SearchActivity.this, "test_db", null, 1);
                school.clear();

                school_status.clear();
                work.clear();

                work_status.clear();
                daily.clear();

                daily_status.clear();
                SQLiteDatabase db = dbHelper.getReadableDatabase();
                String q1="select * from todo where use='school' and info like '%"+key+"%'";
                String q2="select * from todo where use='work' and info like '%"+key+"%'";
                String q3="select * from todo where use='daily' and info like '%"+key+"%'";
                try {
                    Cursor cursor1 = db.rawQuery(q1, new String[]{});
                    while (cursor1.moveToNext()) {
                        String info = cursor1.getString(cursor1.getColumnIndex("info"));
                        String status = cursor1.getString(cursor1.getColumnIndex("status"));

                        school.add(info);
                        school_status.add(status);

                    }
                    cursor1.close();
                    Cursor cursor2 = db.rawQuery(q2, new String[]{});
                    while (cursor2.moveToNext()) {
                        String info = cursor2.getString(cursor2.getColumnIndex("info"));
                        String status = cursor2.getString(cursor2.getColumnIndex("status"));

                        work.add(info);
                        work_status.add(status);

                    }
                    cursor2.close();
                    Cursor cursor3 = db.rawQuery(q3, new String[]{});
                    while (cursor3.moveToNext()) {
                        String info = cursor3.getString(cursor3.getColumnIndex("info"));
                        String status = cursor3.getString(cursor3.getColumnIndex("status"));

                        daily.add(info);
                        daily_status.add(status);

                    }
                    cursor3.close();
                } catch (Exception ignored) {

                }
                SimpleAdapter simpleAdapter1=new SimpleAdapter(SearchActivity.this,setlistitem1(school,school_status),
                        R.layout.detail_layout,new String[]{"item","status"},new int[]{R.id.todo,R.id.status});
                school_list.setAdapter(simpleAdapter1);
                SimpleAdapter simpleAdapter2=new SimpleAdapter(SearchActivity.this,setlistitem1(work,work_status),
                        R.layout.detail_layout,new String[]{"item","status"},new int[]{R.id.todo,R.id.status});
                work_list.setAdapter(simpleAdapter2);
                SimpleAdapter simpleAdapter3=new SimpleAdapter(SearchActivity.this,setlistitem1(daily,daily_status),
                        R.layout.detail_layout,new String[]{"item","status"},new int[]{R.id.todo,R.id.status});
                daily_list.setAdapter(simpleAdapter3);
            }
        });
    }
    private List<Map<String,Object>> setlistitem1(List<String> items, List<String> status){
        List<Map<String,Object>> ListItems=new ArrayList<Map<String, Object>>();
        for (int i=0;i<items.size();i++){
            Map<String,Object> listItem=new HashMap<String,Object>();
            listItem.put("item",items.get(i));
            listItem.put("status",status.get(i));
            ListItems.add(listItem);
        }
        return ListItems;
    }
}
