package com.udacity.gradle.builditbigger.free;

import android.util.Log;

import com.udacity.gradle.builditbigger.MyEndPointAsyncTask;

import java.util.concurrent.ExecutionException;

public class MainActivityFragmentTest {

    private static final String TAG = MainActivityFragmentTest.class.getSimpleName();

    public void testForEmpty () {
        Log.d(TAG, "testForEmpty start here");
        String test = null;
        MyEndPointAsyncTask task = new MyEndPointAsyncTask();
        task.execute();

        try {
            test = task.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }

}