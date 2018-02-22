package utils;

import android.app.DownloadManager;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Created by Aisha on 2/14/2018.
 */

public class FireBaseHandler {

    private DatabaseReference mDatabaseRef;
    private FirebaseDatabase mFirebaseDatabase;


    public FireBaseHandler() {

        mFirebaseDatabase = FirebaseDatabase.getInstance();

    }

    public void uploadIdiom(final Idioms idiom, final OnQuestionlistener onQuestionlistener) {


        mDatabaseRef = mFirebaseDatabase.getReference().child("Idioms/");

        idiom.setIdiomsUID(mDatabaseRef.push().getKey());

        DatabaseReference mDatabaseRef1 = mFirebaseDatabase.getReference().child("Idioms/" + idiom.getIdiomsUID());


        mDatabaseRef1.setValue(idiom).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                onQuestionlistener.onQuestionDownLoad(idiom, true);
                onQuestionlistener.onQuestionUpload(true);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("Failed to Upload Story", e.getMessage());

                onQuestionlistener.onQuestionUpload(false);
                onQuestionlistener.onQuestionDownLoad(null, false);
            }
        });


    }

    //random no generate
    /*
    final int min = 1;
    final int max = 100;
    Random random = new Random();
    final int r = random.nextInt((max - min) + 1) + min;

*/
    public void downloadIdiomList(int limit, int randomNo, final OnQuestionlistener onQuestionlistener) {


        mDatabaseRef = mFirebaseDatabase.getReference().child("Idioms/");

        Query myref2 = mDatabaseRef.orderByChild("randomNo").startAt(randomNo).limitToFirst(limit);

        myref2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Idioms> questionsArrayList = new ArrayList<Idioms>();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    Idioms idioms = snapshot.getValue(Idioms.class);
                    if (idioms != null) {
                        idioms.setIdiomsUID(snapshot.getKey());
                    }
                    questionsArrayList.add(idioms);
                }

                // Collections.reverse(questionsArrayList);

                onQuestionlistener.onQuestionListDownLoad(questionsArrayList, true);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                onQuestionlistener.onQuestionListDownLoad(null, false);

            }
        });


    }

    public void downloadMoreIdiomList(int limit, int lastRandomNo, final OnQuestionlistener onQuestionlistener) {


        mDatabaseRef = mFirebaseDatabase.getReference().child("Idioms/");

        // Query myref2 = mDatabaseRef.orderByKey().limitToLast(limit).endAt(lastQuestionID);

        Query myref2 = mDatabaseRef.orderByChild("randomNo").startAt(lastRandomNo).limitToFirst(limit);

        myref2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Idioms> idiomsArrayList = new ArrayList<Idioms>();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Idioms idioms = snapshot.getValue(Idioms.class);
                    if (idioms != null) {
                        idioms.setIdiomsUID(snapshot.getKey());
                    }
                    idiomsArrayList.add(idioms);
                }

                idiomsArrayList.remove(idiomsArrayList.get(0));
                //  Collections.reverse(questionsArrayList);
                onQuestionlistener.onQuestionListDownLoad(idiomsArrayList, true);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                onQuestionlistener.onQuestionListDownLoad(null, false);

            }
        });


    }


    public void uploadDateName(final String date, final OnDatelistener onDatelistener) {


        mDatabaseRef = mFirebaseDatabase.getReference().child("Date/");


        DatabaseReference mDatabaseRef1 = mFirebaseDatabase.getReference().child("Date/" + mDatabaseRef.push().getKey());


        mDatabaseRef1.setValue(date).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                onDatelistener.onDateDownLoad(date, true);
                onDatelistener.onDateUpload(true);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("Failed to Upload Story", e.getMessage());

                onDatelistener.onDateUpload(false);
                onDatelistener.onDateDownLoad(null, false);
            }
        });


    }

    public void downloadDateList(int limit, final OnDatelistener onDatelistener) {


        mDatabaseRef = mFirebaseDatabase.getReference().child("Date/");

        Query myref2 = mDatabaseRef.orderByKey().limitToLast(limit);

        //databaseReferenceArrayList.add(myref2);

        ValueEventListener valueEventListener = myref2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<String> dateArrayList = new ArrayList<String>();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    String test = snapshot.getValue(String.class);
                    if (test != null) {
                        dateArrayList.add(test);

                    }
                }

                Collections.reverse(dateArrayList);

                onDatelistener.onDateListDownLoad(dateArrayList, true);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                onDatelistener.onDateListDownLoad(null, false);

            }
        });

        //valueEventListenerArrayList.add(valueEventListener);


    }


    public void downloadIdiomList(int limit, String dateName, final OnQuestionlistener onQuestionlistener) {


        mDatabaseRef = mFirebaseDatabase.getReference().child("Idioms/");

        Query myref2 = mDatabaseRef.orderByChild("idiomDateName").equalTo(dateName).limitToLast(limit);

        //databaseReferenceArrayList.add(myref2);


        ValueEventListener valueEventListener = myref2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Idioms> questionsArrayList = new ArrayList<Idioms>();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    Idioms idioms = snapshot.getValue(Idioms.class);
                    if (idioms != null) {

                        idioms.setIdiomsUID(snapshot.getKey());

                    }
                    questionsArrayList.add(idioms);
                }

                Collections.reverse(questionsArrayList);

                onQuestionlistener.onQuestionListDownLoad(questionsArrayList, true);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                onQuestionlistener.onQuestionListDownLoad(null, false);

            }
        });


    }


    public interface OnDatelistener {


        public void onDateDownLoad(String date, boolean isSuccessful);

        public void onDateListDownLoad(ArrayList<String> dateList, boolean isSuccessful);


        public void onDateUpload(boolean isSuccessful);
    }

    public interface OnQuestionlistener {


        public void onQuestionDownLoad(Idioms idioms, boolean isSuccessful);

        public void onQuestionListDownLoad(ArrayList<Idioms> idiomList, boolean isSuccessful);


        public void onQuestionUpload(boolean isSuccessful);
    }


}
