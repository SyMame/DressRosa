package com.example.myEcom;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.myEcom.usersession.UserSession;

public class RegisterActivity extends AppCompatActivity {

    private FrameLayout frameLayout;
    public  static boolean onResetPasswordFragment = false;
    public static boolean setSignUpFragment = false;
    private UserSession session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        frameLayout = findViewById(R.id.register_framelayout);
        session= new UserSession(getApplicationContext());

        if (session.isLoggedIn()){
            Intent loginSuccess = new Intent(RegisterActivity.this, MainActivity.class);
            startActivity(loginSuccess);
            finish();
        }else {
            if (setSignUpFragment) {
                setSignUpFragment=false;
                setDefaultFragment(new SignUpFragment());
            } else {
                setDefaultFragment(new SignInFragment());
            }
        }

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (onResetPasswordFragment) {
                onResetPasswordFragment = false;
                setFragment(new SignInFragment());
                return false;
            }
        } else {

        }
        return super.onKeyDown(keyCode, event);

    }

    private void setDefaultFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(frameLayout.getId(), fragment);
        fragmentTransaction.commit();
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_from_left, R.anim.slideout_from_right);
        fragmentTransaction.replace(frameLayout.getId(), fragment);
        fragmentTransaction.commit();
    }


}
