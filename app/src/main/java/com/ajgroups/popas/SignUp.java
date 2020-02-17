package com.ajgroups.popas;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.ajgroups.popas.DataAdapter.UserAdapter;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class SignUp extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private Spinner bloodGroup;
    private Button register;
    private String bloodG;
    private EditText userName,firstName,lastName,age,weight,height,mobile,password,confirmPassword;
    private String bloodGroupList[] = {"A+ve","A-ve","B+ve","B-ve","AB+ve","AB-ve","O+ve","O-ve"};
    private RadioButton male,female;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        bloodGroup = findViewById(R.id.bloodGroup);
        register = findViewById(R.id.register);
        userName = findViewById(R.id.userName);
        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        age = findViewById(R.id.age);
        weight = findViewById(R.id.weight);
        height = findViewById(R.id.height);
        mobile = findViewById(R.id.mobile);
        password = findViewById(R.id.password);
        male = findViewById(R.id.male);
        female = findViewById(R.id.female);
        confirmPassword = findViewById(R.id.confirmPassword);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,bloodGroupList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bloodGroup.setAdapter(adapter);
        bloodGroup.setOnItemSelectedListener(this);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pass, cpass;
                pass = password.getText().toString();
                cpass= confirmPassword.getText().toString();
                UserAdapter users = new UserAdapter();
                if(male.isChecked())
                {
                    users.setGender("Male");
                }
                else if(female.isChecked())
                {
                    users.setGender("Female");
                }
                users.setUserName(userName.getText().toString());
                users.setFirstName(firstName.getText().toString());
                users.setLastName(lastName.getText().toString());
                users.setAge(age.getText().toString());
                users.setWeight(weight.getText().toString());
                users.setHeight(height.getText().toString());
                users.setMobileNumber( mobile.getText().toString());
                users.setBloodGroup(bloodG);
                if(users.getUserName().isEmpty() || users.getFirstName().isEmpty() || users.getLastName().isEmpty() || users.getAge().isEmpty() ||  users.getWeight().isEmpty() || users.getHeight().isEmpty() || users.getMobileNumber().isEmpty() || pass.isEmpty() || cpass.isEmpty() || bloodG.isEmpty())
                {
                    Snackbar.make(view,"Please fill all the fields",Snackbar.LENGTH_LONG).show();
                    return;
                }
                if(pass.equals(cpass)){
                    users.setPassword(pass);
                }else{
                    Snackbar.make(view,"Please enter same password in both password and confirm password",Snackbar.LENGTH_LONG).show();
                    return;
                }


            }
        });
    }
    public void close(View view){
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Exit Registration");
        alertDialogBuilder.setMessage("Are you sure do you want to exit registration");
        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                startActivity(new Intent(SignUp.this,Login.class));
                finish();
            }
        });
        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            bloodG = bloodGroup.getSelectedItem().toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
