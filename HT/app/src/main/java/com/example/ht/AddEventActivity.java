package com.example.ht;


import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
=======

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;

>>>>>>> c604b8f603669ee7c0033be81b91744acccd3399
import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
<<<<<<< HEAD
=======
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
>>>>>>> b39f6f76ff005f938828bde3683392df7955692c
=======
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
>>>>>>> b39f6f76ff005f938828bde3683392df7955692c
=======
import android.os.Build;

>>>>>>> c604b8f603669ee7c0033be81b91744acccd3399
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
=======

import android.widget.ImageView;
import android.widget.Toast;

>>>>>>> c604b8f603669ee7c0033be81b91744acccd3399
import android.widget.TextView;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
<<<<<<< HEAD
=======
=======
>>>>>>> b39f6f76ff005f938828bde3683392df7955692c
import android.widget.ImageView;
import android.widget.Toast;

>>>>>>> b39f6f76ff005f938828bde3683392df7955692c
=======
>>>>>>> c604b8f603669ee7c0033be81b91744acccd3399
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
<<<<<<< HEAD

import org.w3c.dom.Document;

=======

import org.w3c.dom.Document;

>>>>>>> b39f6f76ff005f938828bde3683392df7955692c
import java.io.ByteArrayOutputStream;
import java.util.HashMap;

<<<<<<< HEAD



/**
 * @author Cole
 * @author Hunter
 *
 * This activity is used for creating habit events.
 * Here you can write a comment and add a location to a habit event
 *
 */


public class AddEventActivity extends AppCompatActivity implements LocationListener, OnMapReadyCallback {
<<<<<<< HEAD
=======
public class AddEventActivity extends AppCompatActivity implements LocationListener, OnMapReadyCallback {
>>>>>>> c4040fbdfb41f01c51e98142027e461335ee5fe8
    EditText habitEventDescription;
    TextView latText;
    TextView lonText;
    Button addHabitEventButton;
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
    Button cancelLocation;
=======
    Button uploadButton;
    ImageView imageView;
>>>>>>> b39f6f76ff005f938828bde3683392df7955692c
=======
    Button uploadButton;
    ImageView imageView;
>>>>>>> b39f6f76ff005f938828bde3683392df7955692c
=======
    Button cancelLocation;
>>>>>>> c4040fbdfb41f01c51e98142027e461335ee5fe8
    String comment;
    String habitID;
    String description;
    String name;
    String username;
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
=======
>>>>>>> c4040fbdfb41f01c51e98142027e461335ee5fe8
    double userLat;
    double userLon;
    String markerLat;
    String markerLon;
    LocationManager locationManager;
    GoogleMap gMap;
<<<<<<< HEAD
=======
    private StorageReference mStorageRef;
    int CAMERA_REQUEST_CODE = 1;
>>>>>>> b39f6f76ff005f938828bde3683392df7955692c

    @SuppressLint("MissingPermission")
=======
    private StorageReference mStorageRef;
    int CAMERA_REQUEST_CODE = 1;

>>>>>>> b39f6f76ff005f938828bde3683392df7955692c
=======
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
    Button uploadButton;
    ImageView imageView;
    String description;
    String hour;
    String minute;

    private StorageReference mStorageRef;
    int CAMERA_REQUEST_CODE = 1;

    /**
     * creates the activity
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
=======

>>>>>>> c4040fbdfb41f01c51e98142027e461335ee5fe8
    @SuppressLint("MissingPermission")
>>>>>>> c604b8f603669ee7c0033be81b91744acccd3399
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        habitEventDescription = findViewById(R.id.comment);
        addHabitEventButton = findViewById(R.id.add_habit_event);
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
=======

        uploadButton = findViewById(R.id.upload);
        imageView = findViewById(R.id.eventImage);


        mStorageRef = FirebaseStorage.getInstance().getReference();

>>>>>>> c604b8f603669ee7c0033be81b91744acccd3399
        cancelLocation = findViewById(R.id.cancellocation);
        latText = findViewById(R.id.lattext);
        lonText = findViewById(R.id.longtext);

<<<<<<< HEAD
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
=======
        // also the photograph and location

>>>>>>> c4040fbdfb41f01c51e98142027e461335ee5fe8
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
<<<<<<< HEAD
<<<<<<< HEAD
=======
        uploadButton = findViewById(R.id.upload);
        imageView = findViewById(R.id.eventImage);


        mStorageRef = FirebaseStorage.getInstance().getReference();

>>>>>>> b39f6f76ff005f938828bde3683392df7955692c
=======
        uploadButton = findViewById(R.id.upload);
        imageView = findViewById(R.id.eventImage);


        mStorageRef = FirebaseStorage.getInstance().getReference();

>>>>>>> b39f6f76ff005f938828bde3683392df7955692c

=======
>>>>>>> c604b8f603669ee7c0033be81b91744acccd3399
        Intent intent = getIntent();
        habitID = intent.getStringExtra("HABITID");



        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, CAMERA_REQUEST_CODE);
            }
        });
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD

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
=======
>>>>>>> b39f6f76ff005f938828bde3683392df7955692c
=======
>>>>>>> b39f6f76ff005f938828bde3683392df7955692c
=======
>>>>>>> c604b8f603669ee7c0033be81b91744acccd3399

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==CAMERA_REQUEST_CODE && resultCode == RESULT_OK){
            Bundle extras = data.getExtras();
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] dataBAOS = baos.toByteArray();

            imageView.setImageBitmap(bitmap);

            FirebaseFirestore db = FirebaseFirestore.getInstance();


            StorageReference filepath = mStorageRef.child("Photos").child("somename");

            UploadTask uploadTask = filepath.putBytes(dataBAOS);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    DocumentReference ref = db.collection("Habits").document(habitID);
                    ref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document.exists()) {
                                    description = document.get("description").toString();
                                    name = document.get("name").toString();
                                    username = document.get("username").toString();
                                }
                            }
                        }
                    });
                }
            });

        // Get relevant information about the habit
=======

        Intent intent = getIntent();
        habitID = intent.getStringExtra("HABITID");

>>>>>>> c4040fbdfb41f01c51e98142027e461335ee5fe8
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
                            public void onSuccess(Void avoid) {
                                Log.d("AddHabitEvent", "HabitEventAddedSuccessfully");
                                goToProfile();

                            }
                    });
                    addHabitEventButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            comment = habitEventDescription.getText().toString();
                            habitEventDescription.getText().clear();
                            HashMap<String, String> data = new HashMap<>();
                            data.put("habitID", habitID);
                            data.put("name", name);
                            data.put("comment", comment);
                            FirebaseFirestore db = FirebaseFirestore.getInstance();
                            db.collection("HabitEvents")
                                    .document()
                                    .set(data)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            goToProfile();
                                        }
                                    });
<<<<<<< HEAD

                        }
                    });
                }
            });

=======

                        }
                    });
                }
            });

>>>>>>> b39f6f76ff005f938828bde3683392df7955692c
        }
    }

    //        DocumentReference ref = db.collection("Habits").document(habitID);
//        ref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                if(task.isSuccessful()){
//                    DocumentSnapshot document = task.getResult();
//                    if(document.exists()){
//                        description = document.get("description").toString();
//                        name = document.get("name").toString();
//                        username = document.get("username").toString();
//                    }
//                }
//            }
//        });
//
//        addHabitEventButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view){
//                comment = habitEventDescription.getText().toString();
//                habitEventDescription.getText().clear();
//                HashMap<String, String> data = new HashMap<>();
//                Log.d("comment", comment);
//                data.put("habitID", habitID);
//                data.put("name", name);
//                data.put("comment", comment);
//                // Log.d("Comment", comment);
//                FirebaseFirestore db = FirebaseFirestore.getInstance();
//                db.collection("HabitEvents")
//                        .document()
//                        .set(data)
//                        .addOnSuccessListener(new OnSuccessListener<Void>() {
//                            @Override
//                            public void onSuccess(Void avoid){
//                                Log.d("AddHabitEvent", "HabitEventAddedSuccessfully");
//                                goToProfile();
//
//                            }
//                        })
//                        .addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//                                Log.d("AddHabitEvent", "Couldn't be added");
//                            }
//                        });
//            }
//
//        });
//    }

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