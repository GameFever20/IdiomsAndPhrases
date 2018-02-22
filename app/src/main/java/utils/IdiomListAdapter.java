package utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.dynamiclinks.DynamicLink;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.ShortDynamicLink;

import java.util.ArrayList;

import app.phrases.idiom.admin.craftystudio.idiomsandphrases.MainActivity;
import app.phrases.idiom.admin.craftystudio.idiomsandphrases.R;

/**
 * Created by Aisha on 10/7/2017.
 */

public class IdiomListAdapter extends ArrayAdapter<String> {

    ArrayList<Idioms> mIdiomList;
    Context mContext;

    int mResourceID;

    ClickListener clickListener;

    ProgressDialog progressDialog;


    public IdiomListAdapter(Context context, int resource, ArrayList<Idioms> topicList) {
        super(context, resource);
        this.mIdiomList = topicList;
        this.mContext = context;
        this.mResourceID = resource;
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.custom_cardview, null, false);
        }
        //getting the view
        //  View view = layoutInflater.inflate(mResourceID, null, false);


        TextView idiomNameTextview = (TextView) convertView.findViewById(R.id.idiom_name);
        TextView idiomMeaningTextview = (TextView) convertView.findViewById(R.id.idiom_meaning);
        TextView idiomExampleTextview = (TextView) convertView.findViewById(R.id.idiom_example);
        ImageView idiomImportantImageview = (ImageView) convertView.findViewById(R.id.idiom_important_imageview);
        ImageView idiomBookmarkImageview = (ImageView) convertView.findViewById(R.id.idiom_bookmark_imageview);
        ImageView idiomShareImageview = (ImageView) convertView.findViewById(R.id.idiom_share_imageview);

        idiomNameTextview.setText((String) mIdiomList.get(position).getIdiomName());
        idiomMeaningTextview.setText((String) mIdiomList.get(position).getIdiomMeaning());
        idiomExampleTextview.setText((String) mIdiomList.get(position).getIdiomExample());

        if (mIdiomList.get(position).isImportantIdiom()) {
            idiomImportantImageview.setVisibility(View.VISIBLE);
        }

        idiomBookmarkImageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBookMarkIdiom(mIdiomList.get(position));
            }
        });

        idiomShareImageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openShareDialog(mIdiomList.get(position));
            }
        });

        // Toast.makeText(mContext, "Topic set", Toast.LENGTH_SHORT).show();
        return convertView;

    }

    public void onBookMarkIdiom(Idioms idiom) {
        //Adding Question to Bookmark
        DataBaseHandler db = new DataBaseHandler(mContext);
        /**
         * CRUD Operations
         * */
        // Inserting Contacts
        //Log.d("Insert: ", "Inserting ..");
        db.addBookMark(idiom);
        Toast.makeText(mContext, "Idiom Bookmarked", Toast.LENGTH_SHORT).show();

    }

    public void showDialog(String message) {
        progressDialog = new ProgressDialog(mContext);
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


    private void onShareClick(final Idioms idioms) {
        showDialog("Creating Link...Please Wait");
        Task<ShortDynamicLink> shortLinkTask = FirebaseDynamicLinks.getInstance().createDynamicLink()
                .setLink(Uri.parse("https://goo.gl/4SBe3L?idiomID=" + idioms.getIdiomsUID() + "&idiomName=" + idioms.getIdiomName()))
                .setDynamicLinkDomain("x2tun.app.goo.gl")
                .setAndroidParameters(
                        new DynamicLink.AndroidParameters.Builder("app.phrases.idiom.admin.craftystudio.idiomsandphrases")
                                .build())
                .setSocialMetaTagParameters(
                        new DynamicLink.SocialMetaTagParameters.Builder()
                                .setTitle(idioms.getIdiomName())
                                .setDescription(idioms.getIdiomMeaning())
                                .build())
                .setGoogleAnalyticsParameters(
                        new DynamicLink.GoogleAnalyticsParameters.Builder()
                                .setSource("share")
                                .setMedium("social")
                                .setCampaign("example-promo")
                                .build())
                .buildShortDynamicLink()
                .addOnCompleteListener(new OnCompleteListener<ShortDynamicLink>() {
                    @Override
                    public void onComplete(@NonNull Task<ShortDynamicLink> task) {
                        if (task.isSuccessful()) {
                            Uri shortLink = task.getResult().getShortLink();

                          //  openShareDialog(shortLink, idioms);
                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });

    }


    private void openShareDialog(Idioms idioms) {


        try {
            /*
            Answers.getInstance().logCustom(new CustomEvent("Share link created").putCustomAttribute("Content Id", questions.getQuestionUID())
                    .putCustomAttribute("Shares", questions.getQuestionTopicName()));
            */
        } catch (Exception e) {
            e.printStackTrace();
        }
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");

        //sharingIntent.putExtra(Intent.EXTRA_STREAM, newsMetaInfo.getNewsImageLocalPath());

        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT,
                idioms.getIdiomName() + " -" +"\n" + idioms.getIdiomMeaning() + "\n\n" + idioms.getIdiomExample() + "\n\n" + "https://goo.gl/4SBe3L");
        mContext.startActivity(Intent.createChooser(sharingIntent, "Share Idioms via"));

        try {
            //Answers.getInstance().logCustom(new CustomEvent("Share question").putCustomAttribute("question", questions.getQuestionName()).putCustomAttribute("question topic", questions.getQuestionTopicName()));
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    @Override
    public int getCount() {
        return mIdiomList.size();

    }

    public void setOnItemCLickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }
}
