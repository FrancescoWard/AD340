/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.wfrs06.hw5;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.wfrs06.hw5.entity.Settings;
import com.example.wfrs06.hw5.models.AppDatabase;
import com.example.wfrs06.hw5.models.AppDbSingleton;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Provides UI for the view with Cards.
 */
public class TileContentFragment extends Fragment {

    public TimePicker mTimePicker;
    public EditText mNumPick, mAgePickFrom, mAgePickTo;
    public EditText mGender;
    public CheckBox mPublicAccount;
    public CheckBox mPrivateAccount;
    public Button mBtnUpdateDb;

    //Time picker fields
    int mHour;
    int mMinute;

    //Number picker fields
    //int mNumPickVal;
    //int mAgeRangeFrom;
    //int mAgeRangeTo;

    //Db fields
    public static List<Settings> mList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.item_tile, container, false);

        mTimePicker = (TimePicker) view.findViewById(R.id.daily_matches_reminder_time);
        mNumPick = (EditText) view.findViewById(R.id.max_distance_search);
        mGender = view.findViewById(R.id.gender);
        mPublicAccount = view.findViewById(R.id.account_type_public);
        mPrivateAccount = view.findViewById(R.id.account_type_private);
        mAgePickFrom = view.findViewById(R.id.age_range_from);
        mAgePickTo = view.findViewById(R.id.age_range_to);
        mBtnUpdateDb = view.findViewById(R.id.btnUpdateDb);


        mTimePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                mHour = hourOfDay;
                mMinute = minute;
            }
        });
/*
        mNumPick.setMinValue(1);
        mNumPick.setMaxValue(200);

        mAgePickFrom.setMinValue(1);
        mAgePickFrom.setMaxValue(150);

        mAgePickTo.setMinValue(1);
        mAgePickTo.setMaxValue(150);

        mNumPick.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                mNumPickVal = mNumPick.getValue();
            }
        });

        mAgePickFrom.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                mAgeRangeFrom = mAgePickFrom.getValue();
            }
        });

        mAgePickTo.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                mAgeRangeTo = mAgePickTo.getValue();
            }
        });
*/
        mBtnUpdateDb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Persist changes
                if (mList.isEmpty())
                {
                    insertDatabase(v);
                }
                else {
                    updateDatabase(v);
                }
                Toast.makeText(getContext(),"Settings saved",Toast.LENGTH_LONG).show();
            }
        });

        new GetSettingsTask(this, 0).execute();

        return view;
    }

    public void insertDatabase(View view) {
        Settings initSettings = new Settings();
        int id = 0;

        int dailyMatchesReminderTime = mHour * 100 + mMinute;
        initSettings.setId(id);
        initSettings.setDailyMatchesReminderTime(dailyMatchesReminderTime);
        initSettings.setMaxDistanceSearch(Integer.valueOf(mNumPick.getText().toString()));
        initSettings.setInterestedAgeRangeFrom(mAgePickFrom.getText().toString());
        initSettings.setInterestedAgeRangeTo(mAgePickTo.getText().toString());
        initSettings.setGender(mGender.getText().toString());
        initSettings.setPublicAcct(mPublicAccount.isChecked());
        initSettings.setPrivateAcct(mPrivateAccount.isChecked());

        new SetSettingsTask(this, initSettings).execute();
    }

    public void updateDatabase(View view) {
        Settings initSettings = new Settings();
        int id = 0;

        int dailyMatchesReminderTime = mHour * 100 + mMinute;
        initSettings.setId(id);
        initSettings.setDailyMatchesReminderTime(dailyMatchesReminderTime);
        initSettings.setMaxDistanceSearch(Integer.valueOf(mNumPick.getText().toString()));
        initSettings.setInterestedAgeRangeFrom(mAgePickFrom.getText().toString());
        initSettings.setInterestedAgeRangeTo(mAgePickTo.getText().toString());
        initSettings.setGender(mGender.getText().toString());
        initSettings.setPublicAcct(mPublicAccount.isChecked());
        initSettings.setPrivateAcct(mPrivateAccount.isChecked());

        new UpdateSettingsTask(this, initSettings).execute();
    }

    private static class GetSettingsTask extends AsyncTask<Void, Void, Settings> {
        private WeakReference<Fragment> weakActivity;
        private int settingsId;

        public GetSettingsTask(Fragment fragment, int id) {
            weakActivity = new WeakReference<>(fragment);
            this.settingsId = id;
        }

        @Override
        protected Settings doInBackground(Void... voids) {
            if(android.os.Debug.isDebuggerConnected())
                android.os.Debug.waitForDebugger();
            Fragment fragment = weakActivity.get();
            if(fragment == null) {
                return null;
            }

            AppDatabase db = AppDbSingleton.getDatabase(fragment.getContext());
            mList = db.settingsDao().getAll();

            List<Settings> settings = db.settingsDao().loadAllByIds(0);

            if(0 != settingsId ||  settings.isEmpty()) {
                return null;
            }

            return settings.get(0);
        }

        @Override
        protected void onPostExecute(Settings settings) {
            TileContentFragment fragment = (TileContentFragment) weakActivity.get();
            if(null == settings || null == fragment) {
                return;
            }

            fragment.mTimePicker.setHour(settings.getDailyMatchesReminderTime() / 100 );
            fragment.mTimePicker.setMinute(settings.getDailyMatchesReminderTime() % 100);
            fragment.mNumPick.setText(String.valueOf(settings.getMaxDistanceSearch()));
            fragment.mAgePickFrom.setText(settings.getInterestedAgeRangeFrom());
            fragment.mAgePickTo.setText(settings.getInterestedAgeRangeTo());
            fragment.mPublicAccount.setChecked(settings.isPublicAcct());
            fragment.mPrivateAccount.setChecked(settings.isPrivateAcct());
            fragment.mGender.setText(settings.getGender());
        }
    }

    private static class SetSettingsTask extends AsyncTask<Void, Void, Settings> {

        private WeakReference<Fragment> weakActivity;
        private Settings settings;

        public SetSettingsTask(Fragment fragment, Settings settings) {
            weakActivity = new WeakReference<>(fragment);
            this.settings = settings;
        }

        @Override
        protected Settings doInBackground(Void... voids) {
            Fragment fragment = weakActivity.get();
            if(fragment == null) {
                return null;
            }

            AppDatabase db = AppDbSingleton.getDatabase(fragment.getContext());

            mList = db.settingsDao().getAll();

            db.settingsDao().insertAll(settings);
            return settings;
        }
    }

    private static class UpdateSettingsTask extends AsyncTask<Void, Void, Settings> {

        private WeakReference<Fragment> weakActivity;
        private Settings settings;

        public UpdateSettingsTask(Fragment fragment, Settings settings) {
            weakActivity = new WeakReference<>(fragment);
            this.settings = settings;
        }

        @Override
        protected Settings doInBackground(Void... voids) {
            if(android.os.Debug.isDebuggerConnected())
                android.os.Debug.waitForDebugger();
            Fragment fragment = weakActivity.get();
            if(fragment == null) {
                return null;
            }

            AppDatabase db = AppDbSingleton.getDatabase(fragment.getContext());

            mList = db.settingsDao().getAll();

            db.settingsDao().updateSettings(settings);

            return settings;
        }

        @Override
        protected void onPostExecute(Settings settings) {
            TileContentFragment fragment = (TileContentFragment) weakActivity.get();
            if(settings == null || fragment == null) {
                return;
            }

            fragment.mTimePicker.setHour(settings.getDailyMatchesReminderTime() / 100 );
            fragment.mTimePicker.setMinute(settings.getDailyMatchesReminderTime() % 100);
            fragment.mPublicAccount.setChecked(settings.isPublicAcct());
            fragment.mPrivateAccount.setChecked(settings.isPrivateAcct());
            fragment.mGender.setText(settings.getGender());
        }
    }

}
