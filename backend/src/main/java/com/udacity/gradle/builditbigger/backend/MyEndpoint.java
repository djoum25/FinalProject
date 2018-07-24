package com.udacity.gradle.builditbigger.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.laurent_julien_nano_degree_project.myjokejavalibrary.My_Joke_Java_Library_Java_Class;

import javax.inject.Named;

/**
 * An endpoint class we are exposing
 */
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

    /**
     * A simple endpoint method that takes a name and says Hi back
     */
    @ApiMethod(name = "sayHi")
    public MyBean sayHi (@Named("name") String name) {
        MyBean response = new MyBean();
        response.setData("Hi, " + name);

        return response;
    }

    @ApiMethod(name = "sendAJoke")
    public MyJokeToSendClass sendAJoke () {
        MyJokeToSendClass send = new MyJokeToSendClass();
        return send;
    }

    class MyJokeToSendClass {
        public String getJokes () {
            return new My_Joke_Java_Library_Java_Class().getJoke2();
        }
    }

}
