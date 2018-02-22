package com.yusufqedan.programmerhelper;

import android.content.Intent;

import com.yusufqedan.programmerhelper.ui.CreateAccountActivity;
import com.yusufqedan.programmerhelper.ui.LoginActivity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.shadows.ShadowApplication;

import static junit.framework.Assert.assertEquals;

@RunWith(RobolectricTestRunner.class)
public class LoginActivityUnitTest {

    @Test
    public void clickingSignUpShouldOpenCreateAccountActivity(){
        LoginActivity activity = Robolectric.setupActivity(LoginActivity.class);
        activity.findViewById(R.id.registerButton).performClick();

        Intent expectedIntent = new Intent(activity, CreateAccountActivity.class);
        Intent actual = ShadowApplication.getInstance().getNextStartedActivity();
        assertEquals(expectedIntent.getComponent(), actual.getComponent());
    }
}
