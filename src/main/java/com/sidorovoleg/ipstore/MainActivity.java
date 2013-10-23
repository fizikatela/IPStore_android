package com.sidorovoleg.ipstore;


import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;
import android.preference.PreferenceActivity;


import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;

import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;



public class MainActivity extends Activity implements OnClickListener {

    final String TAG_LOG = "MyLog" ;
    Button btnEnter;
    EditText etLogin, etPwd;
    ProgressDialog dialog;

    final static int MENU_SET = 1;
    final static int MENU_AUTHOR = 2;
    final static int MENU_EXIT = 3;

    SharedPreferences sp;
    String myURL = "http://176.58.111.100/ipstore/rest/equipment";
    String setURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);


        btnEnter = (Button) findViewById(R.id.btnEnter);
        btnEnter.setOnClickListener(this);

        etLogin = (EditText) findViewById(R.id.etLogin);
        etPwd  = (EditText) findViewById(R.id.etPwd);

        sp = PreferenceManager.getDefaultSharedPreferences(this);
        setURL = "http://" + sp.getString("urlAdress", "");
    }

    protected void onResume() {
        setURL = "http://" + sp.getString("urlAdress", "");;
        super.onResume();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        MenuItem menuItem = menu.add(0, MENU_SET, 0, "Настройка");
        menuItem.setIntent(new Intent(this, PrefAct.class));
        menu.add(0, MENU_AUTHOR, 0, "Автор");
        menu.add(0, MENU_EXIT, 0, "Выход");

        return super.onCreateOptionsMenu(menu);
    }

    public  boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){


            case 2:
                Toast toast = Toast.makeText(this, "Sidorov Oleg / aka.hunter@gmail.com ", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                break;
            case 3:
                finish();
                System.exit(0);
                break;
        }

        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnEnter:

               // проверка полей login & pwd
               if(TextUtils.isEmpty(etLogin.getText().toString()) || TextUtils.isEmpty(etPwd.getText().toString())) {
                   Toast toast = Toast.makeText(this, "Поле \"Логин\" или \"Пароль\" не заполнено", Toast.LENGTH_SHORT);
                   toast.setGravity(Gravity.CENTER, 0,0);
                   toast.show();
                   return;
               }

               MyTask mt = new MyTask();
               mt.execute(myURL);
        }
    }

    class MyTask extends AsyncTask<String, Void, String> {


        protected void onPreExecute() {

            dialog = new ProgressDialog(MainActivity.this);
            dialog.setMessage("Загрузка...");
            dialog.setIndeterminate(true);
            dialog.setCancelable(true);
            dialog.show();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {

            String login = etLogin.getText().toString();
            String pwd = etPwd.getText().toString();

            DefaultHttpClient dhc = new DefaultHttpClient();
            Credentials defaultcreds = new UsernamePasswordCredentials(login,pwd);
            dhc.getCredentialsProvider().setCredentials(new AuthScope(null, -1), defaultcreds);

            ResponseHandler<String> res = new BasicResponseHandler();
            HttpGet gMothod = new HttpGet(params[0]);
            HttpResponse response;

            try {
                response = dhc.execute(gMothod);
                String responseData = dhc.execute(gMothod, res);
                Intent intent = new Intent (MainActivity.this, SecondAct.class);
                intent.putExtra("responseData", responseData);
               // Log.d(TAG_LOG, response.getStatusLine().toString());
                Log.d(TAG_LOG, responseData.toString());
                startActivity(intent);

            } catch (IOException e) {
               Log.d(TAG_LOG, " " + e.toString());

            }
            return null;


        }

   }
}
