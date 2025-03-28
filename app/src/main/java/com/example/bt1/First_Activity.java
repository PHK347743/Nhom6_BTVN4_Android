package com.example.bt1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class First_Activity extends AppCompatActivity {

    private EditText editHoten, editMssv, editLop, editSdt, editKehoach;
    private RadioGroup radioGroup;
    private CheckBox checkNhung, checkDT, checkVT;
    private Button buttonGui;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        setContentView(R.layout.first_activity);

        // Ánh xạ các thành phần UI
        editHoten = findViewById(R.id.editHoten);
        editMssv = findViewById(R.id.editMssv);
        editLop = findViewById(R.id.editLop);
        editSdt = findViewById(R.id.editSdt);
        editKehoach = findViewById(R.id.editkehoach);

        radioGroup = findViewById(R.id.radioGroup);

        checkNhung = findViewById(R.id.checkNhung);
        checkDT = findViewById(R.id.checkDT);
        checkVT = findViewById(R.id.checkVT);

        // Thêm listener cho các checkbox để đảm bảo chỉ chọn được một
        setupCheckBoxListeners();

        buttonGui = findViewById(R.id.buttonGui);

        // Xử lý sự kiện khi nhấn nút Gửi thông tin
        buttonGui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guiThongTin();
            }
        });
    }

    // Thiết lập listener cho checkbox để chỉ chọn được một
    private void setupCheckBoxListeners() {
        View.OnClickListener checkBoxListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox clickedCheckBox = (CheckBox) v;

                // Nếu checkbox được click là đã check, bỏ check các checkbox khác
                if (clickedCheckBox.isChecked()) {
                    if (clickedCheckBox == checkNhung) {
                        checkDT.setChecked(false);
                        checkVT.setChecked(false);
                    } else if (clickedCheckBox == checkDT) {
                        checkNhung.setChecked(false);
                        checkVT.setChecked(false);
                    } else if (clickedCheckBox == checkVT) {
                        checkNhung.setChecked(false);
                        checkDT.setChecked(false);
                    }
                }
            }
        };

        // Gán listener cho từng checkbox
        checkNhung.setOnClickListener(checkBoxListener);
        checkDT.setOnClickListener(checkBoxListener);
        checkVT.setOnClickListener(checkBoxListener);
    }

    private void guiThongTin() {
        // Lấy thông tin từ các trường nhập liệu
        String hoTen = editHoten.getText().toString().trim();
        String mssv = editMssv.getText().toString().trim();
        String lop = editLop.getText().toString().trim();
        String sdt = editSdt.getText().toString().trim();
        String keHoach = editKehoach.getText().toString().trim();

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
        intent.putExtra("KE_HOACH", keHoach);

        startActivity(intent);
    }
}
