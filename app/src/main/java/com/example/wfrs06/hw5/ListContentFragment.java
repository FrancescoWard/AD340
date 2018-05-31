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
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;

import static android.app.Activity.RESULT_OK;

/**
 * Provides UI for the view with List.
 */
public class ListContentFragment extends Fragment {

    private static FragmentActivity mActivity;

    private final int SELECT_PHOTO = 1;

    private static final String TAG_RETAINED_FRAGMENT = "RetainedFragment";

    private static RetainedFragment mRetainedFragment;

    Bitmap mImg;
    static ImageView mImgView;
    TextView mLoginName;
    TextView mLoginAge;
    TextView mOccupation;
    EditText mTxtDesc;
    Button mBtnUpload;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.item_list, container, false);

        mBtnUpload = view.findViewById(R.id.btnUpload);

        try{
            mImgView = view.findViewById(R.id.img);
            mImgView.setImageBitmap(mRetainedFragment.getData());

        }
        catch (NullPointerException e)
        {}



        mBtnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                getActivity().startActivityForResult(photoPickerIntent, SELECT_PHOTO);
            }
        });

        setRetainInstance(true);

        return view;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.item_list, parent, false));
        }
    }

    public static class ContentAdapter extends RecyclerView.Adapter<ViewHolder> {
        // Set numbers of List in RecyclerView.
        private static final int LENGTH = 1;

        public ContentAdapter() {
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()), parent);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            // no-op
        }

        @Override
        public int getItemCount() {
            return LENGTH;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        mImgView = mActivity.findViewById(R.id.img);
        mLoginName = (TextView) mActivity.findViewById(R.id.loginName);
        mLoginAge = (TextView) mActivity.findViewById(R.id.loginAge);
        mOccupation = (TextView) mActivity.findViewById(R.id.loginOccupation);
        mTxtDesc = (EditText) mActivity.findViewById(R.id.txtDesc);
        mBtnUpload = (Button) mActivity.findViewById(R.id.btnUpload);


        FragmentManager fm = mActivity.getSupportFragmentManager();

        mRetainedFragment = (RetainedFragment) fm.findFragmentByTag(TAG_RETAINED_FRAGMENT);
        if (null != mRetainedFragment) {

            int orient = 0;
            Uri uri = mRetainedFragment.getUri();
            if (null != uri) {
                orient = getOrientation(mActivity.getApplicationContext(), uri);

                mImgView.setImageBitmap(mRetainedFragment.getData());

                switch (orient) {
                    case 270:
                        mImgView.setRotation(-90);
                        break;
                    case 90:
                        mImgView.setRotation(90);
                        break;
                    default:
                        break;
                }
            } else {
                mImgView.setImageBitmap(mRetainedFragment.getData());
                //mImgView.setRotation(-90);
            }
        }

        // create the fragment and data the first time
        if (mRetainedFragment == null) {
            // add the fragment
            mRetainedFragment = new RetainedFragment();
            fm.beginTransaction().add(mRetainedFragment, TAG_RETAINED_FRAGMENT).commitAllowingStateLoss();
            // load data from a data source or perform any calculation
            mRetainedFragment.setData(((BitmapDrawable) mImgView.getDrawable()).getBitmap());

            mImgView.setImageBitmap(mRetainedFragment.getData());

        }

        try {
            //show users info
            mLoginName.setText("Welcome " + mActivity.getIntent().getStringExtra("uname").toString());
            mLoginAge.setText("Age: " + mActivity.getIntent().getStringExtra("age").toString());
            mOccupation.setText("Occupation: " + mActivity.getIntent().getStringExtra("occupation").toString());
        } catch (NullPointerException ex) {

        }

        switch (requestCode) {
            case SELECT_PHOTO:
                if (resultCode == RESULT_OK) {
                    try {
                        final Uri imageUri = imageReturnedIntent.getData();
                        final InputStream imageStream = mActivity.getContentResolver().openInputStream(imageUri);
                        Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);

                        int orient = getOrientation(mActivity.getApplicationContext(), imageUri);

                        mImgView.setImageBitmap(selectedImage);

                        switch (orient) {
                            case 270:
                                mImgView.setRotation(-90);
                                break;
                            case 90:
                                mImgView.setRotation(90);
                                break;
                            default:
                                break;
                        }

                        this.mImg = selectedImage;
                        mRetainedFragment.setData(((BitmapDrawable) mImgView.getDrawable()).getBitmap());
                        mRetainedFragment.setUri(imageUri);
                    } /*catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }*/ catch (IOException ioe) {

                    }

                }
                super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        }
    }

    public static int getOrientation(Context context, Uri photoUri) {
        /* it's on the external media. */
        Cursor cursor = context.getContentResolver().query(photoUri,
                new String[]{MediaStore.Images.ImageColumns.ORIENTATION}, null, null, null);

        if (cursor.getCount() != 1) {
            return -1;
        }

        cursor.moveToFirst();
        return cursor.getInt(0);
    }

    public static class RetainedFragment extends Fragment {

        private Uri uri;

        // data object we want to retain
        private Bitmap data;

        // this method is only called once for this fragment
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            // retain this fragment
            setRetainInstance(true);
        }

        public void setData(Bitmap data) {
            this.data = data;
        }

        public Bitmap getData() {
            return data;
        }

        public Uri getUri() {
            return uri;
        }

        public void setUri(Uri uri) {
            this.uri = uri;
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (FragmentActivity) context;

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}