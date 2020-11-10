package com.carverlee.pickle;

import android.util.Log;

import com.carverlee.pickle.annotation.Autowired;
import com.carverlee.pickle.annotation.Bean;
import com.carverlee.pickle.lib.InjectBridge;
import com.carverlee.pickle.lib.Injectable;
import com.carverlee.pickle.lib.PickleApi;

/**
 * @author carverLee
 * 2019/12/10 15:31
 */
@Bean
public class TestServiceImpl implements TestService, InjectBridge {
    private static final String TAG = "ljh:TestServiceImpl";
    @Autowired
    private TestDao testDao;

    public TestServiceImpl() {
        PickleApi.inject(this, TestDao.class);
    }

    @Override
    public void method1() {
        Log.i(TAG, "method1: ");
        testDao.add();
    }

    @Override
    public void method2() {
        Log.i(TAG, "method2: ");
    }

    @Override
    public void inject(Injectable... injection) {
        testDao = (TestDao) injection[0];
    }
}
