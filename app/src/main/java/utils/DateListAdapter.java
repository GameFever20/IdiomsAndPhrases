package utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import app.phrases.idiom.admin.craftystudio.idiomsandphrases.R;

/**
 * Created by Aisha on 2/15/2018.
 */

public class DateListAdapter extends ArrayAdapter<String> {


    ArrayList<String> mDateList;
    Context mContext;

    int mResourceID;

    ClickListener clickListener;

    public DateListAdapter(@NonNull Context context, int resource, ArrayList<String> dateList) {
        super(context, resource);
        this.mDateList = dateList;
        this.mContext = context;
        this.mResourceID = resource;

    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.custom_textview, null, false);
        }
        //getting the view
        //  View view = layoutInflater.inflate(mResourceID, null, false);


        TextView dateNameTextview = (TextView) convertView.findViewById(R.id.custom_textview);

        dateNameTextview.setText((String) mDateList.get(position));

        dateNameTextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListener.onItemCLickListener(view, position);
                //  Toast.makeText(mContext, "Item clicked " + mTopicList.get(position), Toast.LENGTH_SHORT).show();
            }
        });

        return convertView;
    }

    @Override
    public int getCount() {
        return mDateList.size();

    }

    public void setOnItemCLickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }
}
