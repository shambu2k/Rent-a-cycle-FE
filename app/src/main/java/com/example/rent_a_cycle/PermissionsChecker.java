package com.example.rent_a_cycle;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class PermissionsChecker {

    private String[] allPermissions = {Manifest.permission.CAMERA, Manifest.permission.BLUETOOTH,
            Manifest.permission.BLUETOOTH_ADMIN, Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION};
    private Activity activity;
    public static final int REQUEST_PERMISSIONS_CODE = 1234;
    private boolean allGranted;

    public PermissionsChecker(Activity activity) {
        this.activity = activity;
    }

    public void getPermissions(){
        if (ContextCompat.checkSelfPermission(activity.getApplicationContext(), Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(activity.getApplicationContext(), Manifest.permission.BLUETOOTH)
                != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(activity.getApplicationContext(), Manifest.permission.BLUETOOTH_ADMIN)
                != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(activity.getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(activity.getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, allPermissions, REQUEST_PERMISSIONS_CODE);
        } else {
            allGranted = true;
        }
    }

    public boolean isAllGranted() {
        return allGranted;
    }

    public void setAllGranted(boolean allGranted) {
        this.allGranted = allGranted;
    }
}
