package com.androidcodinghub.mapwithmultiplemakeranddialog;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidcodinghub.mapwithmultiplemakeranddialog.basic.CustomInfoWindowAdapter;
import com.androidcodinghub.mapwithmultiplemakeranddialog.service.GPSTracker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback
{

    private GPSTracker gpsTracker;
    private FrameLayout frameLayout;
    private static final String TAG = "MainActivity";
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView=(TextView)findViewById(R.id.tvName);
        getlocation();
        initMap();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getlocation();
    }

    private void getlocation(){
        gpsTracker=new GPSTracker(MainActivity.this);

        // check if GPS enabled
        if (gpsTracker.canGetLocation()){
            double latitude=gpsTracker.getLatitude();
            double longitude=gpsTracker.getLongitude();
            getAddress(latitude, longitude);

            Log.e(TAG, "latitude: "+latitude );
            Log.e(TAG, "longitude: "+ longitude);
            Toast.makeText(getApplicationContext(), "Your Location is - \nLat: "
                    + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
        }
        else {
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gpsTracker.showSettingsAlert();
        }
    }

    private void initMap(){
        SupportMapFragment mapFragment=(SupportMapFragment)getSupportFragmentManager()
                .findFragmentById(R.id.frameLayout);

        if (mapFragment==null){
            mapFragment=SupportMapFragment.newInstance();
            android.support.v4.app.FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.frameLayout, mapFragment, "map");
            transaction.commit();
        }

        mapFragment.getMapAsync(this);

    }

   /* @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        googleMap.getUiSettings().setZoomControlsEnabled(true);

        String title = "This is Title";
        String subTitle = "This is \nSubtitle";

        //Marker
        MarkerOptions markerOpt = new MarkerOptions();



        markerOpt.position(new LatLng(gpsTracker.getLatitude(), gpsTracker.getLongitude()))
                .title(title)
                .snippet(subTitle)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.h_van_green));

        CustomInfoWindowAdapter adapter = new CustomInfoWindowAdapter(MainActivity.this);
        googleMap.setInfoWindowAdapter(adapter);

        googleMap.addMarker(markerOpt).showInfoWindow();
    }
*/

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        googleMap.getUiSettings().setZoomControlsEnabled(true);

        String title = "This is Title";
        String subTitle = "Details";
        //Marker
        final MarkerOptions markerOpt = new MarkerOptions();
        markerOpt.position(new LatLng(gpsTracker.getLatitude(), gpsTracker.getLongitude()))
                .title(title)
                .snippet(subTitle)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.h_van_green));

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(gpsTracker.getLatitude(), gpsTracker.getLongitude()),19.0f));
        googleMap.addMarker(markerOpt);
        googleMap.setOnInfoWindowClickListener(null);
        googleMap.setOnInfoWindowLongClickListener(null);
        googleMap.setIndoorEnabled(true);

        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener()
        {
            @Override
            public boolean onMarkerClick(Marker arg0) {
                    CustomInfoWindowAdapter adapter = new CustomInfoWindowAdapter(MainActivity.this);

                     googleMap.setInfoWindowAdapter(adapter);
                    googleMap.addMarker(markerOpt).showInfoWindow();

                return true;
            }
        });
    }
    public void getAddress(double lat, double lng) {
        Geocoder geocoder = new Geocoder(MainActivity.this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);
            if (addresses!=null && addresses.size()>0) {
                Address obj = addresses.get(0);
                String add = obj.getAddressLine(0);
                add = add + "\n" + obj.getCountryName();//country
                add = add + "\n" + obj.getCountryCode();
                add = add + "\n" + obj.getAdminArea();//state
                add = add + "\n" + obj.getPostalCode();
                add = add + "\n" + obj.getSubAdminArea();
                add = add + "\n" + obj.getLocality();//city
                add = add + "\n" + obj.getSubThoroughfare();

                textView.setText(add);
                Log.v("IGA", "Address" + add);
                // Toast.makeText(this, "Address=>" + add,
                // Toast.LENGTH_SHORT).show();

                // TennisAppActivity.showDialog(add);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

}




//    https://mobikul.com/android-setting-custom-info-window-google-map-marker/