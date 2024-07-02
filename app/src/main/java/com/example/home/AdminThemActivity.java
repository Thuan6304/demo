package com.example.home;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class AdminThemActivity extends AppCompatActivity {

    EditText edtAnh, edtMo_Ta, edtLoai_Xe, edtTen_Xe, edtGia_Thue;
    Button btnThem, btnQuayVe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_them);
        AddControls();
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChenDuLieu();
                xoaTatCa();
            }
        });
        btnQuayVe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void AddControls() {
        edtTen_Xe = (EditText)findViewById(R.id.edtTen_Xe);
        edtLoai_Xe = (EditText)findViewById(R.id.edtLoai_Xe);
        edtMo_Ta = (EditText)findViewById(R.id.edtMo_Ta);
        edtAnh = (EditText)findViewById(R.id.edtAnh);
        edtGia_Thue= (EditText)findViewById(R.id.edtGia_Thue);
        btnThem = (Button)findViewById(R.id.btnThem);
        btnQuayVe = (Button)findViewById(R.id.btnQuayVe);
    }
    private void ChenDuLieu()
    {
        Map<String,Object> map=new HashMap<>();
        map.put("tenXe",edtTen_Xe.getText().toString());
        map.put("loaiXe",edtLoai_Xe.getText().toString());
        map.put("giaThue",edtGia_Thue.getText().toString());
        map.put("moTa",edtMo_Ta.getText().toString());
        map.put("anhXe",edtAnh.getText().toString());

        FirebaseDatabase.getInstance().getReference().child("Xe").push()
                .setValue(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(AdminThemActivity.this,"Tạo mới thành công", Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AdminThemActivity.this,"Thêm mới thất bại", Toast.LENGTH_LONG).show();
                    }
                });
    }
    private void xoaTatCa()
    {
        edtTen_Xe.setText("");
        edtLoai_Xe.setText("");
        edtGia_Thue.setText("");
        edtMo_Ta.setText("");
        edtAnh.setText("");

    }
}