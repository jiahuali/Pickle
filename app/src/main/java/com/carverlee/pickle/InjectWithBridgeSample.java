package com.carverlee.pickle;

import android.util.Log;

import com.carverlee.pickle.lib.PickleApi;

public class InjectWithBridgeSample {

    private TestService testService;

    private TestDao testDao;

    private static final String TAG = InjectWithBridgeSample.class.getCanonicalName();

    InjectWithBridgeSample() {
        //inject without implantation name
        PickleApi.inject((injection) -> {
            testService = (TestService) injection[0];
            testDao = (TestDao) injection[1];
        }, TestService.class, TestDao.class);
    }

    void test() {
        Log.i(TAG, "test: start");
        testService.method1();
        testDao.add();
        Log.i(TAG, "test: end");
    }
}
