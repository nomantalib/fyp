package com.example.sample1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserProfile extends AppCompatActivity {

    TextInputLayout name,username,email,password,phoneNo;
    TextView profileName,profile_username;

    String _name,_username,_password,_email,_phoneNo,_profileName;

    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        reference= FirebaseDatabase.getInstance().getReference("users");

        profileName=findViewById(R.id.profile_name);
        profile_username=findViewById(R.id.profile_user_name);
        name=findViewById(R.id.name);
        username=findViewById(R.id.username);
        email =findViewById(R.id.email);
        password=findViewById(R.id.password);
        phoneNo=findViewById(R.id.phoneNo);

        // function to call the user data;
        showUserData();


    }
       // function to show the desired user data
    private void showUserData() {

        Intent intent= getIntent();
         _username= intent.getStringExtra("userName");
         _name= intent.getStringExtra("name");
         _password= intent.getStringExtra("password");
         _email= intent.getStringExtra("email");
         _phoneNo= intent.getStringExtra("phoneNo");

        profileName.setText(_name);
        profile_username.setText(_username);
        name.getEditText().setText(_name);
        password.getEditText().setText(_password);
        email.getEditText().setText(_email);
        phoneNo.getEditText().setText(_phoneNo);


    }
    public void update(View view){
        if(nameChanged() || passwordChanged())
        {
            Toast.makeText(this,"Data has been Updated",Toast.LENGTH_LONG).show();
        }
        else
        {
            Toast.makeText(this,"Data is the same, Can't be Updated",Toast.LENGTH_LONG).show();
        }
    }

    private boolean passwordChanged() {
        if(!_password.equals(password.getEditText().getText().toString())){

            reference.child(_username).child("password").setValue(password.getEditText().getText().toString());
            _password = password.getEditText().getText().toString();
            return true;
        }
        else
        {
            return false;
        }
    }

    private boolean nameChanged() {
        if(!_name.equals(name.getEditText().getText().toString())){

            reference.child(_username).child("name").setValue(name.getEditText().getText().toString());
            _name = name.getEditText().getText().toString();
            profileName.setText(_name);
            return true;
        }
        else
        {
            return false;
        }
    }
}