package com.carverlee.pickle;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "ljh";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        InjectWithBridgeSample injectWithBridgeSample = new InjectWithBridgeSample();
        injectWithBridgeSample.test();

        InjectWithBridgeAndNameSample injectWithBridgeAndNameSample = new InjectWithBridgeAndNameSample();
        injectWithBridgeAndNameSample.test();

        InjectWithAutoWiredSample injectWithAutoWiredSample = new InjectWithAutoWiredSample();
        injectWithAutoWiredSample.test();
    }

}
