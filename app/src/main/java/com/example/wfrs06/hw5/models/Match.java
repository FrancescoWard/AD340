package com.example.wfrs06.hw5.models;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class Match {

    public String imageUrl;
    public Boolean liked;
    public String name;
    public String uid;
    //public int starCount = 0;
    //public Map<String, Boolean> stars = new HashMap<>();

    public Match() {
        // Default constructor required for calls to DataSnapshot.getValue(Post.class)
    }

    public Match(String imageUrl, boolean liked, String name, String uid) {
        this.imageUrl = imageUrl;
        this.liked = liked;
        this.name = name;
        this.uid = uid;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("imageUrl", imageUrl);
        result.put("liked", liked);
        result.put("name", name);
        result.put("uid", uid);

        return result;
    }

}