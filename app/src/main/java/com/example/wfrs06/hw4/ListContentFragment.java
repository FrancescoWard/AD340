package com.example.wfrs06.hw4;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import static android.app.Activity.RESULT_OK;

public class ListContentFragment extends Fragment {

    private final int SELECT_PHOTO = 1;
    private static final String TAG_RETAINED_FRAGMENT = "RetainedFragment";

    private Profile.RetainedFragment mRetainedFragment;

    Bitmap mImg;
    ImageView mImgView;
    TextView mLoginName;
    TextView mLoginAge;
    TextView mOccupation;
    EditText mTxtDesc;
    Button mBtnUpload;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RecyclerView recyclerView = (RecyclerView) inflater.inflate(
                R.layout.recycler_view, container, false);
        ContentAdapter adapter = new ContentAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);

        // Set padding for Tiles (not needed for Cards/Lists!)
        int tilePadding = getResources().getDimensionPixelSize(R.dimen.tile_padding);
        recyclerView.setPadding(tilePadding, tilePadding, tilePadding, tilePadding);

        mImgView = getActivity().findViewById(R.id.img);
        mLoginName = (TextView)getActivity().findViewById(R.id.loginName);
        mLoginAge = (TextView)getActivity().findViewById(R.id.loginAge);
        mOccupation = (TextView)getActivity().findViewById(R.id.loginOccupation);
        mTxtDesc = (EditText)getActivity().findViewById(R.id.txtDesc);
        mBtnUpload = (Button)getActivity().findViewById(R.id.btnUpload);

        // find the retained fragment on activity restarts
        FragmentManager fm = getActivity().getSupportFragmentManager();

        mRetainedFragment = (Profile.RetainedFragment) fm.findFragmentByTag(TAG_RETAINED_FRAGMENT);
        if (null != mRetainedFragment) {

            int orient = 0;
            Uri uri = mRetainedFragment.getUri();
            if (null != uri) {
                orient = getOrientation(getActivity().getApplicationContext(), uri);

                //mImgView.setImageBitmap(mRetainedFragment.getData());

                switch (orient)
                {
                    case 270: mImgView.setRotation(-90);
                        break;
                    case 90: mImgView.setRotation(90);
                        break;
                    default: break;
                }
            }
            else
            {
                //mImgView.setImageBitmap(mRetainedFragment.getData());
                //mImgView.setRotation(-90);
            }
        }

        // create the fragment and data the first time
        if (mRetainedFragment == null) {
            // add the fragment
            mRetainedFragment = new Profile.RetainedFragment();
            fm.beginTransaction().add(mRetainedFragment, TAG_RETAINED_FRAGMENT).commit();
            // load data from a data source or perform any calculation
            //mRetainedFragment.setData(((BitmapDrawable)mImgView.getDrawable()).getBitmap());

            //mImgView.setImageBitmap(mRetainedFragment.getData());

        }

        try
        {
            //show users info
            mLoginName.setText("Welcome " + getActivity().getIntent().getStringExtra("uname").toString());
            mLoginAge.setText("Age: " + getActivity().getIntent().getStringExtra("age").toString());
            mOccupation.setText("Occupation: " + getActivity().getIntent().getStringExtra("occupation").toString());
        }
        catch (NullPointerException ex)
        {

        }

        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        return recyclerView;
    }

    public ImageView getmImgView() {
        return mImgView;
    }

    public void setmImgView(ImageView mImgView) {
        this.mImgView = mImgView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        switch(requestCode) {
            case SELECT_PHOTO:
                if(resultCode == RESULT_OK){
                    try {
                        final Uri imageUri = imageReturnedIntent.getData();
                        final InputStream imageStream = getActivity().getContentResolver().openInputStream(imageUri);
                        Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);

                        int orient = getOrientation(getActivity().getApplicationContext(), imageUri);

                        mImgView.setImageBitmap(selectedImage);

                        switch (orient)
                        {
                            case 270: mImgView.setRotation(-90);
                                break;
                            case 90: mImgView.setRotation(90);
                                break;
                            default: break;
                        }

                        this.mImg = selectedImage;
                        mRetainedFragment.setData(((BitmapDrawable) mImgView.getDrawable()).getBitmap());
                        mRetainedFragment.setUri(imageUri);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    catch (IOException ioe)
                    {

                    }
                    //TODO implement file too large exception handling
                }
        }
    }

    public static int getOrientation(Context context, Uri photoUri) {
        /* it's on the external media. */
        Cursor cursor = context.getContentResolver().query(photoUri,
                new String[] { MediaStore.Images.ImageColumns.ORIENTATION }, null, null, null);

        if (cursor.getCount() != 1) {
            return -1;
        }

        cursor.moveToFirst();
        return cursor.getInt(0);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.item_list,parent, false));

        }
    }

    //Code from https://developer.android.com/reference/android/app/Fragment.html
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
}

