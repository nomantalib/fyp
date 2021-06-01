package com.example.sample1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
        setContentView(R.layout.activity_dashboard);

        callSignUp= findViewById(R.id.signUp_screen);

        loginUser=findViewById(R.id.login_user);

        loginUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent= new Intent(Login.this,UserProfile.class);
                //startActivity(intent);



                 if(validateUserName() && validatePassword())
                {
                    String userNameValue = username.getEditText().getText().toString().trim();
                    String passwordValue = password.getEditText().getText().toString().trim();

                    DatabaseReference reference= FirebaseDatabase.getInstance().getReference("users");

                    Query checkUserName= reference.orderByChild("userName").equalTo(userNameValue);

                    checkUserName.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.exists())
                            {
                                username.setError(null);
                                username.setErrorEnabled(false);

                                String passwordFromDB= dataSnapshot.child(userNameValue).child("password").getValue(String.class);

                                if(passwordFromDB.equals(passwordValue))
                                {
                                    username.setError(null);
                                    username.setErrorEnabled(false);

                                    String nameFromDB= dataSnapshot.child(userNameValue).child("name").getValue(String.class);
                                    String userNameFromDB= dataSnapshot.child(userNameValue).child("userName").getValue(String.class);
                                    String emailFromDB= dataSnapshot.child(userNameValue).child("email").getValue(String.class);
                                    String phoneNoFromDB= dataSnapshot.child(userNameValue).child("phoneNo").getValue(String.class);

                                    Intent intent= new Intent(Login.this,UserProfile.class);
                                    intent.putExtra("name",nameFromDB);
                                    intent.putExtra("userName",userNameFromDB);
                                    intent.putExtra("phoneNo",phoneNoFromDB);
                                    intent.putExtra("password",passwordFromDB);

                                    startActivity(intent);
                                }
                                else
                                {
                                    password.setError("Wrong Password");
                                    password.requestFocus();
                                }
                            }
                            else
                            {
                                username.setError("User doesnot Exists");
                                username.requestFocus();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
                else
                {
                    return;
                }


            }
        });


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

    //public void loginUser(View view)
    //{

    //}
    private void isUser()
    {

    }

}