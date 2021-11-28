package com.example.ht;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

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

import org.w3c.dom.Document;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;

public class AddEventActivity extends AppCompatActivity {
    EditText habitEventDescription;
    Button addHabitEventButton;
    Button uploadButton;
    ImageView imageView;
    String comment;
    String habitID;
    String description;
    String hour;
    String minute;
    String name;
    String username;
    private StorageReference mStorageRef;
    int CAMERA_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        habitEventDescription = findViewById(R.id.comment);
        addHabitEventButton = findViewById(R.id.add_habit_event);
        uploadButton = findViewById(R.id.upload);
        imageView = findViewById(R.id.eventImage);


        mStorageRef = FirebaseStorage.getInstance().getReference();


        Intent intent = getIntent();
        habitID = intent.getStringExtra("HABITID");


        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, CAMERA_REQUEST_CODE);
            }
        });

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

                        }
                    });
                }
            });

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


}