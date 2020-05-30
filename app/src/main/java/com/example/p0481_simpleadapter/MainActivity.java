package com.example.p0481_simpleadapter;

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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    final String ATTRIBUTE_NAME_TEXT = "text";
    final String ATTRIBUTE_NAME_QUANTITY = "quantity";

    ListView lvSimple;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        String[] texts = {Util.TEXT1, Util.TEXT2, Util.TEXT3, Util.TEXT4,
                Util.TEXT5, Util.TEXT6, Util.TEXT7, Util.TEXT8 };

        int[] quantity2 = {texts[0].length(), texts[1].length(), texts[2].length(),
                texts[3].length(), texts[4].length(), texts[5].length(), texts[6].length() , texts[7].length()};

        ArrayList<Map<String, Object>> data = new ArrayList<Map<String, Object>>(
                texts.length);
        Map<String, Object> m;
        for (int i = 0; i < texts.length; i++) {
            m = new HashMap<String, Object>();
            m.put(ATTRIBUTE_NAME_TEXT, texts[i]);
            m.put(ATTRIBUTE_NAME_QUANTITY, quantity2[i]);
            data.add(m);
        }

        String[] from = { ATTRIBUTE_NAME_TEXT, ATTRIBUTE_NAME_QUANTITY};
        int[] to = { R.id.tvText, R.id.tvText2 };

        final SimpleAdapter sAdapter = new SimpleAdapter(this, data, R.layout.item,
                from, to);

        lvSimple = findViewById(R.id.lvSimple);
        lvSimple.setAdapter(sAdapter);

        lvSimple.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                lvSimple.getItemAtPosition(position);
                lvSimple.removeViewInLayout(view);
            }
        });

        sAdapter.notifyDataSetChanged();
    }

}
