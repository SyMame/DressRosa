package com.example.myEcom.usersession;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.myEcom.RegisterActivity;

import java.util.HashMap;



public class UserSession {

    SharedPreferences pref;

    SharedPreferences.Editor editor;


    Context context;

    int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "UserSessionPref";

    public static final String FIRST_TIME = "firsttime";

    private static final String IS_LOGIN = "IsLoggedIn";

    public static final String KEY_NAME = "name";

    public static final String KEY_EMAIL = "email";

    public static final String KEY_MOBiLE = "mobile";

    public static final String KEY_PHOTO = "photo";

    public static final String KEY_CART = "cartvalue";

    public static final String KEY_WISHLIST = "wishlistvalue";

    public static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";

    public UserSession(Context context){
        this.context = context;
        pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }


    public void createLoginSession(String name, String email, String mobile, String photo){
        editor.putBoolean(IS_LOGIN, true);

        editor.putString(KEY_NAME, name);

        editor.putString(KEY_EMAIL, email);

        editor.putString(KEY_MOBiLE, mobile);

        editor.putString(KEY_PHOTO, photo);

        editor.commit();
    }









    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<>();
        user.put(KEY_NAME, pref.getString(KEY_NAME, null));

        user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));

        user.put(KEY_MOBiLE, pref.getString(KEY_MOBiLE, null));

        user.put(KEY_PHOTO, pref.getString(KEY_PHOTO, null)) ;

        return user;
    }


    public void logoutUser(){
        editor.putBoolean(IS_LOGIN,false);
        editor.commit();

        Intent i = new Intent(context, RegisterActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        context.startActivity(i);
    }


    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }

    public int getCartValue(){
        return pref.getInt(KEY_CART,0);
    }

    public int getWishlistValue(){
        return pref.getInt(KEY_WISHLIST,0);
    }

    public Boolean  getFirstTime() {
        return pref.getBoolean(FIRST_TIME, true);
    }

    public void setFirstTime(Boolean n){
        editor.putBoolean(FIRST_TIME,n);
        editor.commit();
    }


    public void increaseCartValue(){
        int val = getCartValue()+1;
        editor.putInt(KEY_CART,val);
        editor.commit();
        Log.e("Cart Value PE", "Var value : "+val+"Cart Value :"+getCartValue()+" ");
    }

    public void increaseWishlistValue(){
        int val = getWishlistValue()+1;
        editor.putInt(KEY_WISHLIST,val);
        editor.commit();
        Log.e("Cart Value PE", "Var value : "+val+"Cart Value :"+getCartValue()+" ");
    }

    public void decreaseCartValue(){
        int val = getCartValue()-1;
        editor.putInt(KEY_CART,val);
        editor.commit();
        Log.e("Cart Value PE", "Var value : "+val+"Cart Value :"+getCartValue()+" ");
    }

    public void decreaseWishlistValue(){
        int val = getWishlistValue()-1;
        editor.putInt(KEY_WISHLIST,val);
        editor.commit();
        Log.e("Cart Value PE", "Var value : "+val+"Cart Value :"+getCartValue()+" ");
    }

    public void setCartValue(int count){
        editor.putInt(KEY_CART,count);
        editor.commit();
    }

    public void setWishlistValue(int count){
        editor.putInt(KEY_WISHLIST,count);
        editor.commit();
    }

    public void setFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.commit();
    }

    public boolean isFirstTimeLaunch() {
        return pref.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }
}