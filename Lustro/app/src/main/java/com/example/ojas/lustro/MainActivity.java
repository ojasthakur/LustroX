package com.example.ojas.lustro;

import android.content.Intent;
import android.location.Location;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
//import android.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.iid.FirebaseInstanceId;

public class MainActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    //The {@link ViewPager} that will host the section contents.
    private ViewPager mViewPager;
    private FirebaseAuth mFirebaseAuth;
    private String currentUserId;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        //getSupportActionBar().setIcon(R.drawable.LUSTRO);
        getSupportActionBar().setLogo(R.drawable.lustro);


        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);


        mFirebaseAuth= FirebaseAuth.getInstance();
        if(mFirebaseAuth.getCurrentUser() == null)
        {
            //user is not logged in
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }
        ;

    }








    public void setCurrentUserId(String userId)
    {
        this.currentUserId = userId;
    }
    public String getCurrentUserId()
    {
        return currentUserId;
    }









  /*      @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.menu_main_np)
        {
            mViewPager.setCurrentItem(1);
            return true;
        }
        else if (item.getItemId() == R.id.menu_main_update)
        {
            mViewPager.setCurrentItem(0);

            return true;
        }
        return false;
    }

    public void onClickButtonLogout(View view)
    {
        mFirebaseAuth.signOut();
        finish();
        startActivity(new Intent(this, LoginActivity.class));
    }
    public void onClickButtonUpdateProfile(View view)
    {
        //finish();
        startActivity(new Intent(this, UpdateProfileActivity.class));
    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        //I believe its creating a sectionsPagerAdapter object and initializing it with its parent class constructor
        public SectionsPagerAdapter(FragmentManager fm)
        {
            super(fm);   //calls the parent constructor with the argument "fm"

        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            switch(position){
                case 0: return FragmentUserAccount.newInstance();
                case 1: return FragmentPeople.newInstance();
                //case 2: return PlaceholderFragment.newInstance(position);
           }
            //return PlaceholderFragment.newInstance(position + 1);
            return null;
        }

        @Override
        public int getCount() {
            // Show 3 total pages. // change here to change the number of tabs
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Profile";
                case 1:
                    return "New People";
                //case 2: return "Ongoing";
            }
            return null;
        }
    }
}

