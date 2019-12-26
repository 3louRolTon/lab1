package com.example.lab1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_READ_PHONE_STATE = 10001;

    private static final String READ_PHONE_STATE_PERMISSION = Manifest.permission.READ_PHONE_STATE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(!isPermissionGranted(READ_PHONE_STATE_PERMISSION)){
            requestPermission(READ_PHONE_STATE_PERMISSION, REQUEST_READ_PHONE_STATE);
        }
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        displayID();
    }

    public void displayID()
    {
        if(checkSelfPermission(READ_PHONE_STATE_PERMISSION) != PackageManager.PERMISSION_GRANTED)
        {
            TextView v1 = findViewById(R.id.textView);
            final String txtID = "Permission not granted";
            v1.setText(txtID);
        }
        else
        {
            TextView v1 = findViewById(R.id.textView);
            final String txtID = getResources().getString(R.string.textID)+" "+
                    Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
            v1.setText(txtID);
        }

    }

    private void requestPermission(String permission, int requestCode) {
        ActivityCompat.requestPermissions(this, new String[]{permission}, requestCode);
    }

    private boolean isPermissionGranted(String permission) {
        int permissionCheck = ActivityCompat.checkSelfPermission(this, permission);
        return permissionCheck == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[],@NonNull int[] grantResults){
        if(requestCode == REQUEST_READ_PHONE_STATE){
            if(grantResults[0]==PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(this, "Разрешение получены", Toast.LENGTH_SHORT).show();
                onStart();
            }else {
                Toast.makeText(this, "Разрешения не получены", Toast.LENGTH_LONG).show();
            }

        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    public void showVersionName(View view) {
        Toast.makeText(this, "Version name: "+ BuildConfig.VERSION_NAME, Toast.LENGTH_SHORT).show();
    }

    public void givePermission(View view) {
        if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_PHONE_STATE))
        {
            final String message = "You need it :)";
            Snackbar.make(view ,message, Snackbar.LENGTH_LONG).setAction("Ok", new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    requestPermission(READ_PHONE_STATE_PERMISSION, REQUEST_READ_PHONE_STATE);
                }
            }).show();
        }
        else{
            requestPermission(READ_PHONE_STATE_PERMISSION, REQUEST_READ_PHONE_STATE);
        }

    }
}