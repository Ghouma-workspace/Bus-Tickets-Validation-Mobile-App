package com.example.validationv1;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

class GetJSON extends AsyncTask<Void, Void, String> {
    Context mcontext;
    EditText t;
    TextView r;


    public GetJSON(Context mcontext, EditText t, TextView r) {
        this.t=t;
        this.mcontext = mcontext;
        this.r = r;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }


    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        Boolean result;
        try {
            JSONArray jsonArray = new JSONArray(s);
            Integer[] table = new Integer[3];
            Integer ID = Integer.parseInt(t.getText().toString().substring(0,2));
            Integer NUM = Integer.parseInt(t.getText().toString().substring(2));

            for (int molka=0;molka<jsonArray.length();++molka){
                JSONObject obj = jsonArray.getJSONObject(molka);
                if (Integer.parseInt(obj.getString("id"))==ID) {
                    table[0] = Integer.parseInt(obj.getString("id"));
                    table[1] = Integer.parseInt(obj.getString("startnum"));
                    table[2] = Integer.parseInt(obj.getString("endnum"));
                }
            }

            if ((ID == table[0]) && (table[1] <= NUM) && (NUM <= table[2])) {
                r.setText("1");
            }
            else {
                r.setText("0");
            }
        }
        catch (Exception e) {
            r.setText("0");
            e.getMessage();
        }
    }

    @Override
    protected String doInBackground(Void... voids) {
        try {
            URL url = new URL("http://192.168.52.30/Android/transtu.php");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            StringBuilder sb = new StringBuilder();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String json;
            while ((json = bufferedReader.readLine()) != null) {
                sb.append(json + "\n");
            }
            return sb.toString().trim();
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}