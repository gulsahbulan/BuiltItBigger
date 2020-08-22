package com.gbulan.jokedisplay;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.gbulan.jokedisplay.databinding.JokeDisplayActivityBinding;

public class JokeActivity extends AppCompatActivity {
    public static final String EXTRA_JOKE = "EXTRA_JOKE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        JokeDisplayActivityBinding binding = JokeDisplayActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String joke = null;
        Intent intent = getIntent();
        if(intent.hasExtra(JokeActivity.EXTRA_JOKE)) {
            joke = intent.getStringExtra(JokeActivity.EXTRA_JOKE);
        }
        binding.message.setText(joke);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            // When the up button in action bar is clicked, finish the JokeActivity.
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}