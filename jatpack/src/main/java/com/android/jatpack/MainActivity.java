package com.android.jatpack;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.android.jatpack.room.RoomActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
    }

    public void click(View view) {
        switch (view.getId()) {
            case R.id.room:
                open(RoomActivity.class);
                break;
        }
    }

    private void open(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
    }
}
