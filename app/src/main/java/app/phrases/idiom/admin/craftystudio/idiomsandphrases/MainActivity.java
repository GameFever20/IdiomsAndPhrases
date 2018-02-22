package app.phrases.idiom.admin.craftystudio.idiomsandphrases;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Random;

import utils.AppRater;
import utils.ClickListener;
import utils.DataBaseHandler;
import utils.DateListAdapter;
import utils.FireBaseHandler;
import utils.IdiomListAdapter;
import utils.Idioms;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    ArrayList<Idioms> mArraylist = new ArrayList<>();
    ArrayList<String> mDateArraylist = new ArrayList<>();

    ListView idiomListListview;
    IdiomListAdapter adapter;

    DateListAdapter dateListAdapter;


    ProgressDialog progressDialog;

    //random no generate
    final int min = 1;
    final int max = 100;
    Random random = new Random();
    final int r = random.nextInt((max - min) + 1) + min;
    private boolean isLoading = false;

    TextView noBookmarkAddedTextview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadIdiom();

                FireBaseHandler fireBaseHandler = new FireBaseHandler();
                fireBaseHandler.uploadDateName("15 Feb 2018", new FireBaseHandler.OnDatelistener() {
                    @Override
                    public void onDateDownLoad(String date, boolean isSuccessful) {

                    }

                    @Override
                    public void onDateListDownLoad(ArrayList<String> dateList, boolean isSuccessful) {

                    }

                    @Override
                    public void onDateUpload(boolean isSuccessful) {

                    }
                });
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        mArraylist = new ArrayList<>();
        idiomListListview = (ListView) findViewById(R.id.idiomsList_main_listview);

        noBookmarkAddedTextview = (TextView) findViewById(R.id.no_bookmark_textview);


        dateListAdapter = new DateListAdapter(getApplicationContext(), R.layout.custom_textview, mDateArraylist);

        idiomListListview.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {
                if (!idiomListListview.canScrollVertically(1)) {

                    if (!isLoading) {
                        downloadMoreIdiomList();
                        //Toast.makeText(MainActivity.this, "Loading", Toast.LENGTH_SHORT).show();
                    }

                }
            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {

            }
        });


        showDialog("Loading...Please wait");
        downloadTopicList();

        //App rater
        AppRater appRater = new AppRater();
        appRater.app_launched(MainActivity.this);

    }

    private void downloadMoreIdiomList() {

        isLoading = true;
        FireBaseHandler fireBaseHandler = new FireBaseHandler();

        fireBaseHandler.downloadMoreIdiomList(15, mArraylist.get(mArraylist.size() - 1).getRandomNo(), new FireBaseHandler.OnQuestionlistener() {
            @Override
            public void onQuestionDownLoad(Idioms idioms, boolean isSuccessful) {

            }

            @Override
            public void onQuestionListDownLoad(ArrayList<Idioms> idiomList, boolean isSuccessful) {

                isLoading = false;

                if (isSuccessful) {
                    //  Toast.makeText(TopicActivity.this, "size is " + topicList.size(), Toast.LENGTH_SHORT).show();


                    //mArraylist = topicList;


                    for (Idioms name : idiomList) {
                        mArraylist.add(name);
                    }

                    adapter.notifyDataSetChanged();

                }
                hideDialog();


            }

            @Override
            public void onQuestionUpload(boolean isSuccessful) {

            }
        });


    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_idiom:
                    showDialog("Loading...Please wait");
                    noBookmarkAddedTextview.setVisibility(View.GONE);
                    downloadTopicList();
                    return true;

                case R.id.navigation_bookmark:
                    showDialog("Loading...Please wait");
                    noBookmarkAddedTextview.setVisibility(View.GONE);
                    downloadBookmarkIdiomList();
                    return true;

                case R.id.navigation_quiz:
                    showDialog("Loading...Please wait");
                    noBookmarkAddedTextview.setVisibility(View.GONE);
                    downloadDateList();
                    return true;


            }
            return false;
        }

    };

    public void downloadDateList() {
        FireBaseHandler fireBaseHandler = new FireBaseHandler();


        fireBaseHandler.downloadDateList(30, new FireBaseHandler.OnDatelistener() {
            @Override
            public void onDateDownLoad(String date, boolean isSuccessful) {

            }

            @Override
            public void onDateListDownLoad(ArrayList<String> dateList, boolean isSuccessful) {

                if (isSuccessful) {

                    mDateArraylist.clear();

                    for (String name : dateList) {
                        mDateArraylist.add(name);
                    }

                    dateListAdapter = new DateListAdapter(getApplicationContext(), R.layout.custom_textview, mDateArraylist);

                    dateListAdapter.notifyDataSetChanged();
                    dateListAdapter.setOnItemCLickListener(new ClickListener() {
                        @Override
                        public void onItemCLickListener(View view, int position) {
                            openMainActivity(mDateArraylist.get(position));
                            //  Toast.makeText(TopicActivity.this, "In Test " + " Selected " + textview.getText().toString() + " Postion is " + position, Toast.LENGTH_SHORT).show();

                            try {
                                //Answers.getInstance().logCustom(new CustomEvent("Daily Quiz open").putCustomAttribute("Date Name", textview.getText().toString()));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                        }
                    });


                    idiomListListview.post(new Runnable() {
                        public void run() {
                            idiomListListview.setAdapter(dateListAdapter);
                        }
                    });


                }
                hideDialog();

            }

            @Override
            public void onDateUpload(boolean isSuccessful) {

            }


        });
    }


    public void openMainActivity(String date) {

        Intent intent = new Intent(MainActivity.this, DisplayQuizActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("Date", date);
        intent.putExtras(bundle);
        startActivity(intent);

    }


    private void downloadTopicList() {
        FireBaseHandler fireBaseHandler = new FireBaseHandler();

        fireBaseHandler.downloadIdiomList(10, r, new FireBaseHandler.OnQuestionlistener() {
            @Override
            public void onQuestionDownLoad(Idioms idioms, boolean isSuccessful) {

            }

            @Override
            public void onQuestionListDownLoad(ArrayList<Idioms> idiomList, boolean isSuccessful) {

                if (isSuccessful) {
                    //  Toast.makeText(TopicActivity.this, "size is " + topicList.size(), Toast.LENGTH_SHORT).show();


                    //mArraylist = topicList;

                    mArraylist.clear();

                    for (Idioms name : idiomList) {
                        mArraylist.add(name);
                    }


                    adapter = new IdiomListAdapter(getApplicationContext(), R.layout.custom_cardview, mArraylist);

                    adapter.setOnItemCLickListener(new ClickListener() {
                        @Override
                        public void onItemCLickListener(View view, int position) {
                            TextView textview = (TextView) view;


                            //openMainActivity(0, textview.getText().toString(), null);
                            //  Toast.makeText(TopicActivity.this, " Selected " + textview.getText().toString(), Toast.LENGTH_SHORT).show();

                            try {
                                // Answers.getInstance().logCustom(new CustomEvent("Topic open").putCustomAttribute("topic", textview.getText().toString()));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                        }
                    });

                    idiomListListview.post(new Runnable() {
                        public void run() {
                            idiomListListview.setAdapter(adapter);
                        }
                    });


                }
                hideDialog();

            }

            @Override
            public void onQuestionUpload(boolean isSuccessful) {

            }


        });


    }

    private void downloadBookmarkIdiomList() {
        DataBaseHandler db = new DataBaseHandler(MainActivity.this);

        mArraylist.clear();
        mDateArraylist.clear();

        // Reading all bookmarked Question
        ArrayList<Idioms> bookmark_questions = db.getAllBookmarkedIdioms();

        if (!bookmark_questions.isEmpty()) {

            for (Idioms idioms : bookmark_questions) {
                mArraylist.add(idioms);


            }
        } else {
            noBookmarkAddedTextview.setVisibility(View.VISIBLE);
            noBookmarkAddedTextview.setText("No Bookmark Added");


        }
        adapter.notifyDataSetChanged();
        idiomListListview.setAdapter(adapter);

        dateListAdapter.notifyDataSetChanged();

        hideDialog();

    }

    public void showDialog(String message) {
        progressDialog = new ProgressDialog(MainActivity.this);
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


    public void uploadIdiom() {
        final int min = 1;
        final int max = 100;
        Random random = new Random();
        final int r = random.nextInt((max - min) + 1) + min;

        Idioms idioms = new Idioms();
        idioms.setIdiomName("Get tha");
        idioms.setIdiomMeaning("ch samajle");
        idioms.setIdiomExample("Priyank Straight meko choclate chaiye");
        idioms.setUploadDate(System.currentTimeMillis());
        idioms.setIdiomCategory("Black");
        idioms.setImportantIdiom(true);
        idioms.setIdiomDateName("15 Feb 2018");
        idioms.setRandomNo(r);
        idioms.setOptionA("ab");
        idioms.setOptionB("bc");
        idioms.setOptionC("cd");
        idioms.setOptionD("de");
        idioms.setCorrectAnswer("de");


        FireBaseHandler fireBaseHandler = new FireBaseHandler();
        fireBaseHandler.uploadIdiom(idioms, new FireBaseHandler.OnQuestionlistener() {
            @Override
            public void onQuestionDownLoad(Idioms idioms, boolean isSuccessful) {

            }

            @Override
            public void onQuestionListDownLoad(ArrayList<Idioms> idiomList, boolean isSuccessful) {

            }

            @Override
            public void onQuestionUpload(boolean isSuccessful) {
                if (isSuccessful) {
                    Toast.makeText(MainActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_suggest) {

            giveSuggestion();
        } else if (id == R.id.nav_share) {

            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");

            //sharingIntent.putExtra(Intent.EXTRA_STREAM, newsMetaInfo.getNewsImageLocalPath());

            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT,
                    " " + "\n\n https://goo.gl/4SBe3L " + "\n Idioms and Phrases app \n Download App Now");
            startActivity(Intent.createChooser(sharingIntent, "Share Idioms via"));


        } else if (id == R.id.nav_rate) {
            onRateUs();
        } else if (id == R.id.nav_logical_reasoning) {
            onLogicalReasoningClick();
        } else if (id == R.id.nav_personality_development) {
            onPersonalityDevelopment();
        } else if (id == R.id.nav_daily_editorial) {
            onDailyEditorialClick();
        } else if ((id == R.id.nav_pib)) {
            onPIBClick();
        } else if ((id == R.id.nav_basic_Computer)) {
            onBasicComputerClick();
        } else if ((id == R.id.nav_short_key)) {
            onShortKeyClick();
        } else if ((id == R.id.nav_aptitude)) {
            onAptitudeMasterClick();
        } else if ((id == R.id.nav_english_grammar)) {
            onEnglishGrammarClick();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void onEnglishGrammarClick() {
        try {
            String link = "https://play.google.com/store/apps/details?id=app.english.grammar.craftystudio.englishgrammar";
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(link)));

            //Answers.getInstance().logCustom(new CustomEvent("PIB CLick"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void onPIBClick() {
        try {
            String link = "https://play.google.com/store/apps/details?id=app.crafty.studio.current.affairs.pib";
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(link)));

            //Answers.getInstance().logCustom(new CustomEvent("PIB CLick"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void onBasicComputerClick() {
        try {
            String link = "https://play.google.com/store/apps/details?id=app.computer.basic.quiz.craftystudio.computerbasic";
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(link)));

            //  Answers.getInstance().logCustom(new CustomEvent("Basic Computer CLick"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void onShortKeyClick() {
        try {
            String link = "https://play.google.com/store/apps/details?id=app.key.ashort.craftystudio.shortkeysapp";
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(link)));

            // Answers.getInstance().logCustom(new CustomEvent("ShortKey CLick"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void onDailyEditorialClick() {
        try {
            String link = "https://play.google.com/store/apps/details?id=app.craftystudio.vocabulary.dailyeditorial";
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(link)));

            //Answers.getInstance().logCustom(new CustomEvent("Daily Editorial"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void onPersonalityDevelopment() {
        try {
            String link = "https://play.google.com/store/apps/details?id=app.story.craftystudio.shortstory";
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(link)));

            //Answers.getInstance().logCustom(new CustomEvent("Personality Development"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void onLogicalReasoningClick() {
        try {
            String link = "https://play.google.com/store/apps/details?id=app.reasoning.logical.quiz.craftystudio.logicalreasoning";
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(link)));

            //Answers.getInstance().logCustom(new CustomEvent("Logical Reasoning Click"));


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void onAptitudeMasterClick() {
        try {
            String link = "https://play.google.com/store/apps/details?id=app.aptitude.quiz.craftystudio.aptitudequiz";
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(link)));

            //Answers.getInstance().logCustom(new CustomEvent("Aptitude Master Click"));


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void giveSuggestion() {

        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"acraftystudio@gmail.com"});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Suggestion For " + getResources().getString(R.string.app_name));
        emailIntent.setType("text/plain");

        startActivity(Intent.createChooser(emailIntent, "Send mail From..."));

    }

    private void onRateUs() {
        try {
            String link = "https://play.google.com/store/apps/details?id=app.phrases.idiom.admin.craftystudio.idiomsandphrases";
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(link)));
        } catch (Exception e) {

        }
    }


}
