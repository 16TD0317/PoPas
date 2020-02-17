package com.ajgroups.popas.Database;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.ajgroups.popas.DataAdapter.UserAdapter;
import com.ajgroups.popas.Home;
import com.ajgroups.popas.Login;
import com.ajgroups.popas.SignUp;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DatabaseHandler {
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    public void addUsers(final UserAdapter users,final Context context,final SignUp signUp,final View view)
    {
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setTitle("Registering");
        progressDialog.setMessage("Registration is on process");
        progressDialog.show();
        databaseReference.child("Users").child(users.getUserName()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserAdapter userAdapter = dataSnapshot.getValue(UserAdapter.class);
                if(userAdapter == null) {
                    databaseReference.child("Users").child(users.getUserName()).setValue(users);
                    Toast.makeText(context, "Registration Successful", Toast.LENGTH_LONG).show();
                    Intent i = new Intent(signUp, Login.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(i);
                    signUp.finish();
                    return;
                }else {
                    Snackbar.make(view,"Username already exist retry with a new one",Snackbar.LENGTH_LONG).show();
                    progressDialog.dismiss();
                    return;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return;
    }

    public void loginUser(String userName, final String password, final View view, final Context context, final Login login) {
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setTitle("Logging In");
        progressDialog.setMessage("Please wait unit login");
        progressDialog.show();
        databaseReference.child("Users").child(userName).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserAdapter userAdapter = dataSnapshot.getValue(UserAdapter.class);
                if (userAdapter == null) {
                    Snackbar.make(view,"Invalid username or password",Snackbar.LENGTH_LONG).show();
                    progressDialog.dismiss();
                    return;
                } else if (userAdapter.getPassword().equals(password)) {
                    Intent i = new Intent(context, Home.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(i);
                    login.finish();
                } else {
                    Snackbar.make(view,"Invalid username or password",Snackbar.LENGTH_LONG).show();
                    progressDialog.dismiss();
                    return;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
