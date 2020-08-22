package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.gbulan.jokedisplay.JokeActivity;
import com.udacity.gradle.builditbigger.databinding.FragmentMainBinding;


public class MainActivityFragment extends Fragment implements EndpointsAsyncTask.OnTaskComplete{

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentMainBinding binding = FragmentMainBinding.inflate(inflater, container, false);
        View rootView = binding.getRoot();

        binding.btnTellJoke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTask();
            }
        });
        return rootView;
    }

    private void startJokeActivity(String joke) {
        Intent intent = new Intent(getContext(), JokeActivity.class);
        intent.putExtra(JokeActivity.EXTRA_JOKE, joke);
        startActivity(intent);
    }

    private void startTask() {
        // Start a task to retrieve a joke
        new EndpointsAsyncTask(this).execute();
    }

    @Override
    public void onTaskComplete(String joke) {
        startJokeActivity(joke);
    }
}
