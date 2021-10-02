package com.example.sample1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {

    TextInputLayout regName,regUserName,regEmail,regPhoneNo,regPassword;
    Button clickLogin,signUp;

    FirebaseDatabase rootNode;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_up);

        regName=findViewById(R.id.name);
        regUserName=findViewById(R.id.username);
        regEmail=findViewById(R.id.email);
        regPhoneNo=findViewById(R.id.phone_no);
        regPassword=findViewById(R.id.password);

        clickLogin= findViewById(R.id.login_screen);
        signUp=findViewById(R.id.signUp);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!nameValidate() | !userNameValidate() | !passwordValidate() |!emailValidate() |!phoneNoValidate())
                {
                    return;
                }
                rootNode= FirebaseDatabase.getInstance();
                reference= rootNode.getReference("users");
                String name= regName.getEditText().getText().toString();
                String userName= regUserName.getEditText().getText().toString();
                String email= regEmail.getEditText().getText().toString();
                String phone= regPhoneNo.getEditText().getText().toString();
                String password= regPassword.getEditText().getText().toString();

                UserHelperClass helperClass= new UserHelperClass(name,userName,email,phone,password);

                reference.child(userName).setValue(helperClass);

            }
        });






        clickLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUp.this,Login.class);
                startActivity(intent);
            }
        });
    }

    public Boolean nameValidate()
    {
        String val= regName.getEditText().getText().toString();

        if(val.isEmpty())
        {
            regName.setError("Field is empty");
            return false;

        }
        else
        {
            regName.setError(null);
            regName.setErrorEnabled(false);
            return true;
        }
    }
    public Boolean userNameValidate()
    {
        String val= regUserName.getEditText().getText().toString();
       // String noWhiteSpace= "(?==\\s+$)";

        if(val.isEmpty())
        {
            regUserName.setError("Field is empty");
            return false;

        }//else if(!val.matches(noWhiteSpace))
       // {
          //  regUserName.setError("No white space should exists");
          //  return false;
       // }
        else
        {
            regUserName.setError(null);
            regUserName.setErrorEnabled(false);
            return true;
        }
    }
    public Boolean passwordValidate() {
        String val = regPassword.getEditText().getText().toString();
       // String passwordVal = "^" +
        //        "(?=.*[a-zA-Z])" +
        //        "(?=/*[@#$%^&+=])" +
       //         "(?=\\s=$)"+
       //         ".[4,]"+
       //         "$";
        if(val.isEmpty())
        {
            regPassword.setError("Field is empty");
            return false;

        }
       // else if(!val.matches(passwordVal))
        //{
        //    regPassword.setError("Password is too weak");
        //    return false;
       // }
        else
        {
            regPassword.setError(null);
            regPassword.setErrorEnabled(false);
            return true;
        }
    }
    public Boolean emailValidate()
    {
        String val= regEmail.getEditText().getText().toString();
       // String emailPattern= "[a-zA-z0-9._-]+@[a-z]+\\.+[a-z]+";

        if(val.isEmpty())
        {
            regEmail.setError("Field is empty");
            return false;

        }// else if(!val.matches(emailPattern))
       // {
       //     regEmail.setError("Invalid email address");
        //    return false;
       // }
        else
        {
            regEmail.setError(null);
            regEmail.setErrorEnabled(false);
            return true;
        }
    }
    public Boolean phoneNoValidate()
    {
        String val= regPhoneNo.getEditText().getText().toString();

        if(val.isEmpty())
        {
            regPhoneNo.setError("Field is empty");
            return false;

        }
        else
        {
            regPhoneNo.setError(null);
            return true;
        }
    }





}