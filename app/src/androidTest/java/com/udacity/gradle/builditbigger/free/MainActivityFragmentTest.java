package com.udacity.gradle.builditbigger.free;

import android.app.Application;
import android.support.test.filters.SmallTest;
import android.support.test.runner.AndroidJUnit4;
import android.test.AndroidTestCase;
import android.test.ApplicationTestCase;
import android.util.Log;

import com.udacity.gradle.builditbigger.MyEndPointAsyncTask;

import org.junit.runner.RunWith;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@RunWith(AndroidJUnit4.class)
public class MainActivityFragmentTest extends ApplicationTestCase<Application>
        implements MyEndPointAsyncTask.MyEndPointAsyncTaskListener {

    private static final String TAG = MainActivityFragmentTest.class.getSimpleName();
    private CountDownLatch signal;
    private String myJoke;

    public MainActivityFragmentTest(Class<Application> applicationClass) {
        super(applicationClass);
        Log.d(TAG, "MainActivityFragmentTest is called");
    }

    @SmallTest
    public void testForEmptyString() {
        Log.d(TAG, "testForEmptyString is called");
        try {
            signal = new CountDownLatch(1);
            new MyEndPointAsyncTask(getContext(), null).execute();
            signal.await(10, TimeUnit.SECONDS);
            assertNotNull(myJoke);
            assertFalse(myJoke.isEmpty());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onComplete(String response, Exception e) {
        Log.d(TAG, "onComplete: " + response);
        this.myJoke = response;
        signal.countDown();
    }
}

