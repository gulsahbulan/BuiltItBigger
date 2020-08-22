package com.udacity.gradle.builditbigger;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import androidx.test.ext.junit.runners.AndroidJUnit4;


import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@RunWith(AndroidJUnit4.class)
public class EndpointsAsyncTaskTest {
    @Test
    public void jokeTest() {
        final CountDownLatch countDownLatch = new CountDownLatch(1);


        EndpointsAsyncTask endpointsAsyncTask = new EndpointsAsyncTask(new EndpointsAsyncTask.OnTaskComplete() {
            @Override
            public void onTaskComplete(String joke) {
                assertNotNull(joke);
                assertTrue(joke.length() > 0);
                countDownLatch.countDown();
            }
        });

        endpointsAsyncTask.execute();

        try {
            countDownLatch.await(30, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
