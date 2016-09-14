package com.jlu.mzx.tiaoji.Frag;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.jlu.mzx.tiaoji.Adapter.VolunteerInformationAdapter;
import com.jlu.mzx.tiaoji.AppConfig;
import com.jlu.mzx.tiaoji.activity.SearchVolunteer;
import com.jlu.mzx.tiaoji.activity.VolunteerInfo;
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
public class ZhiyuanFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private SwipeRefreshLayout swipeLayout;
    private EditText editText;
    private Spinner spinner;
    private ListView listview;
    private List<Volunteer> datas;
    VolunteerInformationAdapter inforadapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.zhiyuan_fragment, container, false);

        listview = (ListView) view.findViewById(R.id.list);
        swipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.swiprefresh);
        swipeLayout.setOnRefreshListener(this);

        return view;
    }

    @Override
    public void onRefresh() {

    }
}


