package com.example.myEcom;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.myEcom.usersession.UserSession;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.example.myEcom.RegisterActivity.onResetPasswordFragment;



public class SignInFragment extends Fragment {


    public SignInFragment() {

    }

    private TextView donthaveanaccount;
    private FrameLayout parentFrameLayout;
    private EditText email, password;
    private Button signinbtn;
    private ImageButton closebtn;
    private ProgressBar progressBar;
    private FirebaseAuth firebaseAuth;
    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+.[a-z]+";
    private TextView forgotpassword;
    private UserSession session;


    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference mDatabaseReference = database.getReference();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_in, container, false);

        session= new UserSession(getActivity().getApplicationContext());

        donthaveanaccount = view.findViewById(R.id.tv_dont_have_an_account);
        parentFrameLayout = getActivity().findViewById(R.id.register_framelayout);
        email = view.findViewById(R.id.sign_in_email);
        password = view.findViewById(R.id.sign_in_password);
        closebtn = view.findViewById(R.id.sign_in_close_btn);
        progressBar = view.findViewById(R.id.progressBar2);
        firebaseAuth = FirebaseAuth.getInstance();
        signinbtn = view.findViewById(R.id.sign_in_btn);
        forgotpassword = view.findViewById(R.id.sign_in_forgot_password);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        donthaveanaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(new SignUpFragment());
            }
        });

        forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onResetPasswordFragment = true;
                setFragment(new ResetPasswordFragment());
            }
        });

        closebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainIntent();
            }
        });
        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkInputs();
            }


            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkInputs();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        signinbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkEmailandPattern();
            }
        });

    }


    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_from_right, R.anim.slideout_from_left);
        fragmentTransaction.replace(parentFrameLayout.getId(), fragment);
        fragmentTransaction.commit();
    }

    private void checkInputs() {
        if (!TextUtils.isEmpty(email.getText())) {
            if (!TextUtils.isEmpty(password.getText())) {
                signinbtn.setEnabled(true);
                signinbtn.setTextColor(Color.rgb(0, 0, 0));
            } else {
                signinbtn.setEnabled(false);
                signinbtn.setTextColor(Color.argb(50, 0, 0, 0));

            }
        } else {
            signinbtn.setEnabled(false);
            signinbtn.setTextColor(Color.argb(50, 0, 0, 0));
        }
    }

    private void checkEmailandPattern() {
        if (email.getText().toString().matches(emailPattern)) {
            if (password.length() >=8) {
                progressBar.setVisibility(View.VISIBLE);
                signinbtn.setEnabled(false);
                signinbtn.setTextColor(Color.argb(50,0,0,0));
               // session.createLoginSession("abc",email.getText().toString(),"33650191815","");

                firebaseAuth.signInWithEmailAndPassword(email.getText().toString(),password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        Log.e("com.example.myEcom", "firebase result: "+task.getResult()); ;

                        if(task.isSuccessful()){

                            mainIntent();
                        } else{
                            progressBar.setVisibility(View.INVISIBLE);
                            signinbtn.setEnabled(true);
                            signinbtn.setTextColor(Color.rgb(0,0,0));
                            String error = task.getException().getMessage();
                            Toast.makeText(getActivity(),error,Toast.LENGTH_LONG).show();
                        }
                    }
                });
            } else {
                Toast.makeText(getActivity(),"Email ou mot de passe incorrect",Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(getActivity(),"Email ou mot de passe incorrect",Toast.LENGTH_LONG).show();
        }
    }
    private void mainIntent(){

        Intent loginSuccess = new Intent(getActivity(), MainActivity.class);
        startActivity(loginSuccess);
        getActivity().finish();
    }




}

