package cs571.evehw9;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.design.widget.TabLayout;

import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

//import java.math.BigDecimal;
import java.sql.Time;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;


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

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    public ViewPager mViewPager;

    public static String StockSymbol;
    public static String LastPrice;
    public static String Change;
    public static String ChangePercent;
    public static String Timestamp;
    public static String Volume;
    public static String High;
    public static String Low;
    public static String Open;
    public static String Range;
    public static String LastLastPrice;
    public static String ChangeResult;
    public static String Close;
    public static Boolean error;
    public static int Sign;
    public static RequestQueue requestQueue;
    public ImageView imageview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent entrance = getIntent();
        String name = entrance.getStringExtra("symbol");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(name);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        extractData(getIntent());

        requestQueue = Volley.newRequestQueue(MainActivity.this);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mSectionsPagerAdapter.notifyDataSetChanged();
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }
    @Override
    public void onBackPressed(){
        super.onBackPressed();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId() == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return corresponding Fragment (defined as a static inner class below).

            switch (position) {
                case 0:
//                    return Fragment.instantiate(getBaseContext(), CurrentFragment.class.getName());
                    return new CurrentFragment();
                case 1:
//                    return Fragment.instantiate(getBaseContext(), HistoricalFragment.class.getName());
                    return new HistoricalFragment();
                case 2:
//                    return Fragment.instantiate(getBaseContext(), NewsFragment.class.getName());
                    return new NewsFragment();
            }
            return null;
        }



        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "CURRENT";
                case 1:
                    return "HISTORICAL";
                case 2:
                    return "NEWS";
            }
            return null;
        }

    }

    public void extractData(Intent intent){
        if(intent.getStringExtra("error") == "1"){
            error = false;
        }else {
            error = true;
            StockSymbol = intent.getStringExtra("symbol");
            LastPrice = intent.getStringExtra("the_last_close").substring(0, intent.getStringExtra("the_last_close").length() - 2);
            LastLastPrice = intent.getStringExtra("the_second_last_close").substring(0, intent.getStringExtra("the_last_close").length() - 2);
            Float num1 = Float.parseFloat(LastPrice);
            Float num2 = Float.parseFloat(LastLastPrice);
            if ((num1 - num2) < 0) {
                Sign = R.drawable.down;
            } else {
                Sign = R.drawable.up;
            }
            Change = String.format("%.2f", num1 - num2);
            ChangePercent = String.format("%.2f", ((num1 - num2) / num2 * 100)) + "%";
            ChangeResult = Change + "(" + ChangePercent + ")";
            Timestamp = intent.getStringExtra("date");
            if (Timestamp.length() == 10) {
                Timestamp = Timestamp + " 16:00:00 EDT";
                Close = intent.getStringExtra("the_second_last_close").substring(0, intent.getStringExtra("the_last_close").length() - 2);
            } else {
                Timestamp = Timestamp + " EDT";
                Close = intent.getStringExtra("the_last_close").substring(0, intent.getStringExtra("the_last_close").length() - 2);
            }

            Volume = intent.getStringExtra("volume");
            High = intent.getStringExtra("high").substring(0, intent.getStringExtra("high").length() - 2);
            Low = intent.getStringExtra("low").substring(0, intent.getStringExtra("low").length() - 2);
            Range = Low + " - " + High;
            Open = intent.getStringExtra("open").substring(0, intent.getStringExtra("open").length() - 2);
        }
    }

}
