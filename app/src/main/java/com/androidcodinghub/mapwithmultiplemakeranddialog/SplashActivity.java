package com.androidcodinghub.mapwithmultiplemakeranddialog;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.androidcodinghub.mapwithmultiplemakeranddialog.basic.PermissionManager;

import java.util.ArrayList;
import java.util.List;

public class SplashActivity extends AppCompatActivity {

    private static final String TAG = "SplashActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        if (PermissionManager.areExplicitPermissionsRequired()){
            // permission required
            Log.e(TAG, "permission required " );
            List<String> requiredPermission=PermissionManager.isAllPremissiongranted(SplashActivity.this);

            if (requiredPermission !=null && requiredPermission.size() >0){
                PermissionManager.show(SplashActivity.this, "Permission Required", requiredPermission);
            }
            else {
                goToNextActivity();
            }

        }
        else {
            Log.e(TAG, "permission not required " );
            goToNextActivity();
        }
    }

    private void goToNextActivity(){
        final Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);

                //    startActivity(new Intent(SplashActivity.this, MapWithWindowClickEventActivity.class));
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        PermissionManager.requestRunning=false;
        List<String> requiredPermission=new ArrayList<>();
        for (int i=0; i<grantResults.length; i++){
            if (grantResults[i]==-1){
                requiredPermission.add(permissions[i]);
            }
        }

        if (requiredPermission !=null && requiredPermission.size()>0){
            PermissionManager.show(SplashActivity.this, "Permission Required", requiredPermission);
        }
        else {
            goToNextActivity();
        }
    }
}
/*

*/