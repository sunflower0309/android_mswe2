package com.example.android_mswe2;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DetailFragment extends Fragment {

    private static List<String> school=new ArrayList<>();
    private static List<String> school_status=new ArrayList<>();
    private static List<String> school_date=new ArrayList<>();
    private static List<String> work=new ArrayList<>();
    private static List<String> work_status=new ArrayList<>();
    private static List<String> work_date=new ArrayList<>();
    private static List<String> daily=new ArrayList<>();
    private static List<String> daily_status=new ArrayList<>();
    private static List<String> daily_date=new ArrayList<>();
    private ListView listView;
    private String nowlist;
    private DatabaseHelper dbHelper;
    public DetailFragment() {
        // Required empty public constructor
    }

    private void init_date(){
        school.clear();
        school_date.clear();
        school_status.clear();
        work.clear();
        work_date.clear();
        work_status.clear();
        daily.clear();
        daily_date.clear();
        daily_status.clear();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String q1="select * from todo where use='school' order by date desc";
        String q2="select * from todo where use='work'order by date desc";
        String q3="select * from todo where use='daily'order by date desc";
        try {
            Cursor cursor1 = db.rawQuery(q1, new String[]{});
            while (cursor1.moveToNext()) {
                String info = cursor1.getString(cursor1.getColumnIndex("info"));
                String status = cursor1.getString(cursor1.getColumnIndex("status"));
                String date = cursor1.getString(cursor1.getColumnIndex("date"));
                school.add(info);
                school_status.add(status);
                school_date.add(date);
            }
            cursor1.close();
            Cursor cursor2 = db.rawQuery(q2, new String[]{});
            while (cursor2.moveToNext()) {
                String info = cursor2.getString(cursor2.getColumnIndex("info"));
                String status = cursor2.getString(cursor2.getColumnIndex("status"));
                String date = cursor2.getString(cursor2.getColumnIndex("date"));
                work.add(info);
                work_status.add(status);
                work_date.add(date);
            }
            cursor2.close();
            Cursor cursor3 = db.rawQuery(q3, new String[]{});
            while (cursor3.moveToNext()) {
                String info = cursor3.getString(cursor3.getColumnIndex("info"));
                String status = cursor3.getString(cursor3.getColumnIndex("status"));
                String date = cursor3.getString(cursor3.getColumnIndex("date"));
                daily.add(info);
                daily_status.add(status);
                daily_date.add(date);
            }
            cursor3.close();
        } catch (Exception ignored) {

        }
    }
    private void init_alpha(){
        school.clear();
        school_date.clear();
        school_status.clear();
        work.clear();
        work_date.clear();
        work_status.clear();
        daily.clear();
        daily_date.clear();
        daily_status.clear();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String q1="select * from todo where use='school' order by info";
        String q2="select * from todo where use='work'order by info";
        String q3="select * from todo where use='daily'order by info";
        try {
            Cursor cursor1 = db.rawQuery(q1, new String[]{});
            while (cursor1.moveToNext()) {
                String info = cursor1.getString(cursor1.getColumnIndex("info"));
                String status = cursor1.getString(cursor1.getColumnIndex("status"));
                String date = cursor1.getString(cursor1.getColumnIndex("date"));
                school.add(info);
                school_status.add(status);
                school_date.add(date);
            }
            cursor1.close();
            Cursor cursor2 = db.rawQuery(q2, new String[]{});
            while (cursor2.moveToNext()) {
                String info = cursor2.getString(cursor2.getColumnIndex("info"));
                String status = cursor2.getString(cursor2.getColumnIndex("status"));
                String date = cursor2.getString(cursor2.getColumnIndex("date"));
                work.add(info);
                work_status.add(status);
                work_date.add(date);
            }
            cursor2.close();
            Cursor cursor3 = db.rawQuery(q3, new String[]{});
            while (cursor3.moveToNext()) {
                String info = cursor3.getString(cursor3.getColumnIndex("info"));
                String status = cursor3.getString(cursor3.getColumnIndex("status"));
                String date = cursor3.getString(cursor3.getColumnIndex("date"));
                daily.add(info);
                daily_status.add(status);
                daily_date.add(date);
            }
            cursor3.close();
        } catch (Exception ignored) {

        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        dbHelper = new DatabaseHelper(getContext(), "test_db", null, 1);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setRetainInstance(true);
        init_date();
        View v=inflater.inflate(R.layout.fragment_detail, container, false);
        listView=(ListView)v.findViewById(R.id.detail);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                if(nowlist.equals("school")){
                    String info=school.get(position);
                    //String info=school[position];
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    LayoutInflater factory = LayoutInflater.from(getContext());//提示框
                    final View layout = factory.inflate(R.layout.dialog_layout, null);//这里必须是final的
                    final EditText edit=(EditText)layout.findViewById(R.id.edit);//获得输入框对象
                    final Spinner spinner=(Spinner)layout.findViewById(R.id.change_status);
                    builder.setView(layout);
                    builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            school.set(position,edit.getText().toString());
                            school_status.set(position,spinner.getSelectedItem().toString());
//                            school[position]=edit.getText().toString();
//                            school_status[position]=spinner.getSelectedItem().toString();
                            SimpleAdapter simpleAdapter=new SimpleAdapter(getContext(),setlistitem1(school,school_status),
                                    R.layout.detail_layout,new String[]{"item","status"},new int[]{R.id.todo,R.id.status});
                            nowlist="school";
                            listView.setAdapter(simpleAdapter);
                            String update=String.format("update todo set info='%s',status='%s' where date='%s' and use='school'",edit.getText().toString(),spinner.getSelectedItem().toString(),
                            school_date.get(position));
                            dbHelper.getWritableDatabase().execSQL(update);
                        }
                    });
                    builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder.create().show();
                }
                else if(nowlist.equals("work")){
                    String info=work.get(position);
                    //String info=school[position];
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    LayoutInflater factory = LayoutInflater.from(getContext());//提示框
                    final View layout = factory.inflate(R.layout.dialog_layout, null);//这里必须是final的
                    final EditText edit=(EditText)layout.findViewById(R.id.edit);//获得输入框对象
                    final Spinner spinner=(Spinner)layout.findViewById(R.id.change_status);
                    builder.setView(layout);
                    builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            work.set(position,edit.getText().toString());
                            work_status.set(position,spinner.getSelectedItem().toString());

                            SimpleAdapter simpleAdapter=new SimpleAdapter(getContext(),setlistitem1(work,work_status),
                                    R.layout.detail_layout,new String[]{"item","status"},new int[]{R.id.todo,R.id.status});
                            nowlist="work";
                            listView.setAdapter(simpleAdapter);
                            String update=String.format("update todo set info='%s',status='%s' where date='%s' and use='work'",edit.getText().toString(),spinner.getSelectedItem().toString(),
                                    work_date.get(position));
                            dbHelper.getWritableDatabase().execSQL(update);
                        }
                    });
                    builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder.create().show();
                }
                else if(nowlist.equals("daily")){
                    String info=daily.get(position);
                    //String info=school[position];
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    LayoutInflater factory = LayoutInflater.from(getContext());//提示框
                    final View layout = factory.inflate(R.layout.dialog_layout, null);//这里必须是final的
                    final EditText edit=(EditText)layout.findViewById(R.id.edit);//获得输入框对象
                    final Spinner spinner=(Spinner)layout.findViewById(R.id.change_status);
                    builder.setView(layout);
                    builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            daily.set(position,edit.getText().toString());
                            daily_status.set(position,spinner.getSelectedItem().toString());

                            SimpleAdapter simpleAdapter=new SimpleAdapter(getContext(),setlistitem1(daily,daily_status),
                                    R.layout.detail_layout,new String[]{"item","status"},new int[]{R.id.todo,R.id.status});
                            nowlist="daily";
                            listView.setAdapter(simpleAdapter);
                            String update=String.format("update todo set info='%s',status='%s' where date='%s' and use='daily'",edit.getText().toString(),spinner.getSelectedItem().toString(),
                                    daily_date.get(position));
                            dbHelper.getWritableDatabase().execSQL(update);
                        }
                    });
                    builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder.create().show();
                }
            }
        });
        Button al_order=(Button)v.findViewById(R.id.alpha_order);
        al_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                init_alpha();
                setadp(nowlist);
            }
        });
        Button add=(Button)v.findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nowlist.equals("school")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    LayoutInflater factory = LayoutInflater.from(getContext());//提示框
                    final View layout = factory.inflate(R.layout.dialog_layout, null);//这里必须是final的
                    final EditText edit=(EditText)layout.findViewById(R.id.edit);//获得输入框对象
                    final Spinner spinner=(Spinner)layout.findViewById(R.id.change_status);
                    builder.setView(layout);
                    builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Date date=new Date();
                            school.add(edit.getText().toString());
                            school_status.add(spinner.getSelectedItem().toString());
                            school_date.add(String.valueOf(date.getTime()));
                            String insert=String.format("insert into todo values('%s','school','%s','%s')",
                                    edit.getText().toString(),String.valueOf(date.getTime()),spinner.getSelectedItem().toString());
                            dbHelper.getWritableDatabase().execSQL(insert);
                            SimpleAdapter simpleAdapter=new SimpleAdapter(getContext(),setlistitem1(school,school_status),
                                    R.layout.detail_layout,new String[]{"item","status"},new int[]{R.id.todo,R.id.status});
                            nowlist="school";
                            listView.setAdapter(simpleAdapter);
                        }
                    });
                    builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder.create().show();
                }
                else if(nowlist.equals("work")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    LayoutInflater factory = LayoutInflater.from(getContext());//提示框
                    final View layout = factory.inflate(R.layout.dialog_layout, null);//这里必须是final的
                    final EditText edit=(EditText)layout.findViewById(R.id.edit);//获得输入框对象
                    final Spinner spinner=(Spinner)layout.findViewById(R.id.change_status);
                    builder.setView(layout);
                    builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Date date=new Date();
                            work.add(edit.getText().toString());
                            work_status.add(spinner.getSelectedItem().toString());
                            work_date.add(String.valueOf(date.getTime()));
                            String insert=String.format("insert into todo values('%s','work','%s','%s')",
                                    edit.getText().toString(),String.valueOf(date.getTime()),spinner.getSelectedItem().toString());
                            dbHelper.getWritableDatabase().execSQL(insert);
                            SimpleAdapter simpleAdapter=new SimpleAdapter(getContext(),setlistitem1(work,work_status),
                                    R.layout.detail_layout,new String[]{"item","status"},new int[]{R.id.todo,R.id.status});
                            nowlist="work";
                            listView.setAdapter(simpleAdapter);
                        }
                    });
                    builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder.create().show();
                }
                else if(nowlist.equals("daily")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    LayoutInflater factory = LayoutInflater.from(getContext());//提示框
                    final View layout = factory.inflate(R.layout.dialog_layout, null);//这里必须是final的
                    final EditText edit=(EditText)layout.findViewById(R.id.edit);//获得输入框对象
                    final Spinner spinner=(Spinner)layout.findViewById(R.id.change_status);
                    builder.setView(layout);
                    builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Date date=new Date();
                            daily.add(edit.getText().toString());
                            daily_status.add(spinner.getSelectedItem().toString());
                            daily_date.add(String.valueOf(date.getTime()));
                            String insert=String.format("insert into todo values('%s','daily','%s','%s')",
                                    edit.getText().toString(),String.valueOf(date.getTime()),spinner.getSelectedItem().toString());
                            dbHelper.getWritableDatabase().execSQL(insert);
                            SimpleAdapter simpleAdapter=new SimpleAdapter(getContext(),setlistitem1(daily,daily_status),
                                    R.layout.detail_layout,new String[]{"item","status"},new int[]{R.id.todo,R.id.status});
                            nowlist="daily";
                            listView.setAdapter(simpleAdapter);
                        }
                    });
                    builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder.create().show();
                }
            }
        });
        return v;
    }

    private List<Map<String,Object>> setlistitem1(List<String> items,List<String> status){
        List<Map<String,Object>> ListItems=new ArrayList<Map<String, Object>>();
        for (int i=0;i<items.size();i++){
            Map<String,Object> listItem=new HashMap<String,Object>();
            listItem.put("item",items.get(i));
            listItem.put("status",status.get(i));
            ListItems.add(listItem);
        }
        return ListItems;
    }
    private List<Map<String,Object>> setlistitem2(String[] items,String[] status){
        List<Map<String,Object>> ListItems=new ArrayList<Map<String, Object>>();
        for (int i=0;i<items.length;i++){
            Map<String,Object> listItem=new HashMap<String,Object>();
            listItem.put("item",items[i]);
            listItem.put("status",status[i]);
            ListItems.add(listItem);
        }
        return ListItems;
    }

    public void setadp(String str){
        if(str.equals("school")){
            SimpleAdapter simpleAdapter=new SimpleAdapter(getContext(),setlistitem1(school,school_status),
                    R.layout.detail_layout,new String[]{"item","status"},new int[]{R.id.todo,R.id.status});
            nowlist="school";
            listView.setAdapter(simpleAdapter);
        }
        else if(str.equals("work")){
            SimpleAdapter simpleAdapter=new SimpleAdapter(getContext(),setlistitem1(work,work_status),
                    R.layout.detail_layout,new String[]{"item","status"},new int[]{R.id.todo,R.id.status});
            nowlist="work";
            listView.setAdapter(simpleAdapter);
        }
        else if(str.equals("daily")){
            SimpleAdapter simpleAdapter=new SimpleAdapter(getContext(),setlistitem1(daily,daily_status),
                    R.layout.detail_layout,new String[]{"item","status"},new int[]{R.id.todo,R.id.status});
            nowlist="daily";
            listView.setAdapter(simpleAdapter);
        }
    }
    // TODO: Rename method, update argument and hook method into UI event



}
