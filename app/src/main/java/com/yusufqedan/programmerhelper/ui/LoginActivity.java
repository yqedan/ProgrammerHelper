package com.yusufqedan.programmerhelper.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.RuntimeExecutionException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.yusufqedan.programmerhelper.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends BaseActivityNoActionBar implements View.OnClickListener {
    //private static final String TAG = LoginActivity.class.getSimpleName();
    @BindView(R.id.emailEditText)
    EditText mEmailEditText;
    @BindView(R.id.passwordEditText)
    EditText mPasswordEditText;
    @BindView(R.id.passwordLoginButton)
    Button mPasswordLoginButton;
    @BindView(R.id.registerButton)
    Button mRegisterButton;
    @BindView(R.id.resetPasswordButton)
    Button mResetPasswordButton;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private ProgressDialog mAuthProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

        try {
            mAuth = FirebaseAuth.getInstance();
        } catch (IllegalStateException e) { //need to catch this as Robolectric unit test will throw exception when the database access is not needed

        }

        mAuthListener = (firebaseAuth) -> {
            FirebaseUser user = firebaseAuth.getCurrentUser();
            if (user != null) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        };

        mPasswordLoginButton.setOnClickListener(this);
        mRegisterButton.setOnClickListener(this);
        mResetPasswordButton.setOnClickListener(this);

        createAuthProgressDialog();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mAuth != null) {
            mAuth.addAuthStateListener(mAuthListener);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    @Override
    public void onClick(View view) {
        if (view == mRegisterButton) {
            Intent intent = new Intent(LoginActivity.this, CreateAccountActivity.class);
            startActivity(intent);
        }
        if (view == mPasswordLoginButton) {
            loginWithPassword();
        }
        if (view == mResetPasswordButton) {
            String email = mEmailEditText.getText().toString().trim();
            if (email != null && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                confirmResetDialog(email);
            } else {
                if (email.equals("")) {
                    mEmailEditText.setError("Please enter your email");
                } else {
                    mEmailEditText.setError("Please enter a valid email");
                }
            }
        }
    }

    private void createAuthProgressDialog() {
        mAuthProgressDialog = new ProgressDialog(this);
        mAuthProgressDialog.setTitle("Loading...");
        mAuthProgressDialog.setMessage("Logging you in...");
        mAuthProgressDialog.setCancelable(false);
    }

    private void loginWithPassword() {
        String email = mEmailEditText.getText().toString().trim();
        String password = mPasswordEditText.getText().toString().trim();
        if (email != null && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() == false) {
            if (email.equals("")) {
                mEmailEditText.setError("Please enter your email");
            } else {
                mEmailEditText.setError("Please enter a valid email");
            }
            return;
        }
        if (password.equals("")) {
            mPasswordEditText.setError("Password cannot be blank");
            return;
        }
        mAuthProgressDialog.show();
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, (task) -> {
                mAuthProgressDialog.dismiss();
                if (!task.isSuccessful()) {
                    Toast.makeText(LoginActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                }
            }
        );
    }

    private void confirmResetDialog(final String email) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Reset password for " + email + " ?")
                .setPositiveButton("Yes", (dialog, which) -> resetPassword(email))
                .setNegativeButton("No", (dialog, which) -> dialog.cancel())
                .show();
    }

    private void resetPassword(String email) {
        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(this, (task) -> {
            try {
                task.getResult();
                Toast.makeText(LoginActivity.this, "Password reset email sent", Toast.LENGTH_SHORT).show();
            } catch (RuntimeExecutionException e) {
                Toast.makeText(LoginActivity.this, "No user found with that email", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
