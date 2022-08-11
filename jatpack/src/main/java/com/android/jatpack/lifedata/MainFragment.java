package com.android.jatpack.lifedata;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.android.jatpack.R;

/**
 * @author 俞梨
 * @description:
 * @date :2021/9/12 19:46
 */
public class MainFragment extends Fragment {

    private NameViewModel mNameViewModel;
    private int i;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLifecycle().addObserver(new ActivityObserver());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View inflate = inflater.inflate(R.layout.fragment_main, container, false);
        final TextView showTextView = inflate.findViewById(R.id.show_text);
        showTextView.setText("");
        showTextView.setVisibility(View.GONE);
        final Button button = inflate.findViewById(R.id.btn_modify);
        mNameViewModel = new ViewModelProvider.NewInstanceFactory().create(NameViewModel.class);
        mNameViewModel = new ViewModelProvider(requireActivity(),
                new ViewModelProvider.NewInstanceFactory()).get(NameViewModel.class);
        Observer observer = new Observer<String>() {
            @Override
            public void onChanged(String s) {
                showTextView.setText(s);
            }
        };
        mNameViewModel.getCurrentName().observe(requireActivity(), observer);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String anotherName="jett"+(i++);
                mNameViewModel.getCurrentName().setValue(anotherName);
                startActivity(new Intent(getActivity(), SecondActivity.class));
            }
        });
        LiveDataBus.getInstance().with("test", String.class)
                .observe(requireActivity(), new Observer<String>() {
                    @Override
                    public void onChanged(String s) {
                        showTextView.setText(s);
                        Log.d("yuli", "onChanged: "+s);
                    }
                });
        return inflate;
    }
}
