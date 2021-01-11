package com.example.apiapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    Adapter adapter;
    List<model> models;
    List<model> _OfflineList;
    TextView txtCount, txtCall, txt;
    Button btnUpdate, btnCancel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*-------hooks----*/

        txtCall = findViewById(R.id.Call);
        txtCount = findViewById(R.id.txtCount);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnCancel = findViewById(R.id.btnCancel);
        txt = findViewById(R.id.txt);


        /*-----call ups----*/

        recyclerView = findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        _OfflineList = new ArrayList<>();
        models = new ArrayList<>();
        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
     if (networkInfo == null || !networkInfo.isConnected() || !networkInfo.isAvailable()) {
       AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
       alert.setTitle("Alert");
       alert.setMessage("Please connect to the internet to get the updated table");
       final AlertDialog alertDialog = alert.create();
       alert.show();
         _OfflineList = loadData(getApplicationContext());
         Populate(_OfflineList);
     }
        Api api = ApiClient.getClient().create(Api.class);
        Call<List<model>> call = api.getModels();


            call.enqueue(new Callback<List<model>>() {
                @Override
                public void onResponse(Call<List<model>> call, Response<List<model>> response) {
                    models = response.body();
                    txtCall.setText("Successful");
                    Populate(models);
                    Log.d("Mainactivity", "Here" + String.valueOf(loadData(getApplicationContext()).size()));
                }

                @Override
                public void onFailure(Call<List<model>> call, Throwable t) {
                    txtCall.setText("Failed");
                    Toast.makeText(getApplicationContext(), "Please connect to the internet", Toast.LENGTH_LONG).show();
                }
            });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              recreate();

            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               call.cancel();
                Toast.makeText(getApplicationContext(), "Api call cancelled", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void Populate(List<model> data){
        if(data != null) {
            models = data;
            txtCount.setText(String.valueOf(models.size()));
            adapter = new Adapter(getApplicationContext(), models);
            recyclerView.setAdapter(adapter);
            saveData(getApplicationContext(), models);
            txt.setVisibility(View.INVISIBLE);
        }else {
            txt.setText("No data");
            txt.setVisibility(View.VISIBLE);
        }
    }

    public void saveData(Context context, List<model> list){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        Log.d("view", String.valueOf(json));
        editor.putString("taskList",json);
        editor.apply();
    }

    public static List<model> loadData(Context context){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("taskList", null);
        Type type = new TypeToken<ArrayList<model>>(){}.getType();
        List<model> list = gson.fromJson(json, type);
        return list;

    }


}