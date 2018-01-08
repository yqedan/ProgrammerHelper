package com.yusufqedan.programmerhelper.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.yusufqedan.programmerhelper.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChangePasswordActivity extends BaseActivity implements View.OnClickListener {
    //private static final String TAG = ChangePasswordActivity.class.getSimpleName();
    @BindView(R.id.currentPasswordEditText)
    EditText mCurrentPassword;
    @BindView(R.id.newPasswordEditText)
    EditText mNewPassword;
    @BindView(R.id.confirmNewPasswordEditText)
    EditText mConfirmNewPassword;
    @BindView(R.id.newPasswordSubmitButton)
    Button mNewPasswordSubmitButton;

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        ButterKnife.bind(this);
        setTitle("");
        mNewPasswordSubmitButton.setOnClickListener(this);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
    }

    private boolean isValidPassword(String password, String confirmPassword) {
        if (password.length() < 6) {
            mNewPassword.setError("Please create a password containing at least 6 characters");
            return false;
        } else if (!password.equals(confirmPassword)) {
            mNewPassword.setError("Passwords do not match");
            return false;
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        String currentPassword = mCurrentPassword.getText().toString().trim();
        final String newPassword = mNewPassword.getText().toString().trim();
        String confirmNewPassword = mConfirmNewPassword.getText().toString().trim();
        String email = mUser.getEmail();
        if (currentPassword.equals("")) {
            mCurrentPassword.setError("Password cannot be blank");
            return;
        }

        if (isValidPassword(newPassword, confirmNewPassword)) {
            mAuth.signInWithEmailAndPassword(email, currentPassword).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (!task.isSuccessful()) {
                        Toast.makeText(ChangePasswordActivity.this, "Current Password is incorrect.", Toast.LENGTH_SHORT).show();
                    } else {
                        mUser.updatePassword(newPassword).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(ChangePasswordActivity.this, "Password updated", Toast.LENGTH_SHORT).show();
                                    mCurrentPassword.setText("");
                                    mNewPassword.setText("");
                                    mConfirmNewPassword.setText("");
                                } else {
                                    Toast.makeText(ChangePasswordActivity.this, "Something went wrong try again later", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }
            });
        }
    }
}
