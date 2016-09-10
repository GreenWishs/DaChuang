package com.jlu.mzx.tiaoji.Frag;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.jlu.mzx.tiaoji.Adapter.VolunteerInformationAdapter;
import com.jlu.mzx.tiaoji.AppConfig;
import com.jlu.mzx.tiaoji.activity.SearchVolunteer;
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
 * Created by mzx on 2016/7/15.
 */
public class ZhiyuanFragment extends Fragment implements TextView.OnEditorActionListener {

    private EditText editText;
    private Spinner spinner;
    private ListView listview;
    private List<Volunteer> datas;
    VolunteerInformationAdapter inforadapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.e("onCreateView", "onCreateView");
        View view = inflater.inflate(R.layout.zhiyuan_fragment, container, false);


        /**
         * 初始化view
         */

        datas = new ArrayList<>();
        spinner = (Spinner) view.findViewById(R.id.type);
        editText = (EditText) view.findViewById(R.id.searchEdit);
        listview = (ListView) view.findViewById(R.id.list);

        inforadapter = new VolunteerInformationAdapter(getContext(), datas);
        listview.setAdapter(inforadapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getContext(), VolunteerInfo.class);
                intent.putExtra("info", datas.get(i));
                startActivity(intent);
            }
        });

        /**
         * 设置键盘事件监听 进行搜索
         */
        editText.setOnEditorActionListener(this);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.base_menu, menu);
        menu.findItem(R.id.search).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                startActivity(new Intent(getActivity(), SearchVolunteer.class));
                return true;
            }
        });
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            datas.clear();
            /**
             * 构造数据
             */
            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("app", Context.MODE_PRIVATE);
            final String token = sharedPreferences.getString("token", null);
            final JSONObject jsonObject = new JSONObject();
            try {
                String content = null;
                jsonObject.put("token", token);
                if (spinner.getSelectedItem().equals("学校"))
                    content = "6";
                if (spinner.getSelectedItem().equals("老师"))
                    content = "4";
                if (spinner.getSelectedItem().equals("专业"))
                    content = "2";
                if (spinner.getSelectedItem().equals("地区"))
                    content = "3";
                jsonObject.put("type", content);
                jsonObject.put("content", editText.getText().toString());

            } catch (JSONException e) {
                e.printStackTrace();
            }

            JsonRequest<JSONObject> request = new JsonObjectRequest(Request.Method.POST, AppConfig.SERVERADD + "/searchvolunteer", jsonObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Toast.makeText(getContext(), "qingqiu chenggong", Toast.LENGTH_SHORT).show();
                    Log.e("chenggong", response.toString());
                    try {
                        JSONArray result = response.getJSONArray("volunteer");
                        for (int i = 0; i < result.length(); i++) {
                            JSONObject item = result.optJSONObject(i);
                            Volunteer tmp = new Volunteer();
                            tmp.setAmount(item.get("amount").toString());
                            tmp.setArea(item.get("area").toString());
                            tmp.setCollege(item.get("college").toString());
                            tmp.setRemark(item.get("remark").toString());
                            tmp.setSchool(item.get("school").toString());
                            tmp.setTeacher(item.get("teacher").toString());
                            tmp.setSubject(item.get("subject").toString());
                            tmp.setSpecialty(item.get("specialty").toString());
                            Log.e("tmp", tmp.toString());
                            datas.add(tmp);

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    Log.e("datas", datas.toString());
                    inforadapter.notifyDataSetChanged();

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getContext(), "qingqiu shibai", Toast.LENGTH_SHORT).show();

                }
            });
            MyApplication.getmRequestQueue().add(request);
        }


        Toast.makeText(getActivity().getApplicationContext(), "你点击了搜索按钮" + spinner.getSelectedItem(), Toast.LENGTH_SHORT).show();
        return false;
    }


}

