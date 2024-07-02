package com.example.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.home.adapter.AdapterTrangChu;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class    ChiTietActivity extends AppCompatActivity {

    Button btnQuayLai;
    RecyclerView recyclerView;
    AdapterTrangChu adapterTrangChu;
    List<ModelAdmin> adminList;
    @NonNull
    ModelAdmin modelAdmin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet);
        suKienChiTiet();
        dsXe();
        quayLai();
    }

    private void quayLai() {
        btnQuayLai=(Button) findViewById(R.id.btnQuayLai) ;
        btnQuayLai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(ChiTietActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }


    private void suKienChiTiet() {
        Bundle bundle = getIntent().getExtras();

        ModelAdmin modelTen = (ModelAdmin) bundle.get("tenXe");
        TextView txtTen=findViewById(R.id.txtTenChiTiet);
        txtTen.setText(modelTen.getTenXe());
        //----------------------
        ModelAdmin modelAnh = (ModelAdmin) bundle.get("anhXe");
        ImageView ivAnh=findViewById(R.id.ivHinhChiTiet);
        Glide.with(ivAnh.getContext())
                .load(modelAnh.getAnhXe())
                .placeholder(com.firebase.ui.database.R.drawable.common_google_signin_btn_icon_dark)
                .error(com.firebase.ui.database.R.drawable.common_google_signin_btn_icon_dark_normal)
                .into(ivAnh);

        //----------------------
        ModelAdmin modelMoTa = (ModelAdmin) bundle.get("moTa");
        TextView txtMoTa=findViewById(R.id.txtMoTaChiTiet);
        txtMoTa.setText(modelMoTa.getMoTa());
        //----------------------
        ModelAdmin modelGia= (ModelAdmin) bundle.get("giaThue");
        TextView txtGia=findViewById(R.id.txtGiaThueChiTiet);
        txtGia.setText(modelGia.getGiaThue());
        //----------------------
        ModelAdmin modelLoai = (ModelAdmin) bundle.get("loaiXe");
        TextView txtLoai=findViewById(R.id.txtLoaiXeChiTiet);
        txtLoai.setText(modelLoai.getGiaThue());
    }

    private void dsXe() {
        recyclerView=(RecyclerView) findViewById(R.id.rvChiTiet);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<ModelAdmin> options =
                new FirebaseRecyclerOptions.Builder<ModelAdmin>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Xe"), ModelAdmin.class)
                        .build();
        adapterTrangChu = new AdapterTrangChu(options,this);
        recyclerView.setAdapter(adapterTrangChu);
        GridLayoutManager gridLayoutManager= new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(gridLayoutManager);

    }

    protected void onStart() {
        super.onStart();
        adapterTrangChu.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapterTrangChu.stopListening();
    }


}