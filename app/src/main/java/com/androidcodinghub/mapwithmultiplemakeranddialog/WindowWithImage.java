package com.androidcodinghub.mapwithmultiplemakeranddialog;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import java.util.Hashtable;

public class WindowWithImage extends AppCompatActivity {

    private GoogleMap googleMap;
    private final LatLng HAMBURG = new LatLng(53.558, 9.927);
    private final LatLng KIEL = new LatLng(53.551, 9.993);
    private Marker marker;
    private Hashtable<String, String> markers;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_window_with_image);
    }
}
