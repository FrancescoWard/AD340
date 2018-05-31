package com.example.wfrs06.hw5.datamodels;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.function.Consumer;

public class FirebaseInitModel {

    private DatabaseReference mDatabase;
    private HashMap<DatabaseReference, ValueEventListener> listeners;

    public FirebaseInitModel() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        listeners = new HashMap<>();
    }

    public void getInit(final Consumer<DataSnapshot> dataChangedCallback, final Consumer<DatabaseError> dataErrorCallback) {
        // This is where we can construct our path
        DatabaseReference initRef = mDatabase.child("matches");
        ValueEventListener helloWorldListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dataChangedCallback.accept(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                dataErrorCallback.accept(databaseError);
            }
        };
        initRef.addValueEventListener(helloWorldListener);
        listeners.put(initRef, helloWorldListener);
    }

    public DatabaseReference getmDatabase() {
        return mDatabase;
    }

    public void setmDatabase(DatabaseReference mDatabase) {
        this.mDatabase = mDatabase;
    }

    public HashMap<DatabaseReference, ValueEventListener> getListeners() {
        return listeners;
    }

    public void setListeners(HashMap<DatabaseReference, ValueEventListener> listeners) {
        this.listeners = listeners;
    }

    public void clear() {
        // Clear all the listeners onPause
        listeners.forEach(Query::removeEventListener);
    }

}