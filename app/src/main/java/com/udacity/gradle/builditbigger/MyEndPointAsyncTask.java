package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.laurent_julien_nano_degree_project.my_joke_android_lib.My_Joke_A_Lib_MainActivity;
import com.udacity.gradle.builditbigger.backend.myApi.MyApi;

import java.io.IOException;

public class MyEndPointAsyncTask extends AsyncTask<Context, Void, String> {
    private static MyApi sMyApi = null;
    private Context mContext;
    private ProgressBar mProgressBar;

    public MyEndPointAsyncTask (Context context, ProgressBar progressBar) {
        mContext = context;
        mProgressBar = progressBar;
    }

    public MyEndPointAsyncTask () {
    }

    @Override
    protected String doInBackground (Context... contexts) {
        if (sMyApi == null) {
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                new AndroidJsonFactory(), null)
                .setRootUrl("http://10.0.2.2:8080/_ah/api/")
                .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                    @Override
                    public void initialize (AbstractGoogleClientRequest<?> request) {
                        request.setDisableGZipContent(true);
                    }
                });
            sMyApi = builder.build();
        }
        mContext = contexts[0];

        try {
            return sMyApi.sendAJoke().execute().getJokes();
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    @Override
    protected void onPreExecute () {
        super.onPreExecute();
        if (mProgressBar != null) {
            mProgressBar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onPostExecute (String result) {
        if (mProgressBar != null) {
            mProgressBar.setVisibility(View.GONE);
        }
        Intent intent = new Intent(mContext, My_Joke_A_Lib_MainActivity.class);
        intent.putExtra(My_Joke_A_Lib_MainActivity.MY_JOKE_A_LIB_INTENT_KEY, result);
        mContext.startActivity(intent);
    }
}
