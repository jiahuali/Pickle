package com.carverlee.pickle;

import android.util.Log;

import com.carverlee.pickle.annotation.Bean;

/**
 * @author carverLee
 * 2019/12/10 15:32
 */
@Bean
public class TestDaoImpl implements TestDao {
    private static final String TAG = "ljh:TestDaoImpl";

    @Override
    public void add() {
        Log.i(TAG, "add: ");
    }

    @Override
    public void delete() {
        Log.i(TAG, "delete: ");
    }
}
