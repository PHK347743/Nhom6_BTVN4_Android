package com.example.bt1;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class First_Activity extends AppCompatActivity {

    private EditText editHoten, editMssv, editLop, editSdt;
    private RadioGroup radioGroup;
    private CheckBox checkNhung, checkDT, checkVT;
    private Button buttonGui, buttonCall, buttonSms, buttonCamera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        setContentView(R.layout.first_activity);

        // Ánh xạ UI
        editHoten = findViewById(R.id.editHoten);
        editMssv = findViewById(R.id.editMssv);
        editLop = findViewById(R.id.editLop);
        editSdt = findViewById(R.id.editSdt);

        radioGroup = findViewById(R.id.radioGroup);
        checkNhung = findViewById(R.id.checkNhung);
        checkDT = findViewById(R.id.checkDT);
        checkVT = findViewById(R.id.checkVT);

        buttonGui = findViewById(R.id.buttonGui);
        buttonCall = findViewById(R.id.buttonCall);
        buttonSms = findViewById(R.id.buttonSms);
        buttonCamera = findViewById(R.id.buttonCamera);

        setupCheckBoxListeners();

        // Gửi thông tin
        buttonGui.setOnClickListener(v -> guiThongTin());

        // Mở giao diện gọi điện
        buttonCall.setOnClickListener(v -> moGiaoDienGoi());

        // Mở giao diện gửi SMS
        buttonSms.setOnClickListener(v -> moGiaoDienSms());

        // Mở camera
        buttonCamera.setOnClickListener(v -> moCamera());
    }

    // Mở giao diện gọi điện với số điện thoại đã nhập
    private void moGiaoDienGoi() {
        String sdt = editSdt.getText().toString().trim();
        if (sdt.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập số điện thoại!", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + sdt));
        startActivity(intent);
    }

    // Mở giao diện SMS với nội dung tin nhắn là thông tin đã nhập
    // Mở giao diện nhắn tin có sẵn của điện thoại
    private void moGiaoDienSms() {
        String sdt = editSdt.getText().toString().trim();
        if (sdt.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập số điện thoại!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Tạo nội dung tin nhắn
        String hoTen = editHoten.getText().toString().trim();
        String mssv = editMssv.getText().toString().trim();
        String lop = editLop.getText().toString().trim();

        if (hoTen.isEmpty() || mssv.isEmpty() || lop.isEmpty()) {
            Toast.makeText(this, "Vui lòng điền đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
            return;
        }

        String tinNhan = "Thông tin sinh viên:\n"
                + "Họ tên: " + hoTen + "\n"
                + "MSSV: " + mssv + "\n"
                + "Lớp: " + lop;

        // Tạo Intent gửi tin nhắn
        Uri uri = Uri.parse("smsto:" + sdt);
        Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
        intent.setPackage("com.google.android.apps.messaging"); // Chỉ mở Google Messages
        intent.putExtra("sms_body", tinNhan);

        try {
            startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(this, "Không thể mở ứng dụng Messages!", Toast.LENGTH_SHORT).show();
        }
    }


    // Mở giao diện camera để chụp ảnh
    private void moCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivity(intent);
    }

    // Gửi thông tin sang màn hình thứ hai
    private void guiThongTin() {
        // Lấy thông tin từ các trường nhập liệu
        String hoTen = editHoten.getText().toString().trim();
        String mssv = editMssv.getText().toString().trim();
        String lop = editLop.getText().toString().trim();
        String sdt = editSdt.getText().toString().trim();

        // Kiểm tra thông tin bắt buộc
        if (hoTen.isEmpty() || mssv.isEmpty() || lop.isEmpty() || sdt.isEmpty()) {
            Toast.makeText(this, "Vui lòng điền đầy đủ thông tin cơ bản", Toast.LENGTH_SHORT).show();
            return;
        }

        // Kiểm tra năm học
        int selectedRadioId = radioGroup.getCheckedRadioButtonId();
        if (selectedRadioId == -1) {
            Toast.makeText(this, "Vui lòng chọn năm học", Toast.LENGTH_SHORT).show();
            return;
        }

        // Lấy năm học được chọn
        RadioButton selectedRadioButton = findViewById(selectedRadioId);
        String namHoc = selectedRadioButton.getText().toString();

        // Kiểm tra và lấy chuyên ngành được chọn
        String chuyenNganh = "";
        if (checkNhung.isChecked()) {
            chuyenNganh = checkNhung.getText().toString();
        } else if (checkDT.isChecked()) {
            chuyenNganh = checkDT.getText().toString();
        } else if (checkVT.isChecked()) {
            chuyenNganh = checkVT.getText().toString();
        }

        if (chuyenNganh.isEmpty()) {
            Toast.makeText(this, "Vui lòng chọn một chuyên ngành", Toast.LENGTH_SHORT).show();
            return;
        }

        // Tạo Intent để chuyển dữ liệu sang màn hình thứ hai
        Intent intent = new Intent(First_Activity.this, Secon_Activity.class);

        // Đưa từng thông tin vào Intent
        intent.putExtra("HO_TEN", hoTen);
        intent.putExtra("MSSV", mssv);
        intent.putExtra("LOP", lop);
        intent.putExtra("SDT", sdt);
        intent.putExtra("NAM_HOC", namHoc);
        intent.putExtra("CHUYEN_NGANH", chuyenNganh);

        startActivity(intent);
    }

    private void setupCheckBoxListeners() {
        View.OnClickListener checkBoxListener = v -> {
            CheckBox clickedCheckBox = (CheckBox) v;
            if (clickedCheckBox.isChecked()) {
                checkNhung.setChecked(clickedCheckBox == checkNhung);
                checkDT.setChecked(clickedCheckBox == checkDT);
                checkVT.setChecked(clickedCheckBox == checkVT);
            }
        };

        checkNhung.setOnClickListener(checkBoxListener);
        checkDT.setOnClickListener(checkBoxListener);
        checkVT.setOnClickListener(checkBoxListener);
    }
}
