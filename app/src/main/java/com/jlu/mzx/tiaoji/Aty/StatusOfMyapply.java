package com.jlu.mzx.tiaoji.Aty;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.jlu.mzx.tiaoji.Adapter.StatusofMyapplyAdapter;
import com.jlu.mzx.tiaoji.AppConfig;
import com.jlu.mzx.tiaoji.MyApplication;
import com.jlu.mzx.tiaoji.R;
import com.jlu.mzx.tiaoji.tools.Volunteer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by caosong on 2016/9/1.
 */
public class StatusofMyapply extends AppCompatActivity {
    StatusofMyapplyAdapter statusofMyapplyAdapter;
    int state;
    ListView listview;
    private List<Volunteer> datas;

/**
学生向老师发出的申请状态
*/
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.status_of_myapply_activity);
        datas = new ArrayList<>();
        listview = (ListView) findViewById(R.id.list);
        //TODO status 需要更改
        state = 0;
        statusofMyapplyAdapter = new StatusofMyapplyAdapter(this, datas, state);
        listview.setAdapter(statusofMyapplyAdapter);
        JSONObject jsonObject = new JSONObject();
        //TODO 向..服务器..请求学生申请老师的进度，以及老师的基本信息
        try {
            jsonObject.put("type","teacher");
            jsonObject.put("content","王健");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonRequest<JSONObject> request = new JsonObjectRequest(Request.Method.POST, AppConfig.SERVERADD + "/searchvolunteer", jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray json = response.getJSONArray("volunteer");

                    for (int i = 0 ; i < json.length() ; i++ ){
                        Volunteer vol = new Volunteer();
                        JSONObject tmp = json.optJSONObject(i);
                        vol.setTeacher(tmp.get("teacher").toString());
                        vol.setSchool(tmp.get("school").toString());
                        vol.setSpecialty(tmp.get("specialty").toString());
                        datas.add(vol);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                statusofMyapplyAdapter.notifyDataSetChanged();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        MyApplication.getmRequestQueue().add(request);
    }
}