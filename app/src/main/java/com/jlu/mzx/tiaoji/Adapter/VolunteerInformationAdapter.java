package com.jlu.mzx.tiaoji.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jlu.mzx.tiaoji.R;
import com.jlu.mzx.tiaoji.tools.Volunteer;

import java.util.List;


public class VolunteerInformationAdapter extends BaseAdapter {
    private  LayoutInflater mInflater;// 动态布局映射
    private  List<Volunteer> datas;

    public VolunteerInformationAdapter(Context context, List<Volunteer> datas) {
        this.mInflater = LayoutInflater.from(context);
        this.datas = datas;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int i) {
        return datas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = mInflater.inflate(R.layout.searchresult, null);
        TextView textView1, textView2, textView3;
        textView1 = (TextView) view.findViewById(R.id.teacher);
        textView2 = (TextView) view.findViewById(R.id.name);
        textView3 = (TextView) view.findViewById(R.id.school);
        textView1.setText(datas.get(i).getTeacher());
        textView2.setText(datas.get(i).getSpecialty());
        textView3.setText(datas.get(i).getSchool());
        return view;
    }
}
