package com.jlu.mzx.tiaoji.Frag;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.jlu.mzx.tiaoji.activity.LoginActivity;
import com.jlu.mzx.tiaoji.activity.LookStudentPersonalInformation;
import com.jlu.mzx.tiaoji.activity.StatusofMyapply;
import com.jlu.mzx.tiaoji.R;

/**
 * Created by mzx on 2016/7/15.
 */
public class me_student_fragment extends Fragment {
    TextView textView1;//查看学生所发出申请的状态
    TextView textView2;//学生设置个人信息
    private Button outlogin;
    private View view;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.e("onCreateView", "onCreateView");
        if (view == null) {
            view = inflater.inflate(R.layout.me_student_fragment, container, false);
            outlogin = (Button) view.findViewById(R.id.button);
            textView1 = (TextView) view.findViewById(R.id.publishedzhiyuan);
            textView2 = (TextView) view.findViewById(R.id.setting);
            /**
             * 设置查看学生所发出申请的状态的监听器
             */
            textView1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getContext(), StatusofMyapply.class);
                    startActivity(intent);
                }
            });
            /**
             * 设置设置学生个人信息监听器
             */
            textView2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), LookStudentPersonalInformation.class);
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
