package com.example.home.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.home.ModelAdmin;
import com.example.home.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.Map;

public class AdapterAdmin extends FirebaseRecyclerAdapter<ModelAdmin,AdapterAdmin.myViewHolder> {
    public AdapterAdmin(@NonNull FirebaseRecyclerOptions<ModelAdmin> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder Holder, @SuppressLint("RecyclerView") final int position, @NonNull ModelAdmin modelAdmin) {
        Holder.tenXe.setText(modelAdmin.getTenXe());
        Holder.loaiXe.setText(modelAdmin.getLoaiXe());
        Holder.moTa.setText(modelAdmin.getMoTa());
        Holder.giaThue.setText(modelAdmin.getGiaThue());
        
        Glide.with(Holder.iv1.getContext())
                .load(modelAdmin.getAnhXe())
                .placeholder(com.firebase.ui.database.R.drawable.common_google_signin_btn_icon_dark)
                .error(com.firebase.ui.database.R.drawable.common_google_signin_btn_icon_dark_normal)
                .into(Holder.iv1);

        Holder.btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DialogPlus dialogPlus = DialogPlus.newDialog(Holder.iv1.getContext())
                        .setContentHolder(new ViewHolder(R.layout.capnhat))
                        .setExpanded(true,1500)
                        .create();
                dialogPlus.show();

                View view = dialogPlus.getHolderView();
                EditText edtTen_Xe = view.findViewById(R.id.edtTen_Xe);
                EditText edtLoai_Xe = view.findViewById(R.id.edtLoai_Xe);
                EditText edtMo_Ta = view.findViewById(R.id.edtMo_Ta);
                EditText edtAnh = view.findViewById(R.id.edtAnh);
                EditText edtGia_Thue = view.findViewById(R.id.edtGia_Thue);
                Button btnCapNhat= view.findViewById(R.id.btnCapNhat);

                //lấy thông tin bỏ vào trong phần cập nhật
                edtTen_Xe.setText(modelAdmin.getTenXe());
                edtLoai_Xe.setText(modelAdmin.getLoaiXe());
                edtMo_Ta.setText(modelAdmin.getMoTa());
                edtGia_Thue.setText(modelAdmin.getGiaThue());
                edtAnh.setText(modelAdmin.getAnhXe());

                btnCapNhat.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Map<String,Object> map = new HashMap<>();
                        map.put("tenXe",edtTen_Xe.getText().toString());
                        map.put("loaiXe",edtLoai_Xe.getText().toString());
                        map.put("moTa",edtMo_Ta.getText().toString());
                        map.put("giaThue",edtGia_Thue.getText().toString());
                        map.put("anhXe",edtAnh.getText().toString());

                        FirebaseDatabase.getInstance().getReference().child("Xe")
                                .child(getRef(position).getKey()).updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(Holder.tenXe.getContext(),"Cập nhật thành công", Toast.LENGTH_LONG).show();
                                    dialogPlus.dismiss();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(Holder.tenXe.getContext(),"Cập nhật thất bại", Toast.LENGTH_LONG).show();
                                    }
                                });
                    }
                });
            }
        });
        Holder.btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Holder.tenXe.getContext());
                builder.setTitle("Bạn muốn xóa?");
                builder.setMessage("Dữ liệu khi xóa sẽ không thể khôi phục");
                builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseDatabase.getInstance().getReference().child("Xe")
                                .child(getRef(position).getKey()).removeValue();
                    }
                });
                builder.setNegativeButton("Hủy bỏ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(Holder.tenXe.getContext(),"Hủy bỏ", Toast.LENGTH_LONG).show();
                    }
                });
                builder.show();
            }
        });
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_main_item,parent,false);
        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder{
        Button btnSua, btnXoa;
        ImageView iv1;
        TextView tenXe, moTa , loaiXe, giaThue;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            iv1 = (ImageView)itemView.findViewById(R.id.iv1);
            tenXe = (TextView)itemView.findViewById(R.id.txttenXe);
            loaiXe = (TextView)itemView.findViewById(R.id.txtloaiXe);
            moTa= (TextView)itemView.findViewById(R.id.txtMoTa);
            giaThue= (TextView)itemView.findViewById(R.id.txtGiaThue);
            btnSua=(Button)itemView.findViewById(R.id.btnSua);
            btnXoa=(Button)itemView.findViewById(R.id.btnXoa);
        }
    }
}
