package com.jlu.mzx.tiaoji.Frag;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.jlu.mzx.tiaoji.Adapter.InformationAdapter;
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
 * Created by mzx on 2016/7/15.
 */
public class ZhiyuanFragment extends Fragment implements TextView.OnEditorActionListener {

    private EditText editText;
    private Spinner spinner;
    private ListView listview;
    private List<Volunteer> datas;
    InformationAdapter inforadapter;


    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.e("onCreateView", "onCreateView");
        View view = inflater.inflate(R.layout.zhiyuan_fragment, container, false);


        /**
         * 初始化view
         */
        datas = new ArrayList<Volunteer>();
        spinner = (Spinner) view.findViewById(R.id.type);
        editText = (EditText) view.findViewById(R.id.searchEdit);
        listview = (ListView) view.findViewById(R.id.list);

        inforadapter = new InformationAdapter(getContext(), datas);
        listview.setAdapter(inforadapter);

        /**
         * 设置键盘事件监听 进行搜索
         */
        editText.setOnEditorActionListener(this);
        return view;
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
                    content = "school";
                if (spinner.getSelectedItem().equals("老师"))
                    content = "teacher";
                if (spinner.getSelectedItem().equals("专业"))
                    content = "specialty";
                if (spinner.getSelectedItem().equals("地区"))
                    content = "area";
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
                            datas.add(new Volunteer(item.get("teacher").toString(), item.get("school").toString()));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


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

