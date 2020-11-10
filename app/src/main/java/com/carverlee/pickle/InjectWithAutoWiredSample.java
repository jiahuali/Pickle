package com.carverlee.pickle;

import android.util.Log;

import com.carverlee.pickle.annotation.Autowired;
import com.carverlee.pickle.lib.PickleApi;

public class InjectWithAutoWiredSample {

    @Autowired
    private TestService testService;

    @Autowired
    private TestDao testDao;

    @Autowired("testDao1")
    private TestDao testDao1;

    @Autowired("testDao2")
    private TestDao testDao2;

    private static final String TAG = InjectWithAutoWiredSample.class.getCanonicalName();

    InjectWithAutoWiredSample() {
        //inject with autoWired
        PickleApi.inject(this);
    }

    void test() {
        Log.i(TAG, "test: start");
        testService.method1();
        testDao.add();
        testDao1.add();
        testDao2.add();
        Log.i(TAG, "test: end");
    }
}
