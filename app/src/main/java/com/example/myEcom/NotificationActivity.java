package com.example.myEcom;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.activeandroid.query.Select;
import com.airbnb.lottie.LottieAnimationView;
import com.example.myEcom.adapters.NotificationPojo;
import com.example.myEcom.db.Notification;
import com.example.myEcom.networksync.CheckInternetConnection;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

public class NotificationActivity extends AppCompatActivity {

    private ArrayList<NotificationPojo> listofnotif;
    private ListView listView;
    private LottieAnimationView emptycart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);


    }

    private List<Notification> getAll() {
        return new Select()
                .from(Notification.class)
                .limit(10)
                .execute();
    }

    private void showNotifications() {

        List<Notification> list=getAll();

        listofnotif = new ArrayList<>();


        for (int i=list.size()-1;i>=0;i--){

                listofnotif.add(new NotificationPojo(list.get(i).title,list.get(i).body));

        }

        if (!listofnotif.isEmpty()) {
            NotificationAdapter adapter = new NotificationAdapter(this, listofnotif);
            listView.setAdapter(adapter);
        } else {
            listView.setVisibility(View.INVISIBLE);
            emptycart.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void markAsRead(View view) {

        List<Notification> listtodel= new Select()
                .from(Notification.class)
                .limit(10)
                .execute();

        for (int i=listtodel.size()-1;i>=0;i--){
            Notification.delete(Notification.class,i);
        }
        Intent refresh = new Intent(this, NotificationActivity.class);
        startActivity(refresh);
        finish(); }

    @Override
    protected void onResume() {
        super.onResume();
        new CheckInternetConnection(this).checkConnection();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView cardimage;
        private TextView couponTitle;
        private TextView couponValidity;
        private TextView couponBody;
        private TextView prix;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            couponTitle = itemView.findViewById(R.id.coupon_title);
            couponValidity = itemView.findViewById(R.id.coupon_validity);
            couponBody = itemView.findViewById(R.id.coupon_body);
            prix = itemView.findViewById(R.id.prix);
            cardimage = itemView.findViewById(R.id.coupon_icon);

        }
    }}
