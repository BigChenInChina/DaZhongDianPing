package com.liuchen.dazhongdianping.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.liuchen.dazhongdianping.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment4 extends BaseFragment {


    @BindView(R.id.btn_fragment_skip)
    Button btn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment4, container, false);
        ButterKnife.bind(this,view);
        skip(btn);
        return view;
    }

}
