package com.example.myEcom.prodcutscategory;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.myEcom.Cart;
import com.example.myEcom.IndividualProduct;
import com.example.myEcom.NotificationActivity;
import com.example.myEcom.R;
import com.example.myEcom.models.GenericProductModel;
import com.example.myEcom.networksync.CheckInternetConnection;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class Chemises extends AppCompatActivity {



    private RecyclerView mRecyclerView;
    private StaggeredGridLayoutManager mLayoutManager;
    private LottieAnimationView tv_no_item;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference mDatabaseReference = database.getReference();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cards);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        new CheckInternetConnection(this).checkConnection();

        mRecyclerView = findViewById(R.id.my_recycler_view);
        tv_no_item = findViewById(R.id.tv_no_cards);


        if (mRecyclerView != null) {
            mRecyclerView.setHasFixedSize(true);
        }
        mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);

        final FirebaseRecyclerAdapter<GenericProductModel, Cards.MovieViewHolder> adapter = new FirebaseRecyclerAdapter<GenericProductModel, Cards.MovieViewHolder>(
                GenericProductModel.class,
                R.layout.cards_cardview_layout,
                Cards.MovieViewHolder.class,
                mDatabaseReference.child("Products").child("Chemise").getRef()
        ) {
            @Override
            protected void populateViewHolder(final Cards.MovieViewHolder viewHolder, final GenericProductModel model, final int position) {
                if (tv_no_item.getVisibility() == View.VISIBLE) {
                    tv_no_item.setVisibility(View.GONE);
                }
                viewHolder.cardname.setText(model.getCardname());
                viewHolder.cardprice.setText(Float.toString(model.getCardprice())+"€");
                Picasso.with(Chemises.this).load(model.getCardimage()).into(viewHolder.cardimage);

                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Chemises.this, IndividualProduct.class);
                        intent.putExtra("product", getItem(position));
                        startActivity(intent);
                    }
                });
            }
        };


        mRecyclerView.setAdapter(adapter);

    }

    public void viewCart(View view) {
        startActivity(new Intent(Chemises.this, Cart.class));
        finish();
    }


    public static class MovieViewHolder extends RecyclerView.ViewHolder {

        TextView cardname;
        ImageView cardimage;
        TextView cardprice;

        View mView;

        public MovieViewHolder(View v) {
            super(v);
            mView = v;
            cardname = v.findViewById(R.id.cardcategory);
            cardimage = v.findViewById(R.id.cardimage);
            cardprice = v.findViewById(R.id.cardprice);
        }
    }

    public void Notifications(View view) {
        startActivity(new Intent(Chemises.this, NotificationActivity.class));
        finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();

        new CheckInternetConnection(this).checkConnection();
    }
}