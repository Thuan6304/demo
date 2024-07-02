package com.example.home;

import java.io.Serializable;

public class ModelAdmin implements Serializable {
    String tenXe ,loaiXe, giaThue, moTa, anhXe ;

    public ModelAdmin(String tenXe, String loaiXe, String giaThue, String moTa, String anhXe) {
        this.tenXe = tenXe;
        this.loaiXe = loaiXe;
        this.giaThue = giaThue;
        this.moTa = moTa;
        this.anhXe = anhXe;
    }

    public String getTenXe() {
        return tenXe;
    }

    public void setTenXe(String tenXe) {
        this.tenXe = tenXe;
    }

    public String getLoaiXe() {
        return loaiXe;
    }

    public void setLoaiXe(String loaiXe) {
        this.loaiXe = loaiXe;
    }

    public String getGiaThue() {
        return giaThue;
    }

    public void setGiaThue(String giaThue) {
        this.giaThue = giaThue;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public String getAnhXe() {
        return anhXe;
    }

    public void setAnhXe(String anhXe) {
        this.anhXe = anhXe;
    }

    public ModelAdmin() {
    }


}
