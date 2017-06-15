package com.liuchen.dazhongdianping.Fragment;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.View;

import com.liuchen.dazhongdianping.UI.MainActivity;

/**
 * Created by Administrator on 2017/6/15 0015.
 */

public class BaseFragment extends Fragment {
    public void skip(View view){

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
    }
}
