package com.android.jatpack.lifedata;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import com.android.jatpack.R;

public class SecondActivity extends AppCompatActivity {

    private TextView mShowTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        mShowTextView = findViewById(R.id.show_text);
        LiveDataBus.getInstance().with("test", String.class)
                .observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
//                mShowTextView.setText(s);
                Log.d("yuli", "Second onChanged: " + s);
            }
        });
        try {
            DU du = new DU("qq1337037310");
            DU des = new DU(du.decrypt("18403cf257a48178"));
            String ds = des.decrypt("c56c69224a33b2d18df51f9a11830777");
            Log.d("yuli", "onCreate: " + ds);
            mShowTextView.setText(ds);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
