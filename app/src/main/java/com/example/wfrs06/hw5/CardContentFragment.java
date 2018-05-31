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

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Picture;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wfrs06.hw5.datamodels.FirebaseInitModel;
import com.example.wfrs06.hw5.models.Match;
import com.example.wfrs06.hw5.viewmodels.FirebaseInitViewModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
//import com.google.gson.reflect.TypeToken;

import java.io.Console;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
//import com.google.gson.*;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.stream.Collectors;

import static android.support.v4.content.ContextCompat.checkSelfPermission;

/**
 * Provides UI for the view with Cards.
 */
public class CardContentFragment extends Fragment {

    public static ViewHolder mViewHolder;
    public static String[] gPlaces;

    // Firebase instance variables
    //private FirebaseInitModel fIM;
    private static HashMap<String,String> gMap;
    private static List<Match> matches;
    private static DatabaseReference mFirebaseDatabaseReference;
    private static FirebaseInitViewModel viewModel;
    private static TextView gDesc;
    private static String[] gPlacesDesc;
    private static List<Drawable> gPlacePictures;
    private static List<String> gPlacePicturesURLs;
    private static Object[] vars;
    private static List<Object[]> gObjs = new ArrayList<>();
    private static ImageView gImgView;
    private static List<String> gUIDs;

    //Location
    LocationManager locationManager;
    static double longitudeNetwork, latitudeNetwork;
    TextView longitudeValueNetwork, latitudeValueNetwork;
    private static List<String> mLatitudes;
    private static List<String> mLongitudes;

    //To make button onclick handler available to xml layout ?
    public static Button gBtnLocationController;

    private static ContentAdapter mAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        CardContentFragment myCardFragment = this;

        matches = new ArrayList<>();
        mLatitudes = new ArrayList<>();
        mLongitudes = new ArrayList<>();
        gMap = new HashMap<>();
        viewModel = new FirebaseInitViewModel();
        gPlacePictures = new ArrayList<>();
        gPlacePicturesURLs = new ArrayList<>();
        gUIDs = new ArrayList<>();

        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference().child("matches");

        View view = inflater.inflate(R.layout.recycler_view, container, false);

        //location
        locationManager = (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);

        viewModel.getModel().getmDatabase().addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                    //Clear lists?
                    gPlacesDesc = null;
                    gPlacesDesc = new String[gObjs.size()];
                    mLatitudes.clear();
                    mLongitudes.clear();
                    gPlacePicturesURLs.clear();
                    gUIDs.clear();

                    Map<String, HashMap<String,Object>> map = (Map) postSnapshot.getValue();
                    Iterator<String> it = map.keySet().iterator();

                    //toggleNetworkUpdates(new Button(view.getContext()));

                    int count = 0;
                    //populate the array of strings here
                    if (null != map)
                    {
                        while (it.hasNext()) {
                            String key = it.next();
                            HashMap<String,Object> objs = new HashMap<>();
                            objs = map.get(key);
                            int i = 0;
                            vars = new Object[6];
                            for(String x : objs.keySet())
                            {
                                vars[i] = objs.get(x);
                                i++;
                            }

                            gObjs.add(vars);

                            //check distance here
                            //Only display picture if within 10 mile radius
                            Location targetLocation = new Location("");
                            targetLocation.setLatitude(Double.parseDouble(gObjs.get(count)[5].toString()));
                            targetLocation.setLongitude(Double.parseDouble(gObjs.get(count)[4].toString()));

                            Location myLocation = new Location("");
                            myLocation.setLatitude(latitudeNetwork);
                            myLocation.setLongitude(longitudeNetwork);

                            float distanceInMeters =  targetLocation.distanceTo(myLocation);
                            Double metersPer10Miles = Double.valueOf(1609.34 * 10);
                            int isWithinRange = Double.compare(distanceInMeters,metersPer10Miles);

                            if (isWithinRange < 0)
                            {
                                gPlacesDesc[count] = gObjs.get(count)[0].toString();
                                mLatitudes.add(gObjs.get(count)[5].toString());
                                mLongitudes.add(gObjs.get(count)[4].toString());
                                gPlacePicturesURLs.add(gObjs.get(count)[2].toString());
                                gUIDs.add(gObjs.get(count)[1].toString());
                            }

                            count++;
                        }

                    }

                    // Set the adapter
                    if (view instanceof RecyclerView) {
                        Context context = view.getContext();
                        RecyclerView recyclerView = (RecyclerView) view;
                        recyclerView.setLayoutManager(new LinearLayoutManager(context));
                        ContentAdapter adapter = null;
                        if (null == mAdapter) {
                            adapter = new ContentAdapter(view.getContext(),/*list of cards to display ? */gPlacesDesc, mLatitudes, mLongitudes, gPlacePicturesURLs, gUIDs, myCardFragment);
                        }
                        else
                        {
                            mAdapter = null;
                            mAdapter = new ContentAdapter(view.getContext(),/*list of cards to display ? */gPlacesDesc, mLatitudes, mLongitudes, gPlacePicturesURLs, gUIDs, myCardFragment);;
                            adapter = mAdapter;
                        }
                        //gBtnLocationController = (Button)adapter.findViewById(R.id.locationControllerNetwork);
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                        recyclerView.setAdapter(adapter);

                        mAdapter = adapter;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return view;
    }

    private boolean checkLocation() {
        if(!isLocationEnabled()) {
            showAlert();
        }
        return isLocationEnabled();
    }

    private boolean isLocationEnabled() {
        return locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    private void showAlert() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this.getContext());
        dialog.setTitle(R.string.enable_location)
                .setMessage(getString(R.string.location_message))
                .setPositiveButton(R.string.location_settings, (paramDialogInterface, paramInt) -> {
                    Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(myIntent);
                })
                .setNegativeButton(R.string.location_cancel, (paramDialogInterface, paramInt) -> {});
        dialog.show();
    }

    public void toggleNetworkUpdates(View view) {
        if(!checkLocation()) {
            return;
        }

        Button button = (Button) view;
        if(button.getText().equals(getResources().getString(R.string.pause))) {
            locationManager.removeUpdates(locationListenerNetwork);
            button.setText(R.string.resume);
        }
        else {
            if (getActivity().checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        0);
            }
                try {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,  1000, 10, locationListenerNetwork);
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000 , 10, locationListenerNetwork);
                    Toast.makeText(this.getContext(), R.string.network_provider_started_running, Toast.LENGTH_LONG).show();
                    button.setText(R.string.pause);

                }
                catch (SecurityException e)
                {
                    //oops
                }
        }
    }

    private final LocationListener locationListenerNetwork = new LocationListener() {
        public void onLocationChanged(Location location) {
            longitudeNetwork = location.getLongitude();
            latitudeNetwork = location.getLatitude();

            getActivity().runOnUiThread(()-> {
                try {
                    //Log.v("RUnning on UI thread with lat and long","RUnning on UI thread with lat and long");
                    /*
                    longitudeValueNetwork.setText(String.format("%s", longitudeNetwork));
                    latitudeValueNetwork.setText(String.format("%s", latitudeNetwork));
                    Toast.makeText(CardContentFragment.this.getContext(), R.string.network_provider_update, Toast.LENGTH_SHORT).show();
                    */
                }
                catch (NullPointerException e)
                {

                }
            });
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {}

        @Override
        public void onProviderEnabled(String s) {}

        @Override
        public void onProviderDisabled(String s) {}
    };


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView picture;
        public TextView name;
        public TextView description;
        public AppCompatImageButton mBtnLike;


        //Location
        public CardContentFragment mFragment;
        Button mBtnLocationController;

        public ViewHolder(final LayoutInflater inflater, ViewGroup parent, /*add parent fragment?*/ CardContentFragment mFragment) {
            super(inflater.inflate(R.layout.item_card, parent, false));

            mFragment.longitudeValueNetwork = itemView.findViewById(R.id.longitudeValueNetwork);
            mFragment.latitudeValueNetwork = itemView.findViewById(R.id.latitudeValueNetwork);
 //           gBtnLocationController = itemView.findViewById(R.id.locationControllerNetwork);
            mBtnLocationController = (Button)itemView.findViewById(R.id.locationControllerNetwork);

            try {

                mBtnLocationController.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mFragment.toggleNetworkUpdates(v);
                    }
                });
            }
            catch (NullPointerException e)
            {
                Log.v("TAG",e.getMessage());
            }

            picture = (ImageView) itemView.findViewById(R.id.card_image);
            name = (TextView) itemView.findViewById(R.id.card_title);
            description = (TextView) itemView.findViewById(R.id.card_text);
            mBtnLike = (AppCompatImageButton) itemView.findViewById(R.id.favorite_button);
            mBtnLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int idx = getAdapterPosition();
                    try {
                        Toast.makeText(inflater.getContext(), "You liked " + gPlacesDesc[idx], Toast.LENGTH_LONG).show();

                        //set the database to update the liked field
                        //viewModel.getModel().getmDatabase().child("matches").child(gUIDs.get(idx)/*gPlacesDesc[idx]*/).child("liked").setValue(true);
                        //TODO fix this to get the guid
                        viewModel.getModel().getmDatabase().child("matches/" + gUIDs.get(idx) + "/liked").setValue("true");
                    }catch (Exception e)
                    {
                        //should not get here
                        int i = 0;
                    }
                }
            });
            gDesc = description;
            gImgView = picture;
        }
    }
        @Override
        public void onPause() {
            viewModel.clear();
            super.onPause();
        }

    /**
     * Adapter to display recycler view.
     */
    public static class ContentAdapter extends RecyclerView.Adapter<ViewHolder> {
        //Set numbers of List in RecyclerView.
        //private static final int LENGTH = 6;

        //private final String[] mPlaces;
        private String[] mPlaceDesc;
        //private final Drawable[] mPlacePictures;
        private final String[] mPlacePictures;
        private List<String> latitudes;
        private List<String> longitudes;
        private List<String> gUIDS;

        //Passing down parent fragment
        private CardContentFragment mfrag;
        private static int mLength;

        private AppCompatImageButton[] mBtnLike;

        public ContentAdapter(final Context context /*pass list of cards*/,String[] placesDesc, List<String> lats, List<String> longs, List<String> picsUrls, List<String> gUIDs, CardContentFragment myCardFragment) {
            Resources resources = context.getResources();
            //mPlaces = places;//resources.getStringArray(R.array.places);
            //gPlaces = mPlaces;
            try {
                mLength = placesDesc.length;
            }
            catch (NullPointerException e)
            {
                mLength = 0;
            }
            mPlaceDesc = placesDesc;
            latitudes = lats;
            longitudes = longs;
            gUIDS = gUIDs;
            //gPlacesDesc = mPlaceDesc;

            //TypedArray a = resources.obtainTypedArray(/*right here*/R.array.places_picture);

            //mPlacePictures = new Drawable[picsUrls.size()];
            mPlacePictures = new String[picsUrls.size()];

            for (int i = 0; i < mPlacePictures.length; i++) {
                mPlacePictures[i] = picsUrls.get(i);
                //gPlacePictures.add(mPlacePictures[i]);
            }

            mfrag = myCardFragment;
            //a.recycle();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            mViewHolder = new ViewHolder(LayoutInflater.from(parent.getContext()), parent, this.mfrag );
            return mViewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            try
            {
                {
                    Picasso.get().load(mPlacePictures[position % mPlacePictures.length]).into(gImgView);
                    holder.name.setText(mPlaceDesc[position % mPlaceDesc.length]);
                    holder.description.setText(mPlaceDesc[position % mPlaceDesc.length]);
                }
            }catch (Exception e)
            {
                Log.v("DB hasn't read yet ??",e.getMessage());
            }
        }

        @Override
        public int getItemCount() {
            return 6;//mLength;
        }
    }
}
