package com.example.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

import com.example.home.adapter.AdapterTrangChu;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class LoaiXeActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    AdapterTrangChu adapterTrangChu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loai_xe);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Loại Xe");
        TKxe();
    }

    private void TKxe() {
        recyclerView = (RecyclerView) findViewById(R.id.rvTimKiem);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<ModelAdmin> options =
                new FirebaseRecyclerOptions.Builder<ModelAdmin>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Xe"), ModelAdmin.class)
                        .build();
        adapterTrangChu = new AdapterTrangChu(options, this);
        recyclerView.setAdapter(adapterTrangChu);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);

    }

    //------------
    protected void onStart() {
        super.onStart();
        adapterTrangChu.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapterTrangChu.stopListening();
    }

    //---------------
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_loai, menu);
        MenuItem menuItem = menu.findItem(R.id.searchLoai);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchTheLoai(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                searchTheLoai(query);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private void searchTheLoai(String str) {
        FirebaseRecyclerOptions<ModelAdmin> options =
                new FirebaseRecyclerOptions.Builder<ModelAdmin>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Xe").orderByChild("loaiXe").startAt(str).endAt(str + "~"), ModelAdmin.class)
                        .build();
        adapterTrangChu = new AdapterTrangChu(options, this);
        adapterTrangChu.startListening();
        recyclerView.setAdapter(adapterTrangChu);
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //Chuyển trang đăng nhập
        if (item.getItemId() == R.id.TrangChu_LoaiXe) {
            Intent it1 = new Intent(LoaiXeActivity.this, MainActivity.class);
            startActivity(it1);
        }
        return super.onOptionsItemSelected(item);
    }
}