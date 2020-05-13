package com.example.myEcom.notification;

import android.util.Log;

import com.example.myEcom.db.Notification;
import com.onesignal.OSNotification;
import com.onesignal.OneSignal;

import org.json.JSONObject;



public class MyNotificationReceivedHandler implements OneSignal.NotificationReceivedHandler {

    @Override
    public void notificationReceived(OSNotification notification) {
        JSONObject data = notification.payload.additionalData;

        String message = notification.payload.body;
        String header = notification.payload.title;


        if(message != null && header!=null) {
            Notification notificationmsg = new Notification();
            notificationmsg.title = header;
            notificationmsg.body = message;


            try{
                notificationmsg.save();
                Log.e("notification state :","notification saved !");
            }catch (Exception e){
                Log.e("error notification :",e.toString());
            }
        }

        String customKey;

        if (data != null) {

            customKey = data.optString("customkey", null);
            if (customKey != null)
                Log.i("OneSignalExample", "customkey set with value: " + customKey);
        }
    }

}
