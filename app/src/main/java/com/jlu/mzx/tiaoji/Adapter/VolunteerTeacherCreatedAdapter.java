package com.jlu.mzx.tiaoji.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jlu.mzx.tiaoji.R;
import com.jlu.mzx.tiaoji.activity.LookStudentInfo_teacher;
import com.jlu.mzx.tiaoji.tools.ReleaseVolunteer;

import java.util.List;

/**
 * Created by caosong on 2016/9/14.
 */
public class VolunteerTeacherCreatedAdapter extends BaseAdapter{
    private List<ReleaseVolunteer> data;
    private LayoutInflater layoutInflater;
    private Context context;
    public VolunteerTeacherCreatedAdapter(List<ReleaseVolunteer> data, Context context){
        this.data = data;
        this.context=context;
        layoutInflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = layoutInflater.inflate(R.layout.item_of_creat_volunteer,null);
        LinearLayout linearLayout;
        TextView school,college,subject,specialty,teacher,amount,area;
        linearLayout = (LinearLayout)view.findViewById(R.id.fouritem);
        school = (TextView)view.findViewById(R.id.school);
        college = (TextView)view.findViewById(R.id.college);
        subject = (TextView)view.findViewById(R.id.subject);
        specialty = (TextView)view.findViewById(R.id.specialty);
        teacher = (TextView)view.findViewById(R.id.teacher);
        amount = (TextView)view.findViewById(R.id.amount);
        area = (TextView)view.findViewById(R.id.area);
        school.setText(data.get(i).getSchool());
        college.setText(data.get(i).getCollege());
        subject.setText(data.get(i).getSubject());
        specialty.setText(data.get(i).getSpecialty());
        teacher.setText(data.get(i).getTeacher());
        amount.setText(data.get(i).getAmount());
        area.setText(data.get(i).getArea());
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,LookStudentInfo_teacher.class);
                context.startActivity(intent);
            }
        });

        return view;
    }
}
