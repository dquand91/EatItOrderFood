package com.baitap.quan.eatitorderfood;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.baitap.quan.eatitorderfood.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

public class SignUp extends AppCompatActivity {

    MaterialEditText edtPhone, edtName, edtPassword;
    Button btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        edtName = (MaterialEditText) findViewById(R.id.edtName_SignUp);
        edtPassword = (MaterialEditText) findViewById(R.id.edtPassword_SignUp);
        edtPhone = (MaterialEditText) findViewById(R.id.edtPhone_SignUp);
        btnSignUp = (Button) findViewById(R.id.btnSignUp_SignUp);

        // Init Firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        // Để lấy ra cái bảng "User" từ DataBase của FireBase
        final DatabaseReference table_user =  database.getReference("User");

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog mProgressDialog = new ProgressDialog(SignUp.this);
                mProgressDialog.setMessage("Please wait...");
                mProgressDialog.show();

                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        mProgressDialog.dismiss();
                        //Check if already existed
                        if (dataSnapshot.child(edtPhone.getText().toString()).exists()){
                            Toast.makeText(SignUp.this, "User existed!", Toast.LENGTH_SHORT).show();
                        } else{
                            User user = new User(edtName.getText().toString(), edtPassword.getText().toString());
                            table_user.child(edtPhone.getText().toString()).setValue(user);
                            Toast.makeText(SignUp.this, "SignUp Successfully!", Toast.LENGTH_SHORT).show();
                            finish();
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
