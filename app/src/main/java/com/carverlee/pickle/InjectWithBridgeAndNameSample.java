package com.carverlee.pickle;

import android.util.Log;

import com.carverlee.pickle.lib.KeyClass;
import com.carverlee.pickle.lib.PickleApi;

public class InjectWithBridgeAndNameSample {
    private TestService testService;

    private TestDao testDao;

    private TestDao testDao1;

    private TestDao testDao2;

    private static final String TAG = InjectWithBridgeAndNameSample.class.getCanonicalName();

    InjectWithBridgeAndNameSample() {
        //inject without implantation name
        PickleApi.inject((injection) -> {
                    testService = (TestService) injection[0];
                    testDao1 = (TestDao) injection[1];
                    testDao2 = (TestDao) injection[2];
                    testDao = (TestDao) injection[3];
                }, new KeyClass("", TestService.class),
                new KeyClass("testDao1", TestDao.class),
                new KeyClass("testDao2", TestDao.class),
                new KeyClass("", TestDao.class));

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
