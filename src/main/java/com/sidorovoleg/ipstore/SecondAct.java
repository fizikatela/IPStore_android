package com.sidorovoleg.ipstore;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.apache.http.auth.AuthScope;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class SecondAct extends Activity {

    final String TAG_LOG = "MyLog" ;

    private static ArrayList<HashMap<String, Object>> myIPStore;


    // JSON node
    private static final String TAG_Data = "data";
    private static final String TAG_ID = "id";
    private static final String TAG_IpAddress = "ipAddress";
    private static final String TAG_Type = "type";
    private static final String TAG_UserName = "username";
    private static final String TAG_Login = "login";
    private static final String TAG_Password = "password";
    private static final String TAG_PasswordDate = "passwordDate";
    private static final String TAG_PasswordStatus = "status";
    private static final String TAG_TelnetStatus = "telnetStatus";
    private static final String TAG_TelnetStatusDate = "telnetStatusDate";
    private static final String TAG_TelnetCheck = "telnetCheck";
    private static final String TAG_ClientName = "clientName";
    private static final String TAG_PlacementAddress = "placementAddress";
    private static final String TAG_ApplicationNumber = "applicationNumber";
    private static final String TAG_Description = "description";



    // element detailed
    String id_ = "";
    String IpAddress = "";
    String Type = "";
    String UserName = "";
    String Login = "";
    String Password = "";
    String PasswordDate = "";
    String PasswordStatus = "";
    String TelnetStatus = "";
    String TelnetStatusDate = "";
    String TelnetCheck = "";
    String ClientName = "";
    String PlacementAddress = "";
    String ApplicationNumber = "";
    String Description = "";

    // описание UI
    SimpleAdapter adapter;
    EditText etFind;
    ListView listV;
    ArrayList<ArrayList<String>> Detailed;
    ArrayList<String> itemDetailed;



    public void JSONPars (String result) {
        try {
            JSONObject json = new JSONObject(result);
            JSONArray data = json.getJSONArray(TAG_Data);
            Detailed = new ArrayList<ArrayList<String>>();
            itemDetailed = new ArrayList<String>();
            for (int i = 0; i < data.length(); i++) {
                HashMap<String, Object> hm;
                hm = new HashMap<String, Object>();
                JSONObject c = data.getJSONObject(i);



                id_ = c.getString(TAG_ID).toString();
                IpAddress = c.getString(TAG_IpAddress).toString();
                Type = c.getString(TAG_Type).toString();
                UserName = c.getString(TAG_UserName).toString();
                Login = c.getString(TAG_Login).toString();
                Password = c.getString(TAG_Password).toString();
                PasswordDate = c.getString(TAG_PasswordDate).toString();
                PasswordStatus = c.getString(TAG_PasswordStatus).toString();
                TelnetStatus = c.getString(TAG_TelnetStatus).toString();
                TelnetStatusDate = c.getString(TAG_TelnetStatusDate).toString();
                TelnetCheck = c.getString(TAG_TelnetCheck).toString();
                ClientName = c.getString(TAG_ClientName).toString();
                PlacementAddress = c.getString(TAG_PlacementAddress).toString();
                ApplicationNumber = c.getString(TAG_ApplicationNumber).toString();
                Description = c.getString(TAG_Description).toString();

                itemDetailed.add(id_);
                itemDetailed.add(IpAddress);
                itemDetailed.add(Type);
                itemDetailed.add(UserName);
                itemDetailed.add(Login);
                itemDetailed.add(Password);
                itemDetailed.add(PasswordDate);
                itemDetailed.add(PasswordStatus);
                itemDetailed.add(TelnetStatus);
                itemDetailed.add(TelnetStatusDate);
                itemDetailed.add(TelnetCheck);
                itemDetailed.add(ClientName);
                itemDetailed.add(PlacementAddress);
                itemDetailed.add(ApplicationNumber);
                itemDetailed.add(Description);
                Detailed.add(itemDetailed);

                hm.put(TAG_IpAddress, IpAddress);
                hm.put(TAG_ClientName, ClientName);
                hm.put(TAG_PlacementAddress, PlacementAddress);
                myIPStore.add(hm);
            }


            String[] From = {TAG_IpAddress, TAG_ClientName, TAG_PlacementAddress};
            int[] To = {R.id.tvIPard, R.id.tvClient, R.id.tvPlace};
            adapter = new SimpleAdapter(SecondAct.this, myIPStore, R.layout.item, From, To);
            listV.setAdapter(adapter);
            listV.setChoiceMode(ListView.CHOICE_MODE_SINGLE);


            // обработка нажатий
            listV.setOnItemClickListener (new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Log.d(TAG_LOG, "select " + position);

                    String[] detailDate = Detailed.get(position).toArray(new String[Detailed.size()]);
                    Log.d(TAG_LOG, detailDate[0] + " " + detailDate[1] + " " + detailDate[2]);

                    Intent intent = new Intent(SecondAct.this, DetailedAct.class);
                    intent.putExtra("detailDate", detailDate);
                    intent.putExtra("position", position);
                    startActivity(intent);


                }
            }
            );


            // поиск по  adapter
            etFind.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    adapter.getFilter().filter(s);
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            //  }
        } catch (JSONException e) {
            Log.d(TAG_LOG, "Ошибка " + e.toString());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_main);

        listV = (ListView) findViewById(R.id.listV);
        etFind = (EditText) findViewById(R.id.etFind);
        myIPStore = new ArrayList<HashMap<String, Object>>();
        Intent intent = getIntent();
        String responseData = intent.getStringExtra("responseData");
        JSONPars(responseData);
    }
}
