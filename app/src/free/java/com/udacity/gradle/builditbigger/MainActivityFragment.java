package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.gbulan.jokedisplay.JokeActivity;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;
import com.udacity.gradle.builditbigger.databinding.FragmentMainBinding;


import java.util.Collections;
import java.util.List;


public class MainActivityFragment extends Fragment implements EndpointsAsyncTask.OnTaskComplete{
    private InterstitialAd mInterstitialAd;
    private FragmentMainBinding binding;
    private String mJoke;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMainBinding.inflate(inflater, container, false);
        View rootView = binding.getRoot();
        setupBannerAd();
        setupInterstitialAd();
        setupTellJokeButton();
        return rootView;
    }

    private void startJokeActivity() {
        Intent intent = new Intent(getContext(), JokeActivity.class);
        intent.putExtra(JokeActivity.EXTRA_JOKE, mJoke);
        startActivity(intent);
    }

    private void startTask() {
        // Start a task to retrieve a joke
        new EndpointsAsyncTask(this).execute();
    }

    @Override
    public void onTaskComplete(String joke) {
        mJoke =  joke;
    }

    private void setupBannerAd() {
        List<String> testDeviceIds = Collections.singletonList(AdRequest.DEVICE_ID_EMULATOR);

        RequestConfiguration requestConfiguration = new RequestConfiguration.Builder()
                .setTestDeviceIds(testDeviceIds)
                .build();

        MobileAds.setRequestConfiguration(requestConfiguration);
        MobileAds.initialize(getContext());
        binding.adView.loadAd(new AdRequest.Builder().build());
    }

    private void setupInterstitialAd() {
        mInterstitialAd = new InterstitialAd(requireContext());
        mInterstitialAd.setAdUnitId(getString(R.string.interstitial_ad_unit_id));
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                startJokeActivity();
                // Load the next interstitial.
                mInterstitialAd.loadAd(new AdRequest.Builder().build());

            }

            @Override
            public void onAdFailedToLoad(LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
            }

        });
    }

    private void setupTellJokeButton() {
        binding.btnTellJoke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTask();
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                } else {
                    startJokeActivity();
                }
            }
        });
    }
}
