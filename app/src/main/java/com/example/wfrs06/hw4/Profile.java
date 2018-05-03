package com.example.wfrs06.hw4;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import android.support.v4.app.Fragment;

import android.support.v4.app.FragmentManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.media.ExifInterface;
import android.media.Image;
import android.net.Uri;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Profile extends AppCompatActivity {


        private final int SELECT_PHOTO = 1;
        private static final String TAG_RETAINED_FRAGMENT = "RetainedFragment";

        private RetainedFragment mRetainedFragment;

        Bitmap mImg;
        ImageView mImgView;
        TextView mLoginName;
        TextView mLoginAge;
        TextView mOccupation;
        EditText mTxtDesc;
        Button mBtnUpload;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.item_list);

            mImgView = findViewById(R.id.img);
            mLoginName = (TextView)findViewById(R.id.loginName);
            mLoginAge = (TextView)findViewById(R.id.loginAge);
            mOccupation = (TextView)findViewById(R.id.loginOccupation);
            mTxtDesc = (EditText)findViewById(R.id.txtDesc);
            mBtnUpload = (Button)findViewById(R.id.btnUpload);

            // find the retained fragment on activity restarts
            FragmentManager fm = getSupportFragmentManager();

            mRetainedFragment = (RetainedFragment) fm.findFragmentByTag(TAG_RETAINED_FRAGMENT);
            if (null != mRetainedFragment) {

                int orient = 0;
                Uri uri = mRetainedFragment.getUri();
                if (null != uri) {
                    orient = getOrientation(getApplicationContext(), uri);

                    mImgView.setImageBitmap(mRetainedFragment.getData());

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
                    mImgView.setImageBitmap(mRetainedFragment.getData());
                    //mImgView.setRotation(-90);
                }
            }

            // create the fragment and data the first time
            if (mRetainedFragment == null) {
                // add the fragment
                mRetainedFragment = new RetainedFragment();
                fm.beginTransaction().add(mRetainedFragment, TAG_RETAINED_FRAGMENT).commit();
                // load data from a data source or perform any calculation
                mRetainedFragment.setData(((BitmapDrawable)mImgView.getDrawable()).getBitmap());

                mImgView.setImageBitmap(mRetainedFragment.getData());

            }

            try
            {
                //show users info
                mLoginName.setText("Welcome " + getIntent().getStringExtra("uname").toString());
                mLoginAge.setText("Age: " + getIntent().getStringExtra("age").toString());
                mOccupation.setText("Occupation: " + getIntent().getStringExtra("occupation").toString());
            }
            catch (NullPointerException ex)
            {

            }
/*
            mBtnUpload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Code from http://stacktips.com/tutorials/android/writing-image-picker-using-intent-in-android
                    Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                    photoPickerIntent.setType("image/*");
                    startActivityForResult(photoPickerIntent, SELECT_PHOTO);
                }
            });
            */

        }


        //Code from http://stacktips.com/tutorials/android/writing-image-picker-using-intent-in-android
        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
            super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

            switch(requestCode) {
                case SELECT_PHOTO:
                    if(resultCode == RESULT_OK){
                        try {
                            final Uri imageUri = imageReturnedIntent.getData();
                            final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                            Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);

                            int orient = getOrientation(getApplicationContext(), imageUri);

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

        //Code from http://stacktips.com/tutorials/android/writing-image-picker-using-intent-in-android
        @Override
        public boolean onCreateOptionsMenu(Menu menu)
        {
            getMenuInflater().inflate(R.menu.menu_main, menu);
            return true;
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

        static class Adapter extends FragmentPagerAdapter {
            private final List<Fragment> mFragmentList = new ArrayList<>();
            private final List<String> mFragmentTitleList = new ArrayList<>();

            public Adapter(FragmentManager manager) {
                super(manager);
            }

            @Override
            public Fragment getItem(int position) {
                return mFragmentList.get(position);
            }

            @Override
            public int getCount() {
                return mFragmentList.size();
            }

            public void addFragment(Fragment fragment, String title) {
                mFragmentList.add(fragment);
                mFragmentTitleList.add(title);
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return mFragmentTitleList.get(position);
            }
        }

    public void clickUploadButton(View view)
    {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, SELECT_PHOTO);
    }

}

