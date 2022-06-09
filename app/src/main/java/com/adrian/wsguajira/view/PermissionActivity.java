package com.adrian.wsguajira.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.adrian.wsguajira.R;
import com.adrian.wsguajira.model.Asset;
import com.adrian.wsguajira.remote.CustomRetrofit;
import com.adrian.wsguajira.services.IServicesQuake;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PermissionActivity extends AppCompatActivity {

    private static final int CODE_PERMISSION = 100;
    private static final int CAMERA_CODE = 120;
    private static final int GALERY_CODE = 121;
    private static final String PERMISSION_TAG = "ErrorPermission";
    //UI
    Button permiso, galeria, send, search;
    ImageView camera;
    EditText txtId;
    Bitmap img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission);

        init();
        events();
        getPermission();
    }

    private void init() {
        permiso = findViewById(R.id.btnPermiso);
        galeria = findViewById(R.id.btnGaleria);
        send = findViewById(R.id.btnSend);
        txtId = findViewById(R.id.txtId);
        search = findViewById(R.id.btnSearch);
        camera = findViewById(R.id.imgResult);
    }
    private void events() {
        permiso.setOnClickListener(v->{
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, CAMERA_CODE);
        });

        galeria.setOnClickListener(v->{
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent,"Seleccione la imagen"), GALERY_CODE);
        });

        send.setOnClickListener(v->{
            sendAssetPhoto();
        });

        search.setOnClickListener(v->{
            getAssetPhoto();
        });

    }

    private void getAssetPhoto() {
        camera.setImageDrawable(getResources().getDrawable(R.drawable.ic_launcher_foreground, getTheme()));
        Retrofit retrofit = CustomRetrofit.getRetrofitPhoto(this);
        IServicesQuake quake = retrofit.create(IServicesQuake.class);
        quake.getAsset(Integer.parseInt(txtId.getText().toString()))
                .enqueue(new Callback<Asset>() {
            @Override
            public void onResponse(Call<Asset> call, Response<Asset> response) {
                if(response.isSuccessful())
                    camera.setImageBitmap(convertImgToString(response.body().getAssetPhoto()));
                else
                    Toast.makeText(PermissionActivity.this,":c", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<Asset> call, Throwable t) {
                Log.e(PERMISSION_TAG, "onFailure: ", t );
                Toast.makeText(PermissionActivity.this,"Error :C", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void sendAssetPhoto() {
        Retrofit retrofit = CustomRetrofit.getRetrofitPhoto(this);
        IServicesQuake quake = retrofit.create(IServicesQuake.class);
        quake.sendAsset(new Asset(5,6,convertImgToString(img))).enqueue(new Callback<Asset>() {
            @Override
            public void onResponse(Call<Asset> call, Response<Asset> response) {
                if(response.isSuccessful())
                    Toast.makeText(PermissionActivity.this,"OK", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(PermissionActivity.this,":c", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<Asset> call, Throwable t) {
                Log.e(PERMISSION_TAG, "onFailure: ", t );
                Toast.makeText(PermissionActivity.this,"Error :C", Toast.LENGTH_LONG).show();
            }
        });
    }
    private String convertImgToString(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,stream);
        byte[] imgByte = stream.toByteArray();
        String base64 = Base64.encodeToString(imgByte,Base64.DEFAULT);
        return base64;
    }
    private Bitmap convertImgToString(String bitmapS) {
        byte[] imgByte = Base64.decode(bitmapS, Base64.DEFAULT);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(imgByte);
        Bitmap bitmap = BitmapFactory.decodeByteArray(imgByte, 0, imgByte.length);
        return bitmap;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case CAMERA_CODE:
                camera.setImageBitmap((img = getImage(CAMERA_CODE, data)));
                break;
            case GALERY_CODE:
                camera.setImageBitmap((img = getImage(GALERY_CODE, data)));
                break;
        }
    }

    private Bitmap getImage(int request, Intent data) {
        if(request == CAMERA_CODE)
            return (Bitmap) data.getExtras().get("data");
        else{
            try {
                return MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
            } catch (IOException e) {
                return null;
            }
        }
    }

    private void getPermission() {
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
            permiso.setText("Ya estan listos!");
        }else{
            ActivityCompat.requestPermissions(this,
                    new String[]{ Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    CODE_PERMISSION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode != CODE_PERMISSION)
            return;

        for (int a=0;a< permissions.length;a++){
            Toast.makeText(this, "Permiso: "+permissions[a]+" > "+ (grantResults[a] == PackageManager.PERMISSION_GRANTED ?" aprobado":"no aprobado :c" ), Toast.LENGTH_LONG).show();
        }
    }
}