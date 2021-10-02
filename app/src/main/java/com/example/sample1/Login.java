package com.example.sample1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {

    Button callSignUp,loginUser;
    TextInputLayout username,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        callSignUp= findViewById(R.id.signUp_screen);

        loginUser=findViewById(R.id.login_user);
        username=findViewById(R.id.username);
        password=findViewById(R.id.password);



        callSignUp.setOnClickListener(new View.OnClickListener() {
           @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this,SignUp.class);
                startActivity(intent);
            }
        });

    }

    public Boolean validateUserName()
    {
        String val= username.getEditText().getText().toString();


        if(val.isEmpty())
        {
            username.setError("Field is empty");
            return false;

        }
        else
        {
            username.setError(null);
            username.setErrorEnabled(false);
            return true;
        }
    }
    public Boolean validatePassword() {
        String val = password.getEditText().getText().toString();

        if(val.isEmpty())
        {
            password.setError("Field is empty");
            return false;

        }

        else
        {
            password.setError(null);
            password.setErrorEnabled(false);
            return true;
        }
    }

    public void loginUser(View view){

        if(!validateUserName() | !validatePassword())
        {
            return;
        }
        else
        {
            isUser();
        }
    }

    private void isUser()
    {
        String usernameEntered= username.getEditText().getText().toString().trim();
        String passwordEntered= password.getEditText().getText().toString().trim();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");

        Query checkUser= reference.orderByChild("userName").equalTo(usernameEntered);
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {

                    username.setError(null);
                    username.setErrorEnabled(false);

                    String dbPassword = dataSnapshot.child(usernameEntered).child("password").getValue(String.class);

                    if (dbPassword.equals(passwordEntered)) {

                        username.setError(null);
                        username.setErrorEnabled(false);

                        String name = dataSnapshot.child(usernameEntered).child("name").getValue(String.class);
                        String user_name = dataSnapshot.child(usernameEntered).child("userName").getValue(String.class);
                        String email = dataSnapshot.child(usernameEntered).child("email").getValue(String.class);
                        String phoneNo = dataSnapshot.child(usernameEntered).child("phoneNo").getValue(String.class);

                        Intent intent = new Intent(getApplicationContext(), UserProfile.class);

                        intent.putExtra("userName", user_name);
                        intent.putExtra("name", name);
                        intent.putExtra("phoneNo", phoneNo);
                        intent.putExtra("email", email);
                        intent.putExtra("password", dbPassword);

                        startActivity(intent);

                    }
                    else {
                        password.setError("Wrong Password");
                        password.requestFocus();
                    }
                }
                else
                {
                    username.setError("No such User Exists");
                    username.requestFocus();
                }

            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

}