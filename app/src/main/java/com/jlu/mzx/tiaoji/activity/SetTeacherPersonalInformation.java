package com.jlu.mzx.tiaoji.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.jlu.mzx.tiaoji.AppConfig;
import com.jlu.mzx.tiaoji.MyApplication;
import com.jlu.mzx.tiaoji.R;

import org.json.JSONException;
import org.json.JSONObject;

public class SetTeacherPersonalInformation extends AppCompatActivity {
    String username;
    EditText name,sex,age,school,nation,specialty,phone,idcard,introduct,direction;//学生具体信息
    SharedPreferences sp;
    private JSONObject data, data1;//请求修改用户数据，请求得到用户数据
    private TextView yes;//确认
    private Intent intent;
    private TextView no;//取消

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_teacher_personal_information);
        sp = getSharedPreferences("app", MODE_PRIVATE);
        data = new JSONObject();
        data1 = new JSONObject();
        username = sp.getString("username", null);
        yes = (TextView) findViewById(R.id.yes);
        no = (TextView) findViewById(R.id.no);
        name = (EditText) findViewById(R.id.name);
        sex = (EditText) findViewById(R.id.sex);
        age = (EditText) findViewById(R.id.age);
        school = (EditText) findViewById(R.id.school);
        nation = (EditText) findViewById(R.id.nation);
        specialty = (EditText) findViewById(R.id.specialty);
        phone = (EditText) findViewById(R.id.phone);
        idcard = (EditText) findViewById(R.id.idcard);
        introduct = (EditText) findViewById(R.id.introduct);
        direction = (EditText)findViewById(R.id.direction);
        try {
            data1.put("username", username);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonRequest<JSONObject> request1 = new JsonObjectRequest(Request.Method.POST, AppConfig.SERVERADD + "/queryuserinfo", data1, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(getBaseContext(), "获取成功", Toast.LENGTH_SHORT).show();
                try {
                    name.setText(response.getString("realname"));
                    sex.setText(response.getString("sex"));
                    age.setText(response.getString("age"));
                    school.setText(response.getString("school"));
                    nation.setText(response.getString("nation"));
                    specialty.setText(response.getString("specialty"));
                    phone.setText(response.getString("phone"));
                    idcard.setText(response.getString("idcard"));
                    introduct.setText(response.getString("introduct"));
                    direction.setText(response.getString("direction"));

                } catch (JSONException e) {
                    e.printStackTrace();

                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getBaseContext(), "获取失败", Toast.LENGTH_SHORT).show();
                name.setText(sp.getString("realname",null));
                sex.setText(sp.getString("sex",null));
                age.setText(sp.getString("age",null));
                school.setText(sp.getString("school",null));
                nation.setText(sp.getString("nation",null));
                specialty.setText(sp.getString("specialty",null));
                phone.setText(sp.getString("phone",null));
                idcard.setText(sp.getString("idcard",null));
                introduct.setText(sp.getString("introduct",null));
                direction.setText(sp.getString("direction",null));

            }
        });
        MyApplication.getmRequestQueue().add(request1);


        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    data.put("username", username);
                    data.put("realname", name.getText().toString());
                    data.put("sex", sex.getText().toString());
                    data.put("age", age.getText().toString());
                    data.put("idcard", idcard.getText().toString());
                    data.put("nation", nation.getText().toString());
                    data.put("school", school.getText().toString());
                    data.put("specialty", specialty.getText().toString());
                    data.put("introduct", introduct.getText().toString());
                    data.put("phone", phone.getText().toString());
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("realname", name.getText().toString());
                    editor.putString("sex", sex.getText().toString());
                    editor.putString("age", age.getText().toString());
                    editor.putString("idcard", idcard.getText().toString());
                    editor.putString("nation", nation.getText().toString());
                    editor.putString("school", school.getText().toString());
                    editor.putString("specialty", specialty.getText().toString());
                    editor.putString("introduct", introduct.getText().toString());
                    editor.putString("phone", phone.getText().toString());
                    editor.apply();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                JsonRequest<JSONObject> request2 = new JsonObjectRequest(Request.Method.POST, AppConfig.SERVERADD + "/settinguserinfo", data, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(getBaseContext(), "修改成功", Toast.LENGTH_SHORT).show();

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getBaseContext(), "修改失败", Toast.LENGTH_SHORT).show();

                    }
                });
                MyApplication.getmRequestQueue().add(request2);
                finish();
            }
        });
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}

