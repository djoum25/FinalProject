package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.udacity.gradle.builditbigger.backend.myApi.MyApi;

import java.io.IOException;


/*
    Credit goes to - Chiu-Ki Chan - from Lynda.com in Effective Android Testing for Mobile Developers
 */

public class MyEndPointAsynckTask extends AsyncTask<Context, Void, String> {
    private static final String TAG = MyEndPointAsynckTask.class.getSimpleName();
    private static MyApi myApi = null;
    private CallBack mCallback;
    private ProgressBar mProgressBar;


    public MyEndPointAsynckTask(MainActivityFragment mCallback, ProgressBar progressBar) {
        this.mCallback = (CallBack) mCallback;
        this.mProgressBar = progressBar;
    }

    @Override
    protected String doInBackground(Context... contexts) {

        if (myApi == null) {
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    .setRootUrl("http://10.0.2.2:8080/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> request) {
                            request.setDisableGZipContent(true);
                        }
                    });
            myApi = builder.build();
        }

        try {
            return myApi.sendAJoke().execute().getJokes();
        } catch (IOException e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (mProgressBar != null) {
            mProgressBar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onPostExecute(String s) {
        if (mCallback != null && !s.isEmpty()) {
            mCallback.onCallBack(s);
        }

        if (mProgressBar != null) {
            mProgressBar.setVisibility(View.GONE);
        }
    }

    public interface CallBack {
        void onCallBack(String result);
    }
}
