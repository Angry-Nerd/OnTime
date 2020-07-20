package com.akshit.ontime.ui.auth;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.akshit.ontime.R;
import com.akshit.ontime.databinding.ActivityResetPasswordBinding;
import com.akshit.ontime.util.FirebaseUtil;

public class ResetPassword extends AppCompatActivity {

    private ActivityResetPasswordBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_reset_password);
        mBinding.btnResetPassword.setOnClickListener(v -> {
            final String email = mBinding.resetPasswordId.getText().toString().trim();
            if (TextUtils.isEmpty(email)) {
                Toast.makeText(getApplication(), "Enter your registered email id", Toast.LENGTH_LONG).show();
                return;
            }
            mBinding.resetPasswordId.setText("");
            mBinding.resetPasswordProgressBar.setVisibility(View.VISIBLE);

            FirebaseUtil.getAuth().sendPasswordResetEmail(email).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(ResetPassword.this, "We have sent a mail to your registered Id to reset your password!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(ResetPassword.this, "Failed to send reset email!", Toast.LENGTH_LONG).show();
                }
                mBinding.resetPasswordProgressBar.setVisibility(View.GONE);
            }).addOnFailureListener(e -> {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
                mBinding.resetPasswordProgressBar.setVisibility(View.GONE);
            });
        });
    }
}
