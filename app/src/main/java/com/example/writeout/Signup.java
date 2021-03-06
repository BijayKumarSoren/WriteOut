package com.example.writeout;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Signup extends AppCompatActivity {

    //variables
    TextInputLayout regName, regUsername, regEmail, regPhoneNo, regPassword;
    Button regBtn, regToLoginBtn;

    // Write a message to the database
    FirebaseDatabase rootNode;
    DatabaseReference reference;

    //Hooks to all xml elements in activity_signup.xml
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        regName = findViewById(R.id.reg_name);
        regUsername = findViewById(R.id.reg_username);
        regEmail = findViewById(R.id.reg_email);
        regPhoneNo = findViewById(R.id.reg_phoneNo);
        regPassword = findViewById(R.id.reg_password);
        regBtn = findViewById(R.id.btn_signup);
        regToLoginBtn = findViewById(R.id.login_screen);
        }

    //validate Name
    private Boolean validateName(){
        String val = regName.getEditText().getText().toString();
        if(val.isEmpty()){
            regName.setError("Field cannot be empty");
            return false;
        }
        else {
            regName.setError(null);
            regName.setErrorEnabled(false);
            return true;
        }
    }

    //validate username
    private Boolean validateUsername(){
        String val = regUsername.getEditText().getText().toString();
        String noWhiteSpace = "\\A\\w{4,20}\\z";

        if(val.isEmpty()){
            regUsername.setError("Field cannot be empty");
            return false;
        } else if (val.length()>=15){
            regUsername.setError("Username too long");
            return false;
        } else if(!val.matches(noWhiteSpace)){
            regUsername.setError("White Spaces are not allowed");
            return false;
        }
        else {
            regUsername.setError(null);
            regUsername.setErrorEnabled(false);
            return true;
        }
    }

    //validate Email
    private Boolean validateEmail(){
        String val = regEmail.getEditText().getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if(val.isEmpty()){
            regEmail.setError("Field cannot be empty");
            return false;
        } else if (!val.matches(emailPattern)){
            regEmail.setError("Invalid email address");
            return false;
        }
        else {
            regEmail.setError(null);
            regEmail.setErrorEnabled(false);
            return true;
        }
    }

    //validate phoneNo
    private Boolean validatePhoneNo(){
        String val = regPhoneNo.getEditText().getText().toString();
        if(val.isEmpty()){
            regPhoneNo.setError("Field cannot be empty");
            return false;
        } else if (!(val.length()==10)){
            regPhoneNo.setError("Invalid Phone Number");
            return false;
        }
        else {
            regPhoneNo.setError(null);
            regPhoneNo.setErrorEnabled(false);
            return true;
        }
    }

    //validate password
    private Boolean validatePassword(){
        String val = regPassword.getEditText().getText().toString();
        String passwordVal = "[a-zA-Z0-9\\!\\@\\#\\$]{8,24}";

        if(val.isEmpty()){
            regPassword.setError("Field cannot be empty");
            return false;
        }else if (!val.matches(passwordVal)){
            regPassword.setError("Password is too weak");
            return false;
        }
        else {
            regPassword.setError(null);
            regPassword.setErrorEnabled(false);
            return true;
        }
    }

    //move to login page
    public void loginButtonClick(View view) {
        Intent intent = new Intent(getApplicationContext(),Login.class);
        startActivity(intent);
        finish();
    }

    //Save data in FireBase on button click
    public void registerUser(View view){
        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("users");

        if(!validateName() | !validateUsername() | !validateEmail() | !validatePhoneNo() | !validatePassword()){
            return;
        }

        //Get all the values in string
        String name =regName.getEditText().getText().toString();
        String username =regUsername.getEditText().getText().toString();
        String email =regEmail.getEditText().getText().toString();
        String phoneNo =regPhoneNo.getEditText().getText().toString();
        String password =regPassword.getEditText().getText().toString();

        UserHelperClass helperClass = new UserHelperClass(name, username, email, phoneNo, password);

        reference.child(username).setValue(helperClass);
        Toast.makeText(Signup.this,"Successfully Signed Up!!",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getApplicationContext(),Dashboard.class);
        startActivity(intent);
        finish();
    }

}




