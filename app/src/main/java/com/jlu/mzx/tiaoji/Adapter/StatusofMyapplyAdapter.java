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

/**
 * Created by caosong on 2016/9/1.
 */
public class StatusofMyapplyAdapter extends BaseAdapter {
    private List<Volunteer> datas;
    private LayoutInflater layoutInflater;
    public StatusofMyapplyAdapter(Context context, List<Volunteer> datas ) {
        this.datas = datas;

        this.layoutInflater = LayoutInflater.from(context);
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
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = layoutInflater.inflate(R.layout.item_of_status_of_myapply_activity, null);
        TextView textView1, textView2, textView3, textView4;
        textView1 = (TextView) view.findViewById(R.id.speciality);
        textView2 = (TextView) view.findViewById(R.id.school);
        textView3 = (TextView) view.findViewById(R.id.teacher);
        textView4 = (TextView) view.findViewById(R.id.status);
        textView1.setText(datas.get(i).getSpecialty());
        textView2.setText(datas.get(i).getSchool());
        textView3.setText(datas.get(i).getTeacher());
        int status=datas.get(i).getRequeststatus();
        if (status == 0) {
            textView4.setText("处理中");
        } else if (status == 1) {
            textView4.setText("通过");
        } else if (status == 2) {
            textView4.setText("拒绝");
        } else {
            textView4.setText("处理异常");
        }
        return view;
    }
}
