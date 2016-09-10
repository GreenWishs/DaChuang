package com.jlu.mzx.tiaoji.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.jlu.mzx.tiaoji.R;
import com.jlu.mzx.tiaoji.tools.Volunteer;

import io.rong.imkit.RongIM;

public class VolunteerInfo extends Activity {
    private ListView listView;
    private Volunteer volunteer;
    private Button startcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volunteer_info);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        listView = (ListView) findViewById(R.id.info_list);
        Intent intent = getIntent();
        volunteer = (Volunteer) intent.getSerializableExtra("info");
        listView.setAdapter(new VolunteerInfoListAdapter(volunteer));
        startcon = (Button) findViewById(R.id.startconversation);
        startcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO 这个地方开启回话有问题，应查询到老师的id之后进行对话
                if (RongIM.getInstance() != null)
                    RongIM.getInstance().startPrivateChat(VolunteerInfo.this, "123", "title");
            }
        });
    }

    class VolunteerInfoListAdapter extends BaseAdapter {
        Volunteer volunteer;

        public VolunteerInfoListAdapter(Volunteer volunteer) {
            this.volunteer = volunteer;
        }

        @Override
        public int getCount() {
            return 6;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.volunteer_info_item, null);
            TextView rc_left = (TextView) view.findViewById(R.id.rc_left);
            TextView rc_right = (TextView) view.findViewById(R.id.rc_right);
            switch (i) {
                case 0:
                    rc_left.setText("地区");
                    rc_right.setText(volunteer.getArea());
                    break;
                case 1:
                    rc_left.setText("院校");
                    rc_right.setText(volunteer.getCollege());
                    break;
                case 2:
                    rc_left.setText("所属科目");
                    rc_right.setText(volunteer.getSpecialty());
                    break;
                case 3:
                    rc_left.setText("老师");
                    rc_right.setText(volunteer.getTeacher());
                    break;
                case 4:
                    rc_left.setText("名额");
                    rc_right.setText(volunteer.getAmount());
                    break;
                case 5:
                    rc_left.setText("备注");
                    rc_right.setText(volunteer.getRemark());
                    break;

            }
            return view;
        }
    }
}
