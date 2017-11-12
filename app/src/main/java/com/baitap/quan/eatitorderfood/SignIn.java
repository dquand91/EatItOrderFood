package com.baitap.quan.eatitorderfood;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.baitap.quan.eatitorderfood.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignIn extends AppCompatActivity {

    Button btnSignIn_SignIn;
    EditText edtPhone, edtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        edtPassword = (EditText) findViewById(R.id.edtPassword);
        edtPhone = (EditText) findViewById(R.id.edtPhone);
        btnSignIn_SignIn = (Button) findViewById(R.id.btnSignIn_SignIn);

        // Init Firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        // Để lấy ra cái bảng "User" từ DataBase của FireBase
        final DatabaseReference table_user =  database.getReference("User");

        btnSignIn_SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final ProgressDialog mProgressDialog = new ProgressDialog(SignIn.this);
                mProgressDialog.setMessage("Please wait...");
                mProgressDialog.show();

                // add Call Back for reponse from FireBase
                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        mProgressDialog.dismiss();
                        // Check if user not existed in DataBase
                        // .child là phương thức của dataSnapshot để lấy ra phần tử trả về.
                        if (dataSnapshot.child(edtPhone.getText().toString()).exists()){
                            // Get User Information
                            User user = dataSnapshot.child(edtPhone.getText().toString()).getValue(User.class);
                            if (user.getPassword().equals(edtPassword.getText().toString())){
                                Toast.makeText(SignIn.this, "Sign in success!", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(SignIn.this, "Wrong Password!", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(SignIn.this, "User does not exist!", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
    }
}
