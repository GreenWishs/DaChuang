package com.jlu.mzx.tiaoji.Frag;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.jlu.mzx.tiaoji.Adapter.VolunteerInformationAdapter;
import com.jlu.mzx.tiaoji.AppConfig;
import com.jlu.mzx.tiaoji.activity.VolunteerInfo;
import com.jlu.mzx.tiaoji.MyApplication;
import com.jlu.mzx.tiaoji.R;
import com.jlu.mzx.tiaoji.tools.Volunteer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mengz on 2016/9/8.
 */
public class SearchvolunteerFragment extends Fragment {
    private List<Volunteer> datas;
    private ListView listview;
    private VolunteerInformationAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.searchvolunteerfragment, container, false);
        Log.e("oncreatview", "oncreatview");
        listview = (ListView) view.findViewById(R.id.listview);


        //设置适配器
        datas = new ArrayList<>();
        adapter = new VolunteerInformationAdapter(getActivity(), datas);
        listview.setAdapter(adapter);


        //点击进入详情
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getContext(), VolunteerInfo.class);
                intent.putExtra("info", datas.get(i));
                startActivity(intent);
            }
        });
        return view;
    }

    public void updatelist(int type, String content) {
        datas.clear();
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("type", type);
            jsonObject.put("content", content);
            Log.e("json", jsonObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonRequest<JSONObject> request = new JsonObjectRequest(Request.Method.POST,
                AppConfig.SERVERADD + "/searchvolunteer",
                jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("chenggong", response.toString());
                try {
                    JSONArray result = response.getJSONArray("volunteer");
                    Log.e("json", result.toString());
                    for (int i = 0; i < result.length(); i++) {
                        JSONObject item = result.optJSONObject(i);
                        Log.e("json", item.toString());
                        Volunteer tmp = new Volunteer();

                        tmp.setAmount(item.get("amount").toString());
                        tmp.setArea(item.get("area").toString());
                        tmp.setCollege(item.get("college").toString());
                        tmp.setRemark(item.get("remark").toString());
                        tmp.setSchool(item.get("school").toString());
                        tmp.setTeacher(item.get("teacher").toString());
                        tmp.setSubject(item.get("subject").toString());
                        tmp.setSpecialty(item.get("specialty").toString());
                        Log.e("json", tmp.getAmount() + tmp.getId());
                        Log.e("tmp", datas.toString());

                        datas.add(tmp);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.e("datas", "" + datas.size());
                adapter.notifyDataSetChanged();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        MyApplication.getmRequestQueue().add(request);
    }
}
