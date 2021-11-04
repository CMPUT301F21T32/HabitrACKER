/**
 * LOG IN ACTIVITY
 * @author cole
 *
 * This is a control class which creates a screen from
 * which users can log in to their account.
 *
 */

package com.example.ht;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        TextView error = findViewById(R.id.login_error);
        EditText username = findViewById(R.id.loginName_editText);
        EditText password = findViewById(R.id.loginPassword_editText);
        Button loginButton = findViewById(R.id.login_button);

        error.setVisibility(View.INVISIBLE);


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usernameText = username.getText().toString();
                String passwordText = password.getText().toString();


                DocumentReference ref = db.collection("users").document(usernameText);
                ref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists() && document.get("password").equals(passwordText)) {
                                Log.d("sample: ", "Document exists!");
                                // GO TO NEXT ACTIVITY
                                goToAccount();

                            } else {
                                username.setText("");
                                password.setText("");
                                error.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                });
            }
        });
    }

    //starts the next activity
    // currently set to return to start, will be changed when activity is added
    private void goToAccount(){
        Intent intent = new Intent(this, FeedActivity.class);
        startActivity(intent);
    }
}