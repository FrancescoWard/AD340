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
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Picture;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.google.firebase.database.ValueEventListener;
import com.google.gson.reflect.TypeToken;

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
import com.google.gson.*;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.stream.Collectors;

/**
 * Provides UI for the view with Cards.
 */
public class CardContentFragment extends Fragment {

    public static ViewHolder mViewHolder;
    public static String[] gPlaces;


    // Firebase instance variables
    private FirebaseInitModel fIM;
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


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        matches = new ArrayList<>();

        gMap = new HashMap<>();
        viewModel = new FirebaseInitViewModel();
        gPlacePictures = new ArrayList<>();
        gPlacePicturesURLs = new ArrayList<>();
        gUIDs = new ArrayList<>();

        setRetainInstance(true);

        //KYle's code
        View view = inflater.inflate(R.layout.recycler_view, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));

            ViewHolder.ContentAdapter adapter = new ViewHolder.ContentAdapter(view.getContext());
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView.setAdapter(adapter);
        }

        viewModel.getModel().getmDatabase().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Map<String, HashMap<String,Object>> map = (Map) postSnapshot.getValue();
                    Iterator<String> it = map.keySet().iterator();
                    //String[] messages = new String[map.keySet().size()];
                    int count = 0;
                    //populate the array of strings here
                    if (map != null)
                    {
                        while (it.hasNext()) {
                            String key = it.next();
                            HashMap<String,Object> objs = new HashMap<>();
                            objs = map.get(key);
                            int i = 0;
                            vars = new Object[4];
                            for(String x : objs.keySet())
                            {
                                vars[i] = objs.get(x);
                                i++;
                            }
                            gObjs.add(vars);
                            gPlacesDesc[count] = gObjs.get(count)[0].toString();
                            //gPlacePicturesURLs = new String[map.size()];
                            gPlacePicturesURLs.add(gObjs.get(count)[2].toString());
                            gUIDs.add(gObjs.get(count)[1].toString());

                            count++;


                        }

                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return view;


    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView picture;
        public TextView name;
        public TextView description;
        public AppCompatImageButton mBtnLike;


        public ViewHolder(final LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.item_card, parent, false));


            picture = (ImageView) itemView.findViewById(R.id.card_image);
            name = (TextView) itemView.findViewById(R.id.card_title);
            description = (TextView) itemView.findViewById(R.id.card_text);
            mBtnLike = (AppCompatImageButton) itemView.findViewById(R.id.favorite_button);
            mBtnLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int idx = getAdapterPosition();
                    Toast.makeText(inflater.getContext(), "You liked " + gPlacesDesc[idx], Toast.LENGTH_LONG).show();

                    //set the database to update the liked field
                    //viewModel.getModel().getmDatabase().child("matches").child(gUIDs.get(idx)/*gPlacesDesc[idx]*/).child("liked").setValue(true);
                    viewModel.getModel().getmDatabase().child("matches/" + gUIDs.get(idx) + "/liked").setValue("true");
                }
            });
            gDesc = description;
            gImgView = picture;
        }

        /**
         * Adapter to display recycler view.
         */
        public static class ContentAdapter extends RecyclerView.Adapter<ViewHolder> {
            // Set numbers of List in RecyclerView.
            private static final int LENGTH = 6;
            private final String[] mPlaces;
            private String[] mPlaceDesc;
            private final Drawable[] mPlacePictures;


            private AppCompatImageButton[] mBtnLike;

            public ContentAdapter(final Context context) {
                Resources resources = context.getResources();
                mPlaces = resources.getStringArray(R.array.places);
                gPlaces = mPlaces;
                mPlaceDesc = new String[6];
                gPlacesDesc = mPlaceDesc;

                TypedArray a = resources.obtainTypedArray(R.array.places_picture);
                mPlacePictures = new Drawable[a.length()];
                //gPlacePictures = new ArrayList<>();
                //mBtnLike = new AppCompatImageButton[a.length()];
                for (int i = 0; i < mPlacePictures.length; i++) {
                    mPlacePictures[i] = a.getDrawable(i);
                    gPlacePictures.add(mPlacePictures[i]);
                }

                a.recycle();
            }

            @Override
            public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                mViewHolder = new ViewHolder(LayoutInflater.from(parent.getContext()), parent);
                return mViewHolder;
            }

            @Override
            public void onBindViewHolder(ViewHolder holder, int position) {
                //holder.picture.setImageDrawable(gPlacePictures.get(position % gPlacePictures.size()));
                try {
                    Picasso.get().load(gPlacePicturesURLs.get(position % gPlacePictures.size())).into(gImgView);
                }catch (Exception e)
                {

                }
                    holder.name.setText(gPlacesDesc[position % gPlacesDesc.length]);
                    holder.description.setText(gPlacesDesc[position % gPlacesDesc.length]);
            }

            @Override
            public int getItemCount() {
                return LENGTH;
            }

        }



    }
        @Override
        public void onPause() {
            viewModel.clear();
            super.onPause();
        }

}
