package br.com.test.kaioh.addeventcalendar;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * Created by @autor Kaio Henrique on 27/02/2018.
 */

public class PermissionManager {

    public PermissionManager(){}

    private static final String[] PERMISSIONS = {
            Manifest.permission.READ_CALENDAR,
            Manifest.permission.WRITE_CALENDAR};

    private static final int REQUEST_CODE = 101;

    public boolean userHasPermission(Context context){
        int permissionCheck = ContextCompat.checkSelfPermission(context, PERMISSIONS[0]);
        int permissionCheck2 = ContextCompat.checkSelfPermission(context, PERMISSIONS[1]);
        return permissionCheck == PackageManager.PERMISSION_GRANTED &&
                permissionCheck2 == PackageManager.PERMISSION_GRANTED;
    }

    public void requestPermission(Activity activity){
        ActivityCompat.requestPermissions(activity, PERMISSIONS, REQUEST_CODE);
    }
}
