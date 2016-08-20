package com.jlu.mzx.tiaoji.Aty;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.jlu.mzx.tiaoji.AppConfig;
import com.jlu.mzx.tiaoji.MyApplication;
import com.jlu.mzx.tiaoji.R;

import org.json.JSONException;
import org.json.JSONObject;

public class Register extends AppCompatActivity implements View.OnClickListener {

    private EditText username, passwd, passwd2, email;
    private Button sub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initview();

        sub.setOnClickListener(this);
    }


    private void initview() {
        setContentView(R.layout.activity_register);

        username = (EditText) findViewById(R.id.user_name);
        passwd = (EditText) findViewById(R.id.passwd);
        passwd2 = (EditText) findViewById(R.id.passwd2);
        email = (EditText) findViewById(R.id.email);
        sub = (Button) findViewById(R.id.register);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.register) {
            Log.e("button", "pressbutton");
            String user_name = username.getText().toString();
            String password = passwd.getText().toString();
            String password2 = passwd2.getText().toString();
            String eemail = email.getText().toString();
            //TODO 对用户输入的信息进行正则验证
            if (user_name.equals("") || password.equals("") || password2.equals("") || eemail.equals("")) {
                return;
            }
            JSONObject js = new JSONObject();
            try {
                js.put("username", user_name);
                js.put("password", password);
                js.put("email", eemail);

            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                JsonRequest<JSONObject> request = new JsonObjectRequest(com.android.volley.Request.Method.POST,
                        AppConfig.SERVERADD + AppConfig.REGISTER, js, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("success", "success");
                        Log.e("success", response.toString());
                        decide(response);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Fail", "Fail");
                    }
                });
                MyApplication.getmRequestQueue().add(request);
            }

        }
    }

    private void decide(JSONObject result) {
        try {
            String status = result.getString("status");
            if (status.equals("1")) {
                Toast.makeText(Register.this, "恭喜你注册成功！", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Register.this, LoginActivity.class));
            } else {
                Log.e("fail", "register fial");
                Toast.makeText(Register.this, "抱歉您输入的用户名已被占用", Toast.LENGTH_LONG).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
