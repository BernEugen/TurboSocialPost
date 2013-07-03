package com.turbosocialpost.Maps;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.turbosocialpost.R;

public class GoogleMapsActivity extends Activity {

    private LatLng myLocation;
    private GoogleMap googleMap;
    private Button showLocation;
    private LocationManager locationManager;
    private double latitude;
    private double longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.maps_layout);

        googleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        LocationListener locationListener = new MyLocationListener();
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

        showLocation = (Button) findViewById(R.id.showLocation);
        showLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCurrentLocation();
                showCurrentLocation();
            }
        });
    }

    public void showCurrentLocation() {
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(myLocation, 9);
        googleMap.addMarker(new MarkerOptions().position(myLocation).title("Your Position"));
        googleMap.animateCamera(update);
    }

    public void getCurrentLocation() {
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (location != null) {
            latitude = location.getLatitude();
            longitude = location.getLongitude();
            myLocation = new LatLng(latitude, longitude);
        }
    }

    private class MyLocationListener implements LocationListener {

        @Override
        public void onLocationChanged(Location location) {
            //Nothing to implement
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            //Nothing to implement
        }

        @Override
        public void onProviderEnabled(String provider) {
            //Nothing to implement
        }

        @Override
        public void onProviderDisabled(String provider) {
            //Nothing to implement
        }
    }
}
