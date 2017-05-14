package com.yusufqedan.programmerhelper.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.yusufqedan.programmerhelper.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ChangePasswordActivity extends AppCompatActivity implements View.OnClickListener {
    //private static final String TAG = ChangePasswordActivity.class.getSimpleName();
    @Bind(R.id.newPasswordEditText)
    EditText mNewPassword;
    @Bind(R.id.confirmNewPasswordEditText)
    EditText mConfirmNewPassword;
    @Bind(R.id.newPasswordSubmitButton)
    Button mNewPasswordSubmitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        ButterKnife.bind(this);
        setTitle("");
        mNewPasswordSubmitButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        //Log.d(TAG, "onClick: ");
    }
}
