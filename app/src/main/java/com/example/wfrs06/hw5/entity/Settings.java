package com.example.wfrs06.hw5.entity;
import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Entity
public class Settings {
    @PrimaryKey
    @NonNull
    private int id;

    @ColumnInfo(name = "daily_matches_reminder_time")
    private int dailyMatchesReminderTime;

    @ColumnInfo(name = "max_distance_search")
    private int maxDistanceSearch;

    @ColumnInfo(name = "gender")
    private String gender;

    @ColumnInfo(name = "private_account")
    private boolean privateAcct;

    @ColumnInfo(name = "public_account")
    private boolean publicAcct;

    @ColumnInfo(name = "interested_age_range_from")
    private String interestedAgeRangeFrom;

    @ColumnInfo(name = "interested_age_range_to")
    private String interestedAgeRangeTo;

    public String getInterestedAgeRangeFrom() {
        return interestedAgeRangeFrom;
    }

    public void setInterestedAgeRangeFrom(String interestedAgeRangeFrom) {
        this.interestedAgeRangeFrom = interestedAgeRangeFrom;
    }

    public String getInterestedAgeRangeTo() {
        return interestedAgeRangeTo;
    }

    public void setInterestedAgeRangeTo(String interestedAgeRangeTo) {
        this.interestedAgeRangeTo = interestedAgeRangeTo;
    }

    @NonNull
    public int getId() {
        return id;
    }

    public void setId(@NonNull int id) {
        this.id = id;
    }

    public int getDailyMatchesReminderTime() {
        return dailyMatchesReminderTime;
    }

    public void setDailyMatchesReminderTime(int dailyMatchesReminderTime) {
        this.dailyMatchesReminderTime = dailyMatchesReminderTime;
    }

    public int getMaxDistanceSearch() {
        return maxDistanceSearch;
    }

    public void setMaxDistanceSearch(int maxDistanceSearch) {
        this.maxDistanceSearch = maxDistanceSearch;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public boolean isPrivateAcct() {
        return privateAcct;
    }

    public void setPrivateAcct(boolean privateAcct) {
        this.privateAcct = privateAcct;
    }

    public boolean isPublicAcct() {
        return publicAcct;
    }

    public void setPublicAcct(boolean publicAcct) {
        this.publicAcct = publicAcct;
    }
}
