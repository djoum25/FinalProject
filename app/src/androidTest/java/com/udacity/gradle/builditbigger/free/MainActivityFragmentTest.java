package com.udacity.gradle.builditbigger.free;

import android.test.AndroidTestCase;
import android.util.Log;

import com.udacity.gradle.builditbigger.MyEndPointAsynckTask;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;


/*
    Credit goes to - Chiu-Ki Chan - from Lynda.com in Effective Android Testing for Mobile Developers
 */

public class MainActivityFragmentTest extends AndroidTestCase {

    private static final String TAG = MainActivityFragmentTest.class.getSimpleName();

    public void test() throws InterruptedException, ExecutionException, TimeoutException {

        String result = new MyEndPointAsynckTask(null, null)
                .execute(getContext()).get(20, TimeUnit.SECONDS);

        Log.d(TAG, "test " + result);

        assertNotNull(result);
        assertFalse(result.length() == 0);

    }

}
