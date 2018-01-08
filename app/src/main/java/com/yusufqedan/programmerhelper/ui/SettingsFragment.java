package com.yusufqedan.programmerhelper.ui;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.yusufqedan.programmerhelper.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SettingsFragment extends Fragment implements ListView.OnItemClickListener {
    private static Context mContext;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    private SharedPreferences.OnSharedPreferenceChangeListener mListener;
    @BindView(R.id.settings_list)
    ListView mSettingsList;
    private String[] mSettingsListItems = {"Change Password", "Theme"};

    public SettingsFragment() {
        // Required empty public constructor
    }

    public static SettingsFragment newInstance(Context context) {
        Bundle args = new Bundle();
        SettingsFragment fragment = new SettingsFragment();
        fragment.setArguments(args);
        mContext = context;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        ButterKnife.bind(this, view);
        ArrayAdapter adapter = new ArrayAdapter(mContext, android.R.layout.simple_list_item_1, mSettingsListItems);
        mSettingsList.setAdapter(adapter);
        mSettingsList.setOnItemClickListener(this);
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        mEditor = mSharedPreferences.edit();
        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        if (position == 0) { //Change Password
            startActivity(new Intent(mContext, ChangePasswordActivity.class));
        } else if (position == 1) { //Theme
            boolean toggle = mSharedPreferences.getBoolean("pref_dark_theme", false);
            mEditor.putBoolean("pref_dark_theme",!toggle).apply();
            final MainActivity activity = (MainActivity) getActivity();
            activity.recreate();
        }
    }
}
