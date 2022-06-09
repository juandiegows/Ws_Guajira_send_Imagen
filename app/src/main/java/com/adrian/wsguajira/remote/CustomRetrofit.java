package com.adrian.wsguajira.remote;

import android.content.Context;

import com.adrian.wsguajira.R;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CustomRetrofit {

    public static Retrofit getRetrofit(Context context){
        return new Retrofit.Builder().baseUrl(context.getString(R.string.www)).addConverterFactory(GsonConverterFactory.create()).build();
    }

    public static Retrofit getRetrofitPhoto(Context context){
        return new Retrofit.Builder().baseUrl(context.getString(R.string.www_api)).addConverterFactory(GsonConverterFactory.create()).build();
    }
}
