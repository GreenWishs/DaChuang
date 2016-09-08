package com.jlu.mzx.tiaoji.Aty;

import android.os.Bundle;
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
    ListView listview;
    private List<Volunteer> datas;
    private StatusofMyapplyAdapter adapter;
    int num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.status_of_myapply_activity);
        datas = new ArrayList<>();
        listview = (ListView) findViewById(R.id.list);

        adapter = new StatusofMyapplyAdapter(this, datas);
        listview.setAdapter(adapter);

        querystatus();


    }

    private void querystatus() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("type", "1");
            jsonObject.put("content", AppConfig.USERNAME);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonRequest<JSONObject> request = new JsonObjectRequest(Request.Method.POST,
                AppConfig.SERVERADD + "/searchvolunteerrequest",
                jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("request", response.toString());
                        try {
                            JSONArray request = response.getJSONArray("request");
                            for (int i = 0; i < request.length(); i++) {
                                Volunteer vol = new Volunteer();
                                JSONObject tmp = request.optJSONObject(i);
                                vol.setId(tmp.getInt("volunteerid"));
                                vol.setRequeststatus(tmp.getInt("requeststatus"));
                                datas.add(vol);
                                Log.e("datasize", "" + datas.size());

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        queryvolunteer();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        MyApplication.getmRequestQueue().add(request);

    }

    private void queryvolunteer() {
        JSONObject volpost = new JSONObject();
        Log.e("datasize", "" + datas.size());

        for (num = 0; num < datas.size();num++ ) {
            Log.e("datanum", "" + num);
            try {
                volpost.put("type", 5);
                volpost.put("content", datas.get(num).getId());

                Log.e("datanum", "" + num);
                JsonObjectRequest volunteerrequest = new JsonObjectRequest(Request.Method.POST,
                        AppConfig.SERVERADD + "/searchvolunteer", volpost,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                JSONArray json = null;
                                Log.e("request", response.toString());
                                try {
                                    json = response.getJSONArray("volunteer");
                                    Log.e("volunteer", json.toString());
                                    JSONObject tmp = json.optJSONObject(0);
                                    Log.e("datanum", "" + num);
                                    for (Volunteer i : datas) {
                                        if (i.getId()==tmp.getInt("volunteer"))
                                        datas.get(num).setTeacher(tmp.get("teacher").toString());
                                        datas.get(num).setSchool(tmp.get("school").toString());
                                        datas.get(num).setSpecialty(tmp.get("specialty").toString());
                                        datas.get(num).setRequeststatus(tmp.getInt("requeststatus"));
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        });
                volunteerrequest.setTag(num);
                MyApplication.getmRequestQueue().add(volunteerrequest);
                adapter.notifyDataSetChanged();


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


    }
}