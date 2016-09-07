package com.jlu.mzx.tiaoji.Aty;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
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

/**
 * Created by caosong on 2016/9/3.
 */
public class SetStudentPersonalInformation extends AppCompatActivity {
    String username;
    EditText editText1,editText2,editText3,editText4,editText5,editText6,editText7,editText8,editText9;//学生具体信息
    private JSONObject data,data1;//请求修改用户数据，请求得到用户数据
    private TextView textView1;//确认
    private Intent intent;
    private TextView textView2;//取消

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_personal_information);
        SharedPreferences sp = getSharedPreferences("app", MODE_PRIVATE);
        username = sp.getString("username",null);
        textView1 = (TextView)findViewById(R.id.yes);
        textView2 = (TextView)findViewById(R.id.no);
        editText1 = (EditText)findViewById(R.id.name);
        editText2 = (EditText)findViewById(R.id.sex);
        editText3 = (EditText)findViewById(R.id.age);
        editText4 = (EditText)findViewById(R.id.school);
        editText5 = (EditText)findViewById(R.id.nation);
        editText6 = (EditText)findViewById(R.id.specialty);
        editText7 = (EditText)findViewById(R.id.phone);
        editText8 = (EditText)findViewById(R.id.idcard);
        editText9 = (EditText)findViewById(R.id.introduct);
        intent = new Intent(this, MainActivity.class);

        try {
            data1.put("username",username);
        } catch (JSONException e) {
            e.printStackTrace();
        }
       JsonRequest<JSONObject> request1 = new JsonObjectRequest(Request.Method.POST, AppConfig.SERVERADD+"/searchuserinfo",data1,new Response.Listener<JSONObject>(){

            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(getBaseContext(),"获取成功",Toast.LENGTH_SHORT).show();
                try {
                    editText1.setText(response.getString("realname"));
                    editText2.setText(response.getString("sex"));
                    editText3.setText(response.getString("age"));
                    editText4.setText(response.getString("school"));
                    editText5.setText(response.getString("nation"));
                    editText6.setText(response.getString("specialty"));
                    editText7.setText(response.getString("phone"));
                    editText8.setText(response.getString("idcard"));
                    editText9.setText(response.getString("introduct"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getBaseContext(),"获取失败",Toast.LENGTH_SHORT).show();

            }
        }) ;
        MyApplication.getmRequestQueue().add(request1);



        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    data.put("username",username);
                    data.put("realname",editText1.getText().toString());
                    data.put("sex",editText2.getText().toString());
                    data.put("age",editText3.getText().toString());
                    data.put("idcard",editText8.getText().toString());
                    data.put("nation",editText5.getText().toString());
                    data.put("school",editText4.getText().toString());
                    data.put("specialty",editText6.getText().toString());
                    data.put("introduct",editText9.getText().toString());
                    data.put("phone",editText7.getText().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                JsonRequest<JSONObject> request2 = new JsonObjectRequest(Request.Method.POST, AppConfig.SERVERADD+"/settinguserinfo",data,new Response.Listener<JSONObject>(){

                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(getBaseContext(),"修改成功",Toast.LENGTH_SHORT).show();

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getBaseContext(),"修改失败",Toast.LENGTH_SHORT).show();

                    }
                }) ;
                MyApplication.getmRequestQueue().add(request2);
                startActivity(intent);
            }
        });

    }
}
