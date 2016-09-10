package com.jlu.mzx.tiaoji.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.UserInfo;


public class LoginActivity extends Activity {
    private Button login;
    private String username;
    private String passwd;
    private EditText text1;
    private EditText text2;
    private TextView register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        login = (Button) findViewById(R.id.login);
        register = (TextView) findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, Register.class));
            }
        });


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //异步判断是否登陆成功
                Log.e("login", "start login");
                decideLogin();

            }
        });

    }

    private void decideLogin() {
        //获取用户输入的账号信息
        text1 = (EditText) findViewById(R.id.user_name);
        username = text1.getText().toString();
        text2 = (EditText) findViewById(R.id.passwd);
        passwd = text2.getText().toString();
        JSONObject json = new JSONObject();
        /**
         * 判断用户输入
         */
        if (username != null && passwd != null) {
            Toast.makeText(LoginActivity.this, "" + username + passwd, Toast.LENGTH_SHORT).show();
            try {
                SharedPreferences sp = getSharedPreferences("app", MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("username",username);
                editor.putString("passwd",passwd);
                editor.apply();
                json.put("username", username);
                json.put("password", passwd);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            JsonRequest<JSONObject> jsonRequest = new JsonObjectRequest(com.android.volley.Request.Method.POST,
                    AppConfig.SERVERADD + AppConfig.LOGIN, json, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        int identity = response.getInt("identity");
                        SharedPreferences sp = getSharedPreferences("app", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString("token",response.getString("token"));
                        editor.putInt("identity",identity);
                        editor.apply();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    decide(response);

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(LoginActivity.this, "连接失败，请检查网络或稍后重试", Toast.LENGTH_LONG).show();
                }
            });

            MyApplication.getmRequestQueue().add(jsonRequest);

        } else {
            Toast.makeText(LoginActivity.this, "请输入用户名和密码", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 发起网络请求，判断用户名密码是否正确
     * 正确的话保存token并跳转，否则弹出toast显示登陆失败
     */


    private void rongconnect(String Token) {
        RongIM.connect(Token, new RongIMClient.ConnectCallback() {
            @Override
            public void onTokenIncorrect() {
            }

            @Override
            public void onSuccess(String userId) {
                Log.e("MainActivity", "------onSuccess----" + userId);
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                Log.e("MainActivity", "------onError----" + errorCode);

            }
        });
        RongIM.setUserInfoProvider(new RongIM.UserInfoProvider() {
            @Override
            public UserInfo getUserInfo(String s) {
                Log.e("userinfo", s);
                return new UserInfo(String.valueOf(10010), "liantong", Uri.parse("http://o8511iwjv.qnssl.com/avatar.jpg"));
            }
        }, true);
    }

    private void decide(JSONObject result) {
        try {
            String status = result.getString("status");
            if (status.equals("1")) {

                String rongtoken = result.getString("rongtoken");
                String token = result.getString("token");


                rongconnect(rongtoken);
                startActivity(new Intent(LoginActivity.this, MainActivity.class));

                SharedPreferences sp = getSharedPreferences("app", MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putBoolean("isdenglu", true);
                editor.putString("rongtoken", rongtoken);
                editor.putString("token", token);
                editor.apply();
                LoginActivity.this.finish();

            }
            Log.e("status", status);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
