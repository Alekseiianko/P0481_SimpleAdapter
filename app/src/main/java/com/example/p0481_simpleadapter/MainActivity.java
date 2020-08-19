package com.example.p0481_simpleadapter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.ViewManager;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;
import static java.lang.String.valueOf;

public class MainActivity extends AppCompatActivity {

    private static final int CM_DELETE_ID = 1;
    private static final String ATTRIBUTE_NAME_TEXT = "text";
    private static final String ATTRIBUTE_NAME_QUANTITY = "quantity";
    private static final String PREFERENCES_NAME = "Lol";
    private SimpleAdapter adapter;
    private SharedPreferences sharedPreferences;
    private List<Map<String, Object>> data;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Map<String, String> texts;
    private SharedPreferences.Editor editor;
    private ArrayList<Integer> arrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(savedInstanceState != null){
            savedInstanceState.getIntegerArrayList("lol");
            for(int i : arrayList){
                Integer integer = i;
                data.remove(integer.intValue());
            }
        }
        sharedPreferences = getSharedPreferences(PREFERENCES_NAME, MODE_PRIVATE);
        if (sharedPreferences.getAll().size() == 0) {
            String[] split = getString(R.string.large_text).split("\n\n");
            editor = sharedPreferences.edit();
            for (String string : split) {
                editor.putString(string, valueOf(string.length()));
            }
            editor.apply();
        }

        texts = prepareContent();

        data = new ArrayList<>(texts.size());

        for (String text : texts.keySet()) {
            Map<String, Object> row = new HashMap<>();
            row.put(ATTRIBUTE_NAME_TEXT, text);
            row.put(ATTRIBUTE_NAME_QUANTITY, valueOf(text.length()));
            data.add(row);
        }

        String[] from = {ATTRIBUTE_NAME_TEXT, ATTRIBUTE_NAME_QUANTITY};
        int[] to = {R.id.tvText, R.id.tvText2};

        adapter = new SimpleAdapter(this, data, R.layout.item,
                from, to);

        ListView lvSimple = findViewById(R.id.lvSimple);
        lvSimple.setAdapter(adapter);
        registerForContextMenu(lvSimple);

        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                data.clear();
                for (String text : prepareContent().keySet()) {
                    Map<String, Object> row = new HashMap<>();
                    row.put(ATTRIBUTE_NAME_TEXT, text);
                    row.put(ATTRIBUTE_NAME_QUANTITY, valueOf(text.length()));
                    data.add(row);
                }
                swipeRefreshLayout.setRefreshing(false);
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, CM_DELETE_ID, 0, "Удалить запись");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getItemId() == CM_DELETE_ID) {
            AdapterContextMenuInfo acmi = (AdapterContextMenuInfo) item.getMenuInfo();
            String name = (String) data.get(acmi.position).get(ATTRIBUTE_NAME_TEXT);

            arrayList.add(acmi.position);
            data.remove(acmi.position);

            adapter.notifyDataSetChanged();
            return true;
        }
        return super.onContextItemSelected(item);
    }

    @NonNull
    private Map<String, String> prepareContent() {
        return (Map<String, String>) sharedPreferences.getAll();
    }

    private void fillPrefs() {
        String[] split = getString(R.string.large_text).split("\n\n");
        editor = sharedPreferences.edit();
        for (String string : split) {
            editor.putString(string, valueOf(string.length()));
        }
        editor.apply();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putIntegerArrayList("lol", arrayList);
    }
}
