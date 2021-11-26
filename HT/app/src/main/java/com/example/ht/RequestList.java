package com.example.ht;


import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.ht.Habit;
import com.example.ht.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;
import java.util.Arrays;

public class RequestList extends ArrayAdapter<String> {
    private ConstraintLayout layout;
    private ArrayList<String> requestList;
    private Context context;
    private int content;

    public RequestList(Context context, ArrayList requestList) {
        super(context, 0, requestList);
        this.requestList = requestList;
        this.context = context;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //return super.getView(position, convertView, parent);
        View view = convertView;
        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.request_cell, parent,false);
        }

        String request = requestList.get(position);

        TextView requestLabel = view.findViewById(R.id.requestLabel);
        TextView requestResult = view.findViewById(R.id.reqResult);
        Button accept = view.findViewById(R.id.acceptButton);
        Button deny = view.findViewById(R.id.denyButton);


        requestLabel.setText("@" + request + " want to follow you");

        deny.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                accept.setVisibility(View.INVISIBLE);
                deny.setVisibility(View.INVISIBLE);
                requestResult.setText("Request Denied");
                requestResult.setVisibility(View.VISIBLE);

                FirebaseFirestore db = FirebaseFirestore.getInstance();

                db.collection("Follow Requests")
                        .document(requestList.get(position) + "" + MainUser.getProfile().getUsername())
                        .delete();

            }
        });

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                accept.setVisibility(View.INVISIBLE);
                deny.setVisibility(View.INVISIBLE);
                requestResult.setText("Request Accepted");
                requestResult.setVisibility(View.VISIBLE);

                FirebaseFirestore db = FirebaseFirestore.getInstance();

                // remove from request database
                db.collection("Follow Requests")
                        .document(requestList.get(position) + "" + MainUser.getProfile().getUsername())
                        .delete();

                updateFollowing(requestList.get(position));
                MainUser.getProfile().addFollower(requestList.get(position));

            }
        });

        return view;
    }


    /**
     * this function updates the following list in the firebase of the profile
     * wiht the username stored in user, to include the username of the profile
     * that is currently logged in
     *
     * @param user the username of the profile to be updated
     */
    private void updateFollowing(String user){
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        DocumentReference ref = db.collection("users").document(user);
        ref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        String temp = document.get("following").toString();
                        ArrayList following;

                        // Convert the following string into a list
                        temp = temp.substring(1, temp.length()-1);
                        if(temp.length() > 0){
                            following = new ArrayList<String>(Arrays.asList(temp.split(",")));
                        }else{
                            following = new ArrayList<>();
                        }

                        following.add(MainUser.getProfile().getUsername());

                        db.collection("users").document(user).update("following", following.toString());

                    }
                }
            }
        });
    }


}