package com.jlu.mzx.tiaoji.Frag;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.jlu.mzx.tiaoji.AppConfig;
import com.jlu.mzx.tiaoji.Aty.LoginActivity;
import com.jlu.mzx.tiaoji.Aty.StatusofMyapply;
import com.jlu.mzx.tiaoji.MyApplication;
import com.jlu.mzx.tiaoji.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by mzx on 2016/7/15.
 */
public class me_student_fragment extends Fragment {
    private Button outlogin;
    private View view = null;
    JSONObject jsonObject = new JSONObject();
    private Intent intent = null;
    String[] school_choose = null;
    TextView textView1 = null;//发布志愿

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.e("onCreateView", "onCreateView");
        if (view == null) {
            view = inflater.inflate(R.layout.me_fragment, container, false);
            outlogin = (Button) view.findViewById(R.id.button);
            textView1 = (TextView)view.findViewById(R.id.publishedzhiyuan) ;
            textView1.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    intent = new Intent(getContext(), StatusofMyapply.class);
                    startActivity(intent);
                }
            });

            outlogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    SharedPreferences sp = getActivity().getSharedPreferences("app", Context.MODE_APPEND);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putBoolean("isdenglu", false);
                    editor.commit();

                    Intent intent = new Intent(getContext(), LoginActivity.class);
                    startActivity(intent);
                    getActivity().finish();

                }
            });

        }

        return view;
    }
}
