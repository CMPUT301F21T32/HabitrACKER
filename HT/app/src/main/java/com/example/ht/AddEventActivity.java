package com.example.ht;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class AddEventActivity extends AppCompatActivity implements LocationListener, OnMapReadyCallback {
    EditText habitEventDescription;
    TextView latText;
    TextView lonText;
    Button addHabitEventButton;
    Button cancelLocation;
    String comment;
    String habitID;
    String description;
    String name;
    String username;
    double userLat;
    double userLon;
    String markerLat;
    String markerLon;
    LocationManager locationManager;
    GoogleMap gMap;

    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        habitEventDescription = findViewById(R.id.comment);
        addHabitEventButton = findViewById(R.id.add_habit_event);
        cancelLocation = findViewById(R.id.cancellocation);
        latText = findViewById(R.id.lattext);
        lonText = findViewById(R.id.longtext);

        // also the photograph and location

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        int permissionCheck = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        if(permissionCheck != PackageManager.PERMISSION_GRANTED) {
            // ask permissions here using below code
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    123);
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Intent intent = getIntent();
        habitID = intent.getStringExtra("HABITID");

        DocumentReference ref = db.collection("Habits").document(habitID);
        ref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    if(document.exists()){
                        description = document.get("description").toString();
                        name = document.get("name").toString();
                        username = document.get("username").toString();
                    }
                }
            }
        });

        cancelLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                markerLat = null;
                markerLon = null;
                gMap.clear();
                latText.setText("No location selected");
                lonText.setText("");
            }
        });

        addHabitEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                comment = habitEventDescription.getText().toString();
                habitEventDescription.getText().clear();
                HashMap<String, String> data = new HashMap<>();
                data.put("habitID", habitID);
                data.put("name", name);
                data.put("comment", comment);
                data.put("latitude", markerLat);
                data.put("longitude", markerLon);
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection("HabitEvents")
                        .document()
                        .set(data)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void avoid){
                                Log.d("AddHabitEvent", "HabitEventAddedSuccessfully");
                                goToProfile();

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d("AddHabitEvent", "Couldn't be added");
                            }
                        });
            }

        });
    }

    /**
     * returns app to profile activity
     */
    public void goToProfile(){
        Intent intent = new Intent(this, SelfProfile.class);
        intent.putExtra("USERNAME", username);

        startActivity(intent);
        finish();
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onLocationChanged(Location location) {
        userLat = location.getLatitude();
        userLon = location.getLongitude();
        locationManager.removeUpdates(this);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;
        gMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        gMap.getUiSettings().setZoomControlsEnabled(true);
        gMap.setMyLocationEnabled(true);
        //gMap.animateCamera(CameraUpdateFactory.zoomTo(4f));
        gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(userLat, userLon), 12.0f));
        gMap.addMarker(new MarkerOptions()
                .position(new LatLng(userLat, userLon))
                .title("Marker"));
        markerLat = Double.toString(userLat);
        markerLon = Double.toString(userLon);
        latText.setText("Latitude: " + markerLat);
        lonText.setText("Longitude: " + markerLon);
        gMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(@NonNull LatLng latLng) {
                gMap.clear();
                gMap.addMarker(new MarkerOptions()
                        .position(latLng)
                        .title("Marker"));

                markerLat = Double.toString(latLng.latitude);
                markerLon = Double.toString(latLng.longitude);
                latText.setText("Latitude: " + markerLat);
                lonText.setText("Longitude: " + markerLon);
            }
        });
    }

}