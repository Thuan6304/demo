package com.example.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.FileObserver;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.example.home.adapter.AdapterAdmin;
import com.example.home.adapter.AdapterTrangChu;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    AdapterTrangChu adapterTrangChu;
    NavigationView navigation;
    DrawerLayout drawerLayout;
    ViewFlipper viewFlipper;
    FirebaseAuth mAuth;
    TextView  showemail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("Trang chủ");
        addControls();
        addEvents();
        dsXe();
        showUserInformation();
    }

    private void dsXe() {
        recyclerView=(RecyclerView) findViewById(R.id.rvTrangChu);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<ModelAdmin> options =
                new FirebaseRecyclerOptions.Builder<ModelAdmin>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Truyen"), ModelAdmin.class)
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
    private void addEvents() {
        //bấm vào xe
        //menu trái
        navigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                //Nhấn vào đăng xuất chuyển trang đăng nhập
                if (menuItem.getItemId() == R.id.navLogout) {
                    Intent it1 = new Intent(MainActivity.this, DangnhapActivity.class);
                    startActivity(it1);
                    Toast.makeText(MainActivity.this,"Đăng xuất thành công", Toast.LENGTH_SHORT).show();
                }
                else if (menuItem.getItemId() == R.id.navLoai) {
                    Intent it1 = new Intent(MainActivity.this,LoaiXeActivity.class);
                    startActivity(it1);
                }
                drawerLayout.close();
                return false;
            }
        });
    }


    private void addControls() {
        drawerLayout = findViewById(R.id.drawer);
        navigation = findViewById(R.id.navigation);
        showemail = navigation.getHeaderView(0).findViewById(R.id.showemail);
    }
    //tạo thanh menu
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        MenuItem menuItem = menu.findItem(R.id.mnTimK);
        SearchView searchView = (SearchView)menuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                txtSearch(query);
                return false;
            }
            @Override
            public boolean onQueryTextChange(String query) {
                txtSearch(query);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
    private void txtSearch(String str)
    {
        FirebaseRecyclerOptions<ModelAdmin> options =
                new FirebaseRecyclerOptions.Builder<ModelAdmin>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Xe").orderByChild("tenXe").startAt(str).endAt(str+"~"), ModelAdmin.class)
                        .build();
        adapterTrangChu = new AdapterTrangChu(options,this);
        adapterTrangChu.startListening();
        recyclerView.setAdapter(adapterTrangChu);
    }
    //xử lí thanh menu
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //Chuyển trang đăng nhập
        if(item.getItemId()==R.id.mnTaiK) {
            Intent it1 = new Intent(MainActivity.this, AdminActivity.class);
            startActivity(it1);
        }
        else if(item.getItemId()==R.id.mnMain) {
            drawerLayout.open();
        }
        return super.onOptionsItemSelected(item);
    }
    //--------- Lấy tên người dùng
    private void showUserInformation(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user == null){
            return;
        }
        String email = user.getEmail();
        showemail.setText(email);
    }
}

