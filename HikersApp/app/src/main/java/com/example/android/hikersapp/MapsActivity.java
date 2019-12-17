package com.example.android.hikersapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.security.Permission;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends AppCompatActivity/* implements OnMapReadyCallback */{

    private GoogleMap mMap;

    LocationManager locationManager;
    LocationListener locationListener;

    TextView longitude, latitude, altitude, accuracy;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            startListening();
        }
    }

    public void startListening() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.


        latitude = findViewById(R.id.Latitude);
        longitude = findViewById(R.id.longitude);
        accuracy = findViewById(R.id.Accuracy);
        altitude = findViewById(R.id.Altitude);

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                updateLocation(location);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            @Override
            public void onProviderEnabled(String provider) {
            }

            @Override
            public void onProviderDisabled(String provider) {
            }
        };

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,locationListener);
            Location lastKnown = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (lastKnown != null) {
                updateLocation(lastKnown);
            }
        }
    }

    public void updateLocation(Location location) {
        latitude.setText("Longitude : " + location.getLatitude());
        longitude.setText("Latitude : " + location.getLongitude());
        accuracy.setText("Accuracy : " + location.getAccuracy());
        altitude.setText("Altitude : " + location.getAltitude());

        String add = "";

        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
        try {
            add = "Could not find Address";
            List<Address> addressList = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
            Log.i("Location Details",add);
            if (addressList != null && addressList.size() > 0) {
                add = "Address : \n";
                if (addressList.get(0).getThoroughfare() != null) {
                    add = add + addressList.get(0).getThoroughfare() + "\n";
                }
                if (addressList.get(0).getLocality() != null) {
                    add = add + addressList.get(0).getLocality() + "\n";
                }
                if (addressList.get(0).getPostalCode() != null) {
                    add = add + addressList.get(0).getPostalCode() + "\n";
                }
                if (addressList.get(0).getAdminArea() != null) {
                    add = add + addressList.get(0).getAdminArea();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("Errorr",e.getMessage());
        }

        TextView address = findViewById(R.id.Address);
        address.setText(add);
    }


    /*1
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
/*    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

    }
        // Add a marker in Sydney and move the camera
  */
}
