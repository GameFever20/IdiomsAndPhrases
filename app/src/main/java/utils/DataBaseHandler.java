package utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by Aisha on 2/15/2018.
 */

public class DataBaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "idiomBookmark";

    // Contacts table name
    private static final String TABLE_BOOKMARK = "bookmark";

    // Contacts Table Columns names
    private static final String KEY_NAME = "idiom_name";
    private static final String KEY_IDIOM_UID = "idiom_uid";
    private static final String KEY_MEANING = "idiom_meaning";
    private static final String KEY_EXAMPLE = "idiom_example";
    private static final String KEY_CATEGORY = "idiom_category";
    private static final String KEY_UPLOAD_DATE = "idiom_upload_date";


    public DataBaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_BOOKMARK_TABLE = "CREATE TABLE " + TABLE_BOOKMARK + "("
                + KEY_IDIOM_UID + " TEXT PRIMARY KEY,"
                + KEY_NAME + " TEXT,"
                + KEY_MEANING + " TEXT,"
                + KEY_EXAMPLE + " TEXT,"
                + KEY_CATEGORY + " TEXT,"
                + KEY_UPLOAD_DATE + " INT8" + ")";
        sqLiteDatabase.execSQL(CREATE_BOOKMARK_TABLE);


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // Drop older table if existed
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOKMARK);

        // Create tables again
        onCreate(sqLiteDatabase);

    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    // Adding new bookmark
    public void addBookMark(Idioms idioms) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_IDIOM_UID, idioms.getIdiomsUID()); // Idiom UID
        values.put(KEY_NAME, idioms.getIdiomName()); // idiom Name
        values.put(KEY_MEANING, idioms.getIdiomMeaning()); // idiom meaning
        values.put(KEY_EXAMPLE, idioms.getIdiomExample()); // idiom example
        values.put(KEY_CATEGORY, idioms.getIdiomCategory()); // idiom category
        values.put(KEY_UPLOAD_DATE, idioms.getUploadDate()); // idiom upload date

        // Inserting Row
        db.insert(TABLE_BOOKMARK, null, values);
        db.close(); // Closing database connection
    }

    // Getting All Bookmarks
    public ArrayList<Idioms> getAllBookmarkedIdioms() {
        ArrayList<Idioms> bookmarkQuestionList = new ArrayList<Idioms>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_BOOKMARK;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Idioms idioms = new Idioms();
                idioms.setIdiomsUID(cursor.getString(0));
                idioms.setIdiomName(cursor.getString(1));
                idioms.setIdiomMeaning(cursor.getString(2));
                idioms.setIdiomExample(cursor.getString(3));
                idioms.setIdiomCategory(cursor.getString(4));
                idioms.setUploadDate(Long.parseLong(cursor.getString(5)));

                // Adding bookmark_question to list
                bookmarkQuestionList.add(idioms);

            } while (cursor.moveToNext());
        }

        // return bookmark_question list
        return bookmarkQuestionList;
    }

    // Deleting single idiom
    public void deleteBookmarkQuestion(Idioms idioms) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_BOOKMARK, KEY_IDIOM_UID + " = ?",
                new String[]{idioms.getIdiomsUID()});
        db.close();
    }

    // Getting idioms Count
    public int getIdiomsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_BOOKMARK;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }



}
