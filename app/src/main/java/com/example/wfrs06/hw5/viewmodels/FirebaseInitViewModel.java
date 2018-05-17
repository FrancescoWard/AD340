package com.example.wfrs06.hw5.viewmodels;
import com.example.wfrs06.hw5.datamodels.FirebaseInitModel;
import com.google.firebase.database.DataSnapshot;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class FirebaseInitViewModel {

    private FirebaseInitModel model;
    private HashMap<String,String> map;

    public FirebaseInitViewModel() {
        model = new FirebaseInitModel();
    }

    public void getInit(Consumer<Map<String,String>> resultCallback) {
        model.getInit(
                (DataSnapshot dataSnapshot) -> {
                    Map<String,String> value = (Map<String, String>)dataSnapshot.getValue();
                    //map = new HashMap<>();
                    //map.putAll(value);
                    resultCallback.accept(value);
                },
                (databaseError -> System.out.println("Error reading Hello World: " + databaseError))
        );
    }

    public FirebaseInitModel getModel() {
        return model;
    }

    public void setModel(FirebaseInitModel model) {
        this.model = model;
    }

    public HashMap<String, String> getMap() {
        return map;
    }

    public void setMap(HashMap<String, String> map) {
        this.map = map;
    }

    public void clear() {
        model.clear();
    }
}