package com.example.bt1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Secon_Activity extends AppCompatActivity {

    private TextView textViewThongTin;
    private Button buttonBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        setContentView(R.layout.secon_activity);

        textViewThongTin = findViewById(R.id.editTextTextMultiLine2);
        buttonBack = findViewById(R.id.button2);

        // Nhận dữ liệu từ Intent
        Intent intent = getIntent();
        if (intent != null) {
            String hoTen = intent.getStringExtra("HO_TEN");
            String mssv = intent.getStringExtra("MSSV");
            String lop = intent.getStringExtra("LOP");
            String sdt = intent.getStringExtra("SDT");
            String namHoc = intent.getStringExtra("NAM_HOC");
            String chuyenNganh = intent.getStringExtra("CHUYEN_NGANH");
            String keHoach = intent.getStringExtra("KE_HOACH");

            // Tạo chuỗi thông tin để hiển thị
            StringBuilder thongTin = new StringBuilder();
            thongTin.append("THÔNG TIN SINH VIÊN\n\n");
            thongTin.append("Họ và tên: ").append(hoTen).append("\n\n");
            thongTin.append("MSSV: ").append(mssv).append("\n\n");
            thongTin.append("Lớp: ").append(lop).append("\n\n");
            thongTin.append("Số điện thoại: ").append(sdt).append("\n\n");
            thongTin.append("Sinh viên: ").append(namHoc).append("\n\n");
            thongTin.append("Chuyên ngành: ").append(chuyenNganh).append("\n\n");

            if (keHoach != null && !keHoach.isEmpty()) {
                thongTin.append("Kế hoạch phát triển bản thân:\n").append(keHoach);
            } else {
                thongTin.append("Kế hoạch phát triển bản thân: Chưa có");
            }

            // Hiển thị thông tin
            textViewThongTin.setText(thongTin.toString());
        }

        // Xử lý sự kiện khi nhấn nút BACK
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });
    }

    // Nếu bạn muốn xử lý nút Back của thiết bị
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        // Quay lại màn hình trước đó
        finish();
    }
}
