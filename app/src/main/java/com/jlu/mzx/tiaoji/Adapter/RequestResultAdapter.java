package com.jlu.mzx.tiaoji.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.jlu.mzx.tiaoji.tools.Volunteer;

import java.util.List;

/**
 * Created by caosong on 2016/8/30.
 */
public class RequestResultAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private List<Volunteer> datas;
    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        return view;
    }
}
