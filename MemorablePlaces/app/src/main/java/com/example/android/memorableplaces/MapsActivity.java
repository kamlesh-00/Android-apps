package com.example.android.memorableplaces;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.google.android.gms.maps.GoogleMap.*;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, OnMapLongClickListener {

    private GoogleMap mMap;

    LocationManager locationManager;
    LocationListener locationListener;

    Geocoder geocoder;

    ArrayList<String> newmap = new ArrayList<String>();
    ArrayList<String> newlati = new ArrayList<String>();
    ArrayList<String> newlongi = new ArrayList<String>();

    static int flag=1;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            startListening();
        }
    }

    public void onCenterMap(Location location,String title){
        if(location!=null) {
            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
            mMap.addMarker(new MarkerOptions().position(latLng).title(title));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12));
        }
    }

    public void startListening(){
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,10000,100,locationListener);
            Location lastKnown = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            onCenterMap(lastKnown,"Your Location");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);

//        locationManager = (LocationManager)this.getSystemService(Context.LOCATION_SERVICE);
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in Sydney and move the camera

        mMap.setMapType(MAP_TYPE_HYBRID);

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                onCenterMap(location,"Your Location");
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

        Intent intent =getIntent();
        if(intent.getIntExtra("Map Address",0) == 0){
            //Zoom on User's Location

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            } else {
                startListening();
                //Didn't worked previous one
            /*  LatLng posit = new LatLng(lastKnown.getLatitude(),lastKnown.getLongitude());
            mMap.addMarker(new MarkerOptions().position(posit).title(posit.toString()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(posit,12));           */
            }

            mMap.setOnMapLongClickListener(this);
        }else{
            Location place = new Location(LocationManager.GPS_PROVIDER);
            Log.i("Errorrr","1");
            try {
                place.setLatitude(MainActivity.locations.get(intent.getIntExtra("Map Address", 0)).latitude);
                Log.i("Errorrr", "2");
                place.setLongitude(MainActivity.locations.get(intent.getIntExtra("Map Address", 0)).longitude);
                Log.i("Errorrr", "3");
                onCenterMap(place, MainActivity.maps.get(intent.getIntExtra("Map Address", 0)));
            }catch (Exception e){
                Log.i("Errorrr",e.getMessage());
            }
        }

     /*   final Intent intent = getIntent();
        try{if(intent.getStringExtra("Map Address")!=null){
            Log.i("Inforr","Hi there");
            String mapAdddress = intent.getStringExtra("Map Address");
            Log.i("Inforr",mapAdddress);
            String arr[] = mapAdddress.split(",");

            Location location = new Location(LocationManager.GPS_PROVIDER);
            location.setLatitude(Double.parseDouble(arr[0]));
            location.setLongitude(Double.parseDouble(arr[1]));
            Log.i("Came on maps activity","True");
            LatLng posit = new LatLng(location.getLatitude(),location.getLongitude());
            mMap.addMarker(new MarkerOptions().position(posit).title(posit.toString()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(posit,12));
        }}
        catch (Exception e){
            Log.i("Error",e.getMessage());
        }           */
    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        geocoder = new Geocoder(getApplicationContext(),Locale.getDefault());
        String address = "";
        try{
            List<Address> addressList = geocoder.getFromLocation(latLng.latitude,latLng.longitude,1);
            if(addressList!=null && addressList.size()>0){
                if(addressList.get(0).getSubThoroughfare()!=null){
                    address+=addressList.get(0).getSubThoroughfare()+", ";
                }
                if(addressList.get(0).getThoroughfare()!=null){
                    address+=addressList.get(0).getThoroughfare()+", ";
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            Log.i("Errorrr","Address"+e.getMessage());
        }
        if(address.equals("")){
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            address = sdf.format(new Date());
        }
        mMap.addMarker(new MarkerOptions().position(latLng).title(address));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,15));

        try {
            MainActivity.maps.add(address);
            MainActivity.locations.add(latLng);
            MainActivity.arrayAdapter.notifyDataSetChanged();
            Toast.makeText(this, "Location Added", Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            Log.i("Errorrr","Adding"+e.getMessage());
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        }
        try{
            if(flag==1){
                for(int i=1;i<MainActivity.maps.size()-1;i++){
                    newmap.add(MainActivity.maps.get(i));
                }
                flag=0;
            }
            newmap.add(address);
            newlati.add(String.valueOf(latLng.latitude));
            newlongi.add(String.valueOf(latLng.longitude));
            MainActivity.sharedPreferences.edit().putString("Maps",ObjectSerializer.serialize(newmap)).apply();
            MainActivity.sharedPreferences.edit().putString("Lati",ObjectSerializer.serialize(newlati)).apply();
            MainActivity.sharedPreferences.edit().putString("Longi",ObjectSerializer.serialize(newlongi)).apply();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}