package com.jlu.mzx.tiaoji.activity;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

public class CreatMyVolunteer extends AppCompatActivity {
    private JSONObject put;
    private TextView yes, no;
    private EditText name, school, teacher, college, subject, area, amount, specialty, remark;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.creat_my_volunteer);
        name = (EditText) findViewById(R.id.name);
        school = (EditText) findViewById(R.id.school);
        teacher = (EditText) findViewById(R.id.teacher);
        college = (EditText) findViewById(R.id.college);
        subject = (EditText) findViewById(R.id.subject);
        area = (EditText) findViewById(R.id.area);
        amount = (EditText) findViewById(R.id.amount);
        specialty = (EditText) findViewById(R.id.specialty);
        remark = (EditText) findViewById(R.id.remark);
        yes = (TextView) findViewById(R.id.yes);
        no = (TextView) findViewById(R.id.no);
        put = new JSONObject();


        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    SharedPreferences sp = getSharedPreferences("app",MODE_PRIVATE);
                    put.put("name",name.getText().toString());
                    put.put("releaseuser",sp.getString("realname",null));
                    put.put("school",school.getText().toString());
                    put.put("teacher",teacher.getText().toString());
                    put.put("college",college.getText().toString());
                    put.put("subject",subject.getText().toString());
                    put.put("specialty",specialty.getText().toString());
                    put.put("area",area.getText().toString());
                    put.put("amount",amount.getText().toString());
                    put.put("remark",remark.getText().toString());
                    put.put("realname",sp.getString("realname",null));
                    put.put("token",sp.getString("token",null));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                JsonRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, AppConfig.SERVERADD + "/releasevolunteer", put, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Toast.makeText(getBaseContext(),"访问成功",Toast.LENGTH_SHORT).show();
                        SharedPreferences volunteer = getSharedPreferences("volunteer",MODE_PRIVATE);
                        SharedPreferences.Editor editor = volunteer.edit();
                        try {
                            editor.putString("name",put.getString("name"));
                            editor.putString("realname",put.getString("releaseuser"));
                            editor.putString("school",put.getString("school"));
                            editor.putString("teacher",put.getString("teacher"));
                            editor.putString("college",put.getString("college"));
                            editor.putString("subject",put.getString("subject"));
                            editor.putString("specialty",put.getString("specialty"));
                            editor.putString("area",put.getString("area"));
                            editor.putString("amount",put.getString("amount"));
                            editor.putString("remark",put.getString("remark"));
                            editor.commit();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getBaseContext(),"访问失败",Toast.LENGTH_SHORT).show();
                    }
                });
                MyApplication.getmRequestQueue().add(jsonRequest);
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

