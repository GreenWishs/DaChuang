package com.jlu.mzx.tiaoji.Aty;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.jlu.mzx.tiaoji.AppConfig;
import com.jlu.mzx.tiaoji.MainActivity;
import com.jlu.mzx.tiaoji.R;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;

public class loading extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oading);


        SharedPreferences sp = getSharedPreferences("app", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        Boolean isdenglu = sp.getBoolean("isdenglu", false);
        String rongtoken = sp.getString("rongtoken", null);

        if (isdenglu == true) {//APP处于登录状态


            Intent intent = new Intent(this, MainActivity.class);

            /**
             * 进行融云登陆
             */
            RongIM.connect(rongtoken, new RongIMClient.ConnectCallback() {
                @Override
                public void onTokenIncorrect() {
                    Toast.makeText(loading.this, "onTokenIncorrect", Toast.LENGTH_SHORT).show();
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

            startActivity(intent);
            finish();

        } else {//APP处于离线状态
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();

        }
    }
}
