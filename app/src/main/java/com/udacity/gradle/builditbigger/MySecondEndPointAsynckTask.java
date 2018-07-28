package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.udacity.gradle.builditbigger.backend.myApi.MyApi;

import java.io.IOException;

public class MySecondEndPointAsynckTask extends AsyncTask<Context, Void, String>{
    private static final String TAG = MySecondEndPointAsynckTask.class.getSimpleName();
    private static  MyApi myApi = null;
    private Context mContext;
    private CallBack mCallback;
    private ProgressBar mProgressBar;

    // TODO: 7/27/18 remember to remove this
    public MySecondEndPointAsynckTask(MainActivityFragment mCallback, ProgressBar progressBar) {
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
                        public void initialize (AbstractGoogleClientRequest<?> request) {
                            request.setDisableGZipContent(true);
                        }
                    });
            myApi = builder.build();
        }
        mContext=contexts[0];

        try {
            return myApi.sendAJoke().execute().getJokes();
        } catch (IOException e) {
            e.printStackTrace();
            Log.d(TAG, "doInBackground " + e.getMessage());
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
        if (mCallback != null && !s.isEmpty()){
            mCallback.onCallBack(s);
        }

        if (mProgressBar != null) {
            mProgressBar.setVisibility(View.GONE);
        }
    }

    public interface CallBack{
        void onCallBack(String result);
    }
}
