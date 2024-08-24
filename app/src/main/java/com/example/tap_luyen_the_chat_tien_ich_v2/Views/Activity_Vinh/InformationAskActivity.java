package com.example.tap_luyen_the_chat_tien_ich_v2.Views.Activity_Vinh;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tap_luyen_the_chat_tien_ich_v2.Models.User;
import com.example.tap_luyen_the_chat_tien_ich_v2.R;
import com.example.tap_luyen_the_chat_tien_ich_v2.Views.Activities.LoginActivity;
import com.example.tap_luyen_the_chat_tien_ich_v2.Views.Activities.MainActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class InformationAskActivity extends AppCompatActivity {
    private EditText editTextUsername;
    private Button buttonNext;
    private String gender;
    private NumberPicker ageNumberPicker;
    private DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_name);
        editTextUsername = findViewById(R.id.editTextUsername);
        buttonNext = findViewById(R.id.buttonNext);
        // Khởi tạo DatabaseReference
        databaseReference = FirebaseDatabase.getInstance().getReference();
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveUsername();
                ClickScreenAge();
            }
        });
    }
    public void saveUsername() {
        String username = editTextUsername.getText().toString().trim();
        // Lưu tên người dùng vào Firebase Database
        databaseReference.child("user").child("profile").child("username").setValue(username);
    }
    public void ClickScreenAge(){
        setContentView(R.layout.layout_age);
        ageNumberPicker = findViewById(R.id.ageNumberPicker);
        ageNumberPicker.setMinValue(13);
        ageNumberPicker.setMaxValue(50);
        ImageButton imgBack = findViewById(R.id.backAgeButton);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InformationAskActivity.this, InformationAskActivity.class);
                startActivity(intent);
            }
        });
        Button btnAge= findViewById(R.id.buttonNext);
        btnAge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int age = ageNumberPicker.getValue();
                databaseReference.child("user").child("profile").child("age").setValue(age);
                ClickScreenGener();
            }
        });
    }
    public void ClickScreenGener() {
        setContentView(R.layout.layout_gender);
        ImageButton Back = findViewById(R.id.backGdrButton);
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //quay lại màn hình tuổi
                ClickScreenAge();
            }
        });
        Button btnNam = findViewById(R.id.buttonNam);
        Button btnNu = findViewById(R.id.buttonNu);
        btnNam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gender = btnNam.getText().toString().trim();
                btnNam.setBackgroundColor(getColor(R.color.green));
                btnNam.setTextColor(getColor(R.color.white));
                btnNu.setSelected(false);
                btnNu.setTextColor(getColor(R.color.black));
                btnNu.setBackground(getDrawable(R.drawable.custom_button));
            }
        });
        btnNu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gender = btnNu.getText().toString().trim();
                btnNu.setBackgroundColor(getColor(R.color.green));
                btnNu.setTextColor(getColor(R.color.white));
                btnNam.setSelected(false);
                btnNam.setTextColor(getColor(R.color.black));
                btnNam.setBackground(getDrawable(R.drawable.custom_button));
                }
        });
        Button btnGener = findViewById(R.id.buttonNextGdr);
        btnGener.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference.child("user").child("profile").child("gender").setValue(gender);
                ClickSreenWeight();
            }
        });
    }
    public void ClickSreenWeight(){
        setContentView(R.layout.layout_weight);
        ImageButton Back = findViewById(R.id.backWghtButton);
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //quay lại màn hình giới tính
                ClickScreenGener();
            }
        });
        EditText editWeight = findViewById(R.id.editWeight);
        EditText editHight = findViewById(R.id.editHight);
        Button btnWght = findViewById(R.id.buttonNextWght);
        btnWght.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String weight= editWeight.getText().toString().trim();
                String hight= editHight.getText().toString().trim();
                if (weight.isEmpty() || hight.isEmpty()) {
                    Toast.makeText(InformationAskActivity.this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                } else {
                    int wght = Integer.parseInt(weight);
                    int hght =  Integer.parseInt(hight);
                    databaseReference.child("user").child("profile").child("weight").setValue(wght);
                    databaseReference.child("user").child("profile").child("hight").setValue(hght);
                    ClickSreenLevel();
                }
            }
        });
    }
    public void ClickSreenLevel(){
        setContentView(R.layout.layout_level);
        ImageButton Back = findViewById(R.id.backLvButton);
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //quay lại màn hình cân nặng
                ClickSreenWeight();
            }
        });
        SeekBar seekBarLevel = findViewById(R.id.seekBarLevel);
        final TextView textViewDescription = findViewById(R.id.textViewDescription);
        textViewDescription.setHint("Ít vận động: " +
                "Hầu như không tập thể dục");
        seekBarLevel.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                String description;
                switch (progress) {
                    case 0:
                        description = "Ít vận động: " +
                                "Hầu như không tập thể dục";
                        break;
                    case 1:
                        description = "Thỉnh thoảng: " +
                                "Thỉnh thoảng tập thể dục hoặc đi bộ";
                        break;
                    case 2:
                        description = "Thường xuyên: " +
                                "Dành ít nhất 1 giờ tập thể dục mỗi ngày";
                        break;
                    case 3:
                        description = "Tích cực: " +
                                "Tập thể dục hằng ngày và muốn có nhiều bài tập hơn";
                        break;
                    default:
                        description = "";
                        break;
                }
                textViewDescription.setText(description);
                //textViewDescription.setVisibility(View.VISIBLE);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Được gọi khi người dùng bắt đầu chạm vào thanh kéo
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Được gọi khi người dùng kết thúc chạm vào thanh kéo
            }
        });
        Button btnLevel = findViewById(R.id.buttonNextLevel);
        btnLevel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClickSreenGoal();
            }
        });
    }
    public void ClickSreenGoal(){
        setContentView(R.layout.layout_goal);
        ImageButton Back = findViewById(R.id.backGoalButton);
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //quay lại màn hình trước
                ClickSreenLevel();
            }
        });
        Button btnGC = findViewById(R.id.buttonGC);
        Button btnCB = findViewById(R.id.buttonCB);
        Button btnSB = findViewById(R.id.buttonSB);
        btnGC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnGC.setBackgroundColor(getColor(R.color.green));
                btnGC.setTextColor(getColor(R.color.white));
                btnCB.setSelected(false);
                btnCB.setTextColor(getColor(R.color.black));
                btnCB.setBackground(getDrawable(R.drawable.custom_button));
                btnSB.setSelected(false);
                btnSB.setTextColor(getColor(R.color.black));
                btnSB.setBackground(getDrawable(R.drawable.custom_button));
            }
        });
        btnCB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnCB.setBackgroundColor(getColor(R.color.green));
                btnCB.setTextColor(getColor(R.color.white));
                btnGC.setSelected(false);
                btnGC.setTextColor(getColor(R.color.black));
                btnGC.setBackground(getDrawable(R.drawable.custom_button));
                btnSB.setSelected(false);
                btnSB.setTextColor(getColor(R.color.black));
                btnSB.setBackground(getDrawable(R.drawable.custom_button));
            }
        });

        btnSB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnSB.setBackgroundColor(getColor(R.color.green));
                btnSB.setTextColor(getColor(R.color.white));
                btnCB.setSelected(false);
                btnCB.setTextColor(getColor(R.color.black));
                btnCB.setBackground(getDrawable(R.drawable.custom_button));
                btnGC.setSelected(false);
                btnGC.setTextColor(getColor(R.color.black));
                btnGC.setBackground(getDrawable(R.drawable.custom_button));
            }
        });
        Button btnGoal = findViewById(R.id.buttonNextGoal);
        btnGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClickSreenTime();
            }
        });
    }
    public void ClickSreenTime(){
        setContentView(R.layout.layout_time);
        ImageButton Back = findViewById(R.id.backTimeButton);
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //quay lại màn hình trước
                ClickSreenGoal();
            }
        });
        Button btn2 = findViewById(R.id.btnT2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn2.setBackgroundColor(getColor(R.color.green));
                btn2.setTextColor(getColor(R.color.white));
            }
        });
        Button btn3 = findViewById(R.id.btnT3);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn3.setBackgroundColor(getColor(R.color.green));
                btn3.setTextColor(getColor(R.color.white));
            }
        });
        Button btn4 = findViewById(R.id.btnT4);
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn4.setBackgroundColor(getColor(R.color.green));
                btn4.setTextColor(getColor(R.color.white));
            }
        });
        Button btn5 = findViewById(R.id.btnT5);
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn5.setBackgroundColor(getColor(R.color.green));
                btn5.setTextColor(getColor(R.color.white));
            }
        });
        Button btn6 = findViewById(R.id.btnT6);
        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn6.setBackgroundColor(getColor(R.color.green));
                btn6.setTextColor(getColor(R.color.white));
            }
        });
        Button btn7 = findViewById(R.id.btnT7);
        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn7.setBackgroundColor(getColor(R.color.green));
                btn7.setTextColor(getColor(R.color.white));
            }
        });
        Button btnCN = findViewById(R.id.btnCN);
        btnCN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnCN.setBackgroundColor(getColor(R.color.green));
                btnCN.setTextColor(getColor(R.color.white));
            }
        });
        Button btnStart = findViewById(R.id.buttonStart);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(InformationAskActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        TextView home = findViewById(R.id.txtViewHome);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Xử lý sự kiện khi người dùng nhấp vào liên kết
                home.setTextColor(getColor(R.color.black));
                Intent intent= new Intent(InformationAskActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
