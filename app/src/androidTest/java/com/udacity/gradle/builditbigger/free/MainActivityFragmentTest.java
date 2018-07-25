package com.udacity.gradle.builditbigger.free;

import android.support.test.runner.AndroidJUnit4;
import android.test.AndroidTestCase;

import com.udacity.gradle.builditbigger.MyEndPointAsyncTask;

import org.junit.runner.RunWith;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@RunWith(AndroidJUnit4.class)
public class MainActivityFragmentTest extends AndroidTestCase {

    private static final String TAG = MainActivityFragmentTest.class.getSimpleName();

    String reSultString = null;
    Exception mException = null;
    CountDownLatch signal = null;

    @Override
    protected void setUp () throws Exception {
        super.setUp();
        signal = new CountDownLatch(1);
    }

    @Override
    protected void tearDown () throws Exception {
        super.tearDown();
        signal.countDown();
    }

    public void testAsyncTask () throws InterruptedException {
        MyEndPointAsyncTask task = new MyEndPointAsyncTask(getContext(), null);
        task.setListener(new MyEndPointAsyncTask.MyEndPointAsyncTaskListener() {
            @Override
            public void onComplete (String response, Exception e) {
                reSultString = response;
                mException = e;
                signal.countDown();
            }
        }).execute();
        signal.await(10, TimeUnit.SECONDS);

        assertNotNull(reSultString);
    }

    //    public void testForEmpty () {
//        Log.d(TAG, "testForEmpty start here");
//        String test = null;
//        MyEndPointAsyncTask task = new MyEndPointAsyncTask(getContext(), null);
//        task.execute();
//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        try {
//            test = task.get();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }
//        Log.d(TAG, "Retrieved a non-empty string successfully: " + test);
//
//        assertNotNull(test);
//    }

}

