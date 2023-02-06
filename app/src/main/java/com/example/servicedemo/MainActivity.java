package com.example.servicedemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.servicedemo.databinding.ActivityMainBinding;

//There are two types of broadcast receivers in android - Internal & External.

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    /**
     * You can always put any custom string here. But for external broadcast receiver, it's always
     * advisable to use the package name before the action,
     * so that your broadcast don't get messed up for the broadcast sent by another app with the same action.
     **/
    public static final String ACTION_WAKE_UP_YODA = "com.example.servicedemo.ACTION_WAKE_UP_YODA";

    /**
     * This is the internal broadcast receiver example
     **/
    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //You'll get your data in the 'intent' object that's been sent through the broadcast here.

            if (intent.hasExtra("babyYoda")) {
                String word = intent.getStringExtra("babyYoda");
                Log.d("TAG", "onReceive: " + "Data :" + word);
                binding.txtBroadcast.setText(word);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        /**
         * register your broadcast receiver here with an intent filter with specific action
         **/
        registerReceiver(broadcastReceiver, new IntentFilter(ACTION_WAKE_UP_YODA));

        binding.btnSend.setOnClickListener(v -> {
            sendBroadcast(new Intent(ACTION_WAKE_UP_YODA));
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        /**
         * Don't forget to unregister the broadcast receiver in onDestroy() of the activity, fragment, service, etc.
         */
        unregisterReceiver(broadcastReceiver);
    }
}