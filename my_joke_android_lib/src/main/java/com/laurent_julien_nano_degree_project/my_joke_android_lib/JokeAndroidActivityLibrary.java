package com.laurent_julien_nano_degree_project.my_joke_android_lib;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class JokeAndroidActivityLibrary extends AppCompatActivity {

    public static final String MY_JOKE_A_LIB_INTENT_KEY = "my_joke_a_lib_intent_key";
    private static final String TAG = "TEST";

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my__joke__a__lib__main);
        TextView jokeTxt = findViewById(R.id.joke);

        Intent intent = getIntent();
        if (intent != null) {
            String joke = intent.getStringExtra(MY_JOKE_A_LIB_INTENT_KEY);
            jokeTxt.setText(joke);
        }
    }

}
