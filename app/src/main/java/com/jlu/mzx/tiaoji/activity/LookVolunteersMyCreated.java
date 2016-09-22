package com.jlu.mzx.tiaoji.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.jlu.mzx.tiaoji.Adapter.VolunteerTeacherCreatedAdapter;
import com.jlu.mzx.tiaoji.AppConfig;
import com.jlu.mzx.tiaoji.MyApplication;
import com.jlu.mzx.tiaoji.R;
import com.jlu.mzx.tiaoji.tools.ReleaseVolunteer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class LookVolunteersMyCreated extends AppCompatActivity {
    private VolunteerTeacherCreatedAdapter volunteerTeacherCreatedAdapter;
    private JSONObject releasevolunteer;
    private ListView volunteermycreated;
    private ImageButton adder;
    private List<ReleaseVolunteer> data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.look_volunteer_my_created);
        releasevolunteer = new JSONObject();
        data = new ArrayList<>();
        volunteerTeacherCreatedAdapter = new VolunteerTeacherCreatedAdapter(data,LookVolunteersMyCreated.this);
        volunteermycreated = (ListView)findViewById(R.id.volunteermycreated);
        adder = (ImageButton)findViewById(R.id.adder);
        volunteermycreated.setAdapter(volunteerTeacherCreatedAdapter);
        SharedPreferences sharedPreferences = getSharedPreferences("app",MODE_PRIVATE);
        try {
            releasevolunteer.put("username",sharedPreferences.getString("username",null));
            releasevolunteer.put("token",sharedPreferences.getString("token",null));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        /**
         * TODO 查看已创建的志愿
         */
        JsonRequest request = new JsonObjectRequest(com.android.volley.Request.Method.POST,
                AppConfig.SERVERADD + "/releasevolunteer", releasevolunteer, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(getBaseContext(),"访问成功",Toast.LENGTH_SHORT).show();
                ReleaseVolunteer releaseVolunteer = new ReleaseVolunteer();
                try {
                    JSONArray request = response.getJSONArray("request");
                    for (int i = 0; i < request.length(); i++ ){
                        releaseVolunteer.setAmount(response.getString("amount"));
                        releaseVolunteer.setArea(response.getString("area"));
                        releaseVolunteer.setCollege(response.getString("college"));
                        releaseVolunteer.setName(response.getString("name"));
                        releaseVolunteer.setRemark(response.getString("remark"));
                        releaseVolunteer.setSchool(response.getString("school"));
                        releaseVolunteer.setSpecialty(response.getString("specialty"));
                        releaseVolunteer.setSubject(response.getString("subject"));
                        releaseVolunteer.setTeacher(response.getString("teacher"));
                        releaseVolunteer.setReleaseuser(response.getString("releaseuser"));
                        data.add(releaseVolunteer);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getBaseContext(),"访问失败",Toast.LENGTH_SHORT).show();

            }
        });
        MyApplication.getmRequestQueue().add(request);


        adder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LookVolunteersMyCreated.this,CreatMyVolunteer.class);
                startActivity(intent);
            }
        });
    }
}
