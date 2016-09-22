package com.jlu.mzx.tiaoji.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by caosong on 2016/9/17.
 */
public class StudentWantMeAdapter extends BaseAdapter{
    private  LayoutInflater layoutInflater;
    private List data;

    public StudentWantMeAdapter(Context context, List data) {
        this.layoutInflater = LayoutInflater.from(context);
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
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
