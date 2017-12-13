package com.androidcodinghub.mapwithmultiplemakeranddialog.basic;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.androidcodinghub.mapwithmultiplemakeranddialog.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

public class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {


    private Activity context;
    private static final String TAG = "CustomInfoWindowAdapter";

    public CustomInfoWindowAdapter(Activity context) {
        this.context = context;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {

        View view = context.getLayoutInflater().inflate(R.layout.custominfowindow, null);

        TextView tvTitle = (TextView) view.findViewById(R.id.tvName);
        Button tvSubTitle = (Button) view.findViewById(R.id.btDetails);

        tvTitle.setText(marker.getTitle());
        tvSubTitle.setText(marker.getSnippet());

        tvSubTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "clicked", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

}

