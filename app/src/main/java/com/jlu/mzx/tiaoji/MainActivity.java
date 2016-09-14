package com.jlu.mzx.tiaoji;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.jlu.mzx.tiaoji.Adapter.MyFragmentAdapter;
import com.jlu.mzx.tiaoji.Frag.ZhiyuanFragment;
import com.jlu.mzx.tiaoji.Frag.me_student_fragment;
import com.jlu.mzx.tiaoji.Frag.me_teacher_fragment;

import java.util.ArrayList;
import java.util.List;

import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imlib.model.Conversation;

public class MainActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private List<Fragment> ls;
    private Fragment mConverstationlist = null;
    private Fragment zhiyuanFragment = null;
    private Fragment meFragment = null;
    private RadioGroup radiogroup;
    private RadioButton xiaoxi, zhiyuan, me;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initview();
    }

    /**
     * 初始化view
     */
    private void initview() {

       // getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        radiogroup = (RadioGroup) findViewById(R.id.radiogroup);
        xiaoxi = (RadioButton) findViewById(R.id.xiaoxi);
        zhiyuan = (RadioButton) findViewById(R.id.zhiyuan);
        me = (RadioButton) findViewById(R.id.me);
        viewPager = (ViewPager) findViewById(R.id.viewPager);

        ls = new ArrayList<>();
        mConverstationlist = initConverstationlist();

        /**
         * 进行用户身份判断 学生和老师加载不同的fragment 实现不同用户显示不同的界面
         */
        //TODO me_teacher_fragment 实现
        SharedPreferences sp = getSharedPreferences("app", MODE_PRIVATE);
        int usersidentity = sp.getInt("identity", 1);
        if (usersidentity == 1) {
            meFragment = new me_student_fragment();
        } else {
            meFragment = new me_teacher_fragment();
        }
        zhiyuanFragment = new ZhiyuanFragment();

        ls.add(mConverstationlist);
        ls.add(zhiyuanFragment);
        ls.add(meFragment);


        viewPager.setAdapter(new MyFragmentAdapter(getSupportFragmentManager(), ls));
        radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.xiaoxi:
                        /**
                         * setCurrentItem第二个参数控制页面切换动画
                         * true:打开/false:关闭
                         */
                        viewPager.setCurrentItem(0, true);
                        break;
                    case R.id.zhiyuan:
                        viewPager.setCurrentItem(1, true);
                        break;
                    case R.id.me:
                        viewPager.setCurrentItem(2, true);
                        break;
                }
            }
        });


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                switch (position) {
                    case 0:
                        radiogroup.check(R.id.xiaoxi);
                        break;
                    case 1:
                        radiogroup.check(R.id.zhiyuan);
                        break;
                    case 2:
                        radiogroup.check(R.id.me);
                        break;
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * 初始化聊天界面fragment
     */
    private Fragment initConverstationlist() {
        if (mConverstationlist == null) {
            ConversationListFragment fragment = new ConversationListFragment();

            Uri uri = Uri.parse("rong://" + getApplicationInfo().packageName).buildUpon()
                    .appendPath("conversationlist")
                    .appendQueryParameter(Conversation.ConversationType.PRIVATE.getName(), "flase") //设置私聊会话是否聚合显示
                    .appendQueryParameter(Conversation.ConversationType.GROUP.getName(), "true")
                    .appendQueryParameter(Conversation.ConversationType.DISCUSSION.getName(), "false")
                    .appendQueryParameter(Conversation.ConversationType.SYSTEM.getName(), "true")
                    .build();

            fragment.setUri(uri);
            return fragment;
        } else {
            return mConverstationlist;
        }
    }
}
