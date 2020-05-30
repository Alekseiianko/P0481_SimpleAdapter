package com.example.p0481_simpleadapter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
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

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final int CM_DELETE_ID = 1;
    final String ATTRIBUTE_NAME_TEXT = "text";
    final String ATTRIBUTE_NAME_QUANTITY = "quantity";
    SimpleAdapter sAdapter;
    String[] texts ;
    int[] quantity2;
    SharedPreferences sharedPreferences ;

    List<Map<String, Object>> data;

    ListView lvSimple;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Gson gson = new Gson();
        String json = gson.toJson(texts);
        sharedPreferences = getSharedPreferences("USER",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Set",json );
        editor.apply();

        texts = prepareContent();

        quantity2 = new int[]{texts[0].length(), texts[1].length(), texts[2].length(),
                texts[3].length(), texts[4].length(), texts[5].length(), texts[6].length(), texts[7].length(),
                texts[8].length(), texts[9].length(), texts[10].length(),
                texts[11].length(), texts[12].length(), texts[13].length(), texts[14].length(), texts[15].length(),
                texts[16].length(), texts[17].length()};

        data = new ArrayList<>(texts.length);
        Map<String, Object> m;
        for (int i = 0; i < texts.length; i++) {
            m = new HashMap<>();
            m.put(ATTRIBUTE_NAME_TEXT, texts[i]);
            m.put(ATTRIBUTE_NAME_QUANTITY, quantity2[i]);
            data.add(m);
        }

        String[] from = {ATTRIBUTE_NAME_TEXT, ATTRIBUTE_NAME_QUANTITY};
        int[] to = {R.id.tvText, R.id.tvText2};

        sAdapter = new SimpleAdapter(this, data, R.layout.item,
                from, to);

        lvSimple = findViewById(R.id.lvSimple);
        lvSimple.setAdapter(sAdapter);
        registerForContextMenu(lvSimple);
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
            data.remove(acmi.position);
            sAdapter.notifyDataSetChanged();
            return true;
        }
        return super.onContextItemSelected(item);
    }

    @NonNull
    private String[] prepareContent() {
        return getString(R.string.large_text).split("\n\n") ;
    }
}

