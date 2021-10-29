package com.example.ht;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class CreateAccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        String TAG = "sample";
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Button createButton = findViewById(R.id.create_button);

        EditText name;
        TextView nameError;
        EditText username;
        TextView usernameError;
        EditText password;
        TextView passwordError;


        name = findViewById(R.id.name_editText);
        nameError = findViewById(R.id.name_error);
        username = findViewById(R.id.username_editText);
        usernameError = findViewById(R.id.username_error);
        password = findViewById(R.id.password_editText);
        passwordError = findViewById(R.id.password_error);




        createButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameText = name.getText().toString();
                String usernameText = username.getText().toString();
                String passwordText = password.getText().toString();


                // turns off errors
                final boolean[] valid = {true};
                nameError.setVisibility(View.INVISIBLE);
                usernameError.setText("");
                passwordError.setVisibility(View.INVISIBLE);

                //check if name has non zero length
                if(nameText.length() == 0){
                    valid[0] = false;
                    nameError.setVisibility(View.VISIBLE);
                }

                //checks if password is valid
                if(passwordText.length() < 8 || passwordText.length() >21){
                    valid[0] = false;
                    passwordError.setVisibility(View.VISIBLE);
                }

                //check if username is unique and longer than 0
                if(username.length() > 0) {
                    DocumentReference ref = db.collection("users").document(usernameText);


                    ref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document.exists()) {
                                    Log.d(TAG, "Document exists!");
                                    usernameError.setVisibility(View.VISIBLE);
                                    usernameError.setText("Username is already taken");
                                    valid[0] = false;
                                } else if(valid[0]) {
                                    HashMap<String, String> user = new HashMap<>();
                                    user.put("name", nameText);
                                    user.put("username", usernameText);
                                    user.put("password", passwordText);


                                    // add a new document
                                    db.collection("users")
                                            .document(usernameText)
                                            .set(user)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    // These are a method which gets executed when the task is succeeded
                                                    Log.d(TAG, "Data has been added successfully!");
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    // These are a method which gets executed if there’s any problem
                                                    Log.d(TAG, "Data could not be added!" + e.toString());
                                                }
                                            });
                                    //MOVE TO NEXT ACTIVITY
                                    goToAccount();
                                }
                            } else {
                                Log.d(TAG, "Failed with: ", task.getException());
                            }
                        }
                    });



                }else{
                    usernameError.setText("Username cannot be empty");
                    usernameError.setVisibility(View.VISIBLE);
                }

            }
        });
    }

    //starts the next activity
    // currently set to return to start, will be changed when activity is added
    private void goToAccount(){
        Intent intent = new Intent(this, StartActivity.class);
        startActivity(intent);
    }
}