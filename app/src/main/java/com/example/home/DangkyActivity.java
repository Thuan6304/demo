package com.example.home;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class DangkyActivity extends AppCompatActivity {
    EditText edtdkemail, edtdkmatkhau, edtdkmatkhau2;
    Button btndkdangnhap, btndkdangky;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dangky);
        addControls();
        addEvents();
    }

    private void addEvents() {
        btndkdangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });
        btndkdangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1 = new Intent(DangkyActivity.this, DangnhapActivity.class);
                startActivity(i1);
            }
        });
    }

    private void register() {
        String email, pass, repass;
        email = edtdkemail.getText().toString();
        pass = edtdkmatkhau.getText().toString();
        repass = edtdkmatkhau2.getText().toString();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Vui lòng nhập email", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(pass)) {
            Toast.makeText(this, "Vui lòng nhập mật khẩu", Toast.LENGTH_SHORT).show();
            return;
        } else if (pass.isEmpty() || pass.length() < 6) {
            Toast.makeText(this, "Mật khẩu phải nhiều hơn 5 kí tự", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(repass)) {
            Toast.makeText(this, "Vui lòng xác nhận mật khẩu", Toast.LENGTH_SHORT).show();
            return;
        }else if (repass.isEmpty() || repass.length() < 6) {
            Toast.makeText(this, "Mật khẩu phải nhiều hơn 5 kí tự", Toast.LENGTH_SHORT).show();
            return;
        }
        mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "Tạo tài khoản thành công", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(DangkyActivity.this, DangnhapActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(), "Tài khoản đã tồn tại", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void addControls() {
        edtdkemail=findViewById(R.id.edtdkemail);
        edtdkmatkhau=findViewById(R.id.edtdkmatkhau);
        edtdkmatkhau2=findViewById(R.id.edtdkmatkhau2);
        btndkdangky=findViewById(R.id.btndkdangky);
        btndkdangnhap=findViewById(R.id.btndkdangnhap);
        mAuth=FirebaseAuth.getInstance();
    }
}