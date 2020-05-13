package com.example.myEcom;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myEcom.networksync.CheckInternetConnection;
import com.example.myEcom.usersession.UserSession;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.mikepenz.crossfadedrawerlayout.view.CrossfadeDrawerLayout;
import com.mikepenz.materialdrawer.Drawer;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class Profile extends AppCompatActivity {

    private Drawer result;
    private CrossfadeDrawerLayout crossfadeDrawerLayout = null;
    private TextView tvemail,tvphone;

    private TextView namebutton;
    private CircleImageView primage;
    private TextView updateDetails;
    private LinearLayout addressview;


    private UserSession session;
    private HashMap<String,String> user;
    private String name,email,photo,mobile;
    private SliderLayout sliderShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        initialize();

        new CheckInternetConnection(this).checkConnection();

        getValues();

        inflateImageSlider();

    }

    private void initialize() {

        addressview = findViewById(R.id.addressview);
        primage=findViewById(R.id.profilepic);
        tvemail=findViewById(R.id.emailview);
        tvphone=findViewById(R.id.mobileview);
        namebutton=findViewById(R.id.name_button);
        updateDetails=findViewById(R.id.updatedetails);

        
        addressview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Profile.this,Wishlist.class));
            }
        });
    }


    private void getValues() {

        session = new UserSession(getApplicationContext());

        session.isLoggedIn();

        user = session.getUserDetails();

        name=user.get(UserSession.KEY_NAME);
        email=user.get(UserSession.KEY_EMAIL);
        mobile=user.get(UserSession.KEY_MOBiLE);

        tvemail.setText(email);
        tvphone.setText(mobile);
        namebutton.setText(name);

        Picasso.with(Profile.this).load(photo).into(primage);


    }

    private void inflateImageSlider() {

        sliderShow = findViewById(R.id.slider);

        ArrayList<String> sliderImages= new ArrayList<>();

        sliderImages.add("https://firebasestorage.googleapis.com/v0/b/myecommerceapplication-7507c.appspot.com/o/Robes%2Frobe.jpg?alt=media&token=739be40f-47ff-4e94-bb77-ee1442732fad");
        sliderImages.add("https://firebasestorage.googleapis.com/v0/b/myecommerceapplication-7507c.appspot.com/o/DressRosa%2F10.jpg?alt=media&token=e8459a4b-f81c-4cf7-8c59-d3fa2ead0844");
        sliderImages.add("https://firebasestorage.googleapis.com/v0/b/myecommerceapplication-7507c.appspot.com/o/DressRosa%2Fnv.jpg?alt=media&token=0273fed0-330c-485b-a002-de50dc28bb09");
        sliderImages.add("https://firebasestorage.googleapis.com/v0/b/myecommerceapplication-7507c.appspot.com/o/DressRosa%2Fp6.jpg?alt=media&token=a547e282-f77e-40d7-8d40-c7d0bb3e7ded");
        sliderImages.add("https://firebasestorage.googleapis.com/v0/b/myecommerceapplication-7507c.appspot.com/o/DressRosa%2Fnvv.jpg?alt=media&token=5128bb90-1bb2-41ab-a1be-55582984bbc7");
        sliderImages.add("https://firebasestorage.googleapis.com/v0/b/myecommerceapplication-7507c.appspot.com/o/DressRosa%2F1.jpg?alt=media&token=a85f7455-3d57-43b1-9767-704b9e588353");
        sliderImages.add("https://firebasestorage.googleapis.com/v0/b/myecommerceapplication-7507c.appspot.com/o/DressRosa%2F3.jpg?alt=media&token=b70e046b-06ca-49c6-8f4a-4241080c5efc");
        sliderImages.add("https://firebasestorage.googleapis.com/v0/b/myecommerceapplication-7507c.appspot.com/o/DressRosa%2F4.jpg?alt=media&token=7f5ebd36-f36e-4d41-8bcd-518e46bd7dba");
        for (String s:sliderImages){
            DefaultSliderView sliderView=new DefaultSliderView(this);
            sliderView.image(s);
            sliderShow.addSlider(sliderView);
        }

        sliderShow.setPresetIndicator(SliderLayout.PresetIndicators.Right_Bottom);

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void viewCart(View view) {
        startActivity(new Intent(Profile.this,Cart.class));
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        new CheckInternetConnection(this).checkConnection();
    }

    public void Notifications(View view) {
        startActivity(new Intent(Profile.this,NotificationActivity.class));
        finish();
    }

    @Override
    protected void onStop() {
        sliderShow.stopAutoCycle();
        super.onStop();

    }
}