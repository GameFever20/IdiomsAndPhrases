package app.phrases.idiom.admin.craftystudio.idiomsandphrases;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import utils.Idioms;

/**
 * Created by Aisha on 2/15/2018.
 */

public class IdiomFragment extends Fragment implements View.OnClickListener{

    ProgressDialog progressDialog;
    static Context DisplayQuestionActivity;

    private OnFragmentInteractionListener mListener;

    Idioms idioms;

    TextView optionA;
    TextView optionB;
    TextView optionC;
    TextView optionD;

    CardView optionACardView;
    CardView optionBCardView;
    CardView optionCCardView;
    CardView optionDCardView;

    public static IdiomFragment newInstance(Idioms idioms, Context context) {

        DisplayQuestionActivity = context;
        IdiomFragment fragment = new IdiomFragment();
        Bundle args = new Bundle();
        args.putSerializable("Idiom", idioms);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.idioms = (Idioms) getArguments().getSerializable("Idiom");

           /* Answers.getInstance().logContentView(new ContentViewEvent()
                    .putContentName(story.getStoryTitle())
                    .putContentId(story.getStoryID())
            );
            */

        }


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_daily_quiz, container, false);
        //initializeView

        TextView idiomName = (TextView) view.findViewById(R.id.fragmentAptitudeQuiz_QuestionName_Textview);
        optionA = (TextView) view.findViewById(R.id.fragmentAptitudeQuiz_optionA_Textview);
        optionB = (TextView) view.findViewById(R.id.fragmentAptitudeQuiz_optionB_Textview);
        optionC = (TextView) view.findViewById(R.id.fragmentAptitudeQuiz_optionC_Textview);
        optionD = (TextView) view.findViewById(R.id.fragmentAptitudeQuiz_optionD_Textview);

        optionACardView = (CardView) view.findViewById(R.id.fragmentAptitudeQuiz_optionA_Cardview);
        optionBCardView = (CardView) view.findViewById(R.id.fragmentAptitudeQuiz_optionB_Cardview);
        optionCCardView = (CardView) view.findViewById(R.id.fragmentAptitudeQuiz_optionC_Cardview);
        optionDCardView = (CardView) view.findViewById(R.id.fragmentAptitudeQuiz_optionD_Cardview);


        idiomName.setText("Q. " + idioms.getIdiomName());
        optionA.setText(idioms.getOptionA());
        optionB.setText(idioms.getOptionB());
        optionC.setText(idioms.getOptionC());
        optionD.setText(idioms.getOptionD());


        optionACardView.setOnClickListener(this);
        optionBCardView.setOnClickListener(this);
        optionCCardView.setOnClickListener(this);
        optionDCardView.setOnClickListener(this);


        // getUserAnswers();

        //initializeNativeAd(view);


        return view;
    }

    @Override
    public void onClick(View view) {

        //normal topic questions and normal test series

        switch (view.getId()) {

            case R.id.fragmentAptitudeQuiz_optionA_Cardview:
                if (idioms.getOptionA().equalsIgnoreCase(idioms.getCorrectAnswer())) {
                    Toast.makeText(DisplayQuestionActivity, "Right Answer", Toast.LENGTH_SHORT).show();
                    optionACardView.setCardBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorGreen));


                } else {
                    // Toast.makeText(mainActivity, "Wrong Answer", Toast.LENGTH_SHORT).show();
                    optionACardView.setCardBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorRed));
                    getRightAnswer();
                }
                break;

            case R.id.fragmentAptitudeQuiz_optionB_Cardview:
                if (idioms.getOptionB().equalsIgnoreCase(idioms.getCorrectAnswer())) {
                    Toast.makeText(DisplayQuestionActivity, "Right Answer", Toast.LENGTH_SHORT).show();
                    optionBCardView.setCardBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorGreen));

                } else {
                    // Toast.makeText(mainActivity, "Wrong Answer", Toast.LENGTH_SHORT).show();
                    optionBCardView.setCardBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorRed));
                    getRightAnswer();
                }
                break;
            case R.id.fragmentAptitudeQuiz_optionC_Cardview:
                if (idioms.getOptionC().equalsIgnoreCase(idioms.getCorrectAnswer())) {
                    Toast.makeText(DisplayQuestionActivity, "Right Answer", Toast.LENGTH_SHORT).show();
                    optionCCardView.setCardBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorGreen));

                } else {
                    //  Toast.makeText(mainActivity, "Wrong Answer", Toast.LENGTH_SHORT).show();
                    optionCCardView.setCardBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorRed));
                    getRightAnswer();
                }
                break;
            case R.id.fragmentAptitudeQuiz_optionD_Cardview:
                if (idioms.getOptionD().equalsIgnoreCase(idioms.getCorrectAnswer())) {
                    Toast.makeText(DisplayQuestionActivity, "Right Answer", Toast.LENGTH_SHORT).show();
                    optionDCardView.setCardBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorGreen));

                } else {
                    // Toast.makeText(mainActivity, "Wrong Answer", Toast.LENGTH_SHORT).show();
                    optionDCardView.setCardBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorRed));
                    getRightAnswer();
                }
                break;

        }


        try {
            //  Answers.getInstance().logContentView(new ContentViewEvent().putContentId("question answer"));
        } catch (Exception e)

        {
            e.printStackTrace();
        }

    }
    private void getRightAnswer() {
        String correctANswer = idioms.getCorrectAnswer().trim();

        if (idioms.getOptionA().trim().equalsIgnoreCase(correctANswer)) {
            optionACardView.setCardBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorGreen));

        } else if (idioms.getOptionB().trim().equalsIgnoreCase(correctANswer)) {
            optionBCardView.setCardBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorGreen));

        } else if (idioms.getOptionC().trim().equalsIgnoreCase(correctANswer)) {
            optionCCardView.setCardBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorGreen));

        } else if (idioms.getOptionD().trim().equalsIgnoreCase(correctANswer)) {
            optionDCardView.setCardBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorGreen));
        }
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

}
