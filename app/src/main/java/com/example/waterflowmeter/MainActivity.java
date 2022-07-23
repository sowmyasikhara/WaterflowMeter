package com.example.waterflowmeter;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.waterflowmeter.FlowDAO;

import com.mysql.cj.xdevapi.JsonParser;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText selectedDate;
    ImageView calendarbtn;
    DatePickerDialog datePickerDialog;
    int year;
    int month;
    int dayOfMonth;
    Calendar calendar;
    String json_string;
    String json_temp;
    Button status;
    FlowDAO object;
    Intent intent;


    TextView tv;
    String ab;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        selectedDate = (EditText) findViewById(R.id.textView2);
        calendarbtn = (ImageView) findViewById(R.id.imageView);
        status = (Button) findViewById(R.id.button);


        calendarbtn.setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.N)
            @SuppressLint("NewApi")
            @Override
            public void onClick(View view) {
                calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(MainActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                selectedDate.setText(day + "/" + (month + 1) + "/" + year);
                            }
                        }, year, month, dayOfMonth);
                //datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });


       // status.setOnClickListener(new View.OnClickListener() {
     //       @Override
         //   public void onClick(View view) {

           // new BackgroundTask().execute();
            //}

        status.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View view) {
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                    StrictMode.setThreadPolicy(policy);
                    String url = "jdbc:mysql://database-1.ciqa3cvp g0rg.us-west-2.rds.amazonaws.com:3306/AWSDB";
                    Connection con = DriverManager.getConnection(url, "admin", "mysqldb09");
                    Statement st = con.createStatement();
                    String sql = "select time,status from firstdata";
                    ResultSet rs = st.executeQuery(sql);
                    ArrayList lists = new ArrayList();

                    while(rs.next()){
                        object = new FlowDAO();
                        object.setTime(rs.getString("time"));
                        object.setStatus(rs.getString("status"));
                    }
                    lists.add(object);
                    intent = new Intent(getApplicationContext(),GetStatusActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putParcelableArrayList("lists", lists);
                    intent.putExtras(bundle);
                    startActivity(intent,bundle);


                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}

/*
        class BackgroundTask extends AsyncTask<Void, Void, String> {

            //String json_url = "http://localhost/flowmeter/Api.php/";
            //String json_url = "http://192.168.1.11/flowmeter/Api.php/";
            //String json_url = "http://testmymeter.000webhostingapp.com/Api.php/";
           String json_url = "http://testmymeter.epizy.com/Api.php/";
            StringBuilder stringBuilder = new StringBuilder();
            JsonParser jParser;


            public BackgroundTask() {
                super();
            }

            @Override
            protected String doInBackground(Void... voids) {
                try {

                    URL url = new URL(json_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    //httpURLConnection.setRequestMethod("GET");
                    httpURLConnection.setRequestProperty("Content-Type", "application/json");
                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    while ((json_string = bufferedReader.readLine()) != null) {
                        stringBuilder.append(json_string + "\n");
                    }
                    bufferedReader.close();
                    inputStream.close();
                    httpURLConnection.disconnect();

                    json_temp = stringBuilder.toString().trim();
                    return stringBuilder.toString().trim();

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }


            @Override
            protected void onPostExecute(String result) {
               // json_temp = result;
                System.out.println("result is "+result.toString().trim());
                Toast.makeText(getApplicationContext(),result.toString(),Toast.LENGTH_LONG).show();
                Toast.makeText(getApplicationContext(), "executed", Toast.LENGTH_LONG).show();
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

        }
        });
    }

*/






