package com.android.androidforall;

import android.app.ListActivity;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class Main2Activity extends ListActivity {

    private ListView mListView;
    private ArrayAdapter<String> mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        mListView = getListView();
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("111");
        arrayList.add("222");
        arrayList.add("333");
        arrayList.add("444");
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1
        , arrayList);
        mListView.setAdapter(mAdapter);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.clear();
                    }
                });
            }
        }, 3000);
    }
}
