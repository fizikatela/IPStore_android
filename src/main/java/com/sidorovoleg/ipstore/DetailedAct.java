package com.sidorovoleg.ipstore;


import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DetailedAct extends Activity {

    ListView lv;

    private static String TAB1 = "label";
    private static String TAB2 = "vol";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailed);

        lv = (ListView) findViewById(R.id.detailedList);

        Intent intent = getIntent();
        String[] detailDate = intent.getStringArrayExtra("detailDate");
        String[] nameDate = {"ID", "IpAddress", "Type", "UserName", "Login", "Password",
                "PasswordDate", "PasswordStatus", "TelnetStatus", "TelnetStatusDate", "TelnetCheck",
                "ClientName", "PlacementAddress", "ApplicationNumber", "Description"};
        ArrayList<Map<String, Object>> data1 = new ArrayList<Map<String, Object>>();
        HashMap<String, Object> m;
        for (int i=0; i < nameDate.length; i++){
            m = new HashMap<String, Object>();
            m.put(TAB1, nameDate[i]);
            m.put(TAB2, detailDate[i]);
            data1.add(m);
        }

        String[] From1 = {TAB1, TAB2};
        int[] To1 = {R.id.tvTab1, R.id.tvTab2};
        SimpleAdapter adapter1 = new SimpleAdapter (DetailedAct.this, data1, R.layout.item_det, From1, To1);
        lv.setAdapter(adapter1);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.detailed_afc, menu);
        return true;
    }
    
}
