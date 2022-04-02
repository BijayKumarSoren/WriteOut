package com.example.writeout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Profile extends AppCompatActivity {

    TextInputLayout fullName, email, phoneNo, password;
    TextView fullNameLabel, usernameLabel;

    //Global Variables to hold user data inside this activity
    String _USERNAME, _NAME, _EMAIL, _PHONENO, _PASSWORD;

    DatabaseReference reference;
//    RecyclerView recyclerView;
//    ProfileAdapter profileAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        reference = FirebaseDatabase.getInstance().getReference("users");

        //Hooks
        fullName = findViewById(R.id.full_name_profile);
        email = findViewById(R.id.email_profile);
        phoneNo = findViewById(R.id.phone_no_profile);
        password = findViewById(R.id.password_profile);
        fullNameLabel = findViewById(R.id.fullname_field);
        usernameLabel = findViewById(R.id.username_field);

        //showAllUserData
        showAllUserData();

//        FirebaseRecyclerOptions<UserHelperClass> options =
//                new FirebaseRecyclerOptions.Builder<UserHelperClass>()
//                .setQuery(FirebaseDatabase.getInstance().getReference().child("users"), UserHelperClass.class)
//                .build();
//
//        profileAdapter = new ProfileAdapter(options);
//        recyclerView.setAdapter(profileAdapter);
    }

    private void showAllUserData() {

        Intent intent = getIntent();
        _USERNAME = intent.getStringExtra("username");
        _NAME = intent.getStringExtra("name");
        _EMAIL = intent.getStringExtra("email");
        _PHONENO = intent.getStringExtra("phoneNo");
        _PASSWORD = intent.getStringExtra("password");

        fullNameLabel.setText(_NAME);
        usernameLabel.setText(_USERNAME);
        fullName.getEditText().setText(_NAME);
        email.getEditText().setText(_EMAIL);
        phoneNo.getEditText().setText(_PHONENO);
        password.getEditText().setText(_PASSWORD);
    }


    public void update(View view) {

        if(isNameChanged() || isPasswordChanged()){
            Toast.makeText(this, "Data has been updated", Toast.LENGTH_LONG).show();
        }
        else Toast.makeText(this, "Data is same and cannot be updated", Toast.LENGTH_LONG).show();
    }

    private boolean isPasswordChanged() {
        if (!_PASSWORD.equals(password.getEditText().getText().toString())){
            reference.child(_USERNAME).child("password").setValue(password.getEditText().getText().toString());
            _PASSWORD = password.getEditText().getText().toString();
            return true;
        }else{
            return false;
        }
    }

    private boolean isNameChanged() {
        if (!_NAME.equals(fullName.getEditText().getText().toString())){
            reference.child(_USERNAME).child("name").setValue(fullName.getEditText().getText().toString());
            _NAME = fullName.getEditText().getText().toString();
            return true;
        }else{
            return false;
        }
    }

    public void back(View view) {
        Intent intent = new Intent(Profile.this,MyPage.class);
        startActivity(intent);
        finish();
    }
}