package com.example.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.home.adapter.AdapterAdmin;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.FirebaseDatabase;

public class AdminActivity extends AppCompatActivity {
    FloatingActionButton floatingActionButton;
    RecyclerView recyclerView;
    AdapterAdmin adapterAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("Admin");
        setContentView(R.layout.activity_admin);

        recyclerView=(RecyclerView) findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<ModelAdmin> options =
                new FirebaseRecyclerOptions.Builder<ModelAdmin>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Xe"), ModelAdmin.class)
                        .build();
        adapterAdmin = new AdapterAdmin(options);
        recyclerView.setAdapter(adapterAdmin);
        floatingActionButton= (FloatingActionButton)findViewById(R.id.fabThem);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),AdminThemActivity.class));
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapterAdmin.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapterAdmin.stopListening();
    }
//tao thanh sreach
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_admin,menu);
        MenuItem item = menu.findItem(R.id.searchAdmin);
        SearchView searchView = (SearchView)item.getActionView();
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
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Xe")
                                   .orderByChild("tenXe").startAt(str).endAt(str+"~"), ModelAdmin.class)
                        .build();
        adapterAdmin = new AdapterAdmin(options);
        adapterAdmin.startListening();
        recyclerView.setAdapter(adapterAdmin);
    }
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //Chuyển trang chủ
        if (item.getItemId() == R.id.AdminMain) {
            Intent it1 = new Intent(AdminActivity.this, MainActivity.class);
            startActivity(it1);
        }
        return super.onOptionsItemSelected(item);
    }
}