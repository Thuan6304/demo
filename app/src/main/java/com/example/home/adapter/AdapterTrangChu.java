package com.example.home.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.home.ChiTietActivity;
import com.example.home.ModelAdmin;
import com.example.home.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.util.List;

public class AdapterTrangChu extends FirebaseRecyclerAdapter<ModelAdmin,AdapterTrangChu.myViewHolder> {
    private Context mcontext; //tao biến môi trường cho sự kiện click chi tiết



    public AdapterTrangChu(@NonNull FirebaseRecyclerOptions<ModelAdmin> options, Context context) {
        super(options);
        this.mcontext=context;// dòng 23
    }

    @Override
    protected void onBindViewHolder(@NonNull AdapterTrangChu.myViewHolder Holder, @SuppressLint("RecyclerView") final int position, @NonNull ModelAdmin modelAdmin) {
        Holder.tenXe.setText(modelAdmin.getTenXe());


        Glide.with(Holder.anhXe.getContext())
                .load(modelAdmin.getAnhXe())
                .placeholder(com.firebase.ui.database.R.drawable.common_google_signin_btn_icon_dark)
                .error(com.firebase.ui.database.R.drawable.common_google_signin_btn_icon_dark_normal)
                .into(Holder.anhXe);
        Holder.Layout_Truyen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnClickChiTiet(modelAdmin);
            }
        });
    }

    private void OnClickChiTiet(ModelAdmin modelAdmin){
        Intent intent = new Intent(mcontext, ChiTietActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("tenXe",modelAdmin);
        bundle.putSerializable("anhXe",modelAdmin);
        bundle.putSerializable("loaiXe",modelAdmin);
        bundle.putSerializable("moTa",modelAdmin);
        bundle.putSerializable("giaThue",modelAdmin);
        intent.putExtras(bundle);
        mcontext.startActivity(intent);
    }

    @NonNull
    @Override

    public AdapterTrangChu.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.xe_item,parent,false);
        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder{

        CardView Layout_Truyen;
        ImageView anhXe;
        TextView tenXe, loaiXe, giaThue;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            Layout_Truyen=(CardView) itemView.findViewById(R.id.Layout_Xe);
            anhXe = (ImageView) itemView.findViewById(R.id.ivAnhXe);
            tenXe = (TextView) itemView.findViewById(R.id.txtTenXe);
        }
    }
}
