package com.example.ht;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
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
import android.os.Build;
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

/**
 * @author Cole
 * @author Hunter
 *
 * This activity is used for creating habit events.
 * Here you can write a comment and add a location to a habit event
 *
 */

public class AddEventActivity extends AppCompatActivity implements LocationListener, OnMapReadyCallback {
    EditText habitEventDescription; // Text box for the description
    TextView latText; // selected latitude
    TextView lonText; // selected longitude
    Button addHabitEventButton; // Button that completes the addition of the event
    Button cancelLocation; // Cancels the selection of a location
    String comment; // Stores the text of the comment about the habit event
    String habitID; // Unique id of the habit
    String name; // name of the habit
    String username; // username of the habit event creator
    double userLat; // Stores the current latitude of the user
    double userLon; // Stores the current longitude of the user
    String markerLat; // The latitude of the marker on the map
    String markerLon; // The longitude of the marker on the map
    LocationManager locationManager; // Used for getting the users location
    GoogleMap gMap; // The map where the user selects their location

    /**
     * creates the activity
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
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

        ActivityResultLauncher<String[]> locationPermissionRequest =
                registerForActivityResult(new ActivityResultContracts
                                .RequestMultiplePermissions(), result -> {
                            Boolean fineLocationGranted = result.getOrDefault(
                                    Manifest.permission.ACCESS_FINE_LOCATION, false);
                            Boolean coarseLocationGranted = result.getOrDefault(
                                    Manifest.permission.ACCESS_COARSE_LOCATION,false);
                            if (fineLocationGranted != null && fineLocationGranted) {
                                // Precise location access granted.
                            } else if (coarseLocationGranted != null && coarseLocationGranted) {
                                // Only approximate location access granted.
                            } else {
                                // No location access granted.
                            }
                        }
                );


        locationPermissionRequest.launch(new String[] {
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
        });

        // Finds the location of the user
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        int permissionCheck = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        if(permissionCheck != PackageManager.PERMISSION_GRANTED) {
            // ask permissions here using below code
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    123);
        }
        // Saves the location of the user then uses it to create the map
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);

        // Set up the connection to the firebase
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Intent intent = getIntent();
        habitID = intent.getStringExtra("HABITID");

        // Get relevant information about the habit
        DocumentReference ref = db.collection("Habits").document(habitID);
        ref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    if(document.exists()){
                        name = document.get("name").toString();
                        username = document.get("username").toString();
                    }
                }
            }
        });

        // Sets the cancel button to clear the selected location on the map
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

        // Adds the habit event to the firebase upon completion
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
                                // Exit the activity
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

    /**
     * Recieves the users location and updates userLat and userLon
     * Then it creates the map using this information
     * Creating the map here guarantees that we have received a location before
     * it is created
     * @param location
     */
    @SuppressLint("MissingPermission")
    @Override
    public void onLocationChanged(Location location) {
        userLat = location.getLatitude();
        userLon = location.getLongitude();
        // Stop the location from constantly updating
        locationManager.removeUpdates(this);
        // Create the map
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;
        // Set the relevent settings of the map
        gMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        gMap.getUiSettings().setZoomControlsEnabled(true);
        gMap.setMyLocationEnabled(true);
        gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(userLat, userLon), 12.0f));
        // Add a marker to the users location by default
        gMap.addMarker(new MarkerOptions()
                .position(new LatLng(userLat, userLon))
                .title("Marker"));
        markerLat = Double.toString(userLat);
        markerLon = Double.toString(userLon);
        // Set the text below the map
        latText.setText("Latitude: " + markerLat);
        lonText.setText("Longitude: " + markerLon);
        // Update the location of the marker when a user clicks somewhere on the map
        gMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(@NonNull LatLng latLng) {
                gMap.clear(); // Clear markers already on the map
                gMap.addMarker(new MarkerOptions()
                        .position(latLng)
                        .title("Marker"));

                // Update the text
                markerLat = Double.toString(latLng.latitude);
                markerLon = Double.toString(latLng.longitude);
                latText.setText("Latitude: " + markerLat);
                lonText.setText("Longitude: " + markerLon);
            }
        });
    }

}