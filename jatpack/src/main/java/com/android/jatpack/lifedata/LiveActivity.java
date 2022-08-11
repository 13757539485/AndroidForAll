package com.android.jatpack.lifedata;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.android.jatpack.R;

public class LiveActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        getLifecycle().addObserver(new ActivityObserver());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_tag, new MainFragment());
        fragmentTransaction.commit();

        new Thread(new Runnable() {
            @Override
            public void run() {
                LiveDataBus.getInstance().with("test", String.class).postValue("数据");
            }
        }).start();
    }
}
