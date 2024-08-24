package com.example.tap_luyen_the_chat_tien_ich_v2.Views.Activity_Vinh;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tap_luyen_the_chat_tien_ich_v2.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.checkerframework.checker.nullness.qual.NonNull;

public class RegisterActivity1 extends AppCompatActivity {
    private EditText editTextEmail, editTextPassword, editTextConfirmPassword;
    private Button buttonRegister;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register1);

        // Khởi tạo Firebase
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        // Ánh xạ các thành phần giao diện
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword);
        buttonRegister = findViewById(R.id.buttonRegister);

        // Xử lý sự kiện đăng ký
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy giá trị từ các trường nhập liệu
                String email = editTextEmail.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();
                String confirmPassword = editTextConfirmPassword.getText().toString().trim();
                // Kiểm tra tính hợp lệ của dữ liệu
                if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                    Toast.makeText(RegisterActivity1.this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                } else if (!password.equals(confirmPassword)) {
                    Toast.makeText(RegisterActivity1.this, "Mật khẩu và mật khẩu xác nhận không khớp!", Toast.LENGTH_SHORT).show();
                } else {
                    // Gọi phương thức đăng ký người dùng
                    registerUser(email, password);
                }
            }
        });
    }
    private void registerUser(String email, String password) {
        // Đăng ký người dùng trên Firebase Authentication
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(RegisterActivity1.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Đăng ký thành công
                            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference();
                            userRef.child("user").child("email").setValue(email);
                            userRef.child("user").child("password").setValue(password);
                            Toast.makeText(RegisterActivity1.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                            //Hiện màn hình chính
                            Intent intent = new Intent(RegisterActivity1.this, InformationAskActivity.class);
                            startActivity(intent);
                        } else {
                            // Xảy ra lỗi trong quá trình đăng ký
                            String errorMessage = task.getException().getMessage();
                            Toast.makeText(RegisterActivity1.this, "Lỗi đăng ký: " + errorMessage, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
