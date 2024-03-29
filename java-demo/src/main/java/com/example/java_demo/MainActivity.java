package com.example.java_demo;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.math.BigDecimal;
import java.util.HashMap;

import co.thepeer.sdk.Thepeer;
import co.thepeer.sdk.model.ThepeerConfig;
import co.thepeer.sdk.ui.ThepeerResultListener;
import co.thepeer.sdk.utils.ThepeerCurrency;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        HashMap<String, String> meta = new HashMap<>();
        // initialize Thepeer SDK
        Thepeer thepeer = new Thepeer.Initiate(
                getResources().getString(R.string.user_reference),
                this,
                new ThepeerResultListener() {
                    @Override
                    public void onSuccess(@NonNull String transaction) {
                        ((TextView) findViewById(R.id.resultText)).setText(transaction.toString());
                    }

                    @Override
                    public void onCancelled() {

                    }

                    @Override
                    public void onError(@NonNull Throwable error) {

                    }

                })
                .build();

        ((Button) findViewById(R.id.btnSendMoney)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                thepeer.send(new ThepeerConfig(new BigDecimal("100000"), ThepeerCurrency.NGN, meta));
            }
        });

    }


}
