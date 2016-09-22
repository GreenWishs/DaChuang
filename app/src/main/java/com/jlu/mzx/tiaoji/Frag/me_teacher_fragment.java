package com.jlu.mzx.tiaoji.Frag;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.jlu.mzx.tiaoji.R;
import com.jlu.mzx.tiaoji.activity.LoginActivity;
import com.jlu.mzx.tiaoji.activity.LookStudentPersonalInformation;
import com.jlu.mzx.tiaoji.activity.LookTeacherPersonalInformation;
import com.jlu.mzx.tiaoji.activity.LookVolunteersMyCreated;
import com.jlu.mzx.tiaoji.activity.StatusofMyapply;

/**
 * Created by mzx on 2016/8/25.
 */
public class me_teacher_fragment extends Fragment {
    TextView publishedzhiyuan;
    TextView setting;
    TextView recivednews;
    TextView newvolunteer;
    private Button outlogin;
    private View view;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.e("onCreateView", "onCreateView");
        if (view == null) {
            view = inflater.inflate(R.layout.me_teacher_fragment, container, false);
            outlogin = (Button) view.findViewById(R.id.button);
            publishedzhiyuan = (TextView) view.findViewById(R.id.publishedzhiyuan);
            setting = (TextView) view.findViewById(R.id.setting);
            recivednews =(TextView)view.findViewById(R.id.recivednews);
            newvolunteer =(TextView)view.findViewById(R.id.newvolunteer);
            /**
             * 设置查看老师所发出申请的状态的监听器
             */
            publishedzhiyuan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getContext(), StatusofMyapply.class);
                    startActivity(intent);
                }
            });
            /**
             * 接收消息监听器
             */
            recivednews.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
            /**
             * 新建志愿监听器
             */
            newvolunteer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), LookVolunteersMyCreated.class);
                    startActivity(intent);
                }
            });
            /**
             * 设置设置老师个人信息监听器
             */
            setting.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), LookTeacherPersonalInformation.class);
                    startActivity(intent);

                }
            });

            /**
             * 设置登出事件监听器
             */
            outlogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    SharedPreferences sp = getActivity().getSharedPreferences("app", Context.MODE_APPEND);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putBoolean("isdenglu", false);
                    editor.apply();
                    Intent intent = new Intent(getContext(), LoginActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                }
            });

        }

        return view;
    }
}
