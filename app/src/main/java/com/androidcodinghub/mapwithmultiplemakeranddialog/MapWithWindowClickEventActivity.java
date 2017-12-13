package com.androidcodinghub.mapwithmultiplemakeranddialog;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.androidcodinghub.mapwithmultiplemakeranddialog.basic.MapWrapperLayout;
import com.androidcodinghub.mapwithmultiplemakeranddialog.basic.OnInfoWindowElemTouchListener;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapWithWindowClickEventActivity extends AppCompatActivity implements OnMapReadyCallback {

    private ViewGroup infoWindow;
    private TextView infoTitle;
    private TextView infoSnippet;
    private Button infoButton;
    private OnInfoWindowElemTouchListener infoButtonListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_with_window_click_event);

        //initMap();
        final MapFragment mapFragment = (MapFragment)getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

   /* private void initMap(){
        SupportMapFragment mapFragment=(SupportMapFragment)getSupportFragmentManager()
                .findFragmentById(R.id.map);

        if (mapFragment==null){
            mapFragment=SupportMapFragment.newInstance();
            android.support.v4.app.FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.frameLayout, mapFragment, "map");
            transaction.commit();
        }

        mapFragment.getMapAsync(this);

    }*/
    public static int getPixelsFromDp(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int)(dp * scale + 0.5f);
    }


    @Override
    public void onMapReady(GoogleMap map) {
        final MapWrapperLayout mapWrapperLayout = (MapWrapperLayout)findViewById(R.id.map_relative_layout);

        // MapWrapperLayout initialization
        // 39 - default marker height
        // 20 - offset between the default InfoWindow bottom edge and it's content bottom edge
        mapWrapperLayout.init(map, getPixelsFromDp(this, 39 + 20));

        // We want to reuse the info window for all the markers,
        // so let's create only one class member instance
        this.infoWindow = (ViewGroup)getLayoutInflater().inflate(R.layout.info_window, null);
        this.infoTitle = (TextView)infoWindow.findViewById(R.id.title);
        this.infoSnippet = (TextView)infoWindow.findViewById(R.id.snippet);
        this.infoButton = (Button)infoWindow.findViewById(R.id.button);

        // Setting custom OnTouchListener which deals with the pressed state
        // so it shows up
        this.infoButtonListener = new OnInfoWindowElemTouchListener(infoButton,
                getResources().getDrawable(R.drawable.h_van_green),
                getResources().getDrawable(R.drawable.h_van_green))
        {
            @Override
            protected void onClickConfirmed(View v, Marker marker) {
                // Here we can perform some action triggered after clicking the button
                Toast.makeText(MapWithWindowClickEventActivity.this,
                        marker.getTitle() + "'s button clicked!", Toast.LENGTH_SHORT).show();
            }
        };
        this.infoButton.setOnTouchListener(infoButtonListener);


        map.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {
                // Setting up the infoWindow with current's marker info
                infoTitle.setText(marker.getTitle());
                infoSnippet.setText(marker.getSnippet());
                infoButtonListener.setMarker(marker);

                // We must call this to set the current marker and infoWindow references
                // to the MapWrapperLayout
                mapWrapperLayout.setMarkerWithInfoWindow(marker, infoWindow);
                return infoWindow;
            }
        });

        // Let's add a couple of markers
        map.addMarker(new MarkerOptions()
                .title("Prague")
                .snippet("Czech Republic")
                .position(new LatLng(50.08, 14.43)));

        map.addMarker(new MarkerOptions()
                .title("Paris")
                .snippet("France")
                .position(new LatLng(48.86,2.33)));

        map.addMarker(new MarkerOptions()
                .title("London")
                .snippet("United Kingdom")
                .position(new LatLng(51.51,-0.1)));
    }
}


/*
   https://stackoverflow.com/questions/14123243/google-maps-android-api-v2-interactive-infowindow-like-in-original-android-go/15040761#15040761



library:

https://github.com/Appolica/InteractiveInfoWindowAndroid
 */