package com.byb.vidio;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.byb.lazynetlibrary.logger.LazyLogger;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LazyLogger.i("tets");

    }
}
