package com.example.java_demo;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.math.BigDecimal;
import java.util.HashMap;

import co.thepeer.sdk.ThePeer;
import co.thepeer.sdk.model.ThePeerTransaction;
import co.thepeer.sdk.ui.ThePeerResultListener;
import co.thepeer.sdk.utils.ThePeerCurrency;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        HashMap<String, String> meta = new HashMap<>();
        //initialize ThePeer SDK
        ThePeer thePeer =new ThePeer.Builder(
                this,
                new BigDecimal("100000.00"),
                ThePeerCurrency.NGN,
                getResources().getString(R.string.user_reference),
                new ThePeerResultListener() {

                    @Override
                    public void onSuccess(@NonNull ThePeerTransaction transaction) {
                        ((TextView) findViewById(R.id.resultText)).setText(transaction.toString());
                    }

                    @Override
                    public void onCancelled() {

                    }

                    @Override
                    public void onError(@NonNull Throwable error) {

                    }

                }).setMeta(meta)
                .build();

        ((Button) findViewById(R.id.btnSendMoney)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                thePeer.send();
            }
        });

    }


}