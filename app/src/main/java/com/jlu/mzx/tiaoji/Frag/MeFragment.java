package com.jlu.mzx.tiaoji.Frag;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.jlu.mzx.tiaoji.Aty.LoginActivity;
import com.jlu.mzx.tiaoji.R;

/**
 * Created by mzx on 2016/7/15.
 */
public class MeFragment extends Fragment {
    private Button outlogin;
    private View view = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.e("onCreateView", "onCreateView");
        if (view == null) {
            view = inflater.inflate(R.layout.me_fragment, container, false);
            outlogin = (Button) view.findViewById(R.id.button);
            outlogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    SharedPreferences sp = getActivity().getSharedPreferences("app", Context.MODE_APPEND);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putBoolean("isdenglu", false);
                    editor.commit();

                    Intent intent = new Intent(getContext(), LoginActivity.class);
                    startActivity(intent);
                    getActivity().finish();

                }
            });
        }

        return view;
    }
}
