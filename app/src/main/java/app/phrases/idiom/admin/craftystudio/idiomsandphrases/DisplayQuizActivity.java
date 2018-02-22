package app.phrases.idiom.admin.craftystudio.idiomsandphrases;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import utils.FireBaseHandler;
import utils.Idioms;
import utils.ZoomOutPageTransformer;

public class DisplayQuizActivity extends AppCompatActivity {

    FireBaseHandler fireBaseHandler;

    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;
    ArrayList<Idioms> mIdiomsList = new ArrayList<>();

    ProgressDialog progressDialog;
    String dateName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_quiz);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        mPager = (ViewPager) findViewById(R.id.dailyQuizActivity_viewpager);

        initializeViewPager();

        dateName = getIntent().getExtras().getString("Date");

        toolbar.setTitle(dateName);
        setSupportActionBar(toolbar);

        showDialog("Loading...Please Wait");
        downloadQuestionByDateName(dateName);

    }

    private void initializeViewPager() {

        // Instantiate a ViewPager and a PagerAdapter.

        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);

        //change to zoom
        mPager.setPageTransformer(true, new ZoomOutPageTransformer());

        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                //checkInterstitialAds();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    public void showDialog(String message) {
        progressDialog = new ProgressDialog(DisplayQuizActivity.this);
        progressDialog.setMessage(message);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    public void hideDialog() {
        try {
            progressDialog.cancel();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* Download questions according to DATE name*/
    public void downloadQuestionByDateName(String dateName) {
        fireBaseHandler = new FireBaseHandler();

        // isMoreQuestionAvailable = false;
        fireBaseHandler.downloadIdiomList(30, dateName, new FireBaseHandler.OnQuestionlistener() {


            @Override
            public void onQuestionDownLoad(Idioms idioms, boolean isSuccessful) {

            }


            @Override
            public void onQuestionListDownLoad(ArrayList<Idioms> idiomList, boolean isSuccessful) {

                if (isSuccessful) {

                    mIdiomsList.clear();

                    for (Idioms idioms : idiomList) {
                        mIdiomsList.add(idioms);
                    }
                    initializeViewPager();

                    //Toast.makeText(DailyQuestionActivity.this, questionList.get(0).getQuestionTopicName(), Toast.LENGTH_SHORT).show();

                    //addNativeAds();

                    mPagerAdapter.notifyDataSetChanged();


                } else {
                    Toast.makeText(DisplayQuizActivity.this, "No Data", Toast.LENGTH_SHORT).show();


                }

                hideDialog();
            }

            @Override
            public void onQuestionUpload(boolean isSuccessful) {


            }
        });


    }


    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            //  adsCount++;
            //getting more stories
            if (position == mIdiomsList.size() - 2) {

             /*   if (isMoreQuestionAvailable) {
                    downloadMoreQuestionList();

                }*/
            }

            return IdiomFragment.newInstance(mIdiomsList.get(position), DisplayQuizActivity.this);
        }

        @Override
        public int getCount() {
            return mIdiomsList.size();
        }

    }


}
