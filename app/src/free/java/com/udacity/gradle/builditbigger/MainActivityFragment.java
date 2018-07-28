package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.laurent_julien_nano_degree_project.my_joke_android_lib.My_Joke_A_Lib_MainActivity;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements View.OnClickListener,
        MyEndPointAsynckTask.CallBack {
    public static final String TAG = MainActivityFragment.class.getSimpleName();
    private ProgressBar mBar;
    private InterstitialAd mInterstitialAd;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);

        AdView mAdView = (AdView) root.findViewById(R.id.adView);
        mBar = root.findViewById(R.id.progressBar2);
        mBar.setVisibility(View.GONE);
        Button jokeBtn = root.findViewById(R.id.joke_btn);
        jokeBtn.setOnClickListener(this);

        MobileAds.initialize(getContext(),
                getString(R.string.banner_add_unit_id));

        mInterstitialAd = new InterstitialAd(getContext());
        mInterstitialAd.setAdUnitId(getString(R.string.interstitial_add_id));
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        AdRequest request = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(request);

        return root;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.joke_btn:
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                }
                new MyEndPointAsynckTask(this, mBar).execute(getActivity());
                break;
        }
    }

    @Override
    public void onCallBack(String result) {
        Intent intent = new Intent(getActivity(), My_Joke_A_Lib_MainActivity.class);
        intent.putExtra(My_Joke_A_Lib_MainActivity.MY_JOKE_A_LIB_INTENT_KEY, result);
        startActivity(intent);
    }
}
