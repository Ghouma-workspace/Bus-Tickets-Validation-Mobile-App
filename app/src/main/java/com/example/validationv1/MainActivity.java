package com.example.validationv1;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.Console;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    Button b,btn_scan;
    EditText et;
    EditText date;
    TextView result;


    /////////////////////////////////////
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        //Local Date!!
        date = findViewById(R.id.date);

        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance().format(calendar.getTime());
        date.setText(currentDate);




        //assign variables
        b = findViewById(R.id.button);
        et = findViewById(R.id.number);
        result = findViewById(R.id.textView);

        //Button Listener

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getJSON("http://192.168.1.104/Android/transtu.php",et,result);
                final Handler handler = new Handler();


                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Do something after 5s = 5000ms
                        int n = Integer.parseInt(result.getText().toString().substring(0));
                        //Toast.makeText(MainActivity.this, "result = " + n, Toast.LENGTH_SHORT).show();



                        if (n == 1) {
                            et.setBackgroundColor(0xFF00FF00);
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    // Do something after 5s = 5000ms
                                    et.setBackgroundColor(0xFFFFFFFF);
                                    et.setText("");
                                }
                            }, 5000);
                        }
                        else {
                            et.setBackgroundColor(0xFFFF0000);
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    // Do something after 5s = 5000ms
                                    et.setBackgroundColor(0xFFFFFFFF);
                                    et.setText("");
                                }
                            }, 5000);
                        }
                            }
                }, 500);

                //hedhi li twari kolchay fel db
                /*List <TitreModel> everyone = databaseHelper.ShowEveryone();
                Toast.makeText(MainActivity.this,everyone.toString(),Toast.LENGTH_SHORT).show();*/
            }
        });

        //SCAAAAN
        btn_scan = findViewById(R.id.btn_scan);
        btn_scan.setOnClickListener(view -> {
            scanCode();
        });

        //
    }
    private void scanCode(){
        ScanOptions options = new ScanOptions();
        options.setPrompt("Volume on to flash on");
        options.setBeepEnabled(true);
        options.setOrientationLocked(true);
        options.setCaptureActivity(CaptureAct.class);
        barLauncher.launch(options);
    }

    ActivityResultLauncher<ScanOptions> barLauncher = registerForActivityResult(new ScanContract(), result -> {
        if (result.getContents() != null){
            Toast.makeText(MainActivity.this,"Detected",Toast.LENGTH_SHORT).show();
            et.setText(result.getContents());
            b.callOnClick();
        }
    });

    //connection to database mysql
    private void getJSON(final String urlWebService, EditText v, TextView r) {
        GetJSON getJSONVar = new GetJSON(MainActivity.this,v,r);
        getJSONVar.execute();
    }
}