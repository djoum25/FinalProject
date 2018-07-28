package com.udacity.gradle.builditbigger.free;

import android.content.Intent;
import android.test.AndroidTestCase;
import android.test.mock.MockContext;

import com.udacity.gradle.builditbigger.MyEndPointAsyncTask;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class MainActivitySecondTest extends AndroidTestCase{

public void test() throws InterruptedException, ExecutionException, TimeoutException {

    MockContext mockContext = new MockContext() {
        @Override
        public String getPackageName() {
            return "com.udacity.gradle.builditbigger.free";
        }

        @Override
        public void startActivity(Intent intent) {
            //super.startActivity(intent);
        }
    };

    String result = new MyEndPointAsyncTask(mockContext, null)
            .execute(mockContext).get(20, TimeUnit.SECONDS);

    assertNotNull(result);
    assertFalse(result.length() == 0);

}


}
