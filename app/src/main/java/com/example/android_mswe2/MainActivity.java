package com.example.android_mswe2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.FragmentManager;
import android.content.res.Configuration;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        ToDoFragment toDoFragment=new ToDoFragment();
        DetailFragment detailFragment=new DetailFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.detailfragment,detailFragment,"detailfragment").commit();
        getSupportFragmentManager().beginTransaction().replace(R.id.todofragment,toDoFragment,"todofragment").commit();
    }

}
