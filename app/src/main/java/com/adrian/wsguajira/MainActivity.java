package com.adrian.wsguajira;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.adrian.wsguajira.adapter.QuakeAdapter;
import com.adrian.wsguajira.model.Count;
import com.adrian.wsguajira.model.Feature;
import com.adrian.wsguajira.remote.CustomRetrofit;
import com.adrian.wsguajira.services.IServicesQuake;
import com.google.android.material.snackbar.Snackbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    private static final String TAG_MAIN = "TAG_MAINACTIVITY";
    Retrofit retrofit;
    IServicesQuake iServicesQuake;

    //UI
    ListView list;

    //Adapter
    QuakeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        events();
    }

    private void events() {
        Button btnUrl = findViewById(R.id.getUrl);
        btnUrl.setOnClickListener(v->{
            //getDataCount(bnt);
            getDataList(btnUrl);
        });
        //findViewById(R.id.getUrl).setOnClickListener(this::xEvento);
    }

    private void getDataList(View btnUrl) {
        iServicesQuake.getEarthQuakeList("geojson","2021-07-01","2021-08-11")
                .enqueue(new Callback<Feature>() {
                    @Override
                    public void onResponse(Call<Feature> call, Response<Feature> response) {
                        if(response.isSuccessful()){
                            Feature feature = response.body();
                            if(feature != null){
                                adapter = new QuakeAdapter(MainActivity.this,feature.getFeatures());
                                list.setAdapter(adapter);
                                Toast.makeText(MainActivity.this,feature.getFeatures().get(0).getProperties().getTitle()+"",Toast.LENGTH_LONG).show();
                            }
                            else
                                Toast.makeText(MainActivity.this,"Error en algun lado >:c",Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Feature> call, Throwable t) {
                        Log.e(TAG_MAIN,"Error List :c ", t);
                        Toast.makeText(MainActivity.this,t.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void init(){
        retrofit = CustomRetrofit.getRetrofit(this);
        iServicesQuake = retrofit.create(IServicesQuake.class);

        //UI
        list = findViewById(R.id.listMain);
    }

    private void getDataCount(View view){
        //Call<Count> count = iServicesQuake.getEarthQuakeCount("geojson","2021-01-08","2021-11-08");
        iServicesQuake.getEarthQuakeCount("geojson","2021-07-01","2021-08-11")
                .enqueue(new Callback<Count>() {
                    @Override
                    public void onResponse(Call<Count> call, Response<Count> response) {
                        if(response.isSuccessful()){
                            Count count = response.body();
                            if(count != null)
                                Toast.makeText(MainActivity.this,count.getCount()+"",Toast.LENGTH_LONG).show();
                            else
                                Toast.makeText(MainActivity.this,"Error en algun lado >:c",Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Count> call, Throwable t) {
                        Log.e(TAG_MAIN,"Error :c ", t);
                        Toast.makeText(MainActivity.this,t.getMessage(),Toast.LENGTH_LONG).show();
                        Snackbar.make(view,t.getMessage(), Snackbar.LENGTH_LONG).show();
                    }
                });

    }
}