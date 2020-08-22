package com.udacity.gradle.builditbigger.backend;

import com.gbulan.jokeprovider.JokeProvider;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;


/** An endpoint class we are exposing */
@Api(
        name = "myApi",
        version = "v1",
        namespace = @ApiNamespace(
                ownerDomain = "backend.builditbigger.gradle.udacity.com",
                ownerName = "backend.builditbigger.gradle.udacity.com",
                packagePath = ""
        )
)
public class MyEndpoint {

    /** A simple endpoint method that takes a name and says Hi back */
    @ApiMethod(name = "pullJokes")
    public MyBean pullJokes() {
        JokeProvider jokeProvider = new JokeProvider();
        String joke = jokeProvider.getJoke();
        MyBean response = new MyBean();
        response.setData(joke);
        return response;
    }

}
