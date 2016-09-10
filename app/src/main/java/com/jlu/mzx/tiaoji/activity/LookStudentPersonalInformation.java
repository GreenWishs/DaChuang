package com.jlu.mzx.tiaoji.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.jlu.mzx.tiaoji.AppConfig;
import com.jlu.mzx.tiaoji.MainActivity;
import com.jlu.mzx.tiaoji.MyApplication;
import com.jlu.mzx.tiaoji.R;

import org.json.JSONException;
import org.json.JSONObject;

public class LookStudentPersonalInformation extends AppCompatActivity {

    private Button change,back;//修改按钮,返回按钮
    private Intent gochange,goback;
    private TextView name,sex,age,school,nation,specialty,phone,idcard,introduct;
    private SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.look_student_personal_information);
        change = (Button)findViewById(R.id.change);
        back = (Button)findViewById(R.id.back);
        name = (TextView) findViewById(R.id.name);
        sex = (TextView)findViewById(R.id.sex);
        age = (TextView)findViewById(R.id.age);
        school = (TextView)findViewById(R.id.school);
        nation = (TextView)findViewById(R.id.nation);
        specialty = (TextView)findViewById(R.id.specialty);
        phone = (TextView)findViewById(R.id.phone);
        idcard = (TextView)findViewById(R.id.idcard);
        introduct = (TextView)findViewById(R.id.introduct);
        JSONObject data = new JSONObject();//根据用户名请求学生个人信息
        sp = getSharedPreferences("app",MODE_PRIVATE);
        String username = sp.getString("username","无记录");
        String tocken = sp.getString("tocken",null);
        try {
            data.put("username",username);
            data.put("tocken",tocken);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonRequest<JSONObject> request1 = new JsonObjectRequest(Request.Method.POST, AppConfig.SERVERADD+"/queryuserinfo",data,new Response.Listener<JSONObject>(){

            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(getBaseContext(),"获取成功",Toast.LENGTH_SHORT).show();
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
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getBaseContext(),"获取失败",Toast.LENGTH_SHORT).show();
                name.setText(sp.getString("realname",null));
                sex.setText(sp.getString("sex",null));
                age.setText(sp.getString("age",null));
                school.setText(sp.getString("school",null));
                nation.setText(sp.getString("nation",null));
                specialty.setText(sp.getString("specialty",null));
                phone.setText(sp.getString("phone",null));
                idcard.setText(sp.getString("idcard",null));
                introduct.setText(sp.getString("introduct",null));

            }
        }) ;
        MyApplication.getmRequestQueue().add(request1);




        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gochange = new Intent(LookStudentPersonalInformation.this,SetStudentPersonalInformation.class);
                startActivity(gochange);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goback = new Intent(LookStudentPersonalInformation.this, MainActivity.class);
                goback.putExtra("id","2");
                finish();
            }
        });
    }
}
