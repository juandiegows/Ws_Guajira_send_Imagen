package com.adrian.wsguajira.services;

import com.adrian.wsguajira.model.Asset;
import com.adrian.wsguajira.model.Count;
import com.adrian.wsguajira.model.Feature;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IServicesQuake {

    @GET("fdsnws/event/1/count")//?format=geojson&
    Call<Count> getEarthQuakeCount(@Query("format") String format, @Query("starttime") String start, @Query("endtime") String end);
    @GET("fdsnws/event/1/query")
    Call<Feature> getEarthQuakeList(@Query("format") String format, @Query("starttime") String start, @Query("endtime") String end);

    @POST("api/AssetPhotos")
    Call<Asset> sendAsset(@Body Asset asset);
    @GET("api/AssetPhotos/{id}")
    Call<Asset> getAsset(@Path("id") int ID);
}
