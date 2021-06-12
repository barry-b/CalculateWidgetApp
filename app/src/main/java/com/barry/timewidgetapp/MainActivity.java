package com.barry.timewidgetapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Test test = new Test();
        test.method();
    }

    static class Test{

        private static final String TAG = Test.class.getSimpleName();

        void method(){
            Log.d(TAG, "method: ===" + Test.class.getName());
        }
    }
}